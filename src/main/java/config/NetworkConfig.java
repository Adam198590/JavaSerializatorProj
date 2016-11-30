package config;

public class NetworkConfig {

	public static final int PACKET_HEADER_SIZE_BYTES = Integer.BYTES + Integer.BYTES;

	public static final int READ_BUFFER_SIZE_BYTES = 32 * 1024;		//32 kB

	public static final int WRITE_BUFFER_SIZE_BYTES = 32 * 1024;	//32 kB
}
