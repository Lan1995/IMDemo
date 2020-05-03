Netty版本的IM
### COMMON
common模块包含客户端,服务器端通用的代码逻辑
1. 通用的ChanelHandler
- 拆包粘包处理器
- 编解码器
- 心跳
2. 自定义协议相关的抽象类以及对应的工具类
自定义协议如下
|魔术(4 byte) | 协议版本(1 byte)|序列化算法类型(1 byte) | 指令(1 byte)  | 数据长度 (4 byte)|data|
- 协议版本
- 自定义序列化

**记录**
1. writeAndFlush会进行类型比较,如果类型一致才会直接返回
MessageToMessageEncoder.class 

line 80
```$xslt
 @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        CodecOutputList out = null;
        try {
            if (acceptOutboundMessage(msg)) {
                out = CodecOutputList.newInstance();
                @SuppressWarnings("unchecked")
                I cast = (I) msg;
                try {
                    encode(ctx, cast, out);
                } finally {
                    ReferenceCountUtil.release(cast);
                }

                if (out.isEmpty()) {
                    out.recycle();
                    out = null;

                    throw new EncoderException(
                            StringUtil.simpleClassName(this) + " must produce at least one message.");
                }
            } else {
                ctx.write(msg, promise);
            }
        } catch (EncoderException e) {
            throw e;
        } catch (Throwable t) {
            throw new EncoderException(t);
        } finally {
            if (out != null) {
                final int sizeMinusOne = out.size() - 1;
                if (sizeMinusOne == 0) {
                    ctx.write(out.get(0), promise);
                } else if (sizeMinusOne > 0) {
                    // Check if we can use a voidPromise for our extra writes to reduce GC-Pressure
                    // See https://github.com/netty/netty/issues/2525
                    ChannelPromise voidPromise = ctx.voidPromise();
                    boolean isVoidPromise = promise == voidPromise;
                    for (int i = 0; i < sizeMinusOne; i ++) {
                        ChannelPromise p;
                        if (isVoidPromise) {
                            p = voidPromise;
                        } else {
                            p = ctx.newPromise();
                        }
                        ctx.write(out.getUnsafe(i), p);
                    }
                    ctx.write(out.getUnsafe(sizeMinusOne), promise);
                }
                out.recycle();
            }
        }
    }
```

