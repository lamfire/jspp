JSPP

# 1.介绍

JSPP(JSON Messaging and Presence Protocol 基于JSON的通讯表示协议)是一个基于JSON的即时消息(IM)服务协议。它继承了XMPP协议的灵活性，可以说是XMPP协议的JSON版。因此，基于JSPP的应用具有超强的可扩展性。经过扩展以后的JSPP可以通过发送扩展的信息来处理用户的需求，以及在JSPP的顶端建立如内容发布系统和基于地址的服务等应用程序。而且，JSPP包含了针对服务器端的软件协议，使之能与另一个进行通话，这使得开发者更容易建立客户应用程序或给一个配好系统添加功能。


在JSPP数据包中，包含三个顶级协义类型：
* 1.MESSAGE 	消息
* 2.PRESENCE 	存在
* 3.SERVICE     服务

JSPP六个通用属性：
* 1. "type"
* 2. "from"
* 3. "to"
* 4. "id"
* 5. "body"
* 6. "error"
注：每个顶级协义类型通过包含多个属性构成JSPP协议。



# 2.JSPP数据流


一次会话由两个平行的JSPP流组成，一个从客户端到服务器，另一个从服务器到客户端。当JSPP客户端连接上服务器时，这个客户端将发起客户端－>服务器JSPP流，同时服务器作为响应也将发起一个服务器－客户端JSPP流。所有的message,presence,service元素都被放在这个JSPP流中进行传输。

> 下面来个对话的简单例子：
> 用户ID为12345发送消息给ID为54321的用户：
{message:{from:"12345@lamfire.com",to:"54321@lamfire.com",body:"hello"}}

> ID为54321用户将接收到消息：
{message:{from:"12345@lamfire.com",to:"54321@lamfire.com",body:"hello"}}


# 3.MESSAGE协议详解
MESSAGE协议是JSPP协义中三个顶级协义类型中的一个。它被定义为两个实体间互相发送消息内容的协议载体。

属性列表
1.type  消息类型
2.from  来自
3.to    发送到
4.id    消息ID(可选)
5.body  消息内容
6.error 错误(由type属性决定必要必)
7.attach 附件(可选)


3.1 "type"属性
JSPP服务器支持几种不同的消息，这些消息通过type属性来进行区分。
type属性的有效值包括：
1.type=[default]:不设置type属性，本示本消息是一个普通消息.
例如：{message:{to:"5284750@lamfire.com",body:"hello"}}

2.type="chat":表示消息为一个接一个的对话消息，一般显示在聊天对话界面上。
例如：{message:{type:"chat",to:"5284750@lamfire.com",from:"123778@lamfire.com",body:"hello"}}

3.type="groupchat":表示消息为群聊消息，一般显示在聊天室对话界面上。
例如：{message:{type:"groupchat",to:"room1@lamfire.com",from:"123778@lamfire.com",body:"hello"}}

4.type="error":表示消息为一个错误。
例如：{message:{type:"error",to:"5284750@lamfire.com",from:"123778@lamfire.com",body:"hello",error:{code:404,body:"Not found"}}}

3.2 "from"属性
消息发送者标志。总的来说这个属性为必填元素，为了对付消息欺骗，这个属性一般由服务器自动设制。客户端发送者不需要关心该属性。
例子:
{presence:{from:"hayash@lamfire.com",to:"lamfire@lamfire.com",id:"ID_98"}}

3.3 "to"属性
表示消息接收者。该属性是必需的。
例子:
{presence:{from:"hayash@lamfire.com",to:"lamfire@lamfire.com",id:"ID_98"}}

3.4 "id"属性
作为消息的唯一标志。由JSPP客户端生成，用于跟踪消息。该属性是可选的。
例子:
{presence:{from:"hayash@lamfire.com",to:"lamfire@lamfire.com",id:"ID_98"}}

3.5 "body"属性
该属性为消息内容。一般为明文。
例子:
{presence:{from:"hayash@lamfire.com",to:"lamfire@lamfire.com",id:"ID_98",body:"hello"}}

3.6 "error"属性
当消息类型为"error"时，该元素才被使用。该属性包括"code(错误码)","body(错误描述)"两个子属性。
下面例子演示了向一个不存的用户发送消息后的回应包体：
发送:{message:{id:"U5284750_1",type:"chat",to:"5284750@lamfire.com",from:"123778@lamfire.com",body:"hello"}}
回应:{message:{id:"U5284750_1",type:"error",to:"5284750@lamfire.com",from:"123778@lamfire.com",body:"hello",error:{code:404,body:"Not found"}}}

