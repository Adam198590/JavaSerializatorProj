package api;

import java.nio.ByteBuffer;

public interface ByteBufferSerializableObject {

    void serialize(ByteBuffer dst);

    void deserialize(ByteBuffer src);

    int size();
}