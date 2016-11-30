package enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Stores the packet type, which can be converted into bytes together with packet body.
 */
public enum PacketType {
    LOGIN(100),
    UNKNOWN(-1);

    private final int value;

    PacketType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private final static Map<Integer, PacketType> map;

    static {
        map = Arrays.stream(values())
                .collect(Collectors.toMap(e -> e.value, e -> e));
    }

    public static PacketType fromInt(int value) {
        return map.getOrDefault(value, UNKNOWN);
    }
}
