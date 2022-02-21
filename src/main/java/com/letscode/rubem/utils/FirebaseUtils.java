package com.letscode.rubem.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public final class FirebaseUtils {

	private FirebaseUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static Firestore getDb() {
		GoogleCredentials credentials;

		InputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream("letscode-service-99fed1683c31.json");
			credentials = GoogleCredentials.fromStream(serviceAccount);
			FirebaseOptions options = FirebaseOptions.builder().setCredentials(credentials)
					.setDatabaseUrl("https://letscode-database.firebaseio.com/").build();

			List<FirebaseApp> apps = FirebaseApp.getApps();
			if (apps.isEmpty()) {

				FirebaseApp.initializeApp(options, "letscode-database");
			}
			return FirestoreClient.getFirestore(FirebaseApp.getInstance("letscode-database"));
		} catch (IOException e) {
			return null;
		}

	}

	public static DocumentReference getRef(String colection, String id) {
		return getDb().collection(colection).document(id);
	}

	public static CollectionReference getCollection(String colection) {
		return getDb().collection(colection);
	}

}
