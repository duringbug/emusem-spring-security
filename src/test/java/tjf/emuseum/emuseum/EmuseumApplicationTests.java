/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 00:40:32
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 02:10:50
 */
package tjf.emuseum.emuseum;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tjf.emuseum.emuseum.data.myBatis.mapper.UserMapper;
import tjf.emuseum.emuseum.entity.User;

@SpringBootTest
class EmuseumApplicationTests {
	@Autowired
	private UserMapper userMapper;

	@Test
	void contextLoads() {
	}

	@Test
	public void testUserMapper() {
		List<User> userlList = userMapper.selectList(null);
		for (User user : userlList) {
			System.out.println(user);
		}
	}
}
