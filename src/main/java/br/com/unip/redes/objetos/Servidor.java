package br.com.unip.redes.objetos;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

public class Servidor {
	
	static Integer countUsuarios=0;
	
	// Lista contendo PrintStream dos clientes
	// Contém o objeto que envia mensagem para um cliente.
	private HashMap<String,PrintStream> listaClientesPrintStream;
	// Lista contendo o nome dos clientes
	// Sua chave é o socket
	private HashMap<Socket,String> listaNomeClientes;
	
	private ServidorSocket servidorSocket;
	
	public Servidor() throws IOException {
		ServidorSocket servidorSocket = new ServidorSocket(8080, this);
		Thread servRunning = new Thread(servidorSocket);
		servRunning.start();
		System.out.println("** Thread ServidorScoket iniciada ** ");
		listaClientesPrintStream = new  HashMap<>();
		listaNomeClientes = new HashMap<>();
	}
	
	public void adicionaNovoCliente(Socket cliente) throws IOException {
		Random random = new Random();
		String usuarioCriado = new Integer(((countUsuarios++)+random.nextInt())).toString();
		// Adiciona novo cliente e nomeia ele com o número contador.
		listaNomeClientes.put(cliente,usuarioCriado);
		listaClientesPrintStream.put(usuarioCriado, new PrintStream(cliente.getOutputStream()));
		
	}
	
	/*
	 * Método criado para receber uma mensagem e enviar para todos os usuários conectados ao servidor.
	 */
	public void sendToAll(String menssage) {
		for(PrintStream sender : listaClientesPrintStream.values()) {
			sender.println(menssage);
		}
	}
	
	public void sendToClient(Socket socket,String mensage) {
		
		String nomeCliente = listaNomeClientes.get(socket);
		sendToClient(nomeCliente, mensage);
	}
	
	public void sendToClient(String clienteDestino,String clienteRemetente,String mensage) {
		PrintStream sender = listaClientesPrintStream.get(clienteDestino);
		sender.println(clienteRemetente+" diz: "+mensage);
	}
	
	public void sendToClient(String clienteDestino,String mensage) {
		PrintStream sender = listaClientesPrintStream.get(clienteDestino);
		sender.println(mensage);
	}
	
	// Getters and Setters
	public ServidorSocket getServidorSocket() {
		return servidorSocket;
	}
	
	/**
	 * Método criado para alterar o nome do usuário no servidor
	 * @param cliente
	 * @param nome
	 */
	public void mudaNomeCliente(Socket cliente, String nome) {
		String nomeServidor = listaNomeClientes.get(cliente);
		listaNomeClientes.put(cliente, nome);
		PrintStream sender = listaClientesPrintStream.get(nomeServidor);
		listaClientesPrintStream.put(nome, sender);
		listaClientesPrintStream.remove(nomeServidor);
		
	}
	
	public void listaClientes() {
		int i=1;
		for(String nome : listaNomeClientes.values()) {
			System.out.println((i++)+") "+nome);
		}
	}
	public void listaClientes(Socket socket) {
		String nomeCliente = listaNomeClientes.get(socket);
		listaClientes(nomeCliente);
	}
	public void listaClientes(String nomeCliente) {
		PrintStream sender = listaClientesPrintStream.get(nomeCliente);
		int i=1;
		sender.println( "   .:: Clientes Logados ::.");
		for(String nome : listaNomeClientes.values()) {
			sender.println("   "+(i++)+") "+nome);
		}
		sender.println( "\n   .:: Fim ::.\n\n");
	}
}