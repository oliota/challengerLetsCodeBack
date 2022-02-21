package com.letscode.rubem.firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.Query.Direction;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.letscode.rubem.model.Game;
import com.letscode.rubem.model.Rank;
import com.letscode.rubem.utils.FirebaseUtils;

public class RankFirebase {

	private static final String COLLECTION = "rank";

	public String updateRank(Game game) {
		try {
			ApiFuture<DocumentSnapshot> future = FirebaseUtils.getRef(COLLECTION, game.getPlayerId()).get();
			DocumentSnapshot document;
			document = future.get();
			Rank rank = document.toObject(Rank.class);

			DocumentReference docRef = FirebaseUtils.getRef(COLLECTION, game.getPlayerId());
			HashMap<String, Object> data = new HashMap<>();
			Double calc;
			Integer countGame = 0;
			Integer hits = 0;
			Integer errors = 0;
			if (rank == null) {

				data.put("playerId", game.getPlayerId());
				data.put("playerName", game.getPlayerName());
				hits = game.getHit();
				errors = game.getError();
				countGame = 1;
			} else {

				data.put("playerId", rank.getPlayerId());
				data.put("playerName", rank.getPlayerName());
				hits = rank.getCountHits() + game.getHit();
				errors = rank.getCountErrors() + game.getError();
				countGame = rank.getCountGames() + 1;
			}
			if (hits.intValue() == 0) {

				calc = 0d;
			} else {
				calc = Double.valueOf(countGame) * Double.valueOf(hits) / Double.valueOf(hits + errors);
			}
			data.put("countHits", hits);
			data.put("countErrors", errors);
			data.put("countGames", countGame);
			data.put("calc", calc);

			docRef.set(data);
			return game.getPlayerId();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public Rank read(String id) {
		try {
			ApiFuture<DocumentSnapshot> future = FirebaseUtils.getRef(COLLECTION, id).get();
			DocumentSnapshot document;
			document = future.get();
			return document.toObject(Rank.class);
		} catch (Exception e) {
			return null;
		}

	}

	public List<Rank> listAll() {
		List<Rank> list = new ArrayList<>();
		try {
			Query query = FirebaseUtils.getCollection(COLLECTION).orderBy("calc", Direction.DESCENDING);
			ApiFuture<QuerySnapshot> querySnapshot = query.get();
			List<QueryDocumentSnapshot> documents;
			documents = querySnapshot.get().getDocuments();
			for (DocumentSnapshot document : documents) {
				list.add(document.toObject(Rank.class));
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

}
