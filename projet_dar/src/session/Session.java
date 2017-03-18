package session;

import java.io.Serializable;
import java.util.UUID;

public class Session implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1153553269193372925L;
	
	private String id_session;
	private long last_maj;
	private Etat etat;
	
	public Session() {
		id_session = UUID.randomUUID().toString();
		last_maj = System.currentTimeMillis();
		etat = new Etat(id_session, true);
	}
	
	public void setFirstTimeToFalse() {
		etat.setFirstTime(false);
	}

	public String getId_session() {
		return id_session;
	}

	public long getLast_maj() {
		return last_maj;
	}

	public void maj_time() {
		last_maj = System.currentTimeMillis();
	}
	
	public Etat getEtat() {
		return etat;
	}
	
	
}
