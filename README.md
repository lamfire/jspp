jspp
=====

1.介绍

JSPP(JSON Messaging and Presence Protocol 基于JSON的通讯和表示协议)是一个基于JSON的即时消息(IM)服务协议。它继承了XMPP协议的灵活性，可以说是XMPP协议的JSON版。因此，基于JSPP的应用具有超强的可扩展性。经过扩展以后的JSPP可以通过发送扩展的信息来处理用户的需求，以及在JSPP的顶端建立如内容发布系统和基于地址的服务等应用程序。而且，JSPP包含了针对服务器端的软件协议，使之能与另一个进行通话，这使得开发者更容易建立客户应用程序或给一个配好系统添加功能。

CIRCE是一个即时消息和在线状态系统。是一个拥有开放JSPP协议的即时消息（IM）服务系统。本文档将对JSPP协议1.0进行介绍。

在JSPP数据包中，circe包含三个顶级协义类型：
1.message 	    消息
2.discovery 	发现
3.service      	服务

六个通用属性：
1. "type"
2. "from"
3. "to"
4. "id"
5. "body"
6. "error"
每顶级协义类型通过包含多个属性构成JSPP协议。

=====================================================

2.JSPP数据流
一次会话由两个平行的JSPP流组成，一个从客户端到服务器，另一个从服务器到客户端。当circe客户端连接上服务器时，这个客户端将发起客户端－服务器JSPP流，同时服务器作为响应也将发起一个服务器－客户端JSPP流。所有的message,presence,iq元素都被放在这个JSPP流中进行传输。

下面来个对话的简单例子：
发送消息给ID为5284750的用户：
{message:{to:"5284750@s1.circe.lamfire.com",body:"hello"}}

5284750的用户接收到消息：
{message:{to:"5284750@s1.circe.lamfire.com",from:"12345@s2.circe.lamfire.com",body:"hello"}}
