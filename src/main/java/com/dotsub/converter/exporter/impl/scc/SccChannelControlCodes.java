package com.dotsub.converter.exporter.impl.scc;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-21.
 */
@SuppressFBWarnings("MS_PKGPROTECT") //I know the array is mutable.
public class SccChannelControlCodes {

    //default control bytes for channels 1-4.
    public static final byte[] channel1 = {(byte) 0x94, (byte) 0x91, (byte) 0x92, (byte) 0x15,
            (byte) 0x16, (byte) 0x97, (byte) 0x10, (byte) 0x13, (byte) 0x94, (byte) 0x97};
    public static final byte[] channel2 = {(byte) 0x1c, (byte) 0x19, (byte) 0x1a, (byte) 0x9d,
            (byte) 0x9e, (byte) 0x1f, (byte) 0x98, (byte) 0x9b, (byte) 0x1c, (byte) 0x1f};
    public static final byte[] channel3 = {(byte) 0x15, (byte) 0x91, (byte) 0x92, (byte) 0x15,
            (byte) 0x16, (byte) 0x97, (byte) 0x10, (byte) 0x13, (byte) 0x94, (byte) 0x97};
    public static final byte[] channel4 = {(byte) 0x9d, (byte) 0x19, (byte) 0x1a, (byte) 0x9d,
            (byte) 0x9e, (byte) 0x1f, (byte) 0x98, (byte) 0x9b, (byte) 0x1c, (byte) 0x1f};

    protected byte controlByte;
    protected byte positionByte1;
    protected byte positionByte2;
    protected byte positionByte3;
    protected byte positionByte4;
    protected byte positionByte5;
    protected byte positionByte6;
    protected byte positionByte7;
    protected byte positionByte8;
    protected byte offsetBit;
    protected byte[][][] position;

