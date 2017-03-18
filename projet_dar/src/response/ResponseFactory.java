package response;

import java.io.PrintWriter;
import java.io.StringWriter;

import session.SessionConstante;
import types.CodeToUser;
import types.HeaderResponse;

public class ResponseFactory {

	public static Response nothingMatch() {
		Response res = new Response();
		res.setCodeRetour(CodeToUser._404);
		res.setBody("<!DOCTYPE html><html><head></head><body>L'URL n'est associee a aucun mapping...</body></html>\n\r");
		return res;
	}
	
	
	public static Response hello() {
		Response res = new Response();
		res.setBody("HTTP/1.1 200 OK\n"+
				"Date: Thu, 11 Jan 2007 14:00:36 GMT\n"+
				"Server: Apache/2.0.54 (Debian GNU/Linux) DAV/2 SVN/1.1.4\n"+
				"Content-Type: text/html\n\n"+
				"<!DOCTYPE html><html><head></head><body>Hello</body></html>\n\r");
		return res;
	}
	
	public static Response internalError(String message) {
		Response res = new Response();
		res.setCodeRetour(CodeToUser._500);
		res.setBody("<!DOCTYPE html><html><head></head><body>"+message+"</body></html>\n\r");
		return res;
	}
	
	public static Response sessionDoesntExistError(String message) {
		Response res = new Response();
		res.setCodeRetour(CodeToUser._400);
		res.putHeader(HeaderResponse.SET_COOKIE.getName(), SessionConstante.cookie_session_name+"=deleted; expires=Thu, 01 Jan 1970 00:00:00 GMT");
		res.setBody("<!DOCTYPE html><html><head></head><body>"+message+"</body></html>\n\r");
		return res;
	}


	public static Response requeteVide() {
		Response res = new Response();
		res.setCodeRetour(CodeToUser._400);
		res.setBody("<!DOCTYPE html><html><head></head><body>La requete est vide, pourquoi tu fais ca ?</body></html>\n\r");
		return res;
	}


	public static Response internalError(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		Response res = new Response();
		res.setCodeRetour(CodeToUser._500);
		res.setBody("<!DOCTYPE html><html><head></head><body>"+sw.toString()+"</body></html>\n\r");
		return res;
	}
}
