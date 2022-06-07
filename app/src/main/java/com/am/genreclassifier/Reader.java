package com.am.genreclassifier;

import java.nio.ByteBuffer;


public class Reader {

    public static short[] byteToShort(byte[] data, int length, boolean reduceStereo) {
        if (reduceStereo) {
            short[] shortData = new short[length / 4];
            for (int i = 0; i < shortData.length; i++) {
                short val1 = (short) ((data[i * 4 + 1] << 8) | (data[i * 4] & 0xff));
                short val2 = (short) ((data[i * 4 + 3] << 8) | (data[i * 4 + 2] & 0xff));
                shortData[i] = (short) (((int) val1 + (int) val2) / 2);
            }
            return shortData;
        } else {
            short[] shortData = new short[length / 2];
            for (int i = 0; i < shortData.length; i++) {
                shortData[i] = (short) ((data[i * 2 + 1] << 8) | (data[i * 2] & 0xff));
            }
            return shortData;
        }
    }
    public static float[] shortToFloat(byte[] data, int length, boolean reduceStereo) {
        if (reduceStereo) {
            float[] shortData = new float[length / 4];
            for (int i = 0; i < shortData.length; i++) {
                float val1 = (float) ((data[i * 4 + 1] << 8) | (data[i * 4] & 0xff));
                float val2 = (float) ((data[i * 4 + 3] << 8) | (data[i * 4 + 2] & 0xff));
                shortData[i] = (float) (((int) val1 + (int) val2) / 2);
            }
            return shortData;
        } else {
            float[] shortData = new float[length / 2];
            for (int i = 0; i < shortData.length; i++) {
                shortData[i] = (float) ((data[i * 2 + 1] << 8) | (data[i * 2] & 0xff));
            }
            return shortData;
        }
    }
    public static float[] shortToFloat(short[] audioShorts) {
        float[] audioFloats = new float[audioShorts.length];
        for (int i = 0; i < audioShorts.length; i++) {
            audioFloats[i] = ((float)audioShorts[i])/0x8000;
        }
        return audioFloats;
    }

    public static short[] floatToShort(float[] audioShorts) {
        short[] audioFloats = new short[audioShorts.length];
        for (int i = 0; i < audioShorts.length; i++) {
            audioFloats[i] = (short) (audioShorts[i] * 0x8000);
        }
        return audioFloats;
    }

    public static float[] floatMe(short[] pcms) {
        float[] floaters = new float[pcms.length];
        for (int i = 0; i < pcms.length; i++) {
            floaters[i] =  (float) pcms[i]/0x8000;
        }
        return floaters;
    }


    public static short[] shortMe(byte[] bytes) {
        short[] out = new short[bytes.length / 2]; // will drop last byte if odd number
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        for (int i = 0; i < out.length; i++) {
            out[i] = bb.getShort();
        }
        return out;
    }



}
