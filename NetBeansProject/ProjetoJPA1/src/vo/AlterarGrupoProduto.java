/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;

/**
 *
 * @author colab
 */
public class AlterarGrupoProduto {

    public static void main(String[] args) {
        EntityManagerFactory fabricaEntityManager = null;
        EntityManager entityManager = null;
        try {
            fabricaEntityManager = Persistence.createEntityManagerFactory("UESTOQUE");

            entityManager = fabricaEntityManager.createEntityManager();
            
            entityManager.getTransaction().begin();
            GrupoProdutoVo grupoVo = null;
            Query query = entityManager.createQuery("SELECT g FROM GrupoProdutoVo g WHERE TRIM(g.nome) =  'Enlatados'", GrupoProdutoVo.class);
            
            grupoVo = (GrupoProdutoVo) query.getResultList().get(0);
            grupoVo.setMargemLucro(50);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

}
