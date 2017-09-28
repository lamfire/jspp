package com.lamfire.jspp;

import com.lamfire.json.JSON;

import java.util.Map;

/**
 * JSPP MESSAGE
 * User: lamfire
 * Date: 13-11-11
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
public class MESSAGE extends JSPP{
    public static final String TYPE_DEFAULT = "default";
    public static final String TYPE_CHAT = "chat";
    public static final String TYPE_GROUPCHAT = "groupchat";
    public static final String TYPE_ERROR = "error";

    private ATTACH attach;

    public ATTACH getAttach() {
        if(attach != null){
            return attach;
        }
        Map<String,Object> map = (Map<String,Object>) get("attach");
        if(map == null){
            return null;
        }
        attach = new ATTACH();
        attach.putAll(map);
        return attach;
    }

    public void setAttach(ATTACH attach) {
        this.attach = attach;
        put("attach",attach);
    }

    public String toString(){
        JSON json = new JSON();
        json.put(JSPP_TYPE_PREFIX_MESSAGE,this);
        return json.toJSONString();
    }

    public static MESSAGE valueOf(String jsppString) {
        JSON json = JSON.fromJSONString(jsppString);
        JSON jspp = (JSON)json.get(JSPP.JSPP_TYPE_PREFIX_MESSAGE);
        if(jspp == null){
            return null;
        }
        MESSAGE m = new MESSAGE();
        m.putAll(jspp);
        return m;
    }
}
