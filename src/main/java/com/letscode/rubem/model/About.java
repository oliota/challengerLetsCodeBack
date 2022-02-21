package com.letscode.rubem.model;

public class About {

	private String author;
	private String description;

	public About() {

	}

	public About(String author, String description) {
		super();
		this.setAuthor(author);
		this.setDescription(description);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

}
