package  util;

import java.nio.ByteBuffer;

public class ByteUtil {

    public static int getByteCapacityForString(String value) {
        return Integer.BYTES + value.getBytes().length;
    }

    public static String getStringFromByteBuffer(ByteBuffer src) {
        byte[] bytes = new byte[src.getInt()];
        src.get(bytes);

        return new String(bytes);
    }

    public static void putStringToByteBuffer(ByteBuffer dst, String value) {
        byte[] valueBytes = value.getBytes();

        dst.putInt(valueBytes.length);
        dst.put(valueBytes);
    }
}