3.7.attach附件
当消息包含附件时使用该元素。该属性包括"type"[image,audio,video,location]类型,"body"内容两个子属性。当type为[image,audio,video]时，body中的内容为URL地址；type为"location"时，body中的内容为地理坐标．
下面例子演示了向一个用户发送图片的消息体：
{message:{id:"U5284750_1",type:"chat",to:"5284750@lamfire.com",from:"123778@lamfire.com",attach:{type:"image",body:"http://www.lamfire.com/pic1.jpg"}}}
下面例子演示了向一个用户发送地理位置的消息:
{message:{id:"U5284750_1",type:"chat",to:"5284750@lamfire.com",from:"123778@lamfire.com",attach:{type:"location",body:"23.1452135,116.265412"}}}


# 4. PRESENCE协议详解
本协议提供用户、服务器等实体的可用性信息订阅/查询协议。一个用户可以与另一个用户进行在线状态信息的同步，通信大多以服务器推送的方式进行。
在线状态分为"available"和"unavailable",由type属性指定。"available" 状态表示可以立即收到消息。"unavailable"状态表示不能在当前时间收到消息。
默认情况下，所有状态都默认为"available"，除非显示指定type:"unavailable"除外。"available"的更多信息通过"body"和"status"元素进行指定。
在JSPP中,也常用PRESENCE来维护用户关系,比如subscribe(请求添加好友关系),unsubscribe(请求解除好友关系),subscribed(接受了好友关系),unsubscribed(拒绝或解除了好友关系)等.
属性列表
1.type  	类型
2.from  	来自
3.id    	消息ID(可选)
4.to    	发送到
5.status 	状态
6.body  	状态描述



4.1."type"属性
"type"属性根据不同目的来使用。除了提示其它用户本用户的可用性状态的"默认"用法外，还包括订阅、取消订阅、以及探测在线状态信息。
下面是"type"属性的可用值：
1.available(可用)
2.error(错误)
3.probe(探测)
4.subscribe(订阅)
5.subscribed(已订阅)
6.unavailable(不可用)
7.unsubscribe(取消订阅)
8.unsubscribed(订阅已取消)

4.1.1. type="available"
如果没有包含任何"type"属性，在线状态默认被设为 type="available"，用来表示用户在线。
当type="available"时，通常包含一个"show"元素以进一步说明可用类型；以及一个"body"(人能看懂的)描述。

例子：
{presence:{type:"available",from:"hayash@lamfire.com",to:"lamfire@s3.jspp.im",status:"away",body:"Stay but a little, I will come again."}}

status元素的可用值为：
1.away(离开) 在线，但已离开座位。
2.chatting(聊天中) 在线并正在聊天。
3.dnd(防打拢)  在线，但不想被打扰（"dnd"表示"do not disturb"）

"body":用户状态的描述。如，"开会中"是"away"的一个表现值，或者"忙于编码"可以是"dnd"的一个表现。

4.1.2. type="error"
当一个在线状态包发送给一个不存在的实体时，或在发送在线状态请求发生一个错误时，服务器都将返回一个type="error"的 在线状态包。
面是一个例子（注意域名的类型）：
{presence:{type="subscribe" to="lamfire@lamfire.comm"}}
回复的例子：
{presence:{to:"lamfire@lamfire.comm",from:"hayash@lamfire.com",type:"error",error:{code:"504",desc:"Remote server timeout. Unable to deliver packet."}}}

4.1.3. type="probe"
向一个特定实体（不能发送给自己）发出在线状态探测请求。服务器进行回应。
客户端发送探测一个指定用户是否在线的请求，如果存在跨域情况，通常由服务器端中转探测请求。
注意，只有被请求者在发起请求的用户的好友列表中，并且对方不处于隐身状态时，服务器才回正确反回响应。
在下面的例子中，我们将看到lamfire 向hayash发出一个探测请求，hayash所在服务器回复一个"状态报告".
探测请求的例子：
{presence:{from:"lamfire@lamfire.com",to:"hayash@lamfire.com",type="probe"}}

