package session;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import exceptions.SessionAlreadyExistException;
import exceptions.SessionDoesntExistException;

public class SessionTools {

	private static List<Session> sessions = loadAllSesion();


	public synchronized static Session createNewSession() {
		Session s = new Session();
		sessions.add(s); // On verifie pas si la session existe deja car on est sur que id_session est unique
		return s;
	}

	public synchronized static Session getSession(String id_session) throws SessionDoesntExistException {
		List<Session> list = sessions.stream().filter(p -> p.getId_session().equals(id_session)).collect(Collectors.toList());
		Session res = list.isEmpty() ? null : list.get(0);
		if(res == null)
			throw new SessionDoesntExistException(id_session);
		return res;
	}

	public synchronized static void addSession(Session s) throws SessionAlreadyExistException {
		if(sessions.stream().filter(p -> p.getId_session().equals(s.getId_session())).count() > 0)
			throw new SessionAlreadyExistException(s.getId_session());
		sessions.add(s);
	}

	@SuppressWarnings("unchecked")
	private synchronized static List<Session> loadAllSesion() {
		try {
			ObjectInputStream in = null;
			List<Session> res;
			try {
				in = new ObjectInputStream(new FileInputStream(SessionConstante.sessionFileSerial));
				res = (List<Session>) in.readObject();
				if(res == null) {
					res = new ArrayList<>();
					System.err.println("WHAT ARE THOSE ON SESSION");
				}
			} catch(FileNotFoundException | EOFException e) { // Si aucune session n'est encore enregistrer
				System.err.println("Creation du fichier de session");
				res = new ArrayList<>();
			} finally {
				if(in != null) in.close();
			}
			System.err.println("{\n\tNumber of sessions = "+res.size());
			res.forEach(s -> System.err.println("\tSession exist : "+s.getId_session() ));
			System.err.println("}");
			return res;
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("HERE 2");
			e.printStackTrace();
		}
		System.out.println("WHY IS HERE ???");
		return null; 
	}

	public synchronized static void saveAllSession() {
		try {
			//System.out.println("[Sauvegarde des sessions]");
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SessionConstante.sessionFileSerial, false));
			out.writeObject(sessions);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized static void cleanOldSession() {
		sessions.removeIf(s -> {
			long temps_ecoule = System.currentTimeMillis() - s.getLast_maj();
			long minutes = TimeUnit.MILLISECONDS.toMinutes(temps_ecoule);
			long dureeMax = SessionConstante.dureeMaxSession;
			//System.err.println("Session Life minutes = "+minutes+", dMax = "+dureeMax);
			return minutes > dureeMax;
		});
		saveAllSession(); // On en profite pour faire une sauvegarde des sessions sur le disque
	}


}
