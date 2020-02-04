package com.resource;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResourceManagementApplicationTests {

	@Test
	public void applicationTest() {
		ResourceManagementApplication.main(new String[] {});
		assertTrue(true);
	}

}
