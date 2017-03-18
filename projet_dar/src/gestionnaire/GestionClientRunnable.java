package gestionnaire;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.EnTeteMalFormeeException;
import exceptions.EndOfStreamException;
import exceptions.RequeteMalFormeeException;
import exceptions.RequeteVideException;
import requetes.AiguilleurRequetes;
import requetes.Request;
import requetes.RequestParser;
import response.Response;
import response.ResponseFactory;
import session.SessionTools;

public class GestionClientRunnable implements Runnable {

	private Socket clientSocket;

	public GestionClientRunnable(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		System.out.println("-------------------\n[Connexion accepted]");
		try {
			String line;
			StringBuilder strBuilder = new StringBuilder();
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			/* Lecture de la requete */
			boolean haveBody = false;
			int sizeBody = 0;
			while (true) { 
				line = bufferReader.readLine();
				if(line == null || line.isEmpty()) {
					if(line == null) 
						throw new EndOfStreamException();
					break;
				}
				/* Gestion content-lenght */
				Pattern patternCtLength = Pattern.compile("^Content-Length:\\s*(\\d+)");
				Matcher matcher = patternCtLength.matcher(line);
				if(matcher.matches()) {
					sizeBody = Integer.parseInt(matcher.group(1));
					haveBody = true;
				}
				/* fin gestion */
				strBuilder.append(line+"\n");
			}

			/* Lecture du body */
			if(haveBody) {
				char [] buf = new char [sizeBody];
				bufferReader.read(buf);
				StringBuilder strBuilder1 = new StringBuilder();
				for (int i = 0; i < buf.length; i++) 
					strBuilder1.append(buf[i]);
				String body = strBuilder1.toString();
				strBuilder.append("\n"+body);
			}

			/* Analyse syntaxique */ 
			Request requete = null;
			boolean parseOk = true;
			try {
				requete = RequestParser.parse(strBuilder.toString().trim());
			} catch (RequeteVideException e) {
				parseOk = false;
				sendResponse(clientSocket, ResponseFactory.requeteVide());
			} catch (RequeteMalFormeeException e) { // TODO
				parseOk = false;
				e.printStackTrace();
			} catch (EnTeteMalFormeeException e) { // TODO
				parseOk = false;
				e.printStackTrace();
			}
			if(parseOk) {
				/* Obtention de la reponse */
				requete.getSession().maj_time(); // On met a jour le time de la session
				Response reponse = AiguilleurRequetes.aiguillage(requete);
				/* Retour de la reponse au format texte */
				sendResponse(clientSocket, reponse);
				/* 
				 * On sauvegarde en dur la mise a jour de la session 
				 * Bon, dans la vrai vie on le ferait pas a chaque appel mais toutes les X minutes/heures
				 * Mais comme dans notre cas, la duree de vie du serveur depasse rarement 5 minutes
				 * on sauvegarde a chaque fois pour pas perdre les modifications
				 */
				SessionTools.saveAllSession();	
			}
		} catch (EndOfStreamException e) { // TODO When stable fusionner les 2 du bas 
			System.out.println("WHAT GESTIONCLIENTRUNNABLE LINE == NULL !!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("[Connexion closed]");
		}
	}

	private static void sendResponse(Socket clientSocket, Response reponse) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new BufferedOutputStream(clientSocket.getOutputStream()), "UTF-8")); // garder ou pas utf 8 ?
			writer.append(reponse.toString()+"\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
