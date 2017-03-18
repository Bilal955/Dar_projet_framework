package exceptions;

public class SessionDoesntExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public SessionDoesntExistException(String id_session) {
		super("Session '"+id_session+"' n'existe pas");
	}


	
}
