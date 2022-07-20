package priv.naj.club;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import priv.naj.club.system.entity.RoleEntity;
import priv.naj.club.system.entity.UserEntity;
import priv.naj.club.system.mapper.RoleMapper;
import priv.naj.club.system.service.RoleService;
import priv.naj.club.system.service.UserService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    RoleMapper roleMapper;

    @Test
    public void test05() {
        userService.joinClub("1369645549495316482");
    }

    @Test
    public void test04() {
        RoleEntity roleEntity = new RoleEntity();
//        roleEntity.setPkid(UUID.randomUUID().toString());
        roleEntity.setPkid("1381609190595596290");
        roleEntity.setRoleCode("test");
        roleEntity.setRoleName("test");
        roleEntity.setRoleDesc("test");
//        String cmd = roleService.createCmd(roleEntity);
//        System.out.println(cmd);    //1381609190595596290
        int i = roleMapper.updateById(roleEntity);
        System.out.println(i);
    }


    @Test
    public void test01() {
        UserEntity entity = userService.getByUserName("zhangsan");
        UserEntity entity2 = userService.getByUserName("zhangsan112");
        System.out.println(entity);
        System.out.println(entity2);
    }

    @Test
    public void test02() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String join = String.join(",", list);
        System.out.println(join);
    }

    @Test
    public void test03() {
        String path = Thread.currentThread().getContextClassLoader().getResource("application-dev.yml")
                            .getPath();
        System.out.println(path);
    }

}
