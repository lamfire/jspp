package com.lamfire.jspp;

import com.lamfire.json.JSON;

import java.util.Map;

/**
 * JSPP抽象类
 * User: lamfire
 * Date: 13-11-13
 * Time: 下午4:13
 * To change this template use File | Settings | File Templates.
 */
public abstract class JSPP extends JSON {
    private static Serializer serializer = new JSPPSerializer();
    public static final String JSPP_TYPE_PREFIX_SERVICE = "service";
    public static final String JSPP_TYPE_PREFIX_MESSAGE = "message";
    public static final String JSPP_TYPE_PREFIX_DISCOVERY = "discovery";

    private ERROR error;

    public String getType() {
        return (String)get("type");
    }

    public void setType(String type) {
        put("type",type);
    }

    public String getFrom() {
        return (String)get("from");
    }

    public void setFrom(String from) {
        put("from",from);
    }

    public String getTo() {
        return (String)get("to");
    }

    public void setTo(String to) {
        put("to",to);
    }

    public String getId() {
        return (String)get("id");
    }

    public void setId(String id) {
        put("id",id);
    }

    public String getBody() {
        return (String)get("body");
    }

    public void setBody(String body) {
        put("body",body);
    }

    public ERROR getError() {
        if(error != null){
            return error;
        }
        Map<String,Object> map = ( Map<String,Object>)get("error");
        if(map == null){
            return null;
        }
        error = new ERROR();
        error.setCode((Integer)map.get("code"));
        error.setBody((String)map.get("body"));
        return error;
    }

    public void setError(ERROR error) {
        this.error = error;
        put("error",error);
    }

    public static ProtocolType getProtocolType(JSPP jspp){
        if(jspp instanceof MESSAGE){
            return ProtocolType.MESSAGE;
        }

        if(jspp instanceof SERVICE){
            return ProtocolType.SERVICE;
        }

        if(jspp instanceof DISCOVERY){
            return ProtocolType.DISCOVERY;
        }
        return null;
    }

    public static byte[] serialize(JSPP jspp){
          return serializer.encode(jspp);
    }

    public static JSPP deserialize(byte[] bytes){
          return serializer.decode(bytes);
    }
}
