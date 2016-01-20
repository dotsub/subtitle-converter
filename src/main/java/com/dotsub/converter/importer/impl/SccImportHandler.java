package com.dotsub.converter.importer.impl;

import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.exception.FileImportException;
import com.dotsub.converter.importer.SubtitleImportHandler;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.SubtitleItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

/**
 * Imports SCC captions from files with Channel 1 control codes.
 *
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-19.
 */
public class SccImportHandler implements SubtitleImportHandler {

    private static final Log log = LogFactory.getLog(SccImportHandler.class);
    private static final Pattern pattern = Pattern.compile(".*(\\d+):(\\d+):(\\d+):(\\d+)(.*)");
    private static final String fileHeader = "Scenarist_SCC V1.0";

    private static Map<String, String> characterMap = new HashMap<>();
    private static Map<String, String> specialCharacterMap = new HashMap<>();
    private static Map<String, String> extendedCharacterMap = new HashMap<>();

    @Override
    public String getFormatName() {
        return fileHeader;
    }

    @Override
    public Configuration getDefaultConfiguration() {
        final Configuration configuration = new Configuration();
        //default file frame-rate is 29.97d fps
        //codes in the file will not match wall-clock time!
        configuration.setImportFps(29.97d);
        return configuration;
    }

    @Override
    public List<SubtitleItem> importFile(InputStream inputStream, Configuration configuration) throws IOException {
        List<SubtitleItem> items = new ArrayList<>();
        Boolean isPal = null;
        int palOffset = 0;
        Iterator lines = IOUtils.lineIterator(new InputStreamReader(inputStream, "UTF-8"));
        int lineNumber = 1;
        String line = (String) lines.next();
        while (lines.hasNext()) {
            //check file header to ensure this is a SCC file
            if (lineNumber == 1 && !line.equals(fileHeader)) {
                throw new FileFormatException("Not an SCC file");
            }
            else if (lineNumber == 1 && line.equals(fileHeader)) {
                line = (String) lines.next();
                lineNumber++;
                continue;
            }
            else if (line.trim().equals("")) {
                //skip blank lines
                line = (String) lines.next();
                lineNumber++;
                continue;
            }
            Matcher matcher = pattern.matcher(line);
            int start = getTimeFromLine(lineNumber, matcher, configuration);

            if (isPal == null) {
                if (start > 3599999) {
                    isPal = true;
                    palOffset = 3600000;
                }
                else {
                    isPal = false;
                }
            }
            start = start - palOffset;
            String lineContent = StringUtils.trim(matcher.group(5));
            StringBuilder lineText = new StringBuilder();

            String[] lineBytes = lineContent.split(" ");
            for (String s: lineBytes) {
                String startByte = s.substring(0,2);
                String endByte = s.substring(2,4);
                if (startByte.equals("91")) {
                    String text = specialCharacterMap.get(endByte);
                    if (text != null) {
                        lineText.append(text);
                    }
                }
                else if (startByte.equals("92")) {
                    String text = extendedCharacterMap.get(endByte);
                    if (text != null) {
                        lineText.append(text);
                    }
                }
                else if (characterMap.get(startByte) == null) {
                    //not in the map means this is a control code
                    //if we are in the middle of the line this control code is to move
                    //the cursor to a new line so we add a space
                    if (lineText.length() > 0 && lineText.charAt(lineText.length() - 1) != '\n') {
                        lineText.append("\n");
                    }
                }
                else {
                    String text = characterMap.get(startByte);
                    if (text != null) {
                        lineText.append(text);
                    }
                    text = characterMap.get(endByte);
                    if (text != null) {
                        lineText.append(text);
                    }
                }
            }
            //duration is set to 3 seconds. This way the last caption will have a run time.
            int duration = 3000;
            //gobble up the next line since it is empty if it is not there we are at the end
            if (lines.hasNext()) {
                lines.next();
                lineNumber++;
                if (lines.hasNext()) {
                    //next line if it exists is the time code
                    line = (String) lines.next();
                    if (StringUtils.isNotBlank(line.trim())) {
                        lineNumber++;
                        matcher = pattern.matcher(line);
                        int end = getTimeFromLine(lineNumber, matcher, configuration);
                        end = end - palOffset;
                        duration = end - start;
                    }
                }
            }
            if (!lineText.toString().equals("")) {
                //fix for 3Play files they encode >> and << when you should not.
                String finalLineText = lineText.toString()
                        .replaceAll("&gt;", ">")
                        .replaceAll("&lt;", "<");

                log.debug(format("Creating a new caption from: \t times:%d ms to %d ms \t content: %s \t",
                        start, duration, finalLineText));

                SubtitleItem item = new SubtitleItem(start, duration, finalLineText.trim());
                items.add(item);
            }
            if (!lines.hasNext()) {
                break;
            }
        }
        return items;
    }
    
