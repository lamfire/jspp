package com.lamfire.jspp.test;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.MessageContext;
import com.lamfire.hydra.Snake;
import com.lamfire.hydra.net.Session;
import com.lamfire.hydra.reply.ReplyFuture;
import com.lamfire.hydra.reply.ReplySnake;
import com.lamfire.jspp.ATTACH;
import com.lamfire.jspp.IQ;
import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.MESSAGE;
import com.lamfire.jspp.hydra.PacketUtils;

/**
 * 一个简单的JSPP客户端
 * User: lamfire
 * Date: 14-2-20
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 */
public class JSPPClient {

    public static void main(String[] args) {
        //创建网络连接对象
        ReplySnake snake = new ReplySnake("127.0.0.1",7100);
        //建立连接
        snake.connect();

        //创建JSPP消息
        MESSAGE m = new MESSAGE();
        m.setType(MESSAGE.TYPE_CHAT);
        m.setTo("lamfire@jspp.im");
        m.setFrom("medusa@jspp.im");
        m.setBody("hello");
        m.setId("100001");

        //设置附件
        ATTACH attach = new ATTACH();
        attach.setType(ATTACH.TYPE_IMAGE);
        attach.setBody("http://www.lamfire.com/1.jpg");
        m.setAttach(attach);

        //设置自定义属性
        m.put("icon", "http://www.lamfire.com/icon.jpg");

        int count = 0;
        long startAt = System.currentTimeMillis();
        while(true){
            //设置自定义属性
            m.put("iid",count);
            //发送消息
            ReplyFuture future = snake.send(PacketUtils.encode(m)) ;
            //获得响应数据
            byte[] bytes = future.getReply();

            //每1000次输出一次响应数据及响应时间
            if((++count) % 1000 == 0){
                System.out.println(count +" - " + new String(bytes) +" " + ( System.currentTimeMillis() - startAt) +"ms");
                startAt = System.currentTimeMillis();
            }
        }
    }
}
