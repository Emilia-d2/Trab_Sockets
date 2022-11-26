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
        
        AgenciaBancariaPagueBem agenciaA = new AgenciaBancariaPagueBem("11");
        
        ContaBancariaPagueBem contaA = new ContaBancariaPagueBem("333");
        
        contaA.setAgencia("iii");
        agenciaA.setContaBancaria(contaA.getNumeroConta(), contaA);
       
    }
    
}
