package com.letscode.rubem.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

	private DateUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static String getMiliseconds() {
		return String.valueOf(LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
	}

	public static String getPrettyDate(String miliseconds) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return formatter
				.format(Instant.ofEpochMilli(Long.valueOf(miliseconds)).atZone(ZoneOffset.UTC).toLocalDateTime());
	}

}
