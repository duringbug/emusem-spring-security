/*
 * @Description:
 * @Author: 唐健峰
 * @Date: 2023-04-15 00:40:32
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 14:54:08
 */
package tjf.emuseum.emuseum;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tjf.emuseum.emuseum.data.myBatis.mapper.*;
import tjf.emuseum.emuseum.entity.User;
import tjf.emuseum.emuseum.utils.Mail.SendMail;

@SpringBootTest
class EmuseumApplicationTests {
	@Autowired
	private SendMail sendMail;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private MenuMapper menuMapper;

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

	@Test
	public void testInsertUser() {
		User user = new User();
		userMapper.insert(user);
	}

	@Test
	public void testMenuMapper() {
		Long id = Long.valueOf(392);
		System.out.println(menuMapper.selectPermsByUserId(id));
	}

	@Test
	public void sendMailTest() {
		sendMail.sendNumber("17269633033@163.com", UUID.randomUUID().toString());
	}

	@Test
	public void getSaltTest() {
		userMapper.findSaltByUserId(Long.valueOf(123));
	}
}
