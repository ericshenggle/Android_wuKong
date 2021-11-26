package edu.cn.WuKongadminister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_createe;
    private Button bt_read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_createe = findViewById(R.id.bt_createe);
        bt_createe.setOnClickListener(this);

        bt_read = findViewById(R.id.bt_read);
        bt_read.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_createe){
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivityForResult(intent, 0);
        }else if (v.getId() == R.id.bt_read){
            Intent intent = new Intent(MainActivity.this, QueryActivity.class);
            startActivityForResult(intent, 3);
        }
    }
}

