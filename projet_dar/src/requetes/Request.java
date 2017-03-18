package requetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.SessionDoesntExistException;
import session.Session;
import session.SessionConstante;
import session.SessionTools;
import types.HeaderResponse;
import types.TypeRequete;

public class Request {

	private String ressourceComplete;
	private String httpVersion;
	private TypeRequete methode;
	private List<String> accepts = new ArrayList<>();
	private Map<String, String> headers = new HashMap<>();
	private Map<String, String> cookies = new HashMap<>();
	private Map<String, String> query = new HashMap<>(); // Exemple : /points/5/x?a=5&b=op ==> ["a" = "5", "b" = "op"]
	private Map<String, String> post_args = new HashMap<>(); // Arg requete POST
	private List<String> pathRessource = new ArrayList<>(); // Exemple : /points/5/x?a=5&b=op ==> ("points", "5", "x")
	private String body;
	private Session session;
	
	
	
	private String contentType = null;


	public Request() {}

	public Request(String ressourceComplete, TypeRequete methode, String httpVersion) {
		this.ressourceComplete = ressourceComplete;
		this.methode = methode;
		this.httpVersion = httpVersion;
		this.body = "";
	}

	/*
	 * Si on a recu un id_session et que celui-ci existe
	 */
	public void loadOrCreateSession() {
		String id_session = cookies.get(SessionConstante.cookie_session_name);
		if(id_session == null) // Si pas de cookie de session
			session = SessionTools.createNewSession(); 
		else {
			try {
				session = SessionTools.getSession(id_session);
				session.setFirstTimeToFalse(); // On indique que la session existait deja et ne vient pas d etre cree
			} catch (SessionDoesntExistException e) {
				session = SessionTools.createNewSession(); 
				// Il vaut mieux supprimer l'ancien cookie (mais bon au pire il sera supprimer tout seul) // TODO
				// MAIS BON ESSAYER DE LE SUPPRIMER C EST PLUS SERIEUX
			}
		}
	}
	

	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(methode + " " + ressourceComplete + " " + " HTTP/" + httpVersion + "\n");
		for(String cle : headers.keySet()) 
			res.append(cle+ ": " + headers.get(cle) + "\n");
		res.append("\n");
		res.append(body);
		return res.toString();
	}

	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Session getSession() {
		return session;
	}

	public List<String> getAccepts() {
		return accepts;
	}

	public void setAccepts(List<String> accepts) {
		this.accepts = accepts;
	}

	public void addHeader(String cle, String valeur) {
		headers.put(cle, valeur);
		if(cle.equals(HeaderResponse.CONTENT_TYPE.getName()))
			contentType = valeur;
	}
	
	public Map<String, String> getPost_args() {
		return post_args;
	}

	public void setPost_args(Map<String, String> post_args) {
		this.post_args = post_args;
	}

	public String getUrl() {
		return ressourceComplete;
	}

	public String getRessourceComplete() {
		return ressourceComplete;
	}

	public void setRessourceComplete(String ressourceComplete) {
		this.ressourceComplete = ressourceComplete;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public TypeRequete getMethode() {
		return methode;
	}

	public void setMethode(TypeRequete methode) {
		this.methode = methode;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	public Map<String, String> getQuery() {
		return query;
	}

	public void setQuery(Map<String, String> query) {
		this.query = query;
	}

	public List<String> getPathRessource() {
		return pathRessource;
	}

	public void setPathRessource(List<String> pathRessource) {
		this.pathRessource = pathRessource;
	}

	public String getPathRessourceAsString() {
		return "/"+String.join("/", pathRessource);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
