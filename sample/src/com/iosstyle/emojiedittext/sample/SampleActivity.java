package com.iosstyle.emojiedittext.sample;


import com.iosstyle.EmojiCodec;
import com.iosstyle.EmojiEditText;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

import java.nio.charset.Charset;
import java.util.logging.Logger;

public class SampleActivity extends Activity {
    EmojiEditText myEditText = null;
    EditText normalEditText = null;
    byte[]  bytes = {103, 104, 104, 32, 98, 97, 110, 100, 105, 116, 32, 104, -16, -97, -110, -91, -16, -97, -110, -94, -16, -97, -110, -90, -16, -97, -111, -119, -16, -97, -111, -121, -16, -97, -111, -119};
    Handler mUiHandler;
    int position = 1;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mUiHandler = new Handler();
        myEditText = (EmojiEditText)findViewById(R.id.my_edit_text);
        normalEditText = (EditText)findViewById(R.id.edit_text);
        final String str = new String(bytes, Charset.forName("UTF-8"));
        char[] charArray = str.toCharArray();
        int codePointLength = str.codePointCount(0, str.length()-1);
        Log.d("Denny", "str is:"+str+",str length:"+str.length()+",charArray length:"+charArray.length
                +",code point length:"+codePointLength);
        /*mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(position < str.length()){
                    CharSequence myStr = str.subSequence(0, position);
                    myEditText.setText(myStr);
                    Log.d("Denny","position:"+position+",str:"+str);
                    position++;
                    mUiHandler.postDelayed(this, 500);
                }
            }
        }, 1000);*/
        byte[] emoji1 = {-16,-97,-111, -119};
        //myEditText.setText(new String(emoji1,Charset.forName("UTF-8")));
        normalEditText.setText(new String(emoji1,Charset.forName("UTF-8")));
        String emojiStr = EmojiCodec.getInstance(getApplicationContext()).formatToEmojiString(str);
        myEditText.setText(emojiStr);
        Log.d("Denny","emojiStr:"+emojiStr);
    }

}
