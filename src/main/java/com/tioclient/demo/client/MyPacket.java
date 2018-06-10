package com.tioclient.demo.client;

import org.tio.core.intf.Packet;

public class MyPacket extends Packet {
    public static final int HEADER_LENGHT = 4;
    public static final String CHARSET = "utf-8";
    private byte[] message = null;

    public MyPacket() {

    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public byte[] getMessage() {
        return message;
    }
}
