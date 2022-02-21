package com.letscode.rubem.letscode.controller;

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

import com.letscode.rubem.controller.GameController;
import com.letscode.rubem.controller.PlayerController;
import com.letscode.rubem.firebase.GameFirebase;
import com.letscode.rubem.firebase.RankFirebase;
import com.letscode.rubem.model.Game;
import com.letscode.rubem.model.Player;
import com.letscode.rubem.model.Rank;
import com.letscode.rubem.model.Round;
import com.letscode.rubem.utils.DateUtils;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

	@Autowired
	private GameController controller;
	@Autowired
	private PlayerController playerController;

	@BeforeEach
	void setUp() {

	}

	/*
	 * @Test void constructor() {
	 * 
	 * Player player = new
	 * Player(String.valueOf(LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant(
	 * ).toEpochMilli()), "new test player", "test_" +
	 * String.valueOf(LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().
	 * toEpochMilli()), "test123");
	 * 
	 * assertNotNull(player); }
	 */
	@Test
	void createError() {

		String id = new GameFirebase().create(null);
		assertTrue(id.contains("null"));
	}

	@Test
	void readSuccess() {

		Player player = new Player();
		player.setName("new test player");
		player.setLogin("test_" + DateUtils.getMiliseconds());
		player.setPassword("test123");

		String id = playerController.create(player);

		try {
			TimeUnit.SECONDS.sleep(3);
			Player founded = playerController.read(id);

			String gameId = playerController.login(founded);
			TimeUnit.SECONDS.sleep(3);
			Game game = controller.read(gameId);
			controller.finish(gameId);
			assertNotNull(game);
			TimeUnit.SECONDS.sleep(3);
			controller.delete(gameId);
			playerController.delete(id);
		} catch (InterruptedException e) {
		}

	}

	@Test
	void readError() {

		Game game = controller.read(null);
		controller.finish(null);

		new RankFirebase().read(null);
		assertNull(game);

	}

	@Test
	void deleteError() {
		String result = controller.delete(null);
		assertTrue(result.contains("null"));
	}

	@Test
	void listAllRank() {
		List<Rank> players = controller.listRank();
		assertNotNull(players);

	}

	@Test
	void goodGame() {

		Player player = new Player();
		player.setName("new test player");
		player.setLogin("test_" + DateUtils.getMiliseconds());
		player.setPassword("test123");

		String id = playerController.create(player);

		try {
			TimeUnit.SECONDS.sleep(3);
			Player founded = playerController.read(id);

			String gameId = playerController.login(founded);
			TimeUnit.SECONDS.sleep(3);
			Game game = controller.read(gameId);

			controller.insertRound(gameId, new Round("Jai Bhim", "Batman"));

			TimeUnit.SECONDS.sleep(3);

			Round round = new Round();
			round.setFilmOne("Jai Bhim");
			round.setFilmTwo("Batman 2");
			controller.insertRound(gameId, round);

			TimeUnit.SECONDS.sleep(3);

			assertNotNull(game);
			controller.delete(gameId);
			playerController.delete(id);
		} catch (InterruptedException e) {
		}

	}

	@Test
	void badAA_AB_B_AGame() {

		Player player = new Player();
		player.setName("new test player");
		player.setLogin("test_" + DateUtils.getMiliseconds());
		player.setPassword("test123");

		String id = playerController.create(player);

		try {
			TimeUnit.SECONDS.sleep(3);
			Player founded = playerController.read(id);

			String gameId = playerController.login(founded);
			TimeUnit.SECONDS.sleep(3);
			Game game = controller.read(gameId);

			controller.insertRound(gameId, new Round("Batman", "Batman"));

			TimeUnit.SECONDS.sleep(3);

			Round round = new Round();
			round.setFilmOne("Matrix");
			round.setFilmTwo("Batman");
			controller.insertRound(gameId, round);

			TimeUnit.SECONDS.sleep(3);
			controller.insertRound(gameId, new Round("Batman ", "Matrix"));

			TimeUnit.SECONDS.sleep(3);

			assertNotNull(game);
			controller.delete(gameId);
			playerController.delete(id);
		} catch (InterruptedException e) {
		}

	}

	@Test
	void gameAfterFinished() {

		Player player = new Player();
		player.setName("new test player");
		player.setLogin("test_" + DateUtils.getMiliseconds());
		player.setPassword("test123");

		String id = playerController.create(player);

		try {
			TimeUnit.SECONDS.sleep(3);
			Player founded = playerController.read(id);

			String gameId = playerController.login(founded);
			TimeUnit.SECONDS.sleep(3);
			Game game = controller.read(gameId);

			controller.insertRound(gameId, new Round("Batman", "Batman2"));

			TimeUnit.SECONDS.sleep(3);

			controller.finish(gameId);

			TimeUnit.SECONDS.sleep(3);
			controller.insertRound(gameId, new Round("Batman ", "Matrix"));
			new RankFirebase().read(id);
			TimeUnit.SECONDS.sleep(3);

			assertNotNull(game);
			controller.delete(gameId);
			playerController.delete(id);
		} catch (InterruptedException e) {
		}

	}

	@Test
	void listAll() {
		List<Game> players = controller.listAll();
		assertNotNull(players);

	}

}
