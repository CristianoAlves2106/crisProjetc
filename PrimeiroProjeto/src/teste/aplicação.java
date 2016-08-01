/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

/**
 *
 * @author aluno
 */
public class aplicação {
    public static void main(String[] args) {
        Aluno aluno = new Aluno();
        aluno.setMundo("mundo");
        aluno.setOla("ola");
        
        System.out.println(aluno.getOla() + " "+aluno.getMundo());
    }
}
