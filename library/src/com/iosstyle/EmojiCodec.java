
package com.iosstyle;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EmojiCodec {
    public final static String TAG = "emoji";

    private static EmojiCodec sInstance = null;

    private final Context mAppContext;

    private EmojiCodec(Context appContext) {
        this.mAppContext = appContext;
    }

    public static final String START_CHAR = "[";

    public static final String END_CHAR = "]";

    public static synchronized EmojiCodec getInstance(Context applicationContext) {
        if (sInstance == null) {
            sInstance = new EmojiCodec(applicationContext);
        }
        return sInstance;
    }

    public String convertToEmojiString(String str) {
        return convertCPArrayToStr(formatCPArray(toCPArray(str)));
    }

    public String getOriginString(String emojiStr) {
        if (TextUtils.isEmpty(emojiStr)) {
            return null;
        }

        int length = 0;
        int position = 0;
        int tagStartPosition = 0;
        int tagLength = 0;
        StringBuilder buffer = new StringBuilder();
        boolean inTag = false;

        do {
            length = emojiStr.length();
            String c = emojiStr.subSequence(position, position + 1).toString();

            if (!inTag && c.equals(START_CHAR)) {
                buffer = new StringBuilder();
                tagStartPosition = position;
                Log.d(TAG, "Entering tag at " + tagStartPosition);

                inTag = true;
                tagLength = 0;
            }

            if (inTag) {
                buffer.append(c);
                tagLength++;

                // Have we reached end of the tag?
                if (c.equals(END_CHAR)) {
                    inTag = false;

                    String tag = buffer.toString();
                    int tagEnd = tagStartPosition + tagLength;
                    Log.d(TAG, "Tag: " + tag + ", started at: " + tagStartPosition
                            + ", finished at " + tagEnd + ", length: " + tagLength);

                    String hexStr = tag.substring(1, tag.length() - 1);
                    try {
                        if (isIosEmojiCode(hexStr)) {
                            String origin = convertHexCPToString(hexStr);
                            emojiStr = emojiStr.replace(START_CHAR + hexStr + END_CHAR, origin);
                            position = 0;
                            continue;
                        }
                    } catch (Exception e) {
                    }
                }
            }

            position++;
        } while (position < length);
        return emojiStr;
    }

    public String convertHexCPToString(String hexStr) {
        String[] hexStrArray = hexStr.split("_");
        String result = "";
        for (String str : hexStrArray) {
            int cp = Integer.parseInt(str, 16);
            char[] chars = Character.toChars(cp);
            for (char c : chars) {
                result += c;
            }
        }
        return result;
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
                int[] replaceIntArray = toCPArray(START_CHAR + hexString + END_CHAR);
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
        StringBuilder strBuilder = new StringBuilder();
        for (Integer i : intArray) {
            strBuilder.append(Character.toChars(i));
        }
        return strBuilder.toString();
    }
}
