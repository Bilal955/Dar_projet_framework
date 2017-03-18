package model.mytools;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import model.Presence;
import model.UserAccount;
import tools.Tools;

public class MyTools {

	public static String getPresenceJSON() throws FileNotFoundException {
		return Tools.fileToString("Ressources/data/isPresent.json");
	}

	public static List<Presence> getPresence() throws FileNotFoundException {
		JSONTokener tokener = new JSONTokener(Tools.fileToString("Ressources/data/isPresent.json"));
		List<Presence> liste = new ArrayList<>();
		JSONArray array = new JSONArray(tokener);
		for(int i = 0; i < array.length(); i++){
			liste.add(new Presence(array.getJSONObject(i).getString("name"), new Boolean(array.getJSONObject(i).getString("presence"))));
		}
		return liste;
	}

	public static void savePresence(Presence p) throws FileNotFoundException {
		List<Presence> liste = getPresence();
		boolean found = false;
		String s = "[";
		for(Presence presence : liste){
			if(presence.getNom().equals(p.getNom())){
				liste.remove(presence);
				liste.add(0, presence);
				found = true;
				s+="{\"name\":" + p.getNom() + ",\"presence\":" + p.getIsPresent() + "},";
			}
			else
				s+="{\"name\":" + presence.getNom() + ",\"presence\":" + presence.getIsPresent() + "},";
		}

		if(!found)
			s+="{\"name\":" + p.getNom() + ",\"presence\":" + p.getIsPresent() + "},";

		s = s.substring(0, s.length()-1);
		s+="]";
		Tools.overrideFile("Ressources/data/isPresent.json", s);
	}	

	public static String getPassword(String login) throws FileNotFoundException{
		JSONTokener tokener = new JSONTokener(Tools.fileToString("Ressources/data/account.json"));
		JSONArray array = new JSONArray(tokener);
		for(int i = 0; i < array.length(); i++){
			if(login.equals(array.getJSONObject(i).getString("login"))){
				return array.getJSONObject(i).getString("mdp");
			}
		}
		return "";
	}

	public static String getMail(String login) throws FileNotFoundException{
		JSONTokener tokener = new JSONTokener(Tools.fileToString("Ressources/data/account.json"));
		JSONArray array = new JSONArray(tokener);
		for(int i = 0; i < array.length(); i++){
			if(login.equals(array.getJSONObject(i).getString("login"))){
				return array.getJSONObject(i).getString("mail");
			}
		}
		return "";
	}

	public static void saveUser(UserAccount u){
		String s = "";
		try {
			s = Tools.fileToString("Ressources/data/account.json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		s = s.substring(0, s.length()-2);
		s += ",\n" + u.toString() + "\n";
		s+="]";
		System.out.println("S : "+s);
		Tools.overrideFile("Ressources/data/account.json", s);

		//On met l'utilisateur non disponible par defaut
		JSONTokener tokener = new JSONTokener("");
		try {
			tokener = new JSONTokener(Tools.fileToString("Ressources/data/isPresent.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JSONArray array = new JSONArray(tokener);
		s = "[{\"name\":\""+u.getLogin()+"\", \"presence\":\"false\"},";
		String s2 = array.toString().substring(1, array.toString().length());
		s += s2;
		Tools.overrideFile("Ressources/data/isPresent.json", s);
	}

	public static String modifyPresence(String login){
		JSONTokener tokener = new JSONTokener("");
		String s = "";
		boolean b = true;
		try {
			tokener = new JSONTokener(Tools.fileToString("Ressources/data/isPresent.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JSONArray array = new JSONArray(tokener);
		for(int i = 0; i < array.length(); i++){
			if(login.equals(array.getJSONObject(i).getString("name"))){
				b = !Boolean.valueOf(array.getJSONObject(i).getString("presence"));
				System.out.println("VAUT : "+ b);
				array.remove(i);
				s += "[{\"name\":\""+login+"\", \"presence\":\""+b+"\"},";
			}
		}
		String s2 = array.toString().substring(1, array.toString().length());
		s += s2;
		Tools.overrideFile("Ressources/data/isPresent.json", s);
		if(b)
			return "true";
		else
			return"false";
	}

}
