package model;

import session.Data;

public class Presence implements Data {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6707768941032869189L;
	private String nom;
	private Boolean isPresent;

	public Presence(String nom, Boolean isPresent) {
		super();
		this.nom = nom;
		this.isPresent = isPresent;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Boolean getIsPresent() {
		return isPresent;
	}

	public void setIsPresent(Boolean isPresent) {
		this.isPresent = isPresent;
	}

}
