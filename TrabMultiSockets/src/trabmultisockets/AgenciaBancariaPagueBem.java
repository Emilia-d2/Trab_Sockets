/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabmultisockets;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;

/**
 *
 * @author milif
 */
public class AgenciaBancariaPagueBem extends Thread{
    
    private String IP = ConfiguracoesConta.IP_SERV;
    private String numero_agencia;
    private ComunicadorBanco canalDoServidor;
    private LinkedHashMap<String, ContaBancariaPagueBem> listaDeContas;
    private short mensagem_tipo;
    private String numeroConta;
    private String descricao;
    private int conexao_porta;
    
    public AgenciaBancariaPagueBem(String numero_agencia) {
        try {
            this.setNumeroAgencia(numero_agencia);
            this.canalDoServidor = new ComunicadorBanco(this.IP);
            this.listaDeContas = new LinkedHashMap<>();

            System.out.println("Servidor da agência " + this.getNumeroAgencia()
                    + " iniciado no canal " + this.canalDoServidor.Server());

            this.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            ByteBuffer buf = null;
            System.out.println("----------------------------------------------------");
            System.out.println("Agencia Bancária do Paga Bem e de Pressa! =>        |");
            System.out.println("\t Recebendo Mensagens ... \n                       |");
            System.out.println("----------------------------------------------------");
            while (true) {
                buf = this.canalDoServidor.RecebendoMensagem();
                this.mensagem_tipo = buf.getShort();
         
                switch (this.mensagem_tipo) {
                    case ConfiguracoesConta.CRIAR_CONTA:
                        this.numeroConta = buf.toString();
                        this.descricao = buf.toString();
                        this.conexao_porta = buf.getInt();
                        System.out.println("--------------------------------------------------");
                        System.out.println("Usuário Conectado =>                              |");
                        System.out.println("Criando...                                        |");
                        System.out.println("Número da Conta Bancária: " + this.numeroConta + "|");
                        System.out.println("Descrição: " + this.descricao + "                 |");
                        System.out.println("--------------------------------------------------");
                        this.tratamentoMensagemCriaConta(this.numeroConta, this.descricao, this.conexao_porta);
                        break;
                    case ConfiguracoesConta.ATUALIZAR_CONTA:
                        this.numeroConta = buf.toString();
                        this.descricao = buf.toString();
                        this.conexao_porta = buf.getInt();
                        System.out.println("--------------------------------------------------");
                        System.out.println("Usuário Conectado =>                              |");
                        System.out.println("Atualizando...                                    |");
                        System.out.println("Número da Conta Bancária: " + this.numeroConta + "|");
                        System.out.println("Descrição: " + this.descricao + "                 |");
                        System.out.println("--------------------------------------------------");
                        this.tratamentoMensagemAtualizaConta(this.numeroConta, this.descricao, this.conexao_porta);
                        break;
                    case ConfiguracoesConta.LER_CONTA:
                        this.numeroConta = buf.toString();
                        this.descricao = buf.toString();
                        this.conexao_porta = buf.getInt();
                        System.out.println("--------------------------------------------------");
                        System.out.println("Usuário Conectado =>                              |");
                        System.out.println("Lendo...                                          |");
                        System.out.println("Número da Conta Bancária: " + this.numeroConta + "|");
                        System.out.println("Descrição: " + this.descricao + "                 |");
                        System.out.println("--------------------------------------------------");
                        this.tratamentoMensagemLerConta(this.numeroConta, this.descricao, this.conexao_porta);
                        break;
                    case ConfiguracoesConta.DELETAR_CONTA:
                        this.numeroConta = buf.toString();
                        this.descricao = buf.toString();
                        this.conexao_porta = buf.getInt();
                        System.out.println("--------------------------------------------------");
                        System.out.println("Usuário Conectado =>                              |");
                        System.out.println("Deletandodo...                                    |");
                        System.out.println("Número da Conta Bancária: " + this.numeroConta + "|");
                        System.out.println("Descrição: " + this.descricao + "                 |");
                        System.out.println("--------------------------------------------------");
                        this.tratamentoMensagemDeletaConta(this.numeroConta, this.descricao, this.conexao_porta);
                        break;
                    default:
                        System.out.println("Cliente =>");
                        System.out.println("TIPO DE MENSAGEM INVALIDA: " + mensagem_tipo + "\n");
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNumeroAgencia(String numero_agencia) {
        this.numero_agencia = numero_agencia;
    }

    public String getNumeroAgencia() {
        return this.numero_agencia;
    }

    public void setContaBancaria(String numeroConta, ContaBancariaPagueBem conta) {
        try {
            this.listaDeContas.put(numeroConta, conta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ContaBancariaPagueBem getContaBancaria(String numeroConta) {
        try {
            return this.listaDeContas.get(numeroConta);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void tratamentoMensagemCriaConta(String contaBancaria, String descricao, int conexao_porta) {
        try {
            if (this.listaDeContas.containsKey(numeroConta)) {
                this.listaDeContas.get(numeroConta).setConexao_porta(conexao_porta);

                if (this.canalDoServidor.getClienteSocketLista().containsKey(conexao_porta)) {
                    this.canalDoServidor.Mensagem_Cria_Conta(this.canalDoServidor.getClienteSocketLista().get(conexao_porta), this.numeroConta, this.descricao);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void tratamentoMensagemAtualizaConta(String contaBancaria, String descricao, int conexao_porta) {
        try {
            if (this.listaDeContas.containsKey(numeroConta)) {
                this.listaDeContas.get(numeroConta).setConexao_porta(conexao_porta);

                if (this.canalDoServidor.getClienteSocketLista().containsKey(conexao_porta)) {
                    this.canalDoServidor.Mensagem_Atualiza_Conta(this.canalDoServidor.getClienteSocketLista().get(conexao_porta), this.numeroConta, this.descricao);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void tratamentoMensagemLerConta(String contaBancaria, String descricao, int conexao_porta) {
        try {
            if (this.listaDeContas.containsKey(numeroConta)) {
                this.listaDeContas.get(numeroConta).setConexao_porta(conexao_porta);

                if (this.canalDoServidor.getClienteSocketLista().containsKey(conexao_porta)) {
                    this.canalDoServidor.Mensagem_Ler_Conta(this.canalDoServidor.getClienteSocketLista().get(conexao_porta), this.numeroConta, this.descricao);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void tratamentoMensagemDeletaConta(String contaBancaria, String descricao, int conexao_porta) {
        try {
            if (this.listaDeContas.containsKey(numeroConta)) {
                this.listaDeContas.get(numeroConta).setConexao_porta(conexao_porta);

                if (this.canalDoServidor.getClienteSocketLista().containsKey(conexao_porta)) {
                    this.canalDoServidor.Mensagem_Deleta_Conta(this.canalDoServidor.getClienteSocketLista().get(conexao_porta), this.numeroConta, this.descricao);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
    
}
