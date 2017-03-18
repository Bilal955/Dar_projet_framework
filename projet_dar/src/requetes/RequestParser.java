package requetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.EnTeteMalFormeeException;
import exceptions.RequeteMalFormeeException;
import exceptions.RequeteVideException;
import tools.Tools;
import types.HeaderRequest;
import types.MimeType;

public class RequestParser {

	public static Request parse(String request) throws RequeteVideException, RequeteMalFormeeException, EnTeteMalFormeeException {
		//System.err.println("REQUEST ======= \n"+request+"\nFIN REQUEST============\n\n");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(request);
		Request res = new Request();

		Matcher matcher;
		Pattern patternFirstLine = Pattern.compile("^(\\w+)\\s+(\\S+)\\s+HTTP/(\\S+)$");
		Pattern patternEnTete = Pattern.compile("^(\\S+):\\s*([^\n]+)$");

		/* First line */
		String firstLine = null;
		try {
			firstLine = scanner.nextLine().trim();
		} catch(java.util.NoSuchElementException e) {
			System.out.println("Pourquoi pas lu de ligne ?");
			throw new RequeteVideException("La requete est vide !");
		}
			
		matcher = patternFirstLine.matcher(firstLine);
		if(matcher.matches()) {
			res.setMethode(Tools.strToType(matcher.group(1)));
			res.setHttpVersion(matcher.group(3));

			String ressource = matcher.group(2);
			parseRessource(ressource, res);
			res.setRessourceComplete(ressource);
		}
		else {
			throw new RequeteMalFormeeException("Requete mal formee : "+firstLine);
		}

		/* Entetes */
		while (scanner.hasNextLine()) {
			String entete = scanner.nextLine().trim();
			if(entete.isEmpty())
				break;
			
			matcher = patternEnTete.matcher(entete);
			if(matcher.matches()) {
				String key = matcher.group(1).trim();
				String val = matcher.group(2).trim();
				
				if(key.equals(HeaderRequest.COOKIE.getName())) // TODO remplacer par headerRequest
					res.setCookies(parseCookie(val));
				else if(matcher.group(1).trim().equals(HeaderRequest.ACCEPT.getName()))
					res.setAccepts(parseAccept(val));
				else
					res.addHeader(key, val);
			}
			else {
				throw new EnTeteMalFormeeException("Entete mal formee : "+entete);
			}
		}

		/* Body */
		/*-- Lecture du body --*/
		StringBuilder sTmp = new StringBuilder();
		while (scanner.hasNextLine()) 
			sTmp.append(scanner.nextLine()+"\n");
		if(sTmp.length() != 0 && request.charAt(request.length()-1) != '\n') // TODO JE VIENS D ADD LE IsEmpty, si bug maybe because
			sTmp.deleteCharAt(sTmp.length()-1);
		String body = sTmp.toString();
		res.setBody(body);
		scanner.close();
		
		// Peut etre tester && POST ?? TODO
		if(res.getContentType() != null && res.getContentType().equals(MimeType.APPLICATION_X_WWW_FORM_URLENCODED.getName())) {
			Pattern patternPostArgs = Pattern.compile(RequeteConstantes.post_regex);
			matcher = patternPostArgs.matcher(body);
			Map<String, String> postArgs = new HashMap<>();
			while(matcher.find()) {
				String cle = matcher.group(1);
				String valeur = matcher.group(2);
				postArgs.put(cle, valeur);
			}
			res.setPost_args(postArgs);
		}
		
		/* Recuperation de la session une fois que toutes les en tetes sont lues */
		res.loadOrCreateSession();
		
		return res;
	}

	private static void parseRessource(String path, Request r) {
		Pattern patternRessource = Pattern.compile(RequeteConstantes.url_regex);
		Matcher matcher = patternRessource.matcher(path);
		if(matcher.matches()) {
			String ressource = matcher.group(1).trim();
			String query = matcher.group(3) == null ? "" : matcher.group(3).trim();
			List<String> pathList = parsePathRessource(ressource, r);
			r.setPathRessource(pathList);
			Map<String, String> mapQuery = parseQuery(query);
			r.setQuery(mapQuery);
		}
	}

	private static List<String> parsePathRessource(String path, Request r) {
		List<String> res = new ArrayList<String>();
		Pattern pattern = Pattern.compile(RequeteConstantes.pathRessource_regex);
		Matcher matcher = pattern.matcher(path);
		while(matcher.find()) {
			String p = matcher.group(1).trim();
			if(!p.isEmpty()) // Dans le cas : points//5/x (plusieurs /// a la suite)
				res.add(p);
		}
		return res;
	}

	private static Map<String, String> parseQuery(String query) {
		Map<String, String> res = new HashMap<>();
		Pattern patternQuery = Pattern.compile(RequeteConstantes.query_regex);
		Matcher matcher = patternQuery.matcher(query);
		while(matcher.find()) {
			String cle = matcher.group(1).trim();
			String valeur = matcher.group(2).trim();
			res.put(cle, valeur);
		}
		return res;
	}

	private static Map<String, String> parseCookie(String cookies) {
		cookies = cookies.trim();
		Map<String, String> res = new HashMap<>();
		Pattern patternCookie = Pattern.compile(RequeteConstantes.cookie_regex);
		Matcher matcher = patternCookie.matcher(cookies);
		while(matcher.find()) {
			String cle = matcher.group(1).trim();
			String valeur = matcher.group(2).trim();
			res.put(cle, valeur);
		}
		return res;
	}

	private static List<String> parseAccept(String accepts) {
		List<String> res = new ArrayList<>();
		Pattern patternAccept = Pattern.compile(RequeteConstantes.accept_regex);
		Matcher matcher = patternAccept.matcher(accepts);

		while(matcher.find()) {
			String valeur = matcher.group(1).trim();
			res.add(valeur);
		}

		return res;
	}



	// private static List<> parseContentType






}
