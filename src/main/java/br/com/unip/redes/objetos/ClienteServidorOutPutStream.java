package br.com.unip.redes.objetos;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteServidorOutPutStream implements Runnable{

	Socket clientInServer;
	private Servidor servidor;
	
	@Override
	public void run() {
		do {
			
			try {
				
				Scanner inPutStreamClient = new Scanner(clientInServer.getInputStream());
				
				while (inPutStreamClient.hasNextLine()) {
					String msg = inPutStreamClient.nextLine();
					System.out.println(msg);
					if(servidor != null)
						servidor.sendToAll(msg);
					
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
	
	public ClienteServidorOutPutStream(Socket cliente, Servidor servidor) throws IOException {
		clientInServer = cliente;
		this.servidor = servidor;
	}

	public Socket getClientInServer() {
		return clientInServer;
	}
	
}
