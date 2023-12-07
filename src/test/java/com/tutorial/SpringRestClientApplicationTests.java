package com.tutorial;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringRestClientApplicationTests {

	@Test
	void contextLoads() {

		int a = 10;
		int b = 10;
		int c = a + b;
		System.out.println("result: " + c);

	}

}
