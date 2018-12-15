package Server.src.main.java.com.yao;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;
import Share.src.main.java.com.yao.module.AskMsg;
import Share.src.main.java.com.yao.module.BaseMsg;
import Share.src.main.java.com.yao.module.LoginMsg;
import Share.src.main.java.com.yao.module.MsgType;
import Share.src.main.java.com.yao.module.PingMsg;
import Share.src.main.java.com.yao.module.ReplyClientBody;
import Share.src.main.java.com.yao.module.ReplyMsg;
import Share.src.main.java.com.yao.module.ReplyServerBody;

/**
 * Created by yaozb on 15-4-11.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<BaseMsg> {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyChannelMap.remove((SocketChannel)ctx.channel());
    }
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) throws Exception {

        if(MsgType.LOGIN.equals(baseMsg.getType())){
            LoginMsg loginMsg=(LoginMsg)baseMsg;
            if("robin".equals(loginMsg.getUserName())&&"yao".equals(loginMsg.getPassword())){
                //鐧诲綍鎴愬姛,鎶奵hannel瀛樺埌鏈嶅姟绔殑map涓�
                NettyChannelMap.add(loginMsg.getClientId(),(SocketChannel)channelHandlerContext.channel());
                System.out.println("client"+loginMsg.getClientId()+" 鐧诲綍鎴愬姛");
            }
        }else{
            if(NettyChannelMap.get(baseMsg.getClientId())==null){
                    //璇存槑鏈櫥褰曪紝鎴栬�呰繛鎺ユ柇浜嗭紝鏈嶅姟鍣ㄥ悜瀹㈡埛绔彂璧风櫥褰曡姹傦紝璁╁鎴风閲嶆柊鐧诲綍
                    LoginMsg loginMsg=new LoginMsg();
                    channelHandlerContext.channel().writeAndFlush(loginMsg);
            }
        }
        switch (baseMsg.getType()){
            case PING:{
                PingMsg pingMsg=(PingMsg)baseMsg;
                PingMsg replyPing=new PingMsg();
                NettyChannelMap.get(pingMsg.getClientId()).writeAndFlush(replyPing);
            }break;
            case ASK:{
                //鏀跺埌瀹㈡埛绔殑璇锋眰
                AskMsg askMsg=(AskMsg)baseMsg;
                if("authToken".equals(askMsg.getParams().getAuth())){
                    ReplyServerBody replyBody=new ReplyServerBody("server info $$$$ !!!");
                    ReplyMsg replyMsg=new ReplyMsg();
                    replyMsg.setBody(replyBody);
                    NettyChannelMap.get(askMsg.getClientId()).writeAndFlush(replyMsg);
                }
            }break;
            case REPLY:{
                //鏀跺埌瀹㈡埛绔洖澶�
                ReplyMsg replyMsg=(ReplyMsg)baseMsg;
                ReplyClientBody clientBody=(ReplyClientBody)replyMsg.getBody();
                System.out.println("receive client msg: "+clientBody.getClientInfo());
            }break;
            default:break;
        }
        ReferenceCountUtil.release(baseMsg);
    }
}
