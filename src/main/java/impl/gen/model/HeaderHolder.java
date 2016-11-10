package impl.gen.model;

import api.ByteBufferSerializableObject;
import enums.PacketType;

import java.nio.ByteBuffer;

public class HeaderHolder implements ByteBufferSerializableObject {
    public static final int HEADER_SIZE = Integer.BYTES + Integer.BYTES;

    private int bodySize;
    public PacketType packetType;

    public HeaderHolder() {
        this.bodySize = 0;
        this.packetType = PacketType.UNKNOWN;
    }

    public HeaderHolder(int bodySize, PacketType packetType) {
        this.bodySize = bodySize;
        this.packetType = packetType;
    }

    @Override
    public void serialize(ByteBuffer dst) {
        dst.putInt(this.bodySize);
        dst.putInt(this.packetType.getValue());
    }

    @Override
    public void deserialize(ByteBuffer src) {
        this.bodySize = src.getInt();
        this.packetType = PacketType.fromInt(src.getInt());
    }

    @Override
    public int size() {
        return bodySize;
    }
}
