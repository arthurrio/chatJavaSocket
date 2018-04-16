package br.com.unip.redes.objetos;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Aplicacao {

	final static int PORTA_SOCKET = 8080;

	public static void main(String[] args) {

		try {
			switch (args[0]) {
			case "servidor":
				ServerSocket serverSocket = new ServerSocket(PORTA_SOCKET);
				InetAddress inet = serverSocket.getInetAddress();
				System.out.println("Servidor localizado no IP: " + inet.getHostAddress() + " na porta : "
						+ serverSocket.getLocalPort());
				do {

					System.out.println("Aguardando usuário...");
					Socket cliente = serverSocket.accept();
					System.out.println("Usuario connectado !");
					ClienteServidor clienteServidor = new ClienteServidor(cliente);
					Thread threadCliente = new Thread(clienteServidor);
					threadCliente.start();
				} while (true);

			case "cliente":

				Socket client = new Socket("127.0.0.1", 8080);
				PrintStream leitorMsg = new PrintStream(client.getOutputStream());
				Scanner msg = new Scanner(System.in);
				do {
					leitorMsg.println(msg.nextLine());
				} while (msg.hasNextLine());
				msg.close();
				leitorMsg.close();
				client.close();
				break;
			default:
				System.err.println("Passe um parâmetro : \"servidor\" ou \"cliente\"");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
