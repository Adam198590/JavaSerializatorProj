package api;

import java.nio.ByteBuffer;

/**
 * Object which can be converted to bytes and vise versa.
 */
public interface ByteBufferSerializableObject {

    void serialize(ByteBuffer dst);

    void deserialize(ByteBuffer src);

    int size();
}
