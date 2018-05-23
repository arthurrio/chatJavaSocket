package br.com.unip.redes.objetos;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteServidorOutPutStream implements Runnable{

	Socket clientInServer;
	private Servidor servidor;
	
	
	@Override
	public void run() {
			try {
				Scanner inPutStreamClient = new Scanner(clientInServer.getInputStream());
			
				sendClient("Parabéns! Você acabou de se connectar ao servidor!\nPor favor, digite agora seu nome: ");
				String usuarioLogado="";
				if(inPutStreamClient.hasNextLine()) {
					usuarioLogado = inPutStreamClient.nextLine();
					sendClient("Seja bem-vindo "+usuarioLogado);
					servidor.mudaNomeCliente(clientInServer, usuarioLogado);
				}
				
				menu();
				while (inPutStreamClient.hasNextLine()) {
					String op = inPutStreamClient.nextLine();
					switch (op){
					case "1":
						sendClient("Digite uma mensagem para ser enviada para todos: ");
						if(inPutStreamClient.hasNextLine()) {
								servidor.sendToAll(inPutStreamClient.nextLine());
						}	
						break;
					case "2":
							sendClient("Para qual usuario deseja enviar a mensagem?");
						if(inPutStreamClient.hasNextLine()) {
							String usuarioDestino = inPutStreamClient.nextLine();
							sendClient("Digite a sua mensagem para "+usuarioDestino+": ");
							if(inPutStreamClient.hasNextLine()) {
									servidor.sendToClient(usuarioDestino,usuarioLogado ,inPutStreamClient.nextLine());
									sendClient("Mensagem enviada com sucesso!");
							}
						}
						break;
					case "3":
						servidor.listaClientes(this.clientInServer);
						break;
					default:
						break;
					}
					menu();
				}
				
				System.err.println("Desligando servidor ... ");
				inPutStreamClient.close();
	            clientInServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
	
	public ClienteServidorOutPutStream(Socket cliente, Servidor servidor) throws IOException {
		clientInServer = cliente;
		this.servidor = servidor;
	}

	public Socket getClientInServer() {
		return clientInServer;
	}
	
	public void menu() {
		sendClient("1)Enviar mensagem para todos\n2)Enviar mensagem para um cliente\n3)Listar todos usuarios");
	}
	
	public void sendClient(String msg) {
		servidor.sendToClient(this.clientInServer, msg);
	}
}
