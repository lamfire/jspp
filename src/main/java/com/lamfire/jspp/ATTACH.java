package com.lamfire.jspp;

import com.lamfire.json.JSON;

/**
 * MESSAGE附件
 * User: lamfire
 * Date: 13-11-13
 * Time: 下午4:16
 * To change this template use File | Settings | File Templates.
 */
public class ATTACH extends JSON {
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_LOCATION = "location";

    public String getType() {
        return (String)get("type");
    }

    public void setType(String type) {
        put("type",type);
    }

    public String getBody() {
        return (String)get("body");
    }

    public void setBody(String body) {
        put("body",body);
    }
}
