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
public class ContaBancariaPagueBem extends Thread{
    
   private int conexao_porta;
    private String numeroConta;
    private float saldo;
    private float limite;
    private String nomeUsuario;
    private String cfp;
    private String agencia;
    private String IP = ConfiguracoesConta.IP_SERV;
    private ComunicadorBanco canalDoServidor;
    private short mensagem_tipo;
    private LinkedHashMap<String, ContaBancariaPagueBem> listaDeContas;
    private float valorDeposito;
    private float valorSaque;
    private float valorTotal;
    
      
    
    public ContaBancariaPagueBem(String numeroConta) {
        this.numeroConta = numeroConta;
        this.limite = 1000000;
        try {
            this.setNumeroConta(numeroConta);
            this.canalDoServidor = new ComunicadorBanco(this.IP);
            this.listaDeContas = new LinkedHashMap<String, ContaBancariaPagueBem>();
            System.out.println("Servidor da Conta Banária " + this.getContaBancaria(numeroConta)
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
            System.out.println("Conta Bancária do Paga Bem e de Pressa! =>          |");
            System.out.println("\t Recebendo Mensagens ... \n                       |");
            System.out.println("----------------------------------------------------");
            while (true) {
                buf = this.canalDoServidor.RecebendoMensagem();
                this.mensagem_tipo = buf.getShort();

                switch (this.mensagem_tipo) {
                    case ConfiguracoesConta.DEPOSITO:
                        this.agencia = buf.toString();
                        this.numeroConta = buf.toString();
                        this.nomeUsuario = buf.toString();
                        this.cfp = buf.toString();
                        this.valorDeposito = buf.getFloat();
                        this.conexao_porta = buf.getInt();
                        System.out.println("--------------------------------------------------");
                        System.out.println(" Usuário Conectado =>                             |");
                        System.out.println(" Recebi mensagem do ....                          |");
                        System.out.println(" Agencia Bancaria: " + this.agencia + "           |");
                        System.out.println(" Conta Bancaria: " + this.numeroConta + "         |");
                        System.out.println(" Nome do usuário: " + this.nomeUsuario + "        |");
                        System.out.println(" CPF do usuário: " + this.cfp + "                 |");
                        System.out.println(" Valor do Depósito : " + this.valorDeposito + "   |");
                        System.out.println("--------------------------------------------------");
                        this.tratamentoMensagemContaBancaria(this.agencia, this.numeroConta, this.nomeUsuario, this.cfp, this.conexao_porta);
                        break;
                    case ConfiguracoesConta.SAQUE:
                        this.agencia = buf.toString();
                        this.numeroConta = buf.toString();
                        this.nomeUsuario = buf.toString();
                        this.cfp = buf.toString();
                        this.valorSaque = buf.getFloat();
                        this.conexao_porta = buf.getInt();
                        System.out.println("--------------------------------------------------");
                        System.out.println(" Usuário Conectado =>                             |");
                        System.out.println(" Recebi mensagem do ....                          |");
                        System.out.println(" Agencia Bancaria: " + this.agencia + "           |");
                        System.out.println(" Conta Bancaria: " + this.numeroConta + "         |");
                        System.out.println(" Nome do usuário: " + this.nomeUsuario + "        |");
                        System.out.println(" CPF do usuário: " + this.cfp + "                 |");
                        System.out.println(" Valor do Saque : " + this.valorSaque + "         |");
                        System.out.println("--------------------------------------------------");
                        this.tratamentoMensagemContaBancaria(this.agencia, this.numeroConta, this.nomeUsuario, this.cfp, this.conexao_porta);
                        break;                  
                    case ConfiguracoesConta.EXTRATO:
                        this.agencia = buf.toString();
                        this.numeroConta = buf.toString();
                        this.nomeUsuario = buf.toString();
                        this.cfp = buf.toString();
                        this.valorTotal = buf.getFloat();
                        this.conexao_porta = buf.getInt();
                        System.out.println("--------------------------------------------------");
                        System.out.println(" Usuário Conectado =>                             |");
                        System.out.println(" Recebi mensagem do ....                          |");
                        System.out.println(" Agencia Bancaria: " + this.agencia + "           |");
                        System.out.println(" Conta Bancaria: " + this.numeroConta + "         |");
                        System.out.println(" Nome do usuário: " + this.nomeUsuario + "        |");
                        System.out.println(" CPF do usuário: " + this.cfp + "                 |");
                        System.out.println(" Valor total : " + this.valorTotal + "            |");
                        System.out.println("--------------------------------------------------");
                        this.tratamentoMensagemContaBancaria(this.agencia, this.numeroConta, this.nomeUsuario, this.cfp, this.conexao_porta);
                        break;
                    default:
                        System.out.println("Usuário =>");
                        System.out.println("\t\t TIPO DE MENSAGEM INVALIDA: " + mensagem_tipo + "\n");
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void tratamentoMensagemContaBancaria(String agencia, String numero_conta, String nomeCliente, String cpf, int conexao_porta) {
        try {
           /* if (this.listaDeContas.containsKey(numeroConta)) {
                this.listaDeContas.get(numeroConta).setConexao_porta(conexao_porta);

                if (this.canalDoServidor.getClienteSocketLista().containsKey(conexao_porta)) {
                    this.canalDoServidor.Mensagem_Conta_Bancari(this.canalDoServidor.getClienteSocketLista().get(conexao_porta), this.numeroConta, this.agencia, this.nomeUsuario,  this.cpf);
                }
            }*/
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getConexao_porta() {
        return conexao_porta;
    }

    public void setConexao_porta(int conexao_porta) {
        this.conexao_porta = conexao_porta;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }
    
     public void deposita(float valor) {
        this.saldo += valor;
    }

    public void saca(float valor) {
        this.saldo -= valor;
    }
    
    public float getSaldo() {
        return saldo + this.limite;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public float getLimite() {
        return limite;
    }

    public void setLimite(float limite) {
        this.limite = limite;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getCfp() {
        return cfp;
    }

    public void setCfp(String cfp) {
        this.cfp = cfp;
    }

    
}
