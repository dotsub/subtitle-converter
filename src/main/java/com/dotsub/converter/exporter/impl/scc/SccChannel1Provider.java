package com.dotsub.converter.exporter.impl.scc;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-21.
 */
public class SccChannel1Provider implements EncodingProvider {

    protected byte specialCharsHighBit = (byte) 0x91;
    protected int frenchSpanishHighBit = 0x92;
    protected int germanDanishHighBit = 0x13;

    /**
     * Creates a new instance of a SccChannel2 encoding provider.
     */
    public byte[] encodeChar(Character character) {
        switch (character) {
            case ' ':
                return new byte[]{(byte) 0x20};
            case '!':
                return new byte[]{(byte) 0xa1};
            case '"':
                return new byte[]{(byte) 0xa2};
            case '#':
                return new byte[]{(byte) 0x23};
            case '$':
                return new byte[]{(byte) 0xa4};
            case '%':
                return new byte[]{(byte) 0x25};
            case '&':
                return new byte[]{(byte) 0x26};
            case '\'':
                return new byte[]{(byte) 0xa7};
            case '(':
                return new byte[]{(byte) 0xa8};
            case ')':
                return new byte[]{(byte) 0x29};
            case 'á':
                return new byte[]{(byte) 0x2A};
            case '+':
                return new byte[]{(byte) 0xab};
            case ',':
                return new byte[]{(byte) 0x2c};
            case '-':
                return new byte[]{(byte) 0xad};
            case '.':
                return new byte[]{(byte) 0xae};
            case '/':
                return new byte[]{(byte) 0x2f};
            case '0':
                return new byte[]{(byte) 0xb0};
            case '1':
                return new byte[]{(byte) 0x31};
            case '2':
                return new byte[]{(byte) 0x32};
            case '3':
                return new byte[]{(byte) 0xb3};
            case '4':
                return new byte[]{(byte) 0x34};
            case '5':
                return new byte[]{(byte) 0xb5};
            case '6':
                return new byte[]{(byte) 0xb6};
            case '7':
                return new byte[]{(byte) 0x37};
            case '8':
                return new byte[]{(byte) 0x38};
            case '9':
                return new byte[]{(byte) 0xb9};
            case ':':
                return new byte[]{(byte) 0xba};
            case ';':
                return new byte[]{(byte) 0x3b};
            case '<':
                return new byte[]{(byte) 0xbc};
            case '=':
                return new byte[]{(byte) 0x3d};
            case '>':
                return new byte[]{(byte) 0x3e};
            case '?':
                return new byte[]{(byte) 0xbf};
            case '@':
                return new byte[]{(byte) 0x40};
            case 'A':
                return new byte[]{(byte) 0xc1};
            case 'B':
                return new byte[]{(byte) 0xc2};
            case 'C':
                return new byte[]{(byte) 0x43};
            case 'D':
                return new byte[]{(byte) 0xc4};
            case 'E':
                return new byte[]{(byte) 0x45};
            case 'F':
                return new byte[]{(byte) 0x46};
            case 'G':
                return new byte[]{(byte) 0xc7};
            case 'H':
                return new byte[]{(byte) 0xc8};
            case 'I':
                return new byte[]{(byte) 0x49};
            case 'J':
                return new byte[]{(byte) 0x4a};
            case 'K':
                return new byte[]{(byte) 0xcb};
            case 'L':
                return new byte[]{(byte) 0x4c};
            case 'M':
                return new byte[]{(byte) 0xcd};
            case 'N':
                return new byte[]{(byte) 0xce};
            case 'O':
                return new byte[]{(byte) 0x4f};
            case 'P':
                return new byte[]{(byte) 0xd0};
            case 'Q':
                return new byte[]{(byte) 0x51};
            case 'R':
                return new byte[]{(byte) 0x52};
            case 'S':
                return new byte[]{(byte) 0xd3};
            case 'T':
                return new byte[]{(byte) 0x54};
            case 'U':
                return new byte[]{(byte) 0xd5};
            case 'V':
                return new byte[]{(byte) 0xd6};
            case 'W':
                return new byte[]{(byte) 0x57};
            case 'X':
                return new byte[]{(byte) 0x58};
            case 'Y':
                return new byte[]{(byte) 0xd9};
            case 'Z':
                return new byte[]{(byte) 0xda};
            case '[':
                return new byte[]{(byte) 0x5B};
            case 'é':
                return new byte[]{(byte) 0xdc};
            case ']':
                return new byte[]{(byte) 0x5d};
            case 'í':
                return new byte[]{(byte) 0x5e};
            case 'ó':
                return new byte[]{(byte) 0xdF};
            case 'ú':
                return new byte[]{(byte) 0xe0};
            case 'a':
                return new byte[]{(byte) 0x61};
            case 'b':
                return new byte[]{(byte) 0x62};
            case 'c':
                return new byte[]{(byte) 0xe3};
            case 'd':
                return new byte[]{(byte) 0x64};
            case 'e':
                return new byte[]{(byte) 0xe5};
            case 'f':
                return new byte[]{(byte) 0xe6};
            case 'g':
                return new byte[]{(byte) 0x67};
            case 'h':
                return new byte[]{(byte) 0x68};
            case 'i':
                return new byte[]{(byte) 0xe9};
            case 'j':
                return new byte[]{(byte) 0xea};
            case 'k':
                return new byte[]{(byte) 0x6b};
            case 'l':
                return new byte[]{(byte) 0xec};
            case 'm':
                return new byte[]{(byte) 0x6d};
            case 'n':
                return new byte[]{(byte) 0x6e};
            case 'o':
                return new byte[]{(byte) 0xef};
            case 'p':
                return new byte[]{(byte) 0x70};
            case 'q':
                return new byte[]{(byte) 0xf1};
            case 'r':
                return new byte[]{(byte) 0xf2};
            case 's':
                return new byte[]{(byte) 0x73};
            case 't':
                return new byte[]{(byte) 0xf4};
            case 'u':
                return new byte[]{(byte) 0x75};
            case 'v':
                return new byte[]{(byte) 0x76};
            case 'w':
                return new byte[]{(byte) 0xf7};
            case 'x':
                return new byte[]{(byte) 0xf8};
            case 'y':
                return new byte[]{(byte) 0x79};
            case 'z':
                return new byte[]{(byte) 0x7a};
            case 'ç':
                return new byte[]{(byte) 0xfb};
            case '÷':
                return new byte[]{(byte) 0x7c};
            case 'Ñ':
                return new byte[]{(byte) 0xfd};
            case 'ñ':
                return new byte[]{(byte) 0xfe};

            //Special Characters
            case '®':
                return new byte[]{specialCharsHighBit, (byte) 0xb0};
            case '°':
                return new byte[]{specialCharsHighBit, (byte) 0x31};
            case '½':
                return new byte[]{specialCharsHighBit, (byte) 0x32};
            case '¿':
                return new byte[]{specialCharsHighBit, (byte) 0xb3};
            case '™':
                return new byte[]{specialCharsHighBit, (byte) 0x34};
            case '¢':
                return new byte[]{specialCharsHighBit, (byte) 0xb5};
            case '£':
                return new byte[]{specialCharsHighBit, (byte) 0xb6};
            case '♪':
                return new byte[]{specialCharsHighBit, (byte) 0x37};
            case 'à':
                return new byte[]{specialCharsHighBit, (byte) 0x38};
//            case ' ':
//                return new byte[]{(byte) 0x11, (byte) 0x39};
            case 'è':
                return new byte[]{specialCharsHighBit, (byte) 0xba};
            case 'â':
                return new byte[]{specialCharsHighBit, (byte) 0x3b};
            case 'ê':
                return new byte[]{specialCharsHighBit, (byte) 0xbc};
            case 'î':
                return new byte[]{specialCharsHighBit, (byte) 0x3d};
            case 'ô':
                return new byte[]{specialCharsHighBit, (byte) 0x3e};
            case 'û':
                return new byte[]{specialCharsHighBit, (byte) 0xbf};
            //EXTENDED French/Spanish
            case 'Á':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x20};
            case 'É':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xa1};
            case 'Ó':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xa2};
            case 'Ú':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x23};
            case 'Ü':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xa4};
            case 'ü':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x25};
            case '‘':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x26};
            case '¡':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xa7};
            case '*':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xa8};
            case '’':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x29};
            case '—':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x2a};
            case '©':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xab};
            case '℠':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x2c};
            case '•':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x2d};
            case '“':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xae};
            case '”':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x2f};
            case 'À':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xb0};
            case 'Â':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x31};
            case 'Ç':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x32};
            case 'È':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xb3};
            case 'Ê':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x34};
            case 'Ë':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xb5};
            case 'ë':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xb6};
            case 'Î':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x37};
            case 'Ï':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x38};
            case 'ï':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xb9};
            case 'Ô':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xba};
            case 'Ù':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x3b};
            case 'ù':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xbc};
            case 'Û':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x3d};
            case '«':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0x3e};
            case '»':
                return new byte[]{(byte) frenchSpanishHighBit, (byte) 0xbf};
            //EXTENDED Portuguese/German/Danish
            case 'Ã':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x20};
            case 'ã':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xa1};
            case 'Í':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xa2};
            case 'Ì':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x23};
            case 'ì':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xa4};
            case 'Ò':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x25};
            case 'ò':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x26};
            case 'Õ':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xa7};
            case 'õ':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xa8};
            case '{':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x29};
            case '}':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x2a};
            case '\\':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xab};
            case '^':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x2c};
            case '_':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x2d};
            case '¦':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xae};
            case '~':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x2f};
            case 'Ä':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xb0};
            case 'ä':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x31};
            case 'Ö':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x32};
            case 'ö':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xb3};
            case 'ß':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x34};
            case '¥':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xb5};
            case '¤':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xb6};
            case '|':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x37};
            case 'Å':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x38};
            case 'å':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xb9};
            case 'Ø':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xba};
            case 'ø':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x3b};
            case '┌':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xbc};
            case '┐':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x3d};
            case '┕':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0x3e};
            case '┘':
                return new byte[]{(byte) germanDanishHighBit, (byte) 0xbf};

            default:
                //all unknowns return solid block
                return new byte[] {(byte)0x7f};
        }
    }
}
