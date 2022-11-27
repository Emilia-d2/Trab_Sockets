/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabmultisockets;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author milif
 */
public class ComunicadorBanco extends Thread{

    private String IP;
    private int PORTA = ConfiguracoesConta.PORTA_SERV;
    private SocketChannel gerenteConta = null;
    private SocketChannel cliente = null;
    private ServerSocketChannel server = null;
    private InetSocketAddress endereco = null;
    private BlockingQueue<ByteBuffer> entradaDados = new LinkedBlockingQueue<>();
    private static Map<Integer, SocketChannel> listaClientes;
    private boolean ativo;
    private int tipoUsuario;
    
    private static final byte SAQUE = ConfiguracoesConta.SAQUE;
    private static final byte DEPOSITO = ConfiguracoesConta.DEPOSITO;
    private static final byte EXTRATO = ConfiguracoesConta.EXTRATO;
    private static final byte CRIAR_CONTA = ConfiguracoesConta.CRIAR_CONTA;
    private static final byte LER_CONTA = ConfiguracoesConta.LER_CONTA;
    private static final byte ATUALIZAR_CONTA = ConfiguracoesConta.ATUALIZAR_CONTA;
    private static final byte DELETAR_CONTA = ConfiguracoesConta.DELETAR_CONTA;
    private static final byte PORTA_CONEXAO = ConfiguracoesConta.PORTA_CONEXAO;
    
    public ComunicadorBanco(String IP) {
        int PORTA_DEF = PORTA;
        this.IP = IP;
        boolean criar = false;
        listaClientes = new LinkedHashMap<>();

        while (!criar) {
            try {
                server = ServerSocketChannel.open();
                endereco = new InetSocketAddress(this.IP, PORTA_DEF);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            try {
                server.socket().bind(endereco);
                criar = true;
            } catch (IOException e) {
                PORTA_DEF++;
            }
        }
        this.ativo = true;
        this.start();
    }

    

    public ComunicadorBanco() {
    }

    public void run() {
        try {
            while (this.ativo) {
                try {
                    cliente = server.accept();
                    gerenteConta = server.accept();
                    listaClientes.put(this.portaRemotaClienteDesc(), cliente);
                    System.out.println("Cliente se comunicou: " + this.canalRemotoClienteDesc());
                    System.out.println("Gerente se comunicou: " + this.canalRemotoGerenteeDesc());
                    Mensagem_Conexao_Server(cliente, this.portaRemotaClienteDesc());
                    Mensagem_Conexao_Server(gerenteConta, this.portaRemotaGerenteDesc());
                    leituraRecebedor();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void conectaServidor(String hostDescription) {
        try {
            String vetores[] = hostDescription.split(":");
            String hostname = vetores[0];
            int porta = Integer.parseInt(vetores[1].trim());
            cliente = SocketChannel.open(new InetSocketAddress(hostname, porta));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        leituraRecebedor();
    }

    private void leituraRecebedor() {
        try {
            RecebeDados primeiro = new RecebeDados(this.cliente, this.gerenteConta, this.entradaDados);
            primeiro.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String canalRemotoClienteDesc() {
        try {
            String enderecoHost = cliente.socket().getInetAddress().getHostAddress();
            String enderecoPorta = Integer.toString(cliente.socket().getPort());
            return enderecoHost + ":" + enderecoPorta;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int portaRemotaClienteDesc() {
        try {
            return cliente.socket().getPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public String canalRemotoGerenteeDesc() {
        try {
            String enderecoHost = gerenteConta.socket().getInetAddress().getHostAddress();
            String enderecoPorta = Integer.toString(gerenteConta.socket().getPort());
            return enderecoHost + ":" + enderecoPorta;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int portaRemotaGerenteDesc() {
        try {
            return gerenteConta.socket().getPort();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String Server() {
        try {
            String enderecoHost = endereco.getAddress().getHostAddress();
            String enderecoPorta = Integer.toString(endereco.getPort());
            return enderecoHost + ":" + enderecoPorta;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SocketChannel getSocket() {
        try {
            return this.cliente;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public SocketChannel getSocketGerente() {
        try {
            return this.gerenteConta;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    

    public ByteBuffer RecebendoMensagem() {
        try {
            return entradaDados.take();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    
    public void Mensagem_Conexao_Server(SocketChannel canal, int conexao_porta) {
        try {
            int tamMsg = 2 + 4 + 4;
            ByteBuffer writeBuffer = ByteBuffer.allocateDirect(tamMsg);
            writeBuffer.putShort(this.PORTA_CONEXAO);
            writeBuffer.putInt(tamMsg);
            writeBuffer.putInt(conexao_porta);
            writeBuffer.rewind();
            Canal(canal, writeBuffer);
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void Mensagem_Cria_Conta(SocketChannel canal, String conta, String descricao) {
        try {
           
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Mensagem_Atualiza_Conta(SocketChannel canal, String conta, String descricao) {
        try {
           
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Mensagem_Ler_Conta(SocketChannel canal, String conta, String descricao) {
        try {
           
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Mensagem_Deleta_Conta(SocketChannel canal, String conta, String descricao) {
        try {
           
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void Mensagem_Cliente(SocketChannel canal, String agencia, String conta, String nome, String cpf, float valor, int conexao_porta) {
        try {
           
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void Mensagem_Gerente(SocketChannel canal, String conta, String descricao, int conexao_porta) {
        try {
           
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     
    public void Mensagem_Saque(SocketChannel canal, String agencia, String numero_conta, String nomeCliente, String cpf) {
        try {
           
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void Mensagem_Deposito(SocketChannel canal, String agencia, String numero_conta, String nomeCliente, String cpf) {
        try {
           
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    public void Mensagem_Extrato(SocketChannel canal, String agencia, String numero_conta, String nomeCliente, String cpf) {
        try {
           
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void Mensagem_Extrato_Cliente(SocketChannel canal, String agencia, String numero_conta, String nomeCliente, String cpf, float Valor_total, int porta) {
        try {
           
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    public void Canal(SocketChannel canal, ByteBuffer writeBuffer) {
        try {
            long nbytes = 0;
            long toWrite = writeBuffer.remaining();
            try {
                while (nbytes != toWrite) {
                    nbytes += canal.write(writeBuffer);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            writeBuffer.rewind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, SocketChannel> getClienteSocketLista() {
        return listaClientes;
    }

    public static void setClienteSocketLista(Map<Integer, SocketChannel> listaClientes) {
        ComunicadorBanco.listaClientes = listaClientes;
    }
        
}
