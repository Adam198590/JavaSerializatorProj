package apitest;

import java.nio.ByteBuffer;

public class TestByteBuffer {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String s = "sadasd";

        buffer.clear();
        buffer.putInt(123);
        buffer.putInt(s.length());
        buffer.put(s.getBytes());

        buffer.flip();

        System.out.println(buffer.getInt());

        byte[] bytes = new byte[buffer.getInt()];
        buffer.get(bytes);
        System.out.println(new String(bytes));
    }
}
