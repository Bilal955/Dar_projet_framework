package requetes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestToFunc {

	private Map<String, String> headers;
	private Map<String, String> cookies;
	private Map<String, String> query;
	private Map<String, String> post; // Argument lors d'une requete POST
	private List<String> pathRessource;
	private List<String> accepts;
	private String body;
	
	public RequestToFunc(Request r) {
		headers = r.getHeaders();
		cookies = r.getCookies();
		query = r.getQuery();
		pathRessource = r.getPathRessource();
		body = r.getBody();
		accepts = r.getAccepts();
		post = r.getPost_args();
	}
	
	public Map<String, String> getHeaders() {
		return headers;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public Map<String, String> getQuery() {
		return query;
	}

	public Map<String, String> getPost() {
		return post;
	}

	public String getHeaders(String h) {
		return headers.get(h);
	}

	public String getCookies(String c) {
		return cookies.get(c);
	}

	public String getPost(String p) {
		return post.get(p);
	}
	
	public String getQuery(String q) {
		return query.get(q);
	}

	public List<String> getPathRessource() {
		return pathRessource;
	}

	public boolean acceptIsPresent(String accept) {
		return (accepts.stream().filter(s -> s.equals(accept)).count() > 0) || 
				(accepts.stream().filter(s -> s.contains(accept)).count() > 0);
	}
	
	public List<String> getAccepts() {
		return new ArrayList<>(accepts);
	}
	
	public String getBody() {
		return body;
	}
	
}
