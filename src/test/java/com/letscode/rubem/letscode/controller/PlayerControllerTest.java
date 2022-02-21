package com.letscode.rubem.letscode.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.letscode.rubem.controller.PlayerController;
import com.letscode.rubem.model.Player;
import com.letscode.rubem.utils.DateUtils;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

	@Autowired
	private PlayerController controller;

	@BeforeEach
	void setUp() {

	}

	@Test
	void constructor() {

		Player player = new Player("new test player", "test_" + DateUtils.getMiliseconds(), "test123");

		assertNotNull(player);
	}

	@Test
	void createSuccess() {

		Player player = new Player();
		player.setName("new test player");
		player.setLogin("test_" + DateUtils.getMiliseconds());
		player.setPassword("test123");

		String id = controller.create(player);
		controller.delete(id);
		assertNotNull(id);
	}

	@Test
	void createError() {

		Player player = null;
		String id = controller.create(player);
		assertTrue(id.contains("null"));
	}

	@Test
	void updateSuccess() {

		Player player = new Player();
		player.setName("new test player for update");
		player.setLogin("test_" + DateUtils.getMiliseconds());
		player.setPassword("test123");

		String id = controller.create(player);
		try {
			TimeUnit.SECONDS.sleep(3);
			Player founded = controller.read(id);
			founded.setName("player updated");
			controller.update(id, founded);
			TimeUnit.SECONDS.sleep(3);
			Player expected = controller.read(id);
			assertEquals(founded.getName(), expected.getName());
			controller.delete(id);
		} catch (InterruptedException e) {
		}

	}

	@Test
	void updateError() {
		String id = controller.update(null, null);
		assertTrue(id.contains("is null"));
	}

	@Test
	void deleteSuccess() {

		Player player = new Player();
		player.setName("new test player for delete");
		player.setLogin("test_" + DateUtils.getMiliseconds());
		player.setPassword("test123");

		String id = controller.create(player);
		try {
			TimeUnit.SECONDS.sleep(3);
			controller.delete(id);
			TimeUnit.SECONDS.sleep(3);
			Player notFounded = controller.read(id);
			assertNull(notFounded);
		} catch (InterruptedException e) {
		}

	}

	@Test
	void deleteError() {
		String result = controller.delete(null);
		assertTrue(result.contains("non-empty"));
	}

	@Test
	void listAll() {
		List<Player> players = controller.listAll();
		assertNotNull(players);

	}

	@Test
	void loginSuccess() {
		Player player = new Player();
		player.setName("new test player login");
		player.setLogin("test_" + DateUtils.getMiliseconds());
		player.setPassword("test123");

		String id = controller.create(player);
		try {
			TimeUnit.SECONDS.sleep(3);
			String expected = controller.login(player);
			TimeUnit.SECONDS.sleep(3);
			assertNotNull(expected);
		} catch (InterruptedException e) {
		}

		controller.delete(id);
	}

	@Test
	void loginError() {

		String expected = controller.login(null);
		assertNull(expected);

	}

}
