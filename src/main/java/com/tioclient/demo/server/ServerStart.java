package com.tioclient.demo.server;

import com.tioclient.demo.client.MyClientAioHandler;
import com.tioclient.demo.client.MyPacket;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.core.Aio;
import org.tio.core.Node;
import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;

@Component
public class ServerStart {
    private static Node note = new Node("127.0.0.1", 8099);
    private static MyServerAioHandler myServerAioHandler = new MyServerAioHandler();
    private static ReconnConf reconnConf = new ReconnConf(50000L);
    private static ServerGroupContext serverGroupContext = new ServerGroupContext("SERVER", myServerAioHandler, null);

    private AioServer aioServer = null;
    private ClientChannelContext clientChannelContext = null;

    public ServerStart() {

    }

    public void start() throws java.lang.Exception {
        aioServer = new AioServer(serverGroupContext);
        aioServer.start(note.getIp(),note.getPort());
    }

}