    /*
    Based on a doc we received from a client:

    HEX    Example    Alternate    Description
    30    ®    See note 1    Registered mark symbol
    31    °        Degree sign
    32    1/2        1/2
    33    ¿        Inverse query
    34    TM    See note 1    Trademark symbol
    35    ¢        Cents sign
    36    £        Pounds Sterling sign
    37    ♪        Music note
    38    à    A    Lower-case a with grave accent
    39            Transparent space
    3A    è    E    Lower-case e with grave accent
    3B    â    A    Lower-case a with circumflex
    3C    ê    E    Lower-case e with circumflex
    3D    î    I    Lower-case i with circumflex
    3E    ô    O    Lower-case o with circumflex
    3F    û    U    Lower-case u with circumflex

    1 Note: The registered and trademark symbols are used to satisfy certain legal requirements.
    There are various legal ways in which these symbols may be drawn or displayed.
    For example, the trademark symbol may be drawn with the “T” next to the “M” or over the “M”.
    It is preferred that the trademark symbol be superscripted, i.e., XYZ TM.
    It is left to each individual manufacturer to interpret these symbols in any way that
    meets the legal needs of the user.

    Standard characters
    HEX    Example    Alternate    Description
    20            Standard space
    21    !        Exclamation mark
    22    “        Quotation mark
    23    #        Pounds (number) sign
    24    $        Dollar sign
    25    %        Percentage sign
    26    &        Ampersand
    27    '        Apostrophe
    28    (        Open parentheses
    29    )        Close parentheses
    2A    á    A    Lower-case a with acute accent
    2B    +        Plus sign
    2C    ,        Comma
    2D    −        Minus (hyphen) sign
    2E    .        Period
    2F    /        Slash
    30    0        Zero
    31    1        One
    32    2        Two
    33    3        Three
    34    4        Four
    35    5        Five
    36    6        Six
    37    7        Seven
    38    8        Eight
    39    9        Nine
    3A    :        Colon
    3B    ;        Semi-colon
    3C    <        Less than sign
    3D    =        Equal sign
    3E    >        Greater than sign
    3F    ?        Question mark
    40    @        At sign
    41    A        Upper-case A
    42    B        Upper-case B
    43    C        Upper-case C
    44    D        Upper-case D
    45    E        Upper-case E
    46    F        Upper-case F
    47    G        Upper-case G
    48    H        Upper-case H
    49    I        Upper-case I
    4A    J        Upper-case J
    4B    K        Upper-case K
    4C    L        Upper-case L
    4D    M        Upper-case M
    4E    N        Upper-case N
    4F    O        Upper-case O
    50    P        Upper-case P
    51    Q        Upper-case Q
    52    R        Upper-case R
    53    S        Upper-case S
    54    T        Upper-case T
    55    U        Upper-case U
    56    V        Upper-case V
    57    W        Upper-case W
    58    X        Upper-case X
    59    Y        Upper-case Y
    5A    Z        Upper-case Z
    5B    [        Open bracket
    5C    é    E    Lower-case e with acute accent
    5D    ]        Close bracket
    5E    í    I    Lower-case i with acute accent
    5F    ó    O    Lower-case o with acute accent
    60    ú    U    Lower-case u with acute accent
    61    a    A    Lower-case a
    62    b    B    Lower-case b
    63    c    C    Lower-case c
    64    d    D    Lower-case d
    65    e    E    Lower-case e
    66    f    F    Lower-case f
    67    g    G    Lower-case g
    68    h    H    Lower-case h
    69    i    I    Lower-case i
    6A    j    J    Lower-case j
    6B    k    K    Lower-case k
    6C    l    L    Lower-case l
    6D    m    M    Lower-case m
    6E    n    N    Lower-case n
    6F    o    O    Lower-case o
    70    p    P    Lower-case p
    71    q    Q    Lower-case q
    72    r    R    Lower-case r
    73    s    S    Lower-case s
    74    t    T    Lower-case t
    75    u    U    Lower-case u
    76    v    V    Lower-case v
    77    w    W    Lower-case w
    78    x    X    Lower-case x
    79    y    Y    Lower-case y
    7A    z    Z    Lower-case z
    7B    ç    C    Lower-case c with cedilla
    7C    ÷        Division sign
    7D    Ñ        Upper-case N with tilde
    7E    ñ    Ñ    Lower-case n with tilde
    7F    ▪        Solid block
     */

