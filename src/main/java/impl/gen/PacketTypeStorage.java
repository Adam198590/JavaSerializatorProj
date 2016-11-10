package impl.gen;

import api.ByteBufferSerializableObject;
import enums.PacketType;
import impl.gen.packet.LoginBody;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Auto generated storage
 */
public class PacketTypeStorage {

    public static Map<PacketType, Supplier<? extends ByteBufferSerializableObject>> packetTypes;

    static {
        packetTypes = new EnumMap<>(PacketType.class);

        //=================here we add new packets==================
        packetTypes.put(PacketType.LOGIN, LoginBody::new);
        //==========================================================
    }
}
