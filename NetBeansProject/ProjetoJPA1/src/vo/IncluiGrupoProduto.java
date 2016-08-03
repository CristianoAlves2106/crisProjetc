/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author colab
 */
public class IncluiGrupoProduto {

    public static void main(String[] args) {
        EntityManagerFactory fabricaEntityManager;
        EntityManager entityManager = null;

        try {
            fabricaEntityManager = Persistence.createEntityManagerFactory("UESTOQUE");

            entityManager = fabricaEntityManager.createEntityManager();
        } catch (Exception e) {

            JOptionPane.showInternalMessageDialog(null, e.toString());
        }
        GrupoProdutoVo grupoVo = new GrupoProdutoVo();
        grupoVo.setNome("Enlatados");
        grupoVo.setMargemLucro(40);
        grupoVo.setPromoçao(5);
        entityManager.getTransaction().begin();
        entityManager.persist(grupoVo);
        entityManager.getTransaction().commit();
    }
}
