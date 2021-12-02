package edu.cn.WuKongadminister;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ubtechinc.sauron.api.TakePicApi;
import com.ubtechinc.skill.SkillHelper;
import com.ubtrobot.commons.ResponseListener;
import com.ubtrobot.transport.message.CallException;
import com.ubtrobot.transport.message.Request;
import com.ubtrobot.transport.message.Response;
import com.ubtrobot.transport.message.ResponseCallback;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddActivity extends Activity implements View.OnClickListener {
    //组件定义

    private Button btnPic;
    private Button btnRecog;
    private Button btnInfo;
    private Button btnBack;
    private static final String TAG = App.DEBUG_TAG;
    private TakePicApi takePicApi;
    private String path = "";
    private String name = "";
    private Bitmap bitmap = null;
    private ImgListener img = new ImgListener();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //初始化界面
        initRobot();
        initView();
    }

    private void initRobot() {
        takePicApi = TakePicApi.get();
    }

    //初始化界面
    private void initView() {
        btnPic = (Button) findViewById(R.id.btn_pic);
        btnPic.setOnClickListener(this);

        btnRecog = (Button) findViewById(R.id.btn_recog);
        btnRecog.setOnClickListener(this);

        btnInfo = (Button) findViewById(R.id.btn_info);
        btnInfo.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_pic) {
            //悟空摄像头模块
            setText("拍照中");
            takePicImmediately();
            this.path = img.getPath();//path为图片路径
            File file = new File(path);
            if (file.exists()) {
                this.bitmap = BitmapFactory.decodeFile(path);//bitmap为图片信息
                ImageView img = findViewById(R.id.img);
                img.setImageBitmap(this.bitmap);
                Toast.makeText(this, "拍照成功", Toast.LENGTH_SHORT).show();
            }
            setText("拍照完成");
        } else if (v.getId() == R.id.btn_recog) {
            setText("识别中");
            // 调用机器学习的部分
            /*


            */
            //输入图片this.bitmap
            //输出识别结果等并将其保存在name变量中
            //this.name = ...
            sentBitmap(this.bitmap);

            boolean success = false;//是否识别成功
            if (success) {
                SkillHelper.startSkillByIntent("nod", null, getListener());
            } else {
                SkillHelper.startSkillByIntent("shake_head", null, getListener());
            }
            Toast.makeText(this, "识别结束", Toast.LENGTH_SHORT).show();


            TextView textView = findViewById(R.id.ans);
            textView.setText(this.name);
            String name = this.name;
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
            Date date = new Date(System.currentTimeMillis());// 获取当前时间
            String time = sdf.format(new Date(date.getTime() + (long) 8 * 60 * 60 * 1000));
            Rubbish o = new Rubbish(time, name);
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




            // 调用语言播报Api
            /*


            */
            //将this.name播报出来
            setText("识别完成");
        } else if (v.getId() == R.id.btn_info) {
            if (!this.name.equals("1")) {
                Intent intent = new Intent(AddActivity.this, ShowRubInfoActivity.class);
                intent.putExtra("data", this.name);
                startActivity(intent);
            } else {
                Toast.makeText(this, "未识别为垃圾不能查看更多信息", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btn_back) {
            finish();
        }
    }

    private void sentBitmap(Bitmap bitmap) {
        new Thread(new Runnable() {
            private Bitmap bitmap;

            @Override
            public void run() {
                try {
                    //new一个访问的url
                    URL url = new URL("http://10.0.2.2:8000/login/");
                    //创建HttpURLConnection 实例
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //提交数据的方式
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    //设置超时时间
                    connection.setConnectTimeout(8000);//连接超时
                    //读取超时
                    connection.setReadTimeout(8000);
                    //连接打开输出流
                    OutputStream os = connection.getOutputStream();
                    String bit;
                    if (bitmap != null) {
                        bit = bitmaptoString(bitmap, 0);
                    } else {
                        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
                        bit = bitmaptoString(bitmap, 0);
                    }
//                    Log.i("img", bit);
                    //jsons数据拼接
                    JSONObject json = new JSONObject();
                    json.put("img", bit);
//                    Log.i("json", json.toString());
                    //数据写入输出流（发送）
                    os.write(json.toString().getBytes());
                    if (connection.getResponseCode() == 200) {
                        //接收服务器输入流信息
                        InputStream is = connection.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        //拿到信息
                        String  result = br.readLine();
                        Log.i("返回数据：", result);
                        is.close();
                        os.close();
                        connection.disconnect();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String bitmaptoString(Bitmap bitmap, int bitmapQuality) {
        // 将Bitmap转换成字符串
         String string = null;
         ByteArrayOutputStream bStream = new ByteArrayOutputStream();
         bitmap.compress(Bitmap.CompressFormat.PNG, bitmapQuality, bStream);
         byte[] bytes = bStream.toByteArray();
         string = Base64.encodeToString(bytes, Base64.DEFAULT);
         return string;
    }

    private void setText(String s) {
        TextView textView = findViewById(R.id.process);
        textView.setText(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void takePicImmediately() {
        takePicApi.takePicImmediately(img);
    }

    class ImgListener implements ResponseListener<String> {
        private String path = "";

        @Override
        public void onResponseSuccess(String string) {
            String sdcardPath = System.getenv("EXTERNAL_STORAGE");
            this.path = sdcardPath + string;
            Toast.makeText(getApplicationContext(), "saving " + string, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(int errorCode, @NonNull String errorMsg) {
            Log.i(TAG, "takePicImmediately接口调用失败,errorCode======" + errorCode + ",errorMsg======" + errorMsg);
        }

        public String getPath() {
            return path;
        }
    }

    @NonNull private ResponseCallback getListener() {
        return new ResponseCallback() {
            @Override public void onResponse(Request request, Response response) {
                Log.i(TAG, "start success.");
            }

            @Override public void onFailure(Request request, CallException e) {
                Log.i(TAG, e.getMessage());
            }
        };
    }
}