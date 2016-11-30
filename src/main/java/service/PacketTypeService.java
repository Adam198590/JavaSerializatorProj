package service;

import api.ByteBufferSerializableObject;
import enums.PacketType;
import impl.gen.packet.LoginBody;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Service with auto generated storage which supplies a packet instance.
 */
public final class PacketTypeService {

    private static final Map<PacketType, Supplier<? extends ByteBufferSerializableObject>> packetTypes;

    static {
        packetTypes = new EnumMap<>(PacketType.class);

        //=================here we add new packets==================
        packetTypes.put(PacketType.LOGIN, LoginBody::new);
        //==========================================================
    }

    public ByteBufferSerializableObject getPacketInstance(PacketType packetType) {
        return packetTypes.get(packetType).get();
    }
}
