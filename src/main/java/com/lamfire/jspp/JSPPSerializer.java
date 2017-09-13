package com.lamfire.jspp;

import com.lamfire.json.JSON;
import com.lamfire.logger.Logger;

import java.nio.charset.Charset;


/**
 * JSPP元素序例化工具类
 * User: lamfire
 * Date: 14-2-13
 * Time: 下午5:07
 */
class JSPPSerializer implements Serializer {
    private static final Logger LOGGER = Logger.getLogger(JSPPSerializer.class);
    private final Charset CHARSET = Charset.forName("utf-8");
    private static final  JSPPSerializer SERIALIZER = new JSPPSerializer();
    public static  JSPPSerializer get(){
        return SERIALIZER;
    }

    @Override
    public byte[] encode(JSPP jspp) {
        if(jspp instanceof MESSAGE){
            return encodeMessage((MESSAGE)jspp);
        }

        if(jspp instanceof SERVICE){
            return encodeService((SERVICE) jspp);
        }

        if(jspp instanceof PRESENCE){
            return encodePresence((PRESENCE) jspp);
        }
        return null;
    }


    public byte[] encodeService(SERVICE service) {
        JSON json = new JSON();
        json.put(JSPP.JSPP_TYPE_PREFIX_SERVICE,service);
        String js = json.toJSONString();
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[ENCODE]:" +js);
        }
        return js.getBytes(CHARSET);
    }


    public byte[] encodeMessage(MESSAGE message) {
        JSON json = new JSON();
        json.put(JSPP.JSPP_TYPE_PREFIX_MESSAGE,message);

        String js = json.toJSONString();
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[ENCODE]:" +js);
        }
        return js.getBytes(CHARSET);
    }


    public byte[] encodePresence(PRESENCE presence) {
        JSON json = new JSON();
        json.put(JSPP.JSPP_TYPE_PREFIX_PRESENCE,presence);
        String js = json.toJSONString();
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[ENCODE]:" +js);
        }
        return js.getBytes(CHARSET);
    }


    public JSPP decode(byte[] bytes) {
        String js = new String(bytes,CHARSET);
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[DECODE]:" +js);
        }
        JSON json = JSON.fromJSONString(js);
        JSON jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_MESSAGE);
        if(jspp != null){
            MESSAGE m = new MESSAGE();
            m.putAll(jspp);
            return m;
        }

        jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_SERVICE);
        if(jspp != null){
            SERVICE m = new SERVICE();
            m.putAll(jspp);
            return m;
        }

        jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_PRESENCE);
        if(jspp != null){
            PRESENCE m = new PRESENCE();
            m.putAll(jspp);
            return m;
        }
        return null;
    }


    public SERVICE decodeService(byte[] bytes) {
        String js = new String(bytes,CHARSET);
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[DECODE]:" +js);
        }
        JSON json = JSON.fromJSONString(js);
        JSON jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_SERVICE);
        if(jspp == null){
            return null;
        }
        SERVICE m = new SERVICE();
        m.putAll(jspp);
        return m;
    }


    public MESSAGE decodeMessage(byte[] bytes) {
        String js = new String(bytes,CHARSET);
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[DECODE]:" +js);
        }
        JSON json = JSON.fromJSONString(js);
        JSON jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_MESSAGE);
        if(jspp == null){
            return null;
        }
        MESSAGE m = new MESSAGE();
        m.putAll(jspp);
        return m;
    }


    public PRESENCE decodePresence(byte[] bytes) {
        String js = new String(bytes,CHARSET);
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("[DECODE]:" +js);
        }
        JSON json = JSON.fromJSONString(js);
        JSON jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_PRESENCE);
        if(jspp == null){
            return null;
        }
        PRESENCE m = new PRESENCE();
        m.putAll(jspp);
        return m;
    }


    public ProtocolType getProtocolType(byte[] bytes) {
        String js = new String(bytes,CHARSET);
        JSON json = JSON.fromJSONString(js);
        return getProtocolType(json);
    }

    public ProtocolType getProtocolType(String json) {
        return getProtocolType(JSON.fromJSONString(json));
    }

    public ProtocolType getProtocolType(JSPP jspp) {
        if(jspp instanceof  MESSAGE){
            return ProtocolType.MESSAGE;
        }else if(jspp instanceof PRESENCE){
            return ProtocolType.PRESENCE;
        }else if(jspp instanceof SERVICE){
            return ProtocolType.SERVICE;
        }
        return null;
    }

    public ProtocolType getProtocolType(MESSAGE jspp) {
         return ProtocolType.MESSAGE;
    }

    public ProtocolType getProtocolType(PRESENCE jspp) {
         return ProtocolType.PRESENCE;
    }

    public ProtocolType getProtocolType(SERVICE jspp) {
        return ProtocolType.SERVICE;
    }

    public ProtocolType getProtocolType(JSON json) {
        if(json instanceof  MESSAGE){
            return ProtocolType.MESSAGE;
        }else if(json instanceof PRESENCE){
            return ProtocolType.PRESENCE;
        }else if(json instanceof SERVICE){
            return ProtocolType.SERVICE;
        }

        JSON jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_MESSAGE);
        if(jspp != null){
            return ProtocolType.MESSAGE;
        }
        jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_SERVICE);
        if(jspp != null){
            return ProtocolType.SERVICE;
        }
        jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_PRESENCE);
        if(jspp != null){
            return ProtocolType.PRESENCE;
        }
        return null;
    }
}
