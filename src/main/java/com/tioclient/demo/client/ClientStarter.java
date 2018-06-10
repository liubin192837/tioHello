package com.tioclient.demo.client;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.core.Aio;
import org.tio.core.Node;

@Component
public class ClientStarter {

    private static Node note = new Node("127.0.0.1", 8099);
    private static MyClientAioHandler myClientAioHandler = new MyClientAioHandler();
    private static ReconnConf reconnConf = new ReconnConf(50000L);
    private static ClientGroupContext clientGroupContext = new ClientGroupContext(myClientAioHandler, null, reconnConf);

    private AioClient aioClient = null;
    private ClientChannelContext clientChannelContext = null;

    public ClientStarter() {

    }

    public void start() throws java.lang.Exception {
        aioClient = new AioClient(clientGroupContext);
        clientChannelContext = aioClient.connect(note);
        send();
    }

    private void send() throws java.lang.Exception {
        MyPacket myPacket = new MyPacket();
        myPacket.setMessage("Hi, are you ok".getBytes(MyPacket.CHARSET));
        Aio.send(clientChannelContext, myPacket);
    }

}
