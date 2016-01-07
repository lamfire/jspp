package com.lamfire.jspp.test;

import com.lamfire.hydra.*;
import com.lamfire.jspp.DISCOVERY;
import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.hydra.PacketUtils;

/**
 * 一个简单的JSPP服务器
 * User: lamfire
 * Date: 14-2-20
 * Time: 下午3:44
 * To change this template use File | Settings | File Templates.
 */
public class JSPPServer implements MessageReceivedListener {
    final Hydra hydra;
    public JSPPServer(String host, int port) {
        HydraBuilder builder = new HydraBuilder();
        builder.bind(host).port(port).messageReceivedListener(this);
        hydra = builder.build();

    }


    public void startup(){
        hydra.startup();
    }

    @Override
    public void onMessageReceived(Session session, Message message) {
        //解码JSPP消息
        JSPP jspp = PacketUtils.decode(message);
        System.out.println("[RECEIVED]:"+ PacketUtils.getProtocolType(jspp)  + ":" +jspp.toJSONString());


        DISCOVERY res = new DISCOVERY();
        res.setTo(jspp.getTo());
        res.setFrom(jspp.getFrom());
        res.setType(DISCOVERY.TYPE_AVAILABLE);

        Message response = MessageFactory.message(message.header().id(),message.header().option(),PacketUtils.encode(res));

        //将JSPP消息返回
        session.send(response);
    }


    public static void main(String[] args) {
        String host = "0.0.0.0";
        int port = 7100;

        //实例化JSPP服务器
        JSPPServer server = new JSPPServer(host,port);

        //开始侦听
        server.startup();

        System.out.println("JSPP Server startup on - " + host +":" + port);
    }
}
