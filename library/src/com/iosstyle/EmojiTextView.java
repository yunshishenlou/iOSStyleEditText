
package com.iosstyle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
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
        //super.setText(text, type);
       /* if(TextUtils.isEmpty(text)){
            return;
        }
        String sourceStr = text.toString();
        String[] hexStringArray = EmojiCodec.convert(sourceStr);
        SpannableStringBuilder builder=new SpannableStringBuilder();

        for(int i = 0;i < hexStringArray.length;i++){
            int id = getContext().getResources().getIdentifier(
                    "emoji_" + hexStringArray[i], "drawable", getContext().getPackageName());
            if(id != 0){
                
            Drawable emoji = getContext().getResources()
                    .getDrawable(id);
            emoji.setBounds(0, 0, this.getLineHeight(),
                    this.getLineHeight());
            ImageSpan imageSpan = new ImageSpan(emoji,
                    ImageSpan.ALIGN_BOTTOM);
            spannable.setSpan(imageSpan, tagStartPosition, tagEnd,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }*/
    }

}
