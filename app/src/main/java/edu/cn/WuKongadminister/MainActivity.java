package edu.cn.WuKongadminister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button bt_createe;
    private Button bt_read;
    private Button bt_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_createe = findViewById(R.id.bt_createe);
        bt_createe.setOnClickListener(this);

        bt_read = findViewById(R.id.bt_read);
        bt_read.setOnClickListener(this);

        bt_show = findViewById(R.id.bt_show);
        bt_show.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_createe){
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.bt_read){
            Intent intent = new Intent(MainActivity.this, QueryActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.bt_show) {
            Intent intent = new Intent(MainActivity.this, ShowWuKongInfoActivity.class);
            startActivity(intent);
        }
    }
}

