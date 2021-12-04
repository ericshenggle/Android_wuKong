package edu.cn.WuKongadminister;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ubtechinc.skill.SkillHelper;
import com.ubtrobot.masterevent.protos.SysMasterEvent;
import com.ubtrobot.mini.sysevent.EventApi;
import com.ubtrobot.mini.sysevent.SysEventApi;
import com.ubtrobot.mini.sysevent.event.BatteryEvent;
import com.ubtrobot.mini.sysevent.listener.subscribe.GetBatteryInfoListener;
import com.ubtrobot.mini.sysevent.receiver.BatteryEventReceiver;
import com.ubtrobot.transport.message.CallException;
import com.ubtrobot.transport.message.Request;
import com.ubtrobot.transport.message.Response;
import com.ubtrobot.transport.message.ResponseCallback;

public class ShowWuKongInfoActivity extends Activity {
    private static final String TAG = "showWuKongInfoActivity";

    private EventApi sysEventApi;
    private BatteryEventReceiver mBatteryEventReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showwukong);

        initRobot();
        subscribeAllEvents();
    }

    public void upgrade(View view) {
        SkillHelper.startSkillByIntent("general_update", null, getListener());
    }

    public void shutdown(View view) {
        SkillHelper.startSkillByIntent("关机", null, getListener());
    }


    public void standby(View view) {
        SkillHelper.startSkillByIntent("待机", null, getListener());
    }

    @NonNull
    private ResponseCallback getListener() {
        return new ResponseCallback() {
            @Override public void onResponse(Request request, Response response) {
                Log.i(TAG, "start success.");
                Toast.makeText(getApplicationContext(), "start success.", Toast.LENGTH_LONG).show();
            }

            @Override public void onFailure(Request request, CallException e) {
                Log.i(TAG, e.getMessage());
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }


    public void subscribeAllEvents(){
        mBatteryEventReceiver = new BatteryEventReceiver() {
            @Override
            public boolean onReceive(BatteryEvent event) {
                Log.d(TAG, "BatteryEvent=======onReceive");
                return true;
            }
        };
        SysEventApi.get().subscribe(BatteryEvent.newInstance(), mBatteryEventReceiver);
    }

    /**
     * 初始化接口类实例
     */
    private void initRobot() {
        sysEventApi = SysEventApi.get();
    }

    /**
     * 异步获取当前电量信息
     *
     * @param view
     */
    public void getCurrentBatteryInfo(View view) {
        sysEventApi.getCurrentBatteryInfo(new GetBatteryInfoListener() {
            @Override
            public void onSuccess(SysMasterEvent.BatteryStatusData data) {
                Log.i(TAG, "getCurrentBatteryInfo接口调用成功！");
                Log.i(TAG, "BatteryStatusData getStatus======" + data.getStatus());
                Log.i(TAG, "BatteryStatusData getLevel======" + data.getLevel());
                Log.i(TAG, "BatteryStatusData getLevelStatus======" + data.getLevelStatus());
                Toast.makeText(getApplicationContext(), "BatteryStatusData getLevel======" + data.getLevel(), Toast.LENGTH_LONG).show();
            }

            @Override public void onFiald(CallException e) {
                Log.i(TAG, "getCurrentBatteryInfo接口调用失败:" + e.getMessage());
                Toast.makeText(getApplicationContext(), "getCurrentBatteryInfo接口调用失败:" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    protected void onDestroy() {
        unsubscribeAllReceivers();
        super.onDestroy();
    }

    private void unsubscribeAllReceivers() {
        if(mBatteryEventReceiver!=null){
            sysEventApi.unsubscribe(mBatteryEventReceiver);
        }
    }
}
