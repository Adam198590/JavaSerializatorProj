package util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Optional;

import api.ByteBufferSerializableObject;
import enums.PacketType;
import impl.ByteBufferPacket;
import impl.gen.model.HeaderHolder;
import service.ByteBufferService;
import service.PacketTypeService;

//TODO - remove some serialize and deserialize methods, depends on what we need on current side (client/server).

/**
 * Utility for read/write packets  from/to channel.
 */
public class SocketChannelUtil {
    private PacketTypeService packetTypeService = new PacketTypeService();
    private ByteBufferService byteBufferService = new ByteBufferService();

    public SocketChannel getSocketChannel(String ip, int port) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(ip, port));

        return channel;
    }

    public void writePacket(SocketChannel channel, ByteBufferPacket packet) throws IOException {
        ByteBuffer byteBuffer = putObjectToByteBuffer(packet);

        while(byteBuffer.hasRemaining()) {
            channel.write(byteBuffer);
        }
    }

    public ByteBufferSerializableObject readPacket(SocketChannel channel) throws Exception {
        ByteBuffer completeBuffer = getByteBufferFromChannel(channel);

        HeaderHolder headerHolderObject = getHeaderFromByteBuffer(completeBuffer);

        return getPacketBodyFromByteBuffer(completeBuffer, headerHolderObject.packetType);
    }

//====================================UTILS=======================================================
    public ByteBuffer putObjectToByteBuffer(ByteBufferPacket packet) {
        ByteBuffer byteBuffer = byteBufferService.getWriteBuffer();
        packet.serialize(byteBuffer);

        return (ByteBuffer) byteBuffer.flip();
    }

    public HeaderHolder getHeaderFromByteBuffer(ByteBuffer packetBuffer) throws Exception {
        HeaderHolder headerHolder = new HeaderHolder();
        headerHolder.deserialize(packetBuffer);

        return headerHolder;
    }

    public ByteBufferSerializableObject getPacketBodyFromByteBuffer(ByteBuffer byteBuffer, PacketType packetType) {
        Optional<ByteBufferSerializableObject> oPacket = Optional.ofNullable(
                packetTypeService.getPacketInstance(packetType)).
                map(packet -> packet);

        ByteBufferSerializableObject packet = oPacket.orElseThrow(IllegalArgumentException::new);
        packet.deserialize(byteBuffer);

        return packet;
    }

    private ByteBuffer getByteBufferFromChannel(SocketChannel channel) throws Exception {
        ByteBuffer buffer = byteBufferService.getReadBuffer();
        channel.read(buffer);

        return (ByteBuffer) buffer.rewind();
    }
}
