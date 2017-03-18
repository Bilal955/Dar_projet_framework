package mapping;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import constantes.ConstantesTools;
import requetes.Request;
import requetes.RequestToFunc;
import response.Response;
import response.ResponseFactory;
import session.Etat;
import session.Session;
import session.SessionConstante;
import types.TypeRequete;


// 1 objet Mapping represente un mapping du fichier listPatterns et map un type d'url avec la bonne fonction
public class Mapping {

	// Accessible partout
	public static List<Mapping> mappings = MappingParseur.loadAllMapping(); // Use one time on mapping

	private String ressource; // Regex representant les urls qui seront traite avec la fonction
	private String functionName; // Fonction de traitement, toutes ces fonctions doivent retourner un objet Response
	private String classFonction; // Class ou se trouve la fonctions (tjrs dans le package fonctions)
	private TypeRequete method; // Get, Put ...
	private List<String> args; // Arguments de la query


	private String contentType;

	public Mapping(String ressource, String function, String classFonction, TypeRequete method, List<String> args, String contentType) {
		super();
		this.ressource = ressource;
		this.functionName = function;
		this.classFonction = classFonction;
		this.method = method;
		this.args = args;
		this.contentType = contentType;
	}


	/*
	 * Return true si la requete correspond a ce mapping (arguments, GET/PUT..., fonction de callback ...)
	 */
	public boolean match(Request r) {
		// Tester si bonne methode
		if(r.getMethode() != method)
			return false;
		// Tester si la query a les bon parametres
		for(String arg : args)
			if(r.getQuery().get(arg) == null)
				return false;
		// la ressource correspond bien (/a/b/c)
		Pattern pattern = Pattern.compile(ressource);
		Matcher matcher = pattern.matcher(r.getPathRessourceAsString());
		if(!matcher.matches())
			return false;

		return true;
	}

	public JSONObject toJSON() {
		JSONObject res = new JSONObject();
		res.accumulate(MappingConstantes.ressource, ressource);
		res.accumulate(MappingConstantes.fonction, functionName);
		res.accumulate(MappingConstantes.methode, method.toString());
		for(String q : args) 
			res.append(MappingConstantes.arguments, q);
		return res;
	}

	@Override
	public String toString() {
		return toJSON().toString();
	}


	/*
	 *  Donner une reponse pre-config, qu'il faudra juste mettre a jour et renvoyer (on peut aussi utiliser la factory a la place)
	 */
	public Response executeFunction(Request r) throws ClassNotFoundException {
		if(!match(r))
			return null;
		RequestToFunc reqToFunc = new RequestToFunc(r);
		Response respToFunc = new Response();
		if(contentType != null) // Facultatif dans le fichier de mapping mais si present on l'ajoute par defaut
			respToFunc.putContentType(contentType);
		Session session = r.getSession();
		Etat etat = session.getEtat();
		try {
			Class<?> cls = Class.forName(ConstantesTools.packageFonctions+"."+classFonction);
			Object obj = cls.newInstance(); 
			Method method = obj.getClass().getMethod(functionName, RequestToFunc.class, Response.class, Etat.class);
			Response res = (Response)method.invoke(obj, reqToFunc, respToFunc, etat);  // Si la methode est statique alors on peut mettre obj null TODO reflechir si mieux de mettre statique

			/* Gestion cookie de session */
			OffsetDateTime cookieTimeToExpire = OffsetDateTime.now(ZoneOffset.UTC).plus(Duration.ofHours(ConstantesTools.nbDaysCookies));
			String dateExpiration = DateTimeFormatter.RFC_1123_DATE_TIME.format(cookieTimeToExpire);
			String valCookie = session.getId_session()+"; expires="+dateExpiration+";";
			res.putCookie(SessionConstante.cookie_session_name, valCookie); // On met/remet a jour l'idSession
			return res;
		} catch (Exception e) {
			// Si passe par la, de grande chance que l'utilisateur a fait n'importe quoi. Le serveur ne doit pas cracher
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String messageError = "Internal Error</br></br>Cause = "+e.getCause()+"</br></br>"+sw.toString();
			return ResponseFactory.internalError(messageError);
		}
	}

}
