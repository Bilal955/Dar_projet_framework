package session;

public class SessionConstante {

	
	public static String sessionFileSerial = "sessions/sessions.ser";
	
	public static final String  id_cookie = "id_cookie";
	public static final String  last_maj = "last_maj";

	public static final int nbMinuteSleep = 15; // Nettoyage des sessions tous les nbMinutesSleep
	public static final int dureeMaxSession = 30; // en minute
	
	
	// TODO faire en sorte qu'on puisse pas ajouter un cookie ayant deja ce nom.... !!!
	public static final String cookie_session_name = "id_session";
}
