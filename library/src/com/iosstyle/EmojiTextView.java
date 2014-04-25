
package com.iosstyle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class EmojiTextView extends TextView {

    public EmojiTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EmojiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiTextView(Context context) {
        super(context);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if(TextUtils.isEmpty(text)){
            return;
        }
        String emojiStr = EmojiCodec.getInstance(getContext().getApplicationContext())
                .convertToEmojiString(text.toString());
        
        super.setText(emotifySpannable(emojiStr.toString()), type);
    }
    private Spannable emotifySpannable(String source) {
        int length = source.length();
        int position = 0;
        int tagStartPosition = 0;
        int tagLength = 0;
        StringBuilder buffer = new StringBuilder();
        Spannable spannable = new SpannableString(source);
        boolean inTag = false;

        if (length <= 0)
            return null;

        do {
            String c = source.subSequence(position, position + 1).toString();

            if (!inTag && c.equals(EmojiCodec.START_CHAR)) {
                buffer = new StringBuilder();
                tagStartPosition = position;
                inTag = true;
                tagLength = 0;
            }

            if (inTag) {
                buffer.append(c);
                tagLength++;

                // Have we reached end of the tag?
                if (c.equals(EmojiCodec.END_CHAR)) {
                    inTag = false;

                    String tag = buffer.toString();
                    int tagEnd = tagStartPosition + tagLength;


                    String hexStr = tag.substring(1, tag.length() - 1);
                    try {
                        int id = getContext().getResources().getIdentifier("emoji_" + hexStr,
                                "drawable", getContext().getPackageName());
                        Drawable emoji = getContext().getResources().getDrawable(id);
                        emoji.setBounds(0, 0, this.getLineHeight(), this.getLineHeight());
                        ImageSpan imageSpan = new ImageSpan(emoji, ImageSpan.ALIGN_BOTTOM);
                        spannable.setSpan(imageSpan, tagStartPosition, tagEnd,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } catch (Exception e) {
                    }
                }
            }

            position++;
        } while (position < length);
    return spannable;
    }
}
