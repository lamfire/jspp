package com.lamfire.jspp;

import com.lamfire.utils.StringUtils;

/**
 * 来自
 * User: lamfire
 * Date: 13-11-11
 * Time: 下午8:40
 * To change this template use File | Settings | File Templates.
 */
public class FROM {
    private String id;
    private String domain;

    public FROM(){

    }

    public FROM(String id,String domain){
        this.id = id;
        this.domain = domain;
    }

    public FROM(String from){
        if(StringUtils.isBlank(from)){
            return;
        }
        String[] ss = StringUtils.split(from, '@');
        if(ss.length == 2){
            id = ss[0];
            domain = ss[1];
        }
    }

    public String getId() {
        return id;
    }

    public String getDomain() {
        return domain;
    }



    public String toString(){
        return String.format("%s@%s", id, domain);
    }
}
