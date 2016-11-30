package impl;

import java.nio.ByteBuffer;

import api.ByteBufferSerializableObject;
import impl.gen.model.HeaderHolder;

/**
 * Complete packet, contains useful data itself and metadata which describes it.
 */
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

	/**
     * The size of packet which contains a body.
     * @return body size
     */
    @Override
    public int size() {
        return body.size();
    }
}