    /**
     * Expects the high bytes for the desired channel.
     * @param controlBytes the control high bytes that are uses for commands and positions. Defaults are above.
     */
    public SccChannelControlCodes(byte[] controlBytes) {
        this.controlByte = controlBytes[0];
        this.positionByte1 = controlBytes[1];
        this.positionByte2 = controlBytes[2];
        this.positionByte3 = controlBytes[3];
        this.positionByte4 = controlBytes[4];
        this.positionByte5 = controlBytes[5];
        this.positionByte6 = controlBytes[6];
        this.positionByte7 = controlBytes[7];
        this.positionByte8 = controlBytes[8];
        this.offsetBit = controlBytes[9];

        //multi-dimension array of positions
        //15 rows, 32 columns, each byte array then contains commands for that position
        position = new byte[][][]{
                //Row 0
                new byte[][]{
                        //0,0
                        new byte[] {positionByte1, (byte) 0x40},
                        new byte[] {positionByte1, (byte) 0x40, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0x40, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0x40, offsetBit, (byte) 0x23},
                        //0,4
                        new byte[] {positionByte1, (byte) 0x52},
                        new byte[] {positionByte1, (byte) 0x52, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0x52, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0x52, offsetBit, (byte) 0x23},
                        //0,8
                        new byte[] {positionByte1, (byte) 0x54},
                        new byte[] {positionByte1, (byte) 0x54, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0x54, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0x54, offsetBit, (byte) 0x23},
                        //0,12
                        new byte[] {positionByte1, (byte) 0xd6},
                        new byte[] {positionByte1, (byte) 0xd6, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0xd6, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0xd6, offsetBit, (byte) 0x23},
                        //0,16
                        new byte[] {positionByte1, (byte) 0x58},
                        new byte[] {positionByte1, (byte) 0x58, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0x58, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0x58, offsetBit, (byte) 0x23},
                        //0,20
                        new byte[] {positionByte1, (byte) 0xda},
                        new byte[] {positionByte1, (byte) 0xda, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0xda, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0xda, offsetBit, (byte) 0x23},
                        //0,24
                        new byte[] {positionByte1, (byte) 0xdc},
                        new byte[] {positionByte1, (byte) 0xdc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0xdc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0xdc, offsetBit, (byte) 0x23},
                        //0,28
                        new byte[] {positionByte1, (byte) 0x5e},
                        new byte[] {positionByte1, (byte) 0x5e, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0x5e, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0x5e, offsetBit, (byte) 0x23},
                },
                //Row 1
                new byte[][]{
                        //1,0
                        new byte[] {positionByte1, (byte) 0xe0},
                        new byte[] {positionByte1, (byte) 0xe0, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0xe0, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0xe0, offsetBit, (byte) 0x23},
                        //1,4
                        new byte[] {positionByte1, (byte) 0xf2},
                        new byte[] {positionByte1, (byte) 0xf2, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0xf2, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0xf2, offsetBit, (byte) 0x23},
                        //1,8
                        new byte[] {positionByte1, (byte) 0xf4},
                        new byte[] {positionByte1, (byte) 0xf4, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0xf4, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0xf4, offsetBit, (byte) 0x23},
                        //1,12
                        new byte[] {positionByte1, (byte) 0x76},
                        new byte[] {positionByte1, (byte) 0x76, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0x76, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0x76, offsetBit, (byte) 0x23},
                        //1,16
                        new byte[] {positionByte1, (byte) 0xf8},
                        new byte[] {positionByte1, (byte) 0xf8, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0xf8, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0xf8, offsetBit, (byte) 0x23},
                        //1,20
                        new byte[] {positionByte1, (byte) 0x7a},
                        new byte[] {positionByte1, (byte) 0x7a, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0x7a, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0x7a, offsetBit, (byte) 0x23},
                        //1,24
                        new byte[] {positionByte1, (byte) 0xfc},
                        new byte[] {positionByte1, (byte) 0xfc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0xfc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0xfc, offsetBit, (byte) 0x23},
                        //1,28
                        new byte[] {positionByte1, (byte) 0xfe},
                        new byte[] {positionByte1, (byte) 0xfe, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte1, (byte) 0xfe, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte1, (byte) 0xfe, offsetBit, (byte) 0x23},
                },
                //Row 2
                new byte[][]{
                        //2,0
                        new byte[] {positionByte2, (byte) 0x40},
                        new byte[] {positionByte2, (byte) 0x40, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0x40, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0x40, offsetBit, (byte) 0x23},
                        //2,4
                        new byte[] {positionByte2, (byte) 0x52},
                        new byte[] {positionByte2, (byte) 0x52, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0x52, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0x52, offsetBit, (byte) 0x23},
                        //2,8
                        new byte[] {positionByte2, (byte) 0x54},
                        new byte[] {positionByte2, (byte) 0x54, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0x54, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0x54, offsetBit, (byte) 0x23},
                        //2,12
                        new byte[] {positionByte2, (byte) 0xd6},
                        new byte[] {positionByte2, (byte) 0xd6, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0xd6, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0xd6, offsetBit, (byte) 0x23},
                        //2,16
                        new byte[] {positionByte2, (byte) 0x58},
                        new byte[] {positionByte2, (byte) 0x58, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0x58, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0x58, offsetBit, (byte) 0x23},
                        //2,20
                        new byte[] {positionByte2, (byte) 0xda},
                        new byte[] {positionByte2, (byte) 0xda, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0xda, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0xda, offsetBit, (byte) 0x23},
                        //2,24
                        new byte[] {positionByte2, (byte) 0xdc},
                        new byte[] {positionByte2, (byte) 0xdc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0xdc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0xdc, offsetBit, (byte) 0x23},
                        //2,28
                        new byte[] {positionByte2, (byte) 0x5e},
                        new byte[] {positionByte2, (byte) 0x5e, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0x5e, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0x5e, offsetBit, (byte) 0x23},
                },
                //Row 3
                new byte[][]{
                        //3,0
                        new byte[] {positionByte2, (byte) 0xe0},
                        new byte[] {positionByte2, (byte) 0xe0, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0xe0, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0xe0, offsetBit, (byte) 0x23},
                        //3,4
                        new byte[] {positionByte2, (byte) 0xf2},
                        new byte[] {positionByte2, (byte) 0xf2, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0xf2, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0xf2, offsetBit, (byte) 0x23},
                        //3,8
                        new byte[] {positionByte2, (byte) 0xf4},
                        new byte[] {positionByte2, (byte) 0xf4, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0xf4, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0xf4, offsetBit, (byte) 0x23},
                        //3,12
                        new byte[] {positionByte2, (byte) 0x76},
                        new byte[] {positionByte2, (byte) 0x76, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0x76, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0x76, offsetBit, (byte) 0x23},
                        //3,16
                        new byte[] {positionByte2, (byte) 0xf8},
                        new byte[] {positionByte2, (byte) 0xf8, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0xf8, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0xf8, offsetBit, (byte) 0x23},
                        //3,20
                        new byte[] {positionByte2, (byte) 0x7a},
                        new byte[] {positionByte2, (byte) 0x7a, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0x7a, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0x7a, offsetBit, (byte) 0x23},
                        //3,24
                        new byte[] {positionByte2, (byte) 0xfc},
                        new byte[] {positionByte2, (byte) 0xfc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0xfc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0xfc, offsetBit, (byte) 0x23},
                        //3,28
                        new byte[] {positionByte2, (byte) 0xfe},
                        new byte[] {positionByte2, (byte) 0xfe, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte2, (byte) 0xfe, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte2, (byte) 0xfe, offsetBit, (byte) 0x23},
                },

                //Row 4
                new byte[][]{
                        //4,0
                        new byte[] {positionByte3, (byte) 0x40},
                        new byte[] {positionByte3, (byte) 0x40, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0x40, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0x40, offsetBit, (byte) 0x23},
                        //4,4
                        new byte[] {positionByte3, (byte) 0x52},
                        new byte[] {positionByte3, (byte) 0x52, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0x52, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0x52, offsetBit, (byte) 0x23},
                        //4,8
                        new byte[] {positionByte3, (byte) 0x54},
                        new byte[] {positionByte3, (byte) 0x54, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0x54, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0x54, offsetBit, (byte) 0x23},
                        //4,12
                        new byte[] {positionByte3, (byte) 0xd6},
                        new byte[] {positionByte3, (byte) 0xd6, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0xd6, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0xd6, offsetBit, (byte) 0x23},
                        //4,16
                        new byte[] {positionByte3, (byte) 0x58},
                        new byte[] {positionByte3, (byte) 0x58, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0x58, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0x58, offsetBit, (byte) 0x23},
                        //4,20
                        new byte[] {positionByte3, (byte) 0xda},
                        new byte[] {positionByte3, (byte) 0xda, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0xda, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0xda, offsetBit, (byte) 0x23},
                        //4,24
                        new byte[] {positionByte3, (byte) 0xdc},
                        new byte[] {positionByte3, (byte) 0xdc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0xdc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0xdc, offsetBit, (byte) 0x23},
                        //4,28
                        new byte[] {positionByte3, (byte) 0x5e},
                        new byte[] {positionByte3, (byte) 0x5e, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0x5e, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0x5e, offsetBit, (byte) 0x23},
                },
                //Row 5
                new byte[][]{
                        //5,0
                        new byte[] {positionByte3, (byte) 0xe0},
                        new byte[] {positionByte3, (byte) 0xe0, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0xe0, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0xe0, offsetBit, (byte) 0x23},
                        //5,4
                        new byte[] {positionByte3, (byte) 0xf2},
                        new byte[] {positionByte3, (byte) 0xf2, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0xf2, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0xf2, offsetBit, (byte) 0x23},
                        //5,8
                        new byte[] {positionByte3, (byte) 0xf4},
                        new byte[] {positionByte3, (byte) 0xf4, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0xf4, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0xf4, offsetBit, (byte) 0x23},
                        //5,12
                        new byte[] {positionByte3, (byte) 0x76},
                        new byte[] {positionByte3, (byte) 0x76, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0x76, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0x76, offsetBit, (byte) 0x23},
                        //5,16
                        new byte[] {positionByte3, (byte) 0xf8},
                        new byte[] {positionByte3, (byte) 0xf8, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0xf8, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0xf8, offsetBit, (byte) 0x23},
                        //5,20
                        new byte[] {positionByte3, (byte) 0x7a},
                        new byte[] {positionByte3, (byte) 0x7a, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0x7a, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0x7a, offsetBit, (byte) 0x23},
                        //5,24
                        new byte[] {positionByte3, (byte) 0xfc},
                        new byte[] {positionByte3, (byte) 0xfc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0xfc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0xfc, offsetBit, (byte) 0x23},
                        //5,28
                        new byte[] {positionByte3, (byte) 0xfe},
                        new byte[] {positionByte3, (byte) 0xfe, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte3, (byte) 0xfe, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte3, (byte) 0xfe, offsetBit, (byte) 0x23},
                },
                //Row 6
                new byte[][]{
                        //6,0
                        new byte[] {positionByte4, (byte) 0x40},
                        new byte[] {positionByte4, (byte) 0x40, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0x40, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0x40, offsetBit, (byte) 0x23},
                        //6,4
                        new byte[] {positionByte4, (byte) 0x52},
                        new byte[] {positionByte4, (byte) 0x52, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0x52, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0x52, offsetBit, (byte) 0x23},
                        //6,8
                        new byte[] {positionByte4, (byte) 0x54},
                        new byte[] {positionByte4, (byte) 0x54, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0x54, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0x54, offsetBit, (byte) 0x23},
                        //6,12
                        new byte[] {positionByte4, (byte) 0xd6},
                        new byte[] {positionByte4, (byte) 0xd6, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0xd6, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0xd6, offsetBit, (byte) 0x23},
                        //6,16
                        new byte[] {positionByte4, (byte) 0x58},
                        new byte[] {positionByte4, (byte) 0x58, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0x58, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0x58, offsetBit, (byte) 0x23},
                        //6,20
                        new byte[] {positionByte4, (byte) 0xda},
                        new byte[] {positionByte4, (byte) 0xda, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0xda, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0xda, offsetBit, (byte) 0x23},
                        //6,24
                        new byte[] {positionByte4, (byte) 0xdc},
                        new byte[] {positionByte4, (byte) 0xdc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0xdc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0xdc, offsetBit, (byte) 0x23},
                        //6,28
                        new byte[] {positionByte4, (byte) 0x5e},
                        new byte[] {positionByte4, (byte) 0x5e, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0x5e, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0x5e, offsetBit, (byte) 0x23},
                },
                //Row 7
                new byte[][]{
                        //7,0
                        new byte[] {positionByte4, (byte) 0xe0},
                        new byte[] {positionByte4, (byte) 0xe0, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0xe0, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0xe0, offsetBit, (byte) 0x23},
                        //7,4
                        new byte[] {positionByte4, (byte) 0xf2},
                        new byte[] {positionByte4, (byte) 0xf2, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0xf2, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0xf2, offsetBit, (byte) 0x23},
                        //7,8
                        new byte[] {positionByte4, (byte) 0xf4},
                        new byte[] {positionByte4, (byte) 0xf4, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0xf4, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0xf4, offsetBit, (byte) 0x23},
                        //7,12
                        new byte[] {positionByte4, (byte) 0x76},
                        new byte[] {positionByte4, (byte) 0x76, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0x76, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0x76, offsetBit, (byte) 0x23},
                        //7,16
                        new byte[] {positionByte4, (byte) 0xf8},
                        new byte[] {positionByte4, (byte) 0xf8, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0xf8, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0xf8, offsetBit, (byte) 0x23},
                        //7,20
                        new byte[] {positionByte4, (byte) 0x7a},
                        new byte[] {positionByte4, (byte) 0x7a, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0x7a, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0x7a, offsetBit, (byte) 0x23},
                        //7,24
                        new byte[] {positionByte4, (byte) 0xfc},
                        new byte[] {positionByte4, (byte) 0xfc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0xfc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0xfc, offsetBit, (byte) 0x23},
                        //7,28
                        new byte[] {positionByte4, (byte) 0xfe},
                        new byte[] {positionByte4, (byte) 0xfe, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte4, (byte) 0xfe, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte4, (byte) 0xfe, offsetBit, (byte) 0x23},
                },

                //Row 8
                new byte[][]{
                        //8,0
                        new byte[] {positionByte5, (byte) 0x40},
                        new byte[] {positionByte5, (byte) 0x40, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0x40, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0x40, offsetBit, (byte) 0x23},
                        //8,4
                        new byte[] {positionByte5, (byte) 0x52},
                        new byte[] {positionByte5, (byte) 0x52, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0x52, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0x52, offsetBit, (byte) 0x23},
                        //8,8
                        new byte[] {positionByte5, (byte) 0x54},
                        new byte[] {positionByte5, (byte) 0x54, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0x54, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0x54, offsetBit, (byte) 0x23},
                        //8,12
                        new byte[] {positionByte5, (byte) 0xd6},
                        new byte[] {positionByte5, (byte) 0xd6, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0xd6, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0xd6, offsetBit, (byte) 0x23},
                        //8,16
                        new byte[] {positionByte5, (byte) 0x58},
                        new byte[] {positionByte5, (byte) 0x58, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0x58, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0x58, offsetBit, (byte) 0x23},
                        //8,20
                        new byte[] {positionByte5, (byte) 0xda},
                        new byte[] {positionByte5, (byte) 0xda, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0xda, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0xda, offsetBit, (byte) 0x23},
                        //8,24
                        new byte[] {positionByte5, (byte) 0xdc},
                        new byte[] {positionByte5, (byte) 0xdc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0xdc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0xdc, offsetBit, (byte) 0x23},
                        //8,28
                        new byte[] {positionByte5, (byte) 0x5e},
                        new byte[] {positionByte5, (byte) 0x5e, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0x5e, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0x5e, offsetBit, (byte) 0x23},
                },
                //Row 9
                new byte[][]{
                        //9,0
                        new byte[] {positionByte5, (byte) 0xe0},
                        new byte[] {positionByte5, (byte) 0xe0, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0xe0, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0xe0, offsetBit, (byte) 0x23},
                        //9,4
                        new byte[] {positionByte5, (byte) 0xf2},
                        new byte[] {positionByte5, (byte) 0xf2, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0xf2, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0xf2, offsetBit, (byte) 0x23},
                        //9,8
                        new byte[] {positionByte5, (byte) 0xf4},
                        new byte[] {positionByte5, (byte) 0xf4, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0xf4, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0xf4, offsetBit, (byte) 0x23},
                        //9,12
                        new byte[] {positionByte5, (byte) 0x76},
                        new byte[] {positionByte5, (byte) 0x76, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0x76, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0x76, offsetBit, (byte) 0x23},
                        //9,16
                        new byte[] {positionByte5, (byte) 0xf8},
                        new byte[] {positionByte5, (byte) 0xf8, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0xf8, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0xf8, offsetBit, (byte) 0x23},
                        //9,20
                        new byte[] {positionByte5, (byte) 0x7a},
                        new byte[] {positionByte5, (byte) 0x7a, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0x7a, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0x7a, offsetBit, (byte) 0x23},
                        //9,24
                        new byte[] {positionByte5, (byte) 0xfc},
                        new byte[] {positionByte5, (byte) 0xfc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0xfc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0xfc, offsetBit, (byte) 0x23},
                        //9,28
                        new byte[] {positionByte5, (byte) 0xfe},
                        new byte[] {positionByte5, (byte) 0xfe, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte5, (byte) 0xfe, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte5, (byte) 0xfe, offsetBit, (byte) 0x23},
                },
                //Row 10
                new byte[][]{
                        //10,0
                        new byte[] {positionByte6, (byte) 0x40},
                        new byte[] {positionByte6, (byte) 0x40, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte6, (byte) 0x40, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte6, (byte) 0x40, offsetBit, (byte) 0x23},
                        //10,4
                        new byte[] {positionByte6, (byte) 0x52},
                        new byte[] {positionByte6, (byte) 0x52, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte6, (byte) 0x52, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte6, (byte) 0x52, offsetBit, (byte) 0x23},
                        //10,8
                        new byte[] {positionByte6, (byte) 0x54},
                        new byte[] {positionByte6, (byte) 0x54, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte6, (byte) 0x54, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte6, (byte) 0x54, offsetBit, (byte) 0x23},
                        //10,12
                        new byte[] {positionByte6, (byte) 0xd6},
                        new byte[] {positionByte6, (byte) 0xd6, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte6, (byte) 0xd6, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte6, (byte) 0xd6, offsetBit, (byte) 0x23},
                        //10,16
                        new byte[] {positionByte6, (byte) 0x58},
                        new byte[] {positionByte6, (byte) 0x58, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte6, (byte) 0x58, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte6, (byte) 0x58, offsetBit, (byte) 0x23},
                        //10,20
                        new byte[] {positionByte6, (byte) 0xda},
                        new byte[] {positionByte6, (byte) 0xda, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte6, (byte) 0xda, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte6, (byte) 0xda, offsetBit, (byte) 0x23},
                        //10,24
                        new byte[] {positionByte6, (byte) 0xdc},
                        new byte[] {positionByte6, (byte) 0xdc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte6, (byte) 0xdc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte6, (byte) 0xdc, offsetBit, (byte) 0x23},
                        //10,28
                        new byte[] {positionByte6, (byte) 0x5e},
                        new byte[] {positionByte6, (byte) 0x5e, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte6, (byte) 0x5e, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte6, (byte) 0x5e, offsetBit, (byte) 0x23},
                },
                //Row 11
                new byte[][]{
                        //11,0
                        new byte[] {positionByte7, (byte) 0x40},
                        new byte[] {positionByte7, (byte) 0x40, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0x40, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0x40, offsetBit, (byte) 0x23},
                        //11,4
                        new byte[] {positionByte7, (byte) 0x52},
                        new byte[] {positionByte7, (byte) 0x52, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0x52, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0x52, offsetBit, (byte) 0x23},
                        //11,8
                        new byte[] {positionByte7, (byte) 0x54},
                        new byte[] {positionByte7, (byte) 0x54, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0x54, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0x54, offsetBit, (byte) 0x23},
                        //11,12
                        new byte[] {positionByte7, (byte) 0xd6},
                        new byte[] {positionByte7, (byte) 0xd6, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0xd6, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0xd6, offsetBit, (byte) 0x23},
                        //11,16
                        new byte[] {positionByte7, (byte) 0x58},
                        new byte[] {positionByte7, (byte) 0x58, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0x58, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0x58, offsetBit, (byte) 0x23},
                        //11,20
                        new byte[] {positionByte7, (byte) 0xda},
                        new byte[] {positionByte7, (byte) 0xda, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0xda, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0xda, offsetBit, (byte) 0x23},
                        //11,24
                        new byte[] {positionByte7, (byte) 0xdc},
                        new byte[] {positionByte7, (byte) 0xdc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0xdc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0xdc, offsetBit, (byte) 0x23},
                        //11,28
                        new byte[] {positionByte7, (byte) 0x5e},
                        new byte[] {positionByte7, (byte) 0x5e, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0x5e, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0x5e, offsetBit, (byte) 0x23},
                },
                //Row 12
                new byte[][]{
                        //12,0
                        new byte[] {positionByte7, (byte) 0xe0},
                        new byte[] {positionByte7, (byte) 0xe0, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0xe0, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0xe0, offsetBit, (byte) 0x23},
                        //12,4
                        new byte[] {positionByte7, (byte) 0xf2},
                        new byte[] {positionByte7, (byte) 0xf2, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0xf2, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0xf2, offsetBit, (byte) 0x23},
                        //12,8
                        new byte[] {positionByte7, (byte) 0xf4},
                        new byte[] {positionByte7, (byte) 0xf4, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0xf4, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0xf4, offsetBit, (byte) 0x23},
                        //12,12
                        new byte[] {positionByte7, (byte) 0x76},
                        new byte[] {positionByte7, (byte) 0x76, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0x76, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0x76, offsetBit, (byte) 0x23},
                        //12,16
                        new byte[] {positionByte7, (byte) 0xf8},
                        new byte[] {positionByte7, (byte) 0xf8, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0xf8, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0xf8, offsetBit, (byte) 0x23},
                        //12,20
                        new byte[] {positionByte7, (byte) 0x7a},
                        new byte[] {positionByte7, (byte) 0x7a, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0x7a, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0x7a, offsetBit, (byte) 0x23},
                        //12,24
                        new byte[] {positionByte7, (byte) 0xfc},
                        new byte[] {positionByte7, (byte) 0xfc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0xfc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0xfc, offsetBit, (byte) 0x23},
                        //12,28
                        new byte[] {positionByte7, (byte) 0xfe},
                        new byte[] {positionByte7, (byte) 0xfe, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte7, (byte) 0xfe, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte7, (byte) 0xfe, offsetBit, (byte) 0x23},
                },
                //Row 13
                new byte[][]{
                        //13,0
                        new byte[] {positionByte8, (byte) 0x40},
                        new byte[] {positionByte8, (byte) 0x40, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0x40, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0x40, offsetBit, (byte) 0x23},
                        //13,4
                        new byte[] {positionByte8, (byte) 0x52},
                        new byte[] {positionByte8, (byte) 0x52, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0x52, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0x52, offsetBit, (byte) 0x23},
                        //13,8
                        new byte[] {positionByte8, (byte) 0x54},
                        new byte[] {positionByte8, (byte) 0x54, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0x54, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0x54, offsetBit, (byte) 0x23},
                        //13,12
                        new byte[] {positionByte8, (byte) 0xd6},
                        new byte[] {positionByte8, (byte) 0xd6, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0xd6, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0xd6, offsetBit, (byte) 0x23},
                        //13,16
                        new byte[] {positionByte8, (byte) 0x58},
                        new byte[] {positionByte8, (byte) 0x58, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0x58, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0x58, offsetBit, (byte) 0x23},
                        //13,20
                        new byte[] {positionByte8, (byte) 0xda},
                        new byte[] {positionByte8, (byte) 0xda, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0xda, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0xda, offsetBit, (byte) 0x23},
                        //13,24
                        new byte[] {positionByte8, (byte) 0xdc},
                        new byte[] {positionByte8, (byte) 0xdc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0xdc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0xdc, offsetBit, (byte) 0x23},
                        //13,28
                        new byte[] {positionByte8, (byte) 0x5e},
                        new byte[] {positionByte8, (byte) 0x5e, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0x5e, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0x5e, offsetBit, (byte) 0x23},
                },
                //Row 14
                new byte[][]{
                        //14,0
                        new byte[] {positionByte8, (byte) 0xe0},
                        new byte[] {positionByte8, (byte) 0xe0, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0xe0, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0xe0, offsetBit, (byte) 0x23},
                        //14,4
                        new byte[] {positionByte8, (byte) 0xf2},
                        new byte[] {positionByte8, (byte) 0xf2, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0xf2, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0xf2, offsetBit, (byte) 0x23},
                        //14,8
                        new byte[] {positionByte8, (byte) 0xf4},
                        new byte[] {positionByte8, (byte) 0xf4, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0xf4, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0xf4, offsetBit, (byte) 0x23},
                        //14,12
                        new byte[] {positionByte8, (byte) 0x76},
                        new byte[] {positionByte8, (byte) 0x76, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0x76, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0x76, offsetBit, (byte) 0x23},
                        //14,16
                        new byte[] {positionByte8, (byte) 0xf8},
                        new byte[] {positionByte8, (byte) 0xf8, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0xf8, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0xf8, offsetBit, (byte) 0x23},
                        //14,20
                        new byte[] {positionByte8, (byte) 0x7a},
                        new byte[] {positionByte8, (byte) 0x7a, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0x7a, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0x7a, offsetBit, (byte) 0x23},
                        //14,24
                        new byte[] {positionByte8, (byte) 0xfc},
                        new byte[] {positionByte8, (byte) 0xfc, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0xfc, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0xfc, offsetBit, (byte) 0x23},
                        //14,28
                        new byte[] {positionByte8, (byte) 0xfe},
                        new byte[] {positionByte8, (byte) 0xfe, offsetBit, (byte) 0xa1},
                        new byte[] {positionByte8, (byte) 0xfe, offsetBit, (byte) 0xa2},
                        new byte[] {positionByte8, (byte) 0xfe, offsetBit, (byte) 0x23},
                }
        };
    }

    public byte[] startPopOnCaption() {
        return new byte[]{ controlByte, (byte) 0x20};
    }

    public byte[] endPopOnCaption() {
        return new byte[]{ controlByte, (byte) 0x2f};
    }

    public byte[] clearScreen() {
        return new byte[]{ controlByte, (byte) 0x2c};
    }

    public byte[] cursorToPosition(int posX, int posY) {
        return position[posX][posY];
    }

    public byte[] startRollUpCaption2() {
        return new byte[]{ controlByte, (byte) 0x25};
    }

    public byte[] startRollUpCaption3() {
        return new byte[]{ controlByte, (byte) 0x26};
    }

    public byte[] startRollUpCaption4() {
        return new byte[]{ controlByte, (byte) 0xa7};
    }

    public byte[] carriageReturn() {
        return new byte[]{ controlByte, (byte) 0xad};
    }
}
