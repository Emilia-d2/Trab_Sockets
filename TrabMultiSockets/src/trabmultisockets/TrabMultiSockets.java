/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trabmultisockets;

/**
 *
 * @author milif
 */
public class TrabMultiSockets {
    public static void main(String[] args) {
        
        AgenciaBancariaPagueBem agenciaA = new AgenciaBancariaPagueBem("0226");  
        ContaBancariaPagueBem contaA = new ContaBancariaPagueBem("5555");
        contaA.setAgencia("0226");
        agenciaA.setContaBancaria(contaA.getNumeroConta(), contaA);
        
        
        AgenciaBancariaPagueBem agenciaB = new AgenciaBancariaPagueBem("0228");
        ContaBancariaPagueBem contaB = new ContaBancariaPagueBem("7777");
        contaB.setAgencia("0228");
        agenciaB.setContaBancaria(contaB.getNumeroConta(), contaB);
       
    }
    
}
