/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabmultisockets;

import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 *
 * @author milif
 */
public class Usuario extends Thread{
    
    private final String IP_SERV = ConfiguracoesConta.IP_SERV;
    private final int PORTA_SERV = ConfiguracoesConta.PORTA_SERV;
    private ComunicadorBanco canalServidor;
    private Scanner entradaDados;
    private short mensagem_tipo;
    private int porta_conexao;
    private String agencia;
    private String conta;
    private String nome;         
    private String cpf;
    private float deposito;
    private float saque;
    private float valorTotal;
          

    public static void main(String[] args) {
        Usuario cliente = new Usuario();
        cliente.conectarServidor();
        cliente.menu();
    }

    public void run() {
        try {
            ByteBuffer buf = null;
            System.out.println("----------------------------------");
            System.out.println("Cliente =>                        |");
            System.out.println("\t Há uma troca de mensagens      |");
           System.out.println("-----------------------------------");
            while (true) {
                buf = this.canalServidor.RecebendoMensagem();
                this.mensagem_tipo = buf.getShort();

                switch (this.mensagem_tipo) {
                    case ConfiguracoesConta.EXTRATO:
                        System.out.println("Cliente =>");
                        System.out.println(" Extrato comigo ");
                        break;
                    case ConfiguracoesConta.DEPOSITO:
                        System.out.println("Cliente =>");
                        System.out.println(" Depositei! ");
                        break;
                    case ConfiguracoesConta.SAQUE:
                        System.out.println("Cliente =>");
                        System.out.println(" Saquei! ");
                        break;
                    case ConfiguracoesConta.PORTA_CONEXAO:
                        this.porta_conexao = buf.getInt();
                        System.out.println("Cliente =>");
                        System.out.println(" Porta de Conexao com o servidor: " + this.porta_conexao);
                        break;
                    default:
                        System.out.println("Cliente =>");
                        System.out.println(" TIPO DE MENSAGEM INVALIDA: " + mensagem_tipo);
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void conectarServidor() {
        try {
            this.canalServidor = new ComunicadorBanco();
            this.canalServidor.conectaServidor(this.IP_SERV + ":" + this.PORTA_SERV);
            System.out.println(" Me conectei ao servidor: " + this.canalServidor.portaRemotaClienteDesc());
            this.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menu() {
        try {
            int opcao;
            this.entradaDados = new Scanner(System.in);

            System.out.println("--------------------------");
            System.out.println(" 1 - Deposito             |");
            System.out.println(" 2 - Saque                |");
            System.out.println(" 3 - Consulta extrato     |");
            System.out.println("--------------------------");
            System.out.println("Digite a opção:");
            opcao = this.entradaDados.nextInt();
            
             while (opcao != 0) {
                switch (opcao) {
                    case 1:
                        deposito();
                        break;

                    case 2:
                        saque();
                        break;

                    case 3:
                        extrato();
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deposito() {
        try {
            this.entradaDados = new Scanner(System.in);
            System.out.println("Informe sua agência bancária: ");
            this.agencia = this.entradaDados.next();
            System.out.println("Informe a conta bancaria: ");
            this.conta = this.entradaDados.next();
            System.out.println("Informe seu nome: ");
            this.nome = this.entradaDados.next();
            System.out.println("Informe seu CPF: ");
            this.cpf = this.entradaDados.next();
            System.out.println("Informe o valor para deposito: ");
            this.deposito = this.entradaDados.nextFloat();
            this.canalServidor.Mensagem_Cliente(this.canalServidor.getSocket(), this.agencia, this.conta, this.nome, this. cpf, this.deposito, this.porta_conexao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saque() {
        try {
           this.entradaDados = new Scanner(System.in);
            System.out.println("Informe sua agência bancária: ");
            this.agencia = this.entradaDados.next();
            System.out.println("Informe a conta bancaria: ");
            this.conta = this.entradaDados.next();
            System.out.println("Informe seu nome: ");
            this.nome = this.entradaDados.next();
            System.out.println("Informe seu CPF: ");
            this.cpf = this.entradaDados.next();
            System.out.println("Informe o valor para deposito: ");
            this.saque = this.entradaDados.nextFloat();
            this.canalServidor.Mensagem_Cliente(this.canalServidor.getSocket(), this.agencia, this.conta, this.nome, this. cpf, this.saque, this.porta_conexao);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extrato() {
        try {
           this.entradaDados = new Scanner(System.in);
            System.out.println("Informe sua agência bancária: ");
            this.agencia = this.entradaDados.next();
            System.out.println("Informe a conta bancaria: ");
            this.conta = this.entradaDados.next();
            System.out.println("Informe seu nome: ");
            this.nome = this.entradaDados.next();
            System.out.println("Informe seu CPF: ");
            this.cpf = this.entradaDados.next();
            this.canalServidor.Mensagem_Extrato_Cliente(this.canalServidor.getSocket(), this.agencia, this.conta, this.nome, this. cpf, this.valorTotal, this.porta_conexao);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPorta_conexao() {
        return porta_conexao;
    }

    public void setPorta_conexao(int porta_conexao) {
        this.porta_conexao = porta_conexao;
    }

    public ComunicadorBanco getCanalServidor() {
        return canalServidor;
    }

    public void setCanalServidor(ComunicadorBanco canalServidor) {
        this.canalServidor = canalServidor;
    }

    public Scanner getEntradaDados() {
        return entradaDados;
    }

    public void setEntradaDados(Scanner entradaDados) {
        this.entradaDados = entradaDados;
    }

    public short getMensagem_tipo() {
        return mensagem_tipo;
    }

    public void setMensagem_tipo(short mensagem_tipo) {
        this.mensagem_tipo = mensagem_tipo;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

   

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    
}
