package response;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import constantes.ConstantesTools;
import types.CodeToUser;
import types.HeaderResponse;


// Faire en sorte que si on indique un content type dans fichier de mapping (que ca soit optionnel) alors il est mit automatiquement
// 1 seul valeur possible, par defaut html

public class Response {

	private CodeToUser codeRetour;
	private String httpVersion;

	private Map<String, String> headers = new HashMap<>();
	private Map<String, String> cookies = new HashMap<>(); 
	private String body = "";


	public Response() {
		codeRetour = CodeToUser._200;
		httpVersion = "1.1";
		headers.put(HeaderResponse.CONTENT_TYPE.getName(), "text/html;charset=UTF-8");
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setCodeRetour(CodeToUser code) {
		codeRetour = code;
	}

	/* Mets a jour si deja existant */
	public void putHeader(String cle, String valeur) {
		headers.put(cle, valeur);
	}
	public boolean removeHeader(String cle) {
		return headers.remove(cle) == null;
	}

	public void putCookie(String cle, String valeur) {
		cookies.put(cle, valeur);
	}

	public boolean removeCookie(String cle) {
		return cookies.remove(cle) == null;
	}


	public void putContentType(String valeur) {
		headers.put(HeaderResponse.CONTENT_TYPE.getName(), valeur);
	}

	public String removeContentType() {
		return headers.remove(HeaderResponse.CONTENT_TYPE.getName());
	}



	@Override
	public String toString() {
		cookies.forEach((key, value) -> {
			System.err.println("[Cookier a poster] Key : " + key + " Value : " + value);
		});

		StringBuilder res = new StringBuilder();

		/* First line */
		res.append("HTTP/"+httpVersion+" "+codeRetour.codeToInteger()+" "+codeRetour.codeToReason()+"\n");

		/* Headers */
		/* -- Add date -- */
		String date = java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
		headers.put(HeaderResponse.DATE.getName(), date);
		/* -- Server name -- */
		headers.put(HeaderResponse.SERVER.getName(), ConstantesTools.SERVER_NAME);

		headers.forEach((cle, valeur) -> {
			res.append(cle+": "+valeur+"\n");
		});

		/* --- Cookies (fait partie des headers) --- */
		cookies.forEach((cle, valeur) -> {
			res.append(HeaderResponse.SET_COOKIE.getName()+": "+cle+"="+valeur+"\n");
		});

		/* Content Length */
		//		byte[] bodyChar = body.getBytes();
		//		res.append(HeaderResponse.CONTENT_LENGTH.getName()+": "+(bodyChar.length+1));
		//		System.err.println(HeaderResponse.CONTENT_LENGTH.getName()+": "+(bodyChar.length+1));
		//		System.err.println("BODY = '"+body+"'");

		/* Ligne vide */
		res.append("\n");

		/* Body */
		res.append(body+"\n");

		return res.toString();
	}


}
