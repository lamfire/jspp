package com.lamfire.jspp.test;

import com.lamfire.jspp.ATTACH;
import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.MESSAGE;

/**
 * 序列化及反序列化
 * User: lamfire
 * Date: 14-2-13
 * Time: 下午4:10
 * To change this template use File | Settings | File Templates.
 */
public class JSPPSerializerTest {
    public static void main(String[] args) {

        //创建JSPP消息
        MESSAGE m = new MESSAGE();
        m.setType(MESSAGE.TYPE_CHAT);
        m.setTo("lamfire@jspp.im");
        m.setFrom("medusa@jspp.im");
        m.setBody("hello");
        m.setId("100001");

        //设置附件
        ATTACH attach = new ATTACH();
        attach.setType(ATTACH.TYPE_IMAGE);
        attach.setBody("http://www.lamfire.com/1.jpg");
        m.setAttach(attach);

        //设置自定义属性
        m.put("icon","http://www.lamfire.com/icon.jpg");

        //序列化
        byte[] bytes = JSPP.serialize(m);
        System.out.println("[serialize]:" + bytes);

        //反序列化
        MESSAGE de = (MESSAGE)JSPP.deserialize(bytes);
        System.out.println("[deserialize]:" + de.toJSONString());
    }
}
