package br.com.unip.redes.objetos;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteServidorInPutStream implements Runnable{

	Socket clientInServer;
	
	@Override
	public void run() {
			
			try {
				Scanner inPutStreamClient = new Scanner(clientInServer.getInputStream());
				
				while (inPutStreamClient.hasNextLine()) {
					System.out.println(inPutStreamClient.nextLine());
				}
				
				inPutStreamClient.close();
	            clientInServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
	
	public ClienteServidorInPutStream(Socket cliente) throws IOException {
		clientInServer = cliente;
	}

	public Socket getClientInServer() {
		return clientInServer;
	}
	
	public void menu() {
		System.out.println("1) Enviar mensagem para todos\n2)Listar todos usuarios\n3)Enviar mensagem para um cliente");
	}
}
