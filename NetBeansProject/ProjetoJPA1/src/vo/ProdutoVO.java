/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author colab
 */
@Entity
@Table(name = "produto")
    public class ProdutoVO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int codigo;
    
    @Column(length = 40,nullable = false)
    private String nome;

    @Column(nullable = false,precision = 5,scale = 2)
    private float margemLucro;

    @Column(nullable = false,precision = 5,scale = 2)
    private float promoçao;
    
    @Column(nullable = false)
    private int estoque;
    
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    private GrupoProdutoVo grupo;

    public GrupoProdutoVo getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoProdutoVo grupo) {
        this.grupo = grupo;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome.trim();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getMargemLucro() {
        return margemLucro;
    }

    public void setMargemLucro(float margemLucro) {
        this.margemLucro = margemLucro;
    }

    public float getPromoçao() {
        return promoçao;
    }

    public void setPromoçao(float promoçao) {
        this.promoçao = promoçao;
    }

    @Override
    public String toString() {
        return this.nome.trim();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.codigo;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProdutoVO other = (ProdutoVO) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        return true;
    }

}
