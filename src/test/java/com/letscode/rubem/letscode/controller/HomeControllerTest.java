package com.letscode.rubem.letscode.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.letscode.rubem.controller.HomeController;
import com.letscode.rubem.model.About;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

	@Autowired
	private HomeController controller;

	@BeforeEach
	void setUp() {

	}

	@Test
	void test() {

		String expected = "woking";

		String result = controller.test();

		assertEquals(expected, result);
	}
	
	@Test
	void constructor() {

		About expected = new About();
		expected.setAuthor("Rubem Oliota");
		expected.setDescription("Challenger for lets code"); 

		assertNotNull(expected);
	}
	@Test
	void about() {

		About expected = new About("Rubem Oliota", "Challenger for lets code");

		About result = controller.getAbout();

		assertEquals(expected.getAuthor(), result.getAuthor());
		assertEquals(expected.getDescription(), result.getDescription());
	}
}
