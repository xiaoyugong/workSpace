package Share.src.main.java.com.yao.module;

import java.io.Serializable;

/**
 * Created by yaozb on 15-4-11.
 * 蹇呴』瀹炵幇搴忓垪,serialVersionUID 涓�瀹氳鏈�
 */

public abstract class BaseMsg  implements Serializable {
    private static final long serialVersionUID = 1L;
    private MsgType type;
    //蹇呴』鍞竴锛屽惁鑰呬細鍑虹幇channel璋冪敤娣蜂贡
    private String clientId;

    //鍒濆鍖栧鎴风id
    public BaseMsg() {
        this.clientId = Constants.getClientId();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }
}
