package service;


import java.nio.ByteBuffer;

import static config.NetworkConfig.READ_BUFFER_SIZE_BYTES;
import static config.NetworkConfig.WRITE_BUFFER_SIZE_BYTES;

public final class ByteBufferService {
	private static final ByteBuffer readBuffer = ByteBuffer.allocate(READ_BUFFER_SIZE_BYTES);
	private static final ByteBuffer writeBuffer = ByteBuffer.allocate(WRITE_BUFFER_SIZE_BYTES);

	public ByteBuffer getReadBuffer() {
		readBuffer.clear();
//		readBuffer.put(new byte[1024]);
//		readBuffer.clear();

		return readBuffer;
	}

	public ByteBuffer getWriteBuffer() {
		writeBuffer.clear();
//		writeBuffer.put(new byte[1024]);
//		writeBuffer.clear();

		return writeBuffer;
	}
}
