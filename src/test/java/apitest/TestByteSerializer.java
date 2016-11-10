package apitest;

import api.ByteBufferSerializableObject;
import enums.PacketType;
import impl.ByteBufferPacket;
import impl.gen.model.HeaderHolder;
import impl.gen.model.UserData;
import impl.gen.packet.LoginBody;
import org.junit.Assert;
import org.junit.Test;
import util.ByteUtil;
import util.SocketChannelUtil;

import java.nio.ByteBuffer;

import static org.mockito.Mockito.spy;

public class TestByteSerializer {
    private SocketChannelUtil channelUtil = spy(new SocketChannelUtil());
    private final int TEST_BODY_SIZE = 16;

    @Test
    public void testSerialization() throws Exception {
        ByteBufferPacket packet = buildCompleteTestPacket();
        ByteBuffer loginPacketBuffer = channelUtil.putObjectToByteBuffer(packet);

        Assert.assertEquals(16, loginPacketBuffer.get(3));
        Assert.assertEquals(100, loginPacketBuffer.get(7));
        Assert.assertEquals(123, loginPacketBuffer.get(11));
        Assert.assertEquals(8, loginPacketBuffer.get(15));
        Assert.assertEquals(117, loginPacketBuffer.get(16));
        Assert.assertEquals(101, loginPacketBuffer.get(23));
    }

    @Test
    public void testHeaderDeserialization() throws Exception {
        ByteBuffer headerBytesFromChannel = buildTestHeaderBuffer(TEST_BODY_SIZE);

        HeaderHolder headerFromByteBuffer =
                channelUtil.getHeaderFromByteBuffer(headerBytesFromChannel);

        Assert.assertEquals(headerFromByteBuffer.packetType, PacketType.LOGIN);
        Assert.assertEquals(headerFromByteBuffer.size(), TEST_BODY_SIZE);
    }

    @Test
    public void testBodyDeserialization() throws Exception {
        ByteBuffer bodyBuf = buildTestBodyBuffer(TEST_BODY_SIZE);

        ByteBufferSerializableObject packetBodyFromByteBuffer =
                channelUtil.getPacketBodyFromByteBuffer(bodyBuf, PacketType.LOGIN);

        Assert.assertEquals(packetBodyFromByteBuffer.size(), TEST_BODY_SIZE);
        Assert.assertEquals(((LoginBody) packetBodyFromByteBuffer).userData.uid, 123);
        Assert.assertEquals(((LoginBody) packetBodyFromByteBuffer).userData.name, "userName");
    }

    private static ByteBufferPacket buildCompleteTestPacket() {
        UserData userData = new UserData(123, "userName");
        ByteBufferSerializableObject body = new LoginBody(userData);
        HeaderHolder headerHolder = new HeaderHolder(body.size(), PacketType.LOGIN);

        return new ByteBufferPacket(headerHolder, body);
    }

    private ByteBuffer buildTestHeaderBuffer(int bodySize) {
        ByteBuffer headerBuf = ByteBuffer.allocate(HeaderHolder.HEADER_SIZE);
        HeaderHolder headerHolder = new HeaderHolder(bodySize, PacketType.LOGIN);
        headerHolder.serialize(headerBuf);
        headerBuf.rewind();

        return headerBuf;
    }

    private static ByteBuffer buildTestBodyBuffer(int bodySize) {
        ByteBuffer bodyBuf = ByteBuffer.allocate(bodySize);
        bodyBuf.putInt(123);
        ByteUtil.putStringToByteBuffer(bodyBuf, "userName");
        bodyBuf.rewind();

        return bodyBuf;
    }
}
