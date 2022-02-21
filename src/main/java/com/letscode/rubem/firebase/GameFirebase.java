package com.letscode.rubem.firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.letscode.rubem.model.Film;
import com.letscode.rubem.model.Game;
import com.letscode.rubem.model.Player;
import com.letscode.rubem.model.Round;
import com.letscode.rubem.utils.DateUtils;
import com.letscode.rubem.utils.FirebaseUtils;
import com.letscode.rubem.utils.HttpUtils;

public class GameFirebase {

	private static final String COLLECTION = "games";
	private static final Integer LIMIT_ERROR = 3;
	private static final Integer WAIT_SECONDS = 2;

	public String create(String playerId) {
		try {
			String id = DateUtils.getMiliseconds();
			DocumentReference docRef = FirebaseUtils.getRef(COLLECTION, id);
			HashMap<String, Object> data = new HashMap<>();
			Player player = new PlayerFirebase().read(playerId);
			data.put("id", id);
			data.put("playerId", playerId.trim());
			data.put("playerName", player.getName());
			data.put("end", null);
			data.put("rounds", new ArrayList<Round>());
			data.put("hit", 0);
			data.put("error", 0);
			docRef.set(data);

			return id;
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public Game read(String id) {
		try {
			ApiFuture<DocumentSnapshot> future = FirebaseUtils.getRef(COLLECTION, id.trim()).get();
			DocumentSnapshot document;
			document = future.get();
			return document.toObject(Game.class);
		} catch (Exception e) {
			return null;
		}

	}

	public String delete(String id) {
		try {
			FirebaseUtils.getRef(COLLECTION, id.trim()).delete();
			return id;
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	public String finish(String id) {
		try {
			Game original = read(id.trim());
			String validation = validGame(original);
			if (validation == null) {
				DocumentReference docRef = FirebaseUtils.getRef(COLLECTION, original.getId());

				HashMap<String, Object> data = new HashMap<>();
				data.put("id", original.getId());
				data.put("playerId", original.getPlayerId());
				data.put("playerName", original.getPlayerName());
				data.put("end", DateUtils.getMiliseconds());
				data.put("rounds", original.getRounds());
				data.put("hit", original.getHit());
				data.put("error", original.getError());
				docRef.set(data);
				new RankFirebase().updateRank(original);
				return id;
			} else {
				return validation;
			}
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	public String insertRound(String id, Round item) {
		try {
			Game original = read(id.trim());
			String validation = validGame(original);
			if (validation == null) {
				DocumentReference docRef = FirebaseUtils.getRef(COLLECTION, original.getId());
				String rejectReasons = validRules(original.getRounds(), item);
				if (rejectReasons == null) {
					Film film = HttpUtils.getImdbRanking();

					HashMap<String, Object> data = new HashMap<>();
					data.put("id", original.getId());
					data.put("playerId", original.getPlayerId());
					data.put("playerName", original.getPlayerName());
					item.setId(DateUtils.getMiliseconds());
					original.getRounds().add(item);
					data.put("rounds", original.getRounds());

					compareResult(original, item, film.getName());

					data.put("end", original.getEnd());
					data.put("hit", original.getHit());
					data.put("error", original.getError());
					docRef.set(data);

					if (validateFinish(id)) {
						return "This game stated on " + DateUtils.getPrettyDate(original.getId())
								+ " was ended , bacause limit of errs was hited no changes can be made.";
					} else {
						return id;
					}
				} else {
					return rejectReasons;
				}
			} else {
				return validation;
			}
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	private boolean validateFinish(String id) {
		try {
			TimeUnit.SECONDS.sleep(WAIT_SECONDS);
			Game original = read(id);
			if (original.getError().intValue() >= LIMIT_ERROR) {
				finish(id);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	private static void compareResult(Game original, Round item, String name) {
		if (item.getFilmOne().equalsIgnoreCase(name) || item.getFilmTwo().equalsIgnoreCase(name)) {
			original.setHit(original.getHit() + 1);
		} else {
			original.setError(original.getError() + 1);
		}

	}

	public List<Game> listAll() {
		List<Game> list = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = FirebaseUtils.getCollection(COLLECTION).get();
			List<QueryDocumentSnapshot> documents;
			documents = future.get().getDocuments();
			for (DocumentSnapshot document : documents) {
				list.add(document.toObject(Game.class));
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	private static String validRules(List<Round> rounds, Round item) {
		if (item.getFilmOne().equalsIgnoreCase(item.getFilmTwo())) {
			return "Rule [A-A]: Can't get into the same movie twice\n";
		}
		for (Round round : rounds) {

			if (item.getFilmOne().equalsIgnoreCase(round.getFilmOne())
					&& item.getFilmTwo().equalsIgnoreCase(round.getFilmTwo())
					|| item.getFilmOne().equalsIgnoreCase(round.getFilmTwo())
							&& item.getFilmTwo().equalsIgnoreCase(round.getFilmOne())) {
				return "Rule [A-B, B-A]: Can't repeat an already inserted movie pair\n";
			}

		}
		return null;
	}

	private static String validGame(Game game) {
		if (game.getEnd() != null) {
			return "This game stated on " + DateUtils.getPrettyDate(game.getId()) + " and has already ended on "
					+ DateUtils.getPrettyDate(game.getEnd()) + ", no changes can be made.";
		} else {
			return null;

		}
	}

}
