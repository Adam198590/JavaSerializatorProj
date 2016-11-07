package impl.gen.packet;

import api.ByteBufferSerializableObject;
import impl.gen.model.UserData;
import util.ByteUtil;

import java.nio.ByteBuffer;

public class LoginBody implements ByteBufferSerializableObject {
    public final UserData userData;

    public LoginBody() {
        this.userData = new UserData();
    }

    public LoginBody(UserData data) {
        this.userData = data;
    }

    @Override
    public void serialize(ByteBuffer dst) {
        dst.putInt(userData.uid);
        ByteUtil.putStringToByteBuffer(dst, userData.name);
    }

    @Override
    public void deserialize(ByteBuffer src) {
        userData.uid = src.getInt();
        userData.name = ByteUtil.getStringFromByteBuffer(src);
    }

    @Override
    public int size() {
        return Integer.BYTES + ByteUtil.getByteCapacityForString(userData.name);
    }
}