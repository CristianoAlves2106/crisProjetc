/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import javax.swing.JOptionPane;
import persistencia.FabricaEntityManager;
import persistencia.GrupoProdutoDAO;
import vo.GrupoProdutoVo;

/**
 *
 * @author colab
 */
public class IncluirGrupoProduto2 {

    public static void main(String[] args) {
        GrupoProdutoDAO grupoDAO = null;

        GrupoProdutoVo grupoVo = new GrupoProdutoVo();
        
        grupoVo.setNome(JOptionPane.showInputDialog("Forneça o nome de Produto"));
        
        grupoVo.setMargemLucro(Float.parseFloat(JOptionPane.showInputDialog("Forneça o % de margem de lucro")));

        grupoVo.setPromoçao(Float.parseFloat(JOptionPane.showInputDialog("Forneça o % de promoção")));
        try {
            grupoDAO = new GrupoProdutoDAO(FabricaEntityManager.getEntityManager());
            grupoDAO.inciarTransacao();
            grupoDAO.incluir(grupoVo);
            grupoDAO.confirmaTransacao();
            
        } catch (Exception e) {
        }
    }

}
