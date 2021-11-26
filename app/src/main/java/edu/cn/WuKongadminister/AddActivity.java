package edu.cn.WuKongadminister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    //组件定义

    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbooks);
        //初始化界面
        initView();
    }

    //初始化界面
    private void initView() {

        btnAdd = (Button) findViewById(R.id.btn_add);
        //设置按钮的点击事件
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //当单击“添加”按钮时，获取添加信息

        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间

        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        Date date = new Date(System.currentTimeMillis());// 获取当前时间
        String time = sdf.format(new Date(date.getTime() + (long) 8 * 60 * 60 * 1000));
        String name = "rubbish";
        Rubbish o =new Rubbish(time, name);

        //创建数据库访问对象
        RubbishDAO dao = new RubbishDAO(getApplicationContext());
        //打开数据库
        dao.open();
        //执行数据库访问方法
        long result = dao.addRubbish(o);

        if (result > 0) {
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
        }
        //关闭数据库
        dao.close();
        //关闭活动
        finish();

    }
}