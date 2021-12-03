package edu.cn.WuKongadminister.skill;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.protobuf.StringValue;
import com.ubtech.utilcode.utils.thread.HandlerUtils;
import com.ubtechinc.skill.ProxySkill;
import com.ubtechinc.skill.SkillApi;
import com.ubtechinc.skill.SkillHelper;
import com.ubtechinc.skill.SkillType;
import com.ubtrobot.commons.ResponseListener;
import com.ubtrobot.master.annotation.Call;
import com.ubtrobot.master.param.ProtoParam;
import com.ubtrobot.master.skill.SkillStopCause;
import com.ubtrobot.mini.sysevent.event.base.KeyEvent;
import com.ubtrobot.speech.protos.Speech;
import com.ubtrobot.transport.message.CallException;
import com.ubtrobot.transport.message.Request;
import com.ubtrobot.transport.message.Responder;
import com.ubtrobot.transport.message.Response;
import com.ubtrobot.transport.message.ResponseCallback;

import java.util.List;

public class RecognitionUnInterrutibleSkill extends ProxySkill {

    private static final String TAG = "InterruptibleSkill";

    @Override
    protected void onSkillStart() {
        super.onSkillStart();
        Log.d(TAG, "可打断技能启动 ");
        HandlerUtils.runUITask(() -> SkillHelper.pushMessageToPhone(" {\"name\": \"John Doe\", \"age\": 18, \"address\": {\"country\" : \"china\", \"zip-code\": \"10000\"}}","1"), 3000);
    }

    @Override
    protected void onSkillStop(SkillStopCause skillStopCause) {
        super.onSkillStop(skillStopCause);
        Log.d(TAG, "可打断技能停止："+skillStopCause);
    }

    @Call(path = "/demo_interruptible/startNod")
    public void onStartNod(Request request, final Responder responder){
        Log.d(TAG,"onStartSkill---");
        SkillHelper.startSkillByIntent("nod", null, getListener());
        responder.respondSuccess();
    }

    @Call(path = "/demo_interruptible/startShake")
    public void onStartShake(Request request, final Responder responder){
        Log.d(TAG,"onStartSkill---");
        SkillHelper.startSkillByIntent("shake_head", null, getListener());
        responder.respondSuccess();
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

    @Override
    protected String getSkillName() {
        return "demo_interruptible_skill";
    }

    @Override
    protected SkillType getSkillType() {
        return SkillType.Interruptible;
    }

    @Override
    protected SkillType getSubSkillType() {
        return null;
    }

    @Override
    protected boolean onHeadTapEvent(KeyEvent keyEvent) {
        stopSkill();
        return false;
    }

    @Override
    protected void onWakeUpEvent(Speech.WakeupParam wakeupParam) {
        stopSkill();
    }

    @Override
    protected boolean isNeedWakeUpEvent() {
        return true;
    }

    @Override
    protected boolean isNeedHeadTapEvent() {
        return true;
    }
}
