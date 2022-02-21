package com.letscode.rubem.firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.letscode.rubem.model.Player;
import com.letscode.rubem.utils.DateUtils;
import com.letscode.rubem.utils.FirebaseUtils;

public class PlayerFirebase {

	private static final String COLLECTION = "players";

	public String create(Player player) {
		try {
			String verification = verifyDuplicatedLogin(player.getLogin());
			if (verification == null) {
				String id = DateUtils.getMiliseconds();
				DocumentReference docRef = FirebaseUtils.getRef(COLLECTION, id);
				HashMap<String, Object> data = new HashMap<>();
				data.put("id", id);
				data.put("name", player.getName());
				data.put("login", player.getLogin());
				data.put("password", player.getPassword());
				docRef.set(data);

				return id;
			} else {
				return "there is already a player with the login '" + player.getLogin()
						+ "', please choose another login and try again";
			}

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public Player read(String id) {
		try {
			ApiFuture<DocumentSnapshot> future = FirebaseUtils.getRef(COLLECTION, id).get();
			DocumentSnapshot document;
			document = future.get();
			return document.toObject(Player.class);
		} catch (Exception e) {
			return null;
		}

	}

	public String update(String id, Player request) {
		try {
			Player original = read(id);
			DocumentReference docRef = FirebaseUtils.getRef(COLLECTION, original.getId());
			HashMap<String, Object> data = new HashMap<>();
			data.put("id", original.getId());
			data.put("name", request.getName());
			data.put("login", original.getLogin());
			data.put("password", request.getPassword());
			docRef.set(data);
			return id;
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	public String delete(String id) {
		try {
			FirebaseUtils.getRef(COLLECTION, id).delete();
			return id;
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	public List<Player> listAll() {
		List<Player> list = new ArrayList<>();
		try {
			ApiFuture<QuerySnapshot> future = FirebaseUtils.getCollection(COLLECTION).get();
			List<QueryDocumentSnapshot> documents;
			documents = future.get().getDocuments();
			for (DocumentSnapshot document : documents) {
				list.add(document.toObject(Player.class));
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	private static String verifyDuplicatedLogin(String login) {
		List<Player> list = new ArrayList<>();
		try {
			Query query = FirebaseUtils.getCollection(COLLECTION).whereEqualTo("login", login);
			ApiFuture<QuerySnapshot> querySnapshot = query.get();
			List<QueryDocumentSnapshot> documents;
			documents = querySnapshot.get().getDocuments();
			for (DocumentSnapshot document : documents) {
				list.add(document.toObject(Player.class));
			}
			if (!list.isEmpty()) {
				return list.get(0).getId();
			} else {

				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public String login(Player request) {
		List<Player> list = new ArrayList<>();
		try {
			Query query = FirebaseUtils.getCollection(COLLECTION).whereEqualTo("login", request.getLogin())
					.whereEqualTo("password", request.getPassword());
			ApiFuture<QuerySnapshot> querySnapshot = query.get();
			List<QueryDocumentSnapshot> documents;
			documents = querySnapshot.get().getDocuments();
			for (DocumentSnapshot document : documents) {
				list.add(document.toObject(Player.class));
			}
			if (!list.isEmpty()) {
				return new GameFirebase().create(list.get(0).getId());
			} else {

				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

}