回复探测请求的例子：
{presence{type="available" from:"hayash@lamfire.com",to:"lamfire@lamfire.com",body:"Stay but a little, I will come again.",status:"away",stamp:15310309234715}}

4.1.4. type="subscribe"
申请订阅。
例子：
{presence:{from="lamfire@lamfire.com",to:"hayash@lamfire.com",type:"subscribe"}

4.1.5. type="subscribed"
表明接受了订阅请求。从现在起，当发送者的在线状态信息改变时，服务器将会把状态信息发送给接收者。
例子：
{presence:{from:"hayash@lamfire.com",to:"lamfire@lamfire.com",type:"subscribed"}}

4.1.6. type="unavailable"
表示目标不在线。
例子：
{presence:{from:"hayash@lamfire.com",to:"lamfire@lamfire.com",type:"unavailable",body:"Disconnnected"}}

4.1.7. type="unsubscribe"
取消在线状态信息订阅。
例子：
{presence:{from:"hayash@lamfire.com",to:"lamfire@lamfire.com",type:"unsubscribe"}}

4.1.8. type="unsubscribed"
这种类型的在线状态包有两个用途：
1.状态订阅已被取消。
2.拒绝一个订阅请求。服务器将不再发送在线状态信息给订阅者。
例子：
{presence:{ from:"lamfire@lamfire.com" ,to:"hayash@lamfire.com" ,type:"unsubscribed"}}

4.2. 其它属性

4.2.1."from"属性
发送者标识。"from"属性中必须写成user@host的格式。属性是必须的，而在实际应用中，都是服务器对该属性进行添加和修改（防止一些欺骗手段），因此客户端不需要考虑它。

4.2.2."id"属性
为在线状态请求包配置唯一的标识。"id"属性由客户端生成，客户端用其为在线状态包的顺序号进行验证。该属性是可选的。
例子：
{presence:{to:"lamfire@lamfire.com",id:"ID_98"}}

4.2.3."to"属性
标识接收者。该属性是必需的。
例子：
{presence:{to:"675784@offline",id:"ID_86"}}

服务器发给指定用户的在线状态例子：
{presence:{to:"lamfire@lamfire.com",from="hayash@lamfire.com",body:"Stay but a little, I will come again.",status:"away"}}

4.3.1."status"属性
可选的"status"元素告诉客户端如何显示一个用户的在线状态。
status元素的可用值为：
1.away(离开) 用户或实体在线，但已离开座位。
2.chatting(聊天中) 用户或实体在线并正在聊天。
3.dnd(防打拢)  用户或实体在线，但不想被打扰（"dnd"表示"do not disturb"）

4.3.2."body"属性
用户在线状态的描述。客户端包含一些默认设置；允许提供富有个性的描述如"我在吃午饭"或者"钓鱼中"。



# 5.服务(SERVICE)协议
主要用于远程服务调用，并且以JSON格式的数据传送请求和响应。

属性列表
1.ns		名称空间
2.args  	参数
3.result  	结果
4.type  	类型
5.from  	来自
6.id    	消息ID(可选)
7.to    	发送到
8.error    	错误
9.key 		密钥



5.1. 名字空间(ns)
在SERVICE元素中只能有一个ns元素。该元素用来标识SERVICE所要执行的具体功能.相当于一个业务方法.

5.2. 参数(args)
在SERVICE中的args子元素定义所执行的请求参数。查询拥有一个特殊的名字空间，这个名字空间是一个通过"ns"属性定义的元素。一个SERVICE元素中只能有一个args子元素。

下面是一个登陆请求例子:
{service:{type="get",ns:"user.auth",args:{username:"lamfire",digest:"f1e881517e9917bb815fed112d81d32b4e4b3aed"}}}

5.3. 结果(result)
在SERVICE中的result子元素定义所执行的请求返回结果。

下面是一个登陆请求所返回result例子:
{service:{type="result",ns:"user.auth",args:{username:"lamfire",digest:"f1e881517e9917bb815fed112d81d32b4e4b3aed"},result:{id:"74Ed74362BA347653",nickname:"linfan"}}}


5.4.    "type"属性
SERVICE属性的"type"属性用于决定信息/查询是请求还是响应等。
下面是"type"属性的可用值：
1.error(错误)
2.get(获取)
3.result(结果)
4.set(设置)

5.4.1. type="error"
表示请求失败。实际错误在"error"子元素中描述。
例子：
{service:{ns:"user.get",type:"error",error:{code=403,body:"Forbidden"}}}

5.4.2. type="get"
请求一个"名称空间"的信息。"get"有一个"args"子属性来表示查询的条件。一次成功的查询请求将返回 type="result"结果集。
例子：
{service:{type="get",ns:"user.auth",args:{username:"lamfire",digest:"f1e881517e9917bb815fed112d81d32b4e4b3aed"}}}

5.4.3. type="result"
表示type="get"或type="set"IQ查询的成功响应。这个成功查询的结果是一个 type="result"的IQ元素，该元素嵌套在一个包含所查询的信息的args。
失改则返回一个type="error"的IQ元素。
例子：
{service:{type:"result",ns:"user.auth",result:{key:"56AE098345788BA98FD43"}}}

5.4.4. type="set"
表示SERVICE是设值或更改现有数据值。一个type="set"的元素总是包含一个指定的args子元素。成功则返回是一个type="result"的SERVICE元素。
例子：
{service:{type:"set",args:{nickname:"lily",age:18,gender:"female"}}}


5.5.  "from"属性
标识发送者。一般"from"属性中必须写成user@host的格式。这个属性不是必须的，而在实际应用中，都是服务器对该属性进行添加和修改（防止一些欺骗的手段），因此客户端不需要考虑它。
例子：
{service:{from:"lamfire@lamfire.com"}}

5.6.  "id"属性
表示SERVICE包唯一的标识。由客户端生成和跟踪。属性是可选的，并且不能用于系统的其它地方。
请求：
{service:{type="get",to:"service.lamfire.com",id="ID_988",ns="user.get",args:{id:"123456"}}}

响应:
{service:{type:"result",to:"service.lamfire.com",id="ID_988",ns="user.get",args:{id:"123456"},result:{user:{...}}}}

5.7  "to"属性
标识SERVICE接收者。该属性在SERVICE中不是必需的。
例子：
{service:{type="get",to:"service.lamfire.com",id="ID_988",ns="user.get",args:{id:"123456"}}}


5.8. 错误(error)
当iq的属性类型被设置为"error"时，将用到本属性。包停驶错误代码和错误的文本描述。
比如：
{error:{code=400,desc:"Bad Request"}}

在附录中，列有常用错误代码及其对应的错误描述。

5.9. 密钥(key)
key元素为客户端－服务器之间交互提供一层安全保护。
当一个客户端发起一个与服务其之间的交互时，服务器将发送一个包含一个唯一值的key给客户端。
客户端在请求{service:{type="set"}的消息中，包含上诉key到子元素中。这样，服务器就能识别请求者身份。



就象你看到的那样，认证查询名称空间"user.auth"向服务器发送认证信息。




# 附录A 常用错误代码

下面是JSPP中错误代码的一些简要描述。服务器在产生错误是生成这些错误代码。错误代码以HTTP规格的RFC 2616为基础定义。JSPP并没有使用所有的HTTP的错误代码，并且当JSPP错误代码与HTTP错误代码对立时，请以JSPP定义为准。
> 错误码  描述 		说明
> 302  	重定向 		重定向错误。
> 400	无效请求 	客户端请求不能被识别。
> 401	授权失败 	错误的认证信息，如，在登陆服务器时使用错误的密码或用户名。
> 402	未授权		为未来使用进行保留，目前还不用。
> 403	禁止访问 	客户端的请求可以识别，但服务器拒绝执行。
> 404	没有找到 	表明服务器找不到任何匹配的内容，该内容可以是客户端发送消息的目的地。
> 405	不允许操作 	用在不允许操作。
> 406	不被接受 	用于服务器因为某些理由不接受该请求。
> 407	必须注册 	当前未被使用。
> 408	请求超时 	客户端在服务器未就绪时发起请求时，服务器生成返回408。
> 409	冲突 		略
> 500	服务器内部错误 	当服务器遇到预期外的错误时发生。如数据库连接失败等。
> 501	不可执行 	服务器不支持客户端请求的功能。
> 502	远程服务器错误 	因为无法到达远程服务器导致转发包失败时发生。
> 503	服务无法获得 	客户端请求一个服务，而服务器由于一些临时原因无法提供该服务。
> 504	远程服务器超时 	用于下列情况:连接服务器发生超时，错误的服务器名。
> 510	连接失败 	暂未使用。

