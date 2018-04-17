package br.com.unip.redes.objetos;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteServidorInPutStream implements Runnable{

	Socket clientInServer;
	
	@Override
	public void run() {
		do {
			
			try {
				System.out.println("Connectou ! ");
				Scanner inPutStreamClient = new Scanner(clientInServer.getInputStream());
				
				while (inPutStreamClient.hasNextLine()) {
					System.out.println(inPutStreamClient.nextLine());
				}
				
				
				System.err.println("Desligando servidor ... ");
				inPutStreamClient.close();
	            clientInServer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} while (true);
	}
	
	public ClienteServidorInPutStream(Socket cliente) throws IOException {
		clientInServer = cliente;
	}

	public Socket getClientInServer() {
		return clientInServer;
	}
	
}