    static {

        //every character is mapped twice the second mapping is
        // 2 --> a
        // 3 --> b
        // 4 --> c
        // 5 --> d
        // 6 --> e
        // 7 --> f
        // This happens since every 'word pair' (ie: two character) must have an even parity for the high bit
        // having 'A' as 41 and c1 ensures you can always have an even parity bit
        characterMap.put("20", " ");
        characterMap.put("21", "!");
        characterMap.put("22", "\"");
        characterMap.put("23", "#");
        characterMap.put("24", "$");
        characterMap.put("25", "%");
        characterMap.put("26", "&");
        characterMap.put("27", "'");
        characterMap.put("28", "(");
        characterMap.put("29", ")");
        characterMap.put("2a", "á");
        characterMap.put("2b", "+");
        characterMap.put("2c", ",");
        characterMap.put("2d", "-");
        characterMap.put("2e", ".");
        characterMap.put("2f", "/");

        characterMap.put("a0", " ");
        characterMap.put("a1", "!");
        characterMap.put("a2", "\"");
        characterMap.put("a3", "#");
        characterMap.put("a4", "$");
        characterMap.put("a5", "%");
        characterMap.put("a6", "&");
        characterMap.put("a7", "'");
        characterMap.put("a8", "(");
        characterMap.put("a9", ")");
        characterMap.put("aa", "á");
        characterMap.put("ab", "+");
        characterMap.put("ac", ",");
        characterMap.put("ad", "-");
        characterMap.put("ae", ".");
        characterMap.put("af", "/");

        characterMap.put("30", "0");
        characterMap.put("31", "1");
        characterMap.put("32", "2");
        characterMap.put("33", "3");
        characterMap.put("34", "4");
        characterMap.put("35", "5");
        characterMap.put("36", "6");
        characterMap.put("37", "7");
        characterMap.put("38", "8");
        characterMap.put("39", "9");
        characterMap.put("3a", ":");
        characterMap.put("3b", ";");
        characterMap.put("3c", "<");
        characterMap.put("3d", "=");
        characterMap.put("3e", ">");
        characterMap.put("3f", "?");

        characterMap.put("b0", "0");
        characterMap.put("b1", "1");
        characterMap.put("b2", "2");
        characterMap.put("b3", "3");
        characterMap.put("b4", "4");
        characterMap.put("b5", "5");
        characterMap.put("b6", "6");
        characterMap.put("b7", "7");
        characterMap.put("b8", "8");
        characterMap.put("b9", "9");
        characterMap.put("ba", ":");
        characterMap.put("bb", ";");
        characterMap.put("bc", "<");
        characterMap.put("bd", "=");
        characterMap.put("be", ">");
        characterMap.put("bf", "?");

        characterMap.put("40", "@");
        characterMap.put("41", "A");
        characterMap.put("42", "B");
        characterMap.put("43", "C");
        characterMap.put("44", "D");
        characterMap.put("45", "E");
        characterMap.put("46", "F");
        characterMap.put("47", "G");
        characterMap.put("48", "H");
        characterMap.put("49", "I");
        characterMap.put("4a", "J");
        characterMap.put("4b", "K");
        characterMap.put("4c", "L");
        characterMap.put("4d", "M");
        characterMap.put("4e", "N");
        characterMap.put("4f", "O");

        characterMap.put("c0", "@");
        characterMap.put("c1", "A");
        characterMap.put("c2", "B");
        characterMap.put("c3", "C");
        characterMap.put("c4", "D");
        characterMap.put("c5", "E");
        characterMap.put("c6", "F");
        characterMap.put("c7", "G");
        characterMap.put("c8", "H");
        characterMap.put("c9", "I");
        characterMap.put("ca", "J");
        characterMap.put("cb", "K");
        characterMap.put("cc", "L");
        characterMap.put("cd", "M");
        characterMap.put("ce", "N");
        characterMap.put("cf", "O");

        characterMap.put("50", "P");
        characterMap.put("51", "Q");
        characterMap.put("52", "R");
        characterMap.put("53", "S");
        characterMap.put("54", "T");
        characterMap.put("55", "U");
        characterMap.put("56", "V");
        characterMap.put("57", "W");
        characterMap.put("58", "X");
        characterMap.put("59", "Y");
        characterMap.put("5a", "Z");
        characterMap.put("5b", "[");
        characterMap.put("5c", "é");
        characterMap.put("5d", "]");
        characterMap.put("5e", "í");
        characterMap.put("5f", "ó");

        characterMap.put("d0", "P");
        characterMap.put("d1", "Q");
        characterMap.put("d2", "R");
        characterMap.put("d3", "S");
        characterMap.put("d4", "T");
        characterMap.put("d5", "U");
        characterMap.put("d6", "V");
        characterMap.put("d7", "W");
        characterMap.put("d8", "X");
        characterMap.put("d9", "Y");
        characterMap.put("da", "Z");
        characterMap.put("db", "[");
        characterMap.put("dc", "é");
        characterMap.put("dd", "]");
        characterMap.put("de", "í");
        characterMap.put("df", "ó");

        characterMap.put("60", "ú");
        characterMap.put("61", "a");
        characterMap.put("62", "b");
        characterMap.put("63", "c");
        characterMap.put("64", "d");
        characterMap.put("65", "e");
        characterMap.put("66", "f");
        characterMap.put("67", "g");
        characterMap.put("68", "h");
        characterMap.put("69", "i");
        characterMap.put("6a", "j");
        characterMap.put("6b", "k");
        characterMap.put("6c", "l");
        characterMap.put("6d", "m");
        characterMap.put("6e", "n");
        characterMap.put("6f", "o");

        characterMap.put("e0", "ú");
        characterMap.put("e1", "a");
        characterMap.put("e2", "b");
        characterMap.put("e3", "c");
        characterMap.put("e4", "d");
        characterMap.put("e5", "e");
        characterMap.put("e6", "f");
        characterMap.put("e7", "g");
        characterMap.put("e8", "h");
        characterMap.put("e9", "i");
        characterMap.put("ea", "j");
        characterMap.put("eb", "k");
        characterMap.put("ec", "l");
        characterMap.put("ed", "m");
        characterMap.put("ee", "n");
        characterMap.put("ef", "o");

        characterMap.put("70", "p");
        characterMap.put("71", "q");
        characterMap.put("72", "r");
        characterMap.put("73", "s");
        characterMap.put("74", "t");
        characterMap.put("75", "u");
        characterMap.put("76", "v");
        characterMap.put("77", "w");
        characterMap.put("78", "x");
        characterMap.put("79", "y");
        characterMap.put("7a", "z");
        characterMap.put("7b", "ç");
        characterMap.put("7c", "÷");
        characterMap.put("7d", "Ñ");
        characterMap.put("7e", "ñ");
        characterMap.put("7f", "▪");

        characterMap.put("f0", "p");
        characterMap.put("f1", "q");
        characterMap.put("f2", "r");
        characterMap.put("f3", "s");
        characterMap.put("f4", "t");
        characterMap.put("f5", "u");
        characterMap.put("f6", "v");
        characterMap.put("f7", "w");
        characterMap.put("f8", "x");
        characterMap.put("f9", "y");
        characterMap.put("fa", "z");
        characterMap.put("fb", "ç");
        characterMap.put("fc", "÷");
        characterMap.put("fd", "Ñ");
        characterMap.put("fe", "ñ");
        characterMap.put("ff", "▪");

        //all prefixed by 91
        specialCharacterMap.put("30", "®");
        specialCharacterMap.put("31", "°");
        specialCharacterMap.put("32", "½");
        specialCharacterMap.put("33", "¿");
        specialCharacterMap.put("34", "™");
        specialCharacterMap.put("35", "¢");
        specialCharacterMap.put("36", "£");
        specialCharacterMap.put("37", "♪");
        specialCharacterMap.put("38", "à");
        specialCharacterMap.put("39", "");
        specialCharacterMap.put("3a", "è");
        specialCharacterMap.put("3b", "â");
        specialCharacterMap.put("3c", "ê");
        specialCharacterMap.put("3d", "î");
        specialCharacterMap.put("3e", "ô");
        specialCharacterMap.put("3f", "û");

        specialCharacterMap.put("b0", "®");
        specialCharacterMap.put("b1", "°");
        specialCharacterMap.put("b2", "½");
        specialCharacterMap.put("b3", "¿");
        specialCharacterMap.put("b4", "™");
        specialCharacterMap.put("b5", "¢");
        specialCharacterMap.put("b6", "£");
        specialCharacterMap.put("b7", "♪");
        specialCharacterMap.put("b8", "à");
        specialCharacterMap.put("b9", "");
        specialCharacterMap.put("ba", "è");
        specialCharacterMap.put("bb", "â");
        specialCharacterMap.put("bc", "ê");
        specialCharacterMap.put("bd", "î");
        specialCharacterMap.put("be", "ô");
        specialCharacterMap.put("bf", "û");

        //mapping for extended prefixed by 92
        extendedCharacterMap.put("30", "Á");
        extendedCharacterMap.put("31", "É");
        extendedCharacterMap.put("32", "Ó");
        extendedCharacterMap.put("33", "Ú");
        extendedCharacterMap.put("34", "Ü");
        extendedCharacterMap.put("35", "ü");
        extendedCharacterMap.put("36", "'");
        extendedCharacterMap.put("37", "i");
        extendedCharacterMap.put("38", "*");
        extendedCharacterMap.put("39", "'");
        extendedCharacterMap.put("3a", "_");
        extendedCharacterMap.put("3b", "©");
        extendedCharacterMap.put("3e", "\"");
        extendedCharacterMap.put("3f", "\"");

        extendedCharacterMap.put("b0", "Á");
        extendedCharacterMap.put("b1", "É");
        extendedCharacterMap.put("b2", "Ó");
        extendedCharacterMap.put("b3", "Ú");
        extendedCharacterMap.put("b4", "Ü");
        extendedCharacterMap.put("b5", "ü");
        extendedCharacterMap.put("b6", "'");
        extendedCharacterMap.put("b7", "i");
        extendedCharacterMap.put("b8", "*");
        extendedCharacterMap.put("b9", "'");
        extendedCharacterMap.put("ba", "_");
        extendedCharacterMap.put("bb", "©");
        extendedCharacterMap.put("be", "\"");
        extendedCharacterMap.put("bf", "\"");

    }

    private int getTimeFromLine(int lineNumber, Matcher matcher, Configuration configuration) {
        if (!matcher.matches()) {
            throw new FileImportException(
                    format("File does not match expected scc time format on line %d", lineNumber));
        }
        int start = 0;
        //TODO support drop framed timing.
        start += (Integer.parseInt(matcher.group(1)) * 108_000); //frames in an hour
        start += Integer.parseInt(matcher.group(2)) * 1_800; //frames per minute
        start += Integer.parseInt(matcher.group(3)) * 30; //frames per second (0-29)
        start += Integer.parseInt(matcher.group(4));
        //this is the number of frames that have passed. Convert this to time
        Double time = start / configuration.getImportFps();
        //we want milliseconds not seconds
        time = time * 1000;
        //we can just cut off the decimals, we only time to the thousandth of a millisecond.
        return time.intValue();
    }
}
