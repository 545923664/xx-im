package com.jzwl.base.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 娴嬭瘯鍩虹被锛屾墍鏈夌殑涓氬姟娴嬭瘯绫婚兘闇�缁ф壙璇ョ被
 * 
 * @ClassName: BaseTest
 * @author: zhang guo yu
 * @date: 2015-1-21 涓嬪崍2:54:43
 * @version: 1.0.0
 */
@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations="/applicationContext.xml")
@ContextConfiguration(locations = { "/applicationContext.xml",
		"/application-init.xml", "/application-jms-send.xml",
		"/application-jms-receive.xml", "/application-writeDataSource.xml",
		"/application-readDataSource.xml", "/application-mongo.xml",
		"/application-redis.xml", "/abstractSessionTest.xml" })
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

	public MockHttpServletRequest request = new MockHttpServletRequest();// 铏氭嫙request锛屽湪鍗曞厓娴嬭瘯涓彲浠ュ綋鍋氭甯竢equest鏉ョ敤
	public MockHttpServletResponse response = new MockHttpServletResponse();// 铏氭嫙response锛屽湪鍗曞厓娴嬭瘯涓彲浠ュ綋鍋氭甯竢esponse鏉ョ敤
	public MockHttpSession session = new MockHttpSession();// 铏氭嫙session锛屽湪鍗曞厓娴嬭瘯涓彲浠ュ綋鍋氭甯竤ession鏉ョ敤

	// 鍦╰est鏂规硶鎵ц鍓嶏紝鍒濆鍖栧弬鏁�
	// @Before
	public void initParams() {
		System.setProperty("localaddress", "127.0.0.1");
		System.setProperty("localport", "8080");
		request.setParameter("name", "mytestname---------------");
	}

	@Test
	public void test() {

		System.out.println(12);
	}

	// @After
	// 鍦╰est鏂规硶鎵ц鍚庯紝鍥炴敹鍙傛暟
	public void destoryParams() {
		request.setParameter("name", "");
	}
}
