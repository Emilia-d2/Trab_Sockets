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
public class Gerente extends Thread{
  
   private final String IP_SERV = ConfiguracoesConta.IP_SERV;
    private final int PORTA_SERV = ConfiguracoesConta.PORTA_SERV;
    private ComunicadorBanco canalServidor;
    private Scanner entradaDados;
    private short mensagem_tipo;
    private int porta_conexao;
    private Usuario usuario; 
    private String conta;
    private String descricao;
    
    public static void main(String[] args) {
        Gerente gerente = new Gerente();
        gerente.conectarServidor();
        gerente.menu();
    }

    public void run() {
        try {
            ByteBuffer buffer = null;
            System.out.println("---------------------------------------");
            System.out.println("Gerente do banco =>                    |");
            System.out.println("Há uma troca de mensagens              |");
            System.out.println("---------------------------------------");
            
            while (true) {
                buffer = this.canalServidor.RecebendoMensagem();
                this.mensagem_tipo = buffer.getShort();

                switch (this.mensagem_tipo) {
                    case ConfiguracoesConta.CRIAR_CONTA:
                        System.out.println("Gerente =>");
                        System.out.println("Criei uma conta Bancária! ");
                        break;
                    case ConfiguracoesConta.ATUALIZAR_CONTA:
                        System.out.println("Gerente =>");
                        System.out.println("Atualizei uma conta Bancária! ");
                        break;
                    case ConfiguracoesConta.LER_CONTA:
                        System.out.println("Gerente =>");
                        System.out.println("Quero ver as contas Bancárias! ");
                        break;
                    case ConfiguracoesConta.DELETAR_CONTA:
                        System.out.println("Gerente =>");
                        System.out.println("Exclui uma conta Bancária! ");
                        break;
                    case ConfiguracoesConta.PORTA_CONEXAO:
                        this.porta_conexao = buffer.getInt();
                        System.out.println("Gerente =>");
                        System.out.println("Porta de Conexao com o servidor: " + this.porta_conexao);
                        break;
                    default:
                        System.out.println("Gerente =>");
                        System.out.println("TIPO DE MENSAGEM INVALIDA: " + mensagem_tipo + "\n");
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void conectarServidor() {
        try {
            this.canalServidor = new ComunicadorBanco(IP_SERV);
            this.canalServidor.conectaServidor(this.IP_SERV + ":" + this.PORTA_SERV);
            System.out.println("Conectei ao servidor: " + this.canalServidor.canalRemotoClienteDesc());
            this.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menu() {
        try {
            int opcao;
            this.entradaDados = new Scanner(System.in);

            System.out.println("---------------------------");
            System.out.println(" 1 - Nova Conta            |");
            System.out.println(" 2 - Consulta Conta        |");
            System.out.println(" 3 - Atualizar Conta       |");
            System.out.println(" 3 - Deletar Conta         |");
            System.out.println("---------------------------");
            System.out.println("Digite a opção ==> ");
            opcao = this.entradaDados.nextInt();
                
            while(opcao != 0) {
                switch (opcao) {
                    case 1:
                        criaConta();
                        break;
                    case 2:
                        consultaConta();
                        break;
                    case 3:
                        atualizaConta();
                        break;
                    case 4:
                        deletarConta();
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
               
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void criaConta() {
        try {
            this.entradaDados = new Scanner(System.in);
            System.out.println("Informe o n° da conta bancaria que deseja criar: ");
            this.conta = this.entradaDados.next();
            System.out.println("Informe a descrição: ");
            this.descricao = this.entradaDados.next();
            this.canalServidor.Mensagem_Gerente(this.canalServidor.getSocket(), this.conta, this.descricao, this.porta_conexao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consultaConta() {
        try {
           this.entradaDados = new Scanner(System.in);
            System.out.println("Informe o n° da conta bancaria que deseja consultar: ");
            this.conta = this.entradaDados.next();
            System.out.println("Informe a descrição: ");
            this.descricao = this.entradaDados.next();
            this.canalServidor.Mensagem_Gerente(this.canalServidor.getSocket(), this.conta, this.descricao, this.porta_conexao);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizaConta() {
        try {
            this.entradaDados = new Scanner(System.in);
            System.out.println("Informe o n° da conta bancaria que deseja atualizar: ");
            this.conta = this.entradaDados.next();
            System.out.println("Informe a descrição: ");
            this.descricao = this.entradaDados.next();
            this.canalServidor.Mensagem_Gerente(this.canalServidor.getSocket(), this.conta, this.descricao, this.porta_conexao);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deletarConta() {
        try {
            this.entradaDados = new Scanner(System.in);
            System.out.println("Informe o n° da conta bancaria que deseja deletar: ");
            this.conta = this.entradaDados.next();
            System.out.println("Informe a descrição: ");
            this.descricao = this.entradaDados.next();
            this.canalServidor.Mensagem_Gerente(this.canalServidor.getSocket(), this.conta, this.descricao, this.porta_conexao);


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
    
    
}
