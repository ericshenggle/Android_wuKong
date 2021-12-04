package edu.cn.WuKongadminister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ShowRubInfoActivity extends Activity {

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
        textView2.setText(RubbishKnowledge.get(rubbish_name));
    }

    public void say(View view) {

        TextView textView = findViewById(R.id.info);
        String text = textView.getText().toString();
        // 语音播报API
        /*




         */
        //
        // text为需要播报的内容
    }
}
