package com.lamfire.jspp.test;

import com.lamfire.hydra.Message;
import com.lamfire.hydra.MessageContext;
import com.lamfire.hydra.Snake;
import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.hydra.PacketUtils;
import com.lamfire.jspp.serializer.JSPPSerializer;

/**
 * 一个简单的JSPP服务器
 * User: lamfire
 * Date: 14-2-20
 * Time: 下午3:44
 * To change this template use File | Settings | File Templates.
 */
public class JSPPServer extends Snake {

    public JSPPServer(String host, int port) {
        super(host, port);
    }

    @Override
    protected void handleMessage(MessageContext context, Message message) {
        //解码JSPP消息
        JSPP jspp = PacketUtils.decode(message);
        //System.out.println("[RECEIVED]:"+jspp.toJSONString());

        //将JSPP消息返回
        PacketUtils.send(context,jspp);
    }

    public static void main(String[] args) {
        //实例化JSPP服务器
        JSPPServer server = new JSPPServer("0.0.0.0",7100);

        //开始侦听
        server.bind();

        System.out.println("JSPP Server startup on - " + server.getHost() +":" + server.getPort());
    }
}
