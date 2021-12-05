package edu.cn.WuKongadminister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ubtrobot.commons.Priority;
import com.ubtrobot.mini.voice.VoiceListener;
import com.ubtrobot.mini.voice.VoicePool;

public class ShowRubInfoActivity extends Activity {

    private static final String TAG = "RubInfo";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrubbsih);
        Intent intent =getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        String rubbish_name = intent.getStringExtra("data");
        init(rubbish_name);
    }

    private void init(String rubbish_name) {
        TextView textView1 = findViewById(R.id.name);
        textView1.setText(rubbish_name);
        TextView textView2 = findViewById(R.id.info);
        String[] item = rubbish_name.split("/");
        textView2.setText(RubbishKnowledge.get(item[0]));
        TextView textView3 = findViewById(R.id.infomore);
        textView3.setText(RubbishKnowledge.getAdvantage());
    }

    public void say(View view) {

        TextView textView = findViewById(R.id.info);
        String text = textView.getText().toString();

        VoiceListener voiceListener = new VoiceListener() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "===yuyin completed!!===");
            }

            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "===yuyin error!!===");
                Log.i(TAG, s);
            }
        };
        VoicePool.get().playTTs(text, Priority.HIGH, voiceListener);
    }
}
