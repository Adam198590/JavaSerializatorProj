package impl;

import api.ByteBufferSerializableObject;
import impl.gen.model.HeaderHolder;

import java.nio.ByteBuffer;

public class ByteBufferPacket implements ByteBufferSerializableObject {
    private HeaderHolder headerHolder;
    private final ByteBufferSerializableObject body;

    public ByteBufferPacket(HeaderHolder headerHolder, ByteBufferSerializableObject body) {
        this.headerHolder = headerHolder;
        this.body = body;
    }

    @Override
    public void serialize(ByteBuffer dst) {
        this.headerHolder.serialize(dst);
        this.body.serialize(dst);
    }

    @Override
    public void deserialize(ByteBuffer src) {
        this.headerHolder.deserialize(src);
        this.body.deserialize(src);
    }

    @Override
    public int size() {
        return headerHolder.size();
    }
}