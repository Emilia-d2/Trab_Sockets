/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabmultisockets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author milif
 */
public class RecebeDados extends Thread{
    
    private boolean recebendo_dados;
    SocketChannel cliente;
    SocketChannel gerente;
    BlockingQueue<ByteBuffer> entrada_dados;
    int bytes;
    
    public RecebeDados(SocketChannel cliente, SocketChannel gerente, BlockingQueue<ByteBuffer> entrada_dados) {
       try { 
           if(entrada_dados == cliente){
           this.cliente = cliente;
           this.entrada_dados = entrada_dados;
           this.recebendo_dados = true;
           }
           if(entrada_dados == gerente){
           this.gerente = gerente;
           this.entrada_dados = entrada_dados;
           this.recebendo_dados = true;
           }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        try {
            while (recebendo_dados) {
                ByteBuffer lendo = bytesRead(6);
                if (lendo == null) {
                    cliente.close();
                    gerente.close();
                } else {
                    
                    lendo.getShort();
                    int size = lendo.getInt();
                    lendo.rewind();

                    ByteBuffer executando = bytesRead(size - 6);
                    ByteBuffer mensagem = ByteBuffer.allocate(size);
                    mensagem.put(lendo).put(executando).flip();

                    try {
                        entrada_dados.put(mensagem);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public ByteBuffer bytesRead(int bytes) throws IOException {
        ByteBuffer mensagem = ByteBuffer.allocate(bytes);
        int leituraBytes = 0;
        while (leituraBytes < bytes) {
            int contagem = cliente.read(mensagem);
            if (contagem == -1) {
                cliente.close();
                gerente.close();
            }
            leituraBytes += contagem;
        }
        mensagem.flip();
        return mensagem;
    }
    
}
