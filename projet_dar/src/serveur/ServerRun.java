package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import constantes.ConstantesTools;
import gestionnaire.GestionClientRunnable;
import session.ThreadCleanSessions;

public class ServerRun {

	// http://127.0.0.1:1025

	//	public static void main(String[] args) throws Exception {
	//
	//		(new Thread(new ThreadCleanSessions())).start();
	//
	//		@SuppressWarnings("resource")
	//		ServerSocket sock = new ServerSocket(ConstantesTools.PORT);
	//		ExecutorService service = Executors.newFixedThreadPool(ConstantesTools.NB_THREAD_MAX);
	//
	//		System.out.println("Server is open, port = "+ConstantesTools.PORT+"\n");
	//		while(true) {
	//			service.execute(new GestionClientRunnable(sock.accept()));
	//		}
	//	}

	public static void runServeur() throws IOException {
		(new Thread(new ThreadCleanSessions())).start();
		@SuppressWarnings("resource")
		ServerSocket sock = new ServerSocket(ConstantesTools.PORT);
		ExecutorService service = Executors.newFixedThreadPool(ConstantesTools.NB_THREAD_MAX);
		System.out.println("Server is open, port = "+ConstantesTools.PORT+"\n");
		while(true) {
			service.execute(new GestionClientRunnable(sock.accept()));
		}
	}

}
