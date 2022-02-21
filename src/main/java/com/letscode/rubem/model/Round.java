package com.letscode.rubem.model;

public class Round {
	private String id;
	private String filmOne;
	private String filmTwo;

	public Round() {

	}

	public Round(String filmOne, String filmTwo) {
		this.setFilmOne(filmOne);
		this.setFilmTwo(filmTwo);
	}

	/**
	 * @return the filmOne
	 */
	public String getFilmOne() {
		return filmOne;
	}

	/**
	 * @param filmOne the filmOne to set
	 */
	public void setFilmOne(String filmOne) {
		this.filmOne = filmOne;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the filmTwo
	 */
	public String getFilmTwo() {
		return filmTwo;
	}

	/**
	 * @param filmTwo the filmTwo to set
	 */
	public void setFilmTwo(String filmTwo) {
		this.filmTwo = filmTwo;
	}

}
