package edu.cn.WuKongadminister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class QueryActivity extends AppCompatActivity implements View.OnClickListener {
    //定义组件
    private ListView listView=null;
    private Button bt_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        bt_delete = findViewById(R.id.bt_delete);
        bt_delete.setOnClickListener(this);
        setTitle("查看记录");
        //初始化界面
        initView();
    }

    private void initView() {
        //建立数据库访问对象
        RubbishDAO dao=new RubbishDAO(getApplicationContext());
        //打开数据库
        dao.open();
        //调用数据库访问方法
        List<Map<String,Object>> mOrderData=dao.getAllRubbish();
        //获取组件
        listView=(ListView)findViewById(R.id.lst_orders);
        //定义数据源
        String[] from={"time","rubbishname"};
        //定义布局控件ID
        int[] to={R.id.tv_lst_time,R.id.tv_lst_name};
        if (mOrderData != null) {
            SimpleAdapter listItemAdapter = new SimpleAdapter(QueryActivity.this, mOrderData, R.layout.item_list, from, to);
            //添加并显示
            listView.setAdapter(listItemAdapter);
        }
        //关闭数据库
        dao.close();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_delete){
            RubbishDAO dao=new RubbishDAO(getApplicationContext());
            //打开数据库
            dao.open();
            dao.deleteRubbish();
            Toast.makeText(this, "清空成功", Toast.LENGTH_SHORT).show();
            dao.close();
            refresh();
        }
    }

    private void refresh() {
        finish();
        Intent intent = new Intent(QueryActivity.this, QueryActivity.class);
        startActivity(intent);
    }
}
