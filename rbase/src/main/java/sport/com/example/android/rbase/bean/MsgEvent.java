package sport.com.example.android.rbase.bean;

/**
 * Created by android on 2018/3/28.
 * 信息管理类
 */

public class MsgEvent {

    private String action;
    private Object data;

    public MsgEvent(String action,Object data){
        this.action=action;
        this.data=data;
    }

    public String getAction() {
        return action;
    }
    public Object getData() {
        return data;
    }

}
