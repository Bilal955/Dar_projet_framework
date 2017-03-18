package session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* 
 * Etat contient des informations de base. Le developpeur doit ensuite creer sa propre classe
 * qui etend celle ci et dans laquelle il pourra mettre les informations souhaites
 */
public class Etat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3573828005026219606L;
	
	private String idSession; // L'id de session a qui est associe l'état
	private Map<String, String> proprietes;
	private Data data; // Objet a utiliser si besoin par le dev
	private boolean firstTime; // True, si la session vient d etre creee a l'instant (cad que l'on a pas recu de cookie de session)
	
	
	public Etat(String idSession, boolean firstTime) {
		this.idSession = idSession;
		this.proprietes = new HashMap<>();
		this.firstTime = firstTime;
	}

	public String getIdSession() {
		return idSession;
	}

	public String getPropriete(String propriete) {
		return proprietes.get(propriete);
	}

	public void putPropriete(String propriete, String valeur) {
		proprietes.put(propriete, valeur);
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
	void setFirstTime(boolean b) { // Package
		firstTime = b;
	}

	public boolean isFirstTime() {
		return firstTime;
	}
	
}
