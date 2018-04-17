package br.com.unip.redes.objetos;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Aplicacao {


	public static void main(String[] args) {

		
		try {
			switch (args[0]) {
			case "servidor":
				int op = 99;
				System.out.println(" ** Inicio de criação do servidor **");
				Scanner scanOp = new Scanner(System.in);
				Servidor servidor = new Servidor();
				System.out.println("Servidor criado!");
				do {
					System.out.println("Digite um número para obter informações do servidor.\n 1 - Total de usuarios");
					op = scanOp.nextInt();
					switch (op) {
					case 1:
						System.out.println("Possui "+ Servidor.countUsuarios+" usuarios connectados.");
						break;

					default:
						System.out.println("Estou funcionando");
						break;
					}
				
				} while (true);

			case "cliente":

				Socket client = new Socket("127.0.0.1", 8080);
				new Thread(new ClienteServidorOutPutStream(client, null)).start();
				
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
