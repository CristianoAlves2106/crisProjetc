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
public class IncluirProduto {

    public static void main(String[] args) {
        EntityManagerFactory fabricaEntityManager;
        EntityManager entityManager = null;

        try {
            fabricaEntityManager = Persistence.createEntityManagerFactory("UESTOQUE");

            entityManager = fabricaEntityManager.createEntityManager();
            
            

        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.toString());
        } 
        Query query = entityManager.createQuery("SELECT g FROM GrupoProdutoVo g WHERE TRIM(g.nome)='Enlatados'",GrupoProdutoVo.class);
        
        GrupoProdutoVo grupo = (GrupoProdutoVo) query.getResultList().get(0);
        
        ProdutoVO produto1 = new ProdutoVO();
        produto1.setNome("Ervilha");
        produto1.setMargemLucro(30);
        produto1.setPromoçao(0);
        produto1.setEstoque(100);
        produto1.setGrupo(grupo);
        
        
        ProdutoVO produto2 = new ProdutoVO();
        produto2.setNome("Milho Verde");
        produto2.setMargemLucro(30);
        produto2.setPromoçao(0);
        produto2.setEstoque(100);
        produto2.setGrupo(grupo);
        
        entityManager.getTransaction().begin();
        entityManager.persist(produto1);
        entityManager.persist(produto2);
        entityManager.getTransaction().commit();

    }

}
