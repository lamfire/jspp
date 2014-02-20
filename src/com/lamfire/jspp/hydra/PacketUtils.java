package com.lamfire.jspp.hydra;


import com.lamfire.hydra.Message;
import com.lamfire.hydra.MessageContext;
import com.lamfire.hydra.net.Session;
import com.lamfire.json.JSON;
import com.lamfire.jspp.*;
import com.lamfire.jspp.serializer.JSPPSerializer;
import com.lamfire.logger.Logger;
import com.lamfire.utils.StringUtils;


/**
 * 数据包工且
 * User: lamfire
 * Date: 13-11-8
 * Time: 下午4:48
 * To change this template use File | Settings | File Templates.
 */
public class PacketUtils {
    private static final Logger LOGGER = Logger.getLogger(PacketUtils.class);

    private static void send(Session session,int id,byte[] bytes){
        Message m = new Message();
        m.setId(id);
        m.setBody(bytes);
        session.send(m);
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[SENDING]:"+new String(bytes));
        }
    }

    public static void send(MessageContext context,JSPP jspp){
        byte[] bytes = encode(jspp);
        context.send(context.getMessage().getId(),bytes);
    }

    public static void send(Session session,int messageId,IQ iq){
        send(session,messageId, encode(iq));
    }

    public static void send(Session session,int messageId,MESSAGE message){
        send(session,messageId,encode(message));
    }

    public static void send(Session session,int messageId,PRESENCE presence){
        send(session,messageId,encode(presence));
    }


    public static JSPP decode(Message message) {
        if(message == null){
            return null;
        }
        byte[] bytes = message.getBody();
        if(bytes == null){
            return null;
        }
        return JSPPSerializer.get().decode(bytes);
    }

    public static byte[] encode(JSPP jspp){
        return JSPPSerializer.get().encode(jspp);
    }

    public ProtocolType getProtocolType(byte[] bytes)    {
        return JSPPSerializer.get().getProtocolType(bytes) ;
    }

    public ProtocolType getProtocolType(String json)    {
        return JSPPSerializer.get().getProtocolType(json) ;
    }

    public static ProtocolType getProtocolType(JSON json){
        return JSPPSerializer.get().getProtocolType(json);
    }
}
