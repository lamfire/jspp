package com.lamfire.jspp.serializer;

import com.lamfire.json.JSON;
import com.lamfire.jspp.*;

/**
 * JSPP序例化接口
 * User: lamfire
 * Date: 13-10-17
 * Time: 下午2:35
 */
public interface Serializer{
    public  byte[] encode(JSPP jspp);
    public JSPP decode(byte[] bytes);
    public ProtocolType getProtocolType(byte[] bytes);
}
