package com.tioclient.demo.client;


import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;

public class MyClientAioHandler implements ClientAioHandler {


    @Override
    public Packet heartbeatPacket() {
        return null;
    }

    @Override
    public Packet decode(ByteBuffer byteBuffer, ChannelContext channelContext) throws AioDecodeException {
        int readableLength = byteBuffer.limit() - byteBuffer.position();
        //收到的数据组不了业务包，则返回null以告诉框架数据不够
        if (readableLength < MyPacket.HEADER_LENGHT) {
            return null;
        }

        //读取消息体的长度
        int bodyLength = byteBuffer.getInt();

        //数据不正确，则抛出AioDecodeException异常
        if (bodyLength < 0) {
            throw new AioDecodeException("bodyLength [" + bodyLength + "] is not right, remote:" + channelContext.getClientNode());
        }

        //计算本次需要的数据长度
        int neededLength = MyPacket.HEADER_LENGHT + bodyLength;
        //收到的数据是否足够组包
        int isDataEnough = readableLength - neededLength;
        // 不够消息体长度(剩下的buffe组不了消息体)
        if (isDataEnough < 0) {
            return null;
        } else //组包成功
        {
            MyPacket imPacket = new MyPacket();
            if (bodyLength > 0) {
                byte[] dst = new byte[bodyLength];
                byteBuffer.get(dst);
                imPacket.setMessage(dst);
            }
            return imPacket;
        }
    }

    @Override
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
        MyPacket myPacket = (MyPacket) packet;
        byte[] body = myPacket.getMessage();
        int bodyLen = 0;
        if (body != null) {
            bodyLen = body.length;
        }

        //bytebuffer的总长度是 = 消息头的长度 + 消息体的长度
        int allLen = MyPacket.HEADER_LENGHT + bodyLen;
        //创建一个新的bytebuffer
        ByteBuffer buffer = ByteBuffer.allocate(allLen);
        //设置字节序
        buffer.order(groupContext.getByteOrder());

        //写入消息头----消息头的内容就是消息体的长度
        buffer.putInt(bodyLen);

        //写入消息体
        if (body != null) {
            buffer.put(body);
        }
        return buffer;
    }

    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        MyPacket myPacket = (MyPacket) packet;
        byte[] body = myPacket.getMessage();
        if (body != null) {
            String str = new String(body, MyPacket.CHARSET);
            System.out.println("收到消息：" + str);
        }
        return;
    }
}

