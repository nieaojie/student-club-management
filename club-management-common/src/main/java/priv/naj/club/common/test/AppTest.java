package priv.naj.club.common.test;

import priv.naj.club.common.util.SnowUUIDGenerator;
import priv.naj.club.common.enums.ClientType;
import priv.naj.club.common.util.UUIDGenerator;

public class AppTest {
    public static void main(String[] args) {
        System.out.println(ClientType.android.getName());   //android
        UUIDGenerator uuidGenerator = new SnowUUIDGenerator();
        System.out.println(uuidGenerator.newID());  //1356764571026403330
    }
}
