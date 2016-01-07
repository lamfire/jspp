package com.lamfire.jspp;

import com.lamfire.utils.StringUtils;

/**
 * TO
 * User: lamfire
 * Date: 13-11-11
 * Time: 下午8:40
 * To change this template use File | Settings | File Templates.
 */
public class TO {
    private String id;
    private String domain;

    public TO(){

    }

    public TO(String id,String domain){
        this.id = id;
        this.domain = domain;
    }

    public TO(String to){
        if(StringUtils.isBlank(to)){
            return;
        }
        String[] ss = StringUtils.split(to, '@');
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

    public void setId(String id) {
        this.id = id;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String toString(){
        return String.format("%s@%s", id, domain);
    }
}
