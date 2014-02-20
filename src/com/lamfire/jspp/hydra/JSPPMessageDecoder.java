package com.lamfire.jspp.hydra;

import com.lamfire.hydra.Message;
import com.lamfire.jspp.JSPP;

/**
 * JSPP消息解码接口
 * User: lamfire
 * Date: 14-2-14
 * Time: 下午4:56
 * To change this template use File | Settings | File Templates.
 */
public interface JSPPMessageDecoder {

    /**
     * 解码二进制消息为JSPP协议
     * @param message
     * @return
     */
    public JSPP decode(Message message);
}
