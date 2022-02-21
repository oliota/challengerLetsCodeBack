package com.letscode.rubem.model;

public class Rank {

	private String playerId;
	private String playerName;
	private Integer countGames;
	private Integer countHits;
	private Integer countErrors;
	private Double calc;

	/**
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	/**
	 * @return the countGames
	 */
	public Integer getCountGames() {
		return countGames;
	}

	/**
	 * @param countGames the countGames to set
	 */
	public void setCountGames(Integer countGames) {
		this.countGames = countGames;
	}

	/**
	 * @return the countHits
	 */
	public Integer getCountHits() {
		return countHits;
	}

	/**
	 * @param countHits the countHits to set
	 */
	public void setCountHits(Integer countHits) {
		this.countHits = countHits;
	}

	/**
	 * @return the countErrors
	 */
	public Integer getCountErrors() {
		return countErrors;
	}

	/**
	 * @param countErrors the countErrors to set
	 */
	public void setCountErrors(Integer countErrors) {
		this.countErrors = countErrors;
	}

	/**
	 * @return the calc
	 */
	public Double getCalc() {
		return calc;
	}

	/**
	 * @param calc the calc to set
	 */
	public void setCalc(Double calc) {
		this.calc = calc;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}
