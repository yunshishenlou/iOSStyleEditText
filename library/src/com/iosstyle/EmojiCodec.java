
package com.iosstyle;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class EmojiCodec {
    private static EmojiCodec sInstance = null;

    private final Context mAppContext;

    private EmojiCodec(Context appContext) {
        this.mAppContext = appContext;
    }

    public static synchronized EmojiCodec getInstance(Context applicationContext) {
        if (sInstance == null) {
            sInstance = new EmojiCodec(applicationContext);
        }
        return sInstance;
    }

    public String formatToEmojiString(String str) {
        return convertCPArrayToStr(formatCPArray(toCPArray(str)));
    }

    private int[] toCPArray(String str) {
        char[] ach = str.toCharArray();
        int len = ach.length;
        int[] acp = new int[Character.codePointCount(ach, 0, len)];
        int j = 0;
        for (int i = 0, cp; i < len; i += Character.charCount(cp)) {
            cp = Character.codePointAt(ach, i);
            acp[j++] = cp;
        }
        return acp;
    }

    private int[] formatCPArray(int[] intArray) {
        List<Integer> formatedList = new ArrayList<Integer>();
        if (intArray == null || intArray.length == 0) {
            return null;
        }
        for (int i = 0; i < intArray.length; i++) {
            String hexString = Integer.toHexString(intArray[i]);
            if (isIosEmojiCode(hexString)) {
                int[] replaceIntArray = toCPArray("[" + hexString + "]");
                for (int a : replaceIntArray) {
                    formatedList.add(Integer.valueOf(a));
                }
            } else {
                formatedList.add(Integer.valueOf(intArray[i]));
            }
        }
        int[] resultArray = new int[formatedList.size()];
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = formatedList.get(i);
        }
        return resultArray;
    }

    private boolean isIosEmojiCode(String hexString) {
        Resources resources = mAppContext.getResources();
        int id = 0;
        try {
            id = resources.getIdentifier("emoji_" + hexString, "drawable",
                    mAppContext.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (id == 0) {
            return false;
        } else {
            return true;
        }
    }

    private String convertCPArrayToStr(int[] intArray) {
        List<Byte> byteList = new ArrayList<Byte>();
        StringBuilder strBuilder = new StringBuilder();
        for (Integer i : intArray) {
            strBuilder.append(Character.toChars(i));
        }
        return strBuilder.toString();
    }

}
