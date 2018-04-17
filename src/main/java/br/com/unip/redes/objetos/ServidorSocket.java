package br.com.unip.redes.objetos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSocket extends ServerSocket implements Runnable{
	
	private Servidor servidor;

	public ServidorSocket(int port, Servidor servidor) throws IOException {
		super(port);
		this.servidor = servidor;
	}

	@Override
	public void run() {
		
		while(true) {
			try {
				Socket cliente = this.accept();
				
				this.servidor.adicionaNovoCliente(cliente);
				ClienteServidorOutPutStream clienteServidor = new ClienteServidorOutPutStream(cliente,servidor);
				Thread threadCliente = new Thread(clienteServidor);
				threadCliente.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
