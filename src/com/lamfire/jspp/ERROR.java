package com.lamfire.jspp;

import com.lamfire.json.JSON;

/**
 * 错误
 * User: lamfire
 * Date: 13-11-11
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public class ERROR extends JSON {

    public ERROR() {
    }

    public ERROR(int code, String body) {
        put("code",code);
        put("body",body);
    }

    public Integer getCode() {
        return (Integer)get("code");
    }

    public void setCode(int code) {
        put("code",code);
    }

    public String getBody() {
        return (String)get("body");
    }

    public void setBody(String body) {
        put("body",body);
    }


}
