package exceptions;

public class SessionAlreadyExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SessionAlreadyExistException(String id_session) {
		super("Session '"+id_session+"' existe deja");
	}

}
