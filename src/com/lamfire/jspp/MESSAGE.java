package com.lamfire.jspp;

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
}
