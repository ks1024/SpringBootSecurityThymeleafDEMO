package com.code4jdemo.sbsst;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testPasswordGen() {
		String rawPassword = "password";
		String encodedPassword = passwordEncoder.encode(rawPassword);
		System.out.println(encodedPassword);
		Assert.assertNotEquals(encodedPassword, "");
	}

}
