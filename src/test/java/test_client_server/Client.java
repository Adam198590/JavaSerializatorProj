package test_client_server;


import api.ByteBufferSerializableObject;
import enums.PacketType;
import impl.ByteBufferPacket;
import impl.gen.model.HeaderHolder;
import impl.gen.model.UserData;
import impl.gen.packet.LoginBody;
import util.SocketChannelUtil;

import java.nio.channels.SocketChannel;

import static test_client_server.Server.log;

public class Client {
    private static SocketChannelUtil socketChannelUtil = new SocketChannelUtil();

    public static void main(String[] args) throws Exception {
        SocketChannel socketClient = socketChannelUtil.getSocketChannel("localhost", 1111);
        log("Connecting to Server on port 1111...");

        ByteBufferPacket packet = buildCompleteTestPacket();

        socketChannelUtil.writePacket(socketClient, packet);

        socketClient.close();
    }

    private static ByteBufferPacket buildCompleteTestPacket() {
        UserData userData = new UserData(123, "userName");
        ByteBufferSerializableObject body = new LoginBody(userData);
        HeaderHolder headerHolder = new HeaderHolder(body.size(), PacketType.LOGIN);

        return new ByteBufferPacket(headerHolder, body);
    }
}
