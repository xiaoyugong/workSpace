package Share.src.main.java.com.yao.module;

/**
 * Created by yaozb on 15-4-11.
 * 鐧诲綍楠岃瘉绫诲瀷鐨勬秷鎭�
 */
public class LoginMsg extends BaseMsg {
    private String userName;
    private String password;
    public LoginMsg() {
        super();
        setType(MsgType.LOGIN);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
