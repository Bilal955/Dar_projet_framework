package session;

import java.io.PrintWriter;
import java.io.StringWriter;

/*
 * On sauvegarde les sessions sur le disque en meme temps qu'on supprime les sessions perimes
 * ou a la fermeture du programme
 */
public class ThreadCleanSessions implements Runnable {

	private final int nbSecondes;
	
	public ThreadCleanSessions() {
		nbSecondes = SessionConstante.nbMinuteSleep * 1000 * 60;
	}
	
	@Override
	public void run() {
		while(true) {
				SessionTools.cleanOldSession();
			try {
				Thread.sleep(nbSecondes);
			} catch (InterruptedException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				String messageError = "Internal Error, Cause = "+e.getCause()+"\n"+sw.toString();
				System.err.println(messageError);
			}
		}
		
	}

	
}

