package util;

import api.ByteBufferSerializableObject;
import enums.PacketType;
import impl.ByteBufferPacket;
import impl.gen.PacketTypeStorage;
import impl.gen.model.HeaderHolder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Optional;
import java.util.function.Supplier;

//TODO - remove some serialize and deserialize methods, depends on what we need on current side (client/server).
//TODO - add metadata to specification.

public class SocketChannelUtil {

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
        ByteBuffer completeBuffer = getByteBufferFromStream(channel, HeaderHolder.HEADER_SIZE);
        HeaderHolder headerHolder = getHeaderFromByteBuffer(completeBuffer);
        ByteBuffer bodyBuffer = getByteBufferFromStream(channel, headerHolder.size());

        return getPacketBodyFromByteBuffer(bodyBuffer, headerHolder.packetType);
    }

//====================================UTILS=======================================================
    public static ByteBuffer putObjectToByteBuffer(ByteBufferPacket packet) {
        int completeSize = HeaderHolder.HEADER_SIZE + packet.size();

        ByteBuffer byteBuffer = ByteBuffer.allocate(completeSize);
        packet.serialize(byteBuffer);

        return (ByteBuffer) byteBuffer.flip();
    }

    private ByteBuffer getByteBufferFromStream(SocketChannel channel, int size) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(size);
        channel.read(buffer);
        return (ByteBuffer) buffer.rewind();
    }

    public HeaderHolder getHeaderFromByteBuffer(ByteBuffer src) {
        HeaderHolder headerHolder = new HeaderHolder();
        headerHolder.deserialize(src);

        return headerHolder;
    }

    public ByteBufferSerializableObject getPacketBodyFromByteBuffer(ByteBuffer src, PacketType packetType) {
        Optional<ByteBufferSerializableObject> oPacket = Optional.ofNullable(
                PacketTypeStorage.packetTypes.get(packetType))
                    .map(Supplier::get)
                    .map(packet -> (ByteBufferSerializableObject) packet);

        ByteBufferSerializableObject packet = oPacket.orElseThrow(IllegalArgumentException::new);
        packet.deserialize(src);

        return packet;
    }
}
