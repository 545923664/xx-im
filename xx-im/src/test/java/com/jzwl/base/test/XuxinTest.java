package com.jzwl.base.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml",
		"/application-mongo.xml", "/application-redis.xml" })
public class XuxinTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Test
	public void test() {

		System.out.println(12);
	}

}
