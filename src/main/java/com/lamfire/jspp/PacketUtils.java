package com.lamfire.jspp;


import com.lamfire.json.JSON;
import com.lamfire.logger.Logger;


/**
 * 数据包工且
 * User: lamfire
 * Date: 13-11-8
 * Time: 下午4:48
 * To change this template use File | Settings | File Templates.
 */
public class PacketUtils {
    private static final Logger LOGGER = Logger.getLogger(PacketUtils.class);

    public static JSPP decode(byte[] message) {
        if(message == null){
            return null;
        }
        return JSPPSerializer.get().decode(message);
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
