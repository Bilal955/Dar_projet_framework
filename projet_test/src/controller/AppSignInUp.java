package controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import justDontTouchPlease.UserInformation;
import requetes.RequestToFunc;
import response.Response;
import session.Etat;
import template.TemplateTools;
import tools.Tools;

public class AppSignInUp {

	public Response home(RequestToFunc r, Response resp, Etat etat) {
		if(etat.isFirstTime() || etat.getPropriete("profilCompleted") == null) {
			System.err.println("Here boy");
			try {
				resp.setBody(Tools.fileToString("Ressources/formulaire.html"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return resp;
		}
		UserInformation u = (UserInformation) etat.getData();
		Map<String, String> map = new HashMap<>();
		map.put("nom", u.getNom());
		map.put("prenom", u.getPrenom());
		map.put("pseudo", u.getPseudo());
		try {
			resp.setBody(TemplateTools.executeTemplateFile("Ressources/hello.html", map));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return resp;
	}

	public Response saveInfo(RequestToFunc r, Response resp, Etat etat) {
		// Recupere les args (bon, en vrai il faut tester qu'ils sont bien la et agir en consequence...
		String nom = r.getPost("nom").trim();
		String prenom = r.getPost("prenom").trim();
		String pseudo = r.getPost("pseudo").trim();
		// Traiter l'enregistrement
		UserInformation u = new UserInformation(nom, prenom, pseudo);
		etat.setData(u);
		etat.putPropriete("profilCompleted", "true");
		Map<String, String> map = new HashMap<>();
		map.put("nom", nom);
		map.put("prenom", prenom);
		map.put("pseudo", pseudo);
		try {
			resp.setBody(TemplateTools.executeTemplateFile("Ressources/hello.html", map));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return resp;
	}

	public Response delete(RequestToFunc r, Response resp, Etat etat) {
		etat.setData(null);
		etat.putPropriete("profilCompleted", null);
		try {
			resp.setBody(Tools.fileToString("Ressources/formulaire.html"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return resp;
	}

}
