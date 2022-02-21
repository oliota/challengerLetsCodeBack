package com.letscode.rubem.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.letscode.rubem.model.Film;

public final class HttpUtils {
	private HttpUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static  Film  getImdbRanking() {
		URL imdbSite;
		try {
			imdbSite = new URL("https://www.imdb.com/search/title/?groups=top_250&sort=user_rating");
			URLConnection con = imdbSite.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder sb = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			in.close();
			Document doc = Jsoup.parse(sb.toString());

			List<Film> films = new ArrayList<>();
			List<Element> elements = doc.select("div.mode-advanced");
			for (Element element : elements) {
				Film film = new Film();
				film.setName(element.select("img.loadlate").attr("alt"));
				film.setRate(Double.valueOf(element.select("div.ratings-imdb-rating").get(0).attr("data-value")));
				film.setImage(element.select("img.loadlate").attr("loadlate"));
				film.setYear(Integer
						.valueOf(element.select("span.lister-item-year").text().replace("(", "").replace(")", "")));
				film.setCategories(element.select("span.genre").text());
				film.setDuration(element.select("span.runtime").text());
				films.add(film);
			}
			Film fist=new Film(
					films.get(0).getName(),
					films.get(0).getRate(),
					films.get(0).getYear(),
					films.get(0).getImage(),
					films.get(0).getDuration(),
					films.get(0).getCategories()
					);
			return fist;
		} catch (Exception e) {
			return null;
		}

	}
}
