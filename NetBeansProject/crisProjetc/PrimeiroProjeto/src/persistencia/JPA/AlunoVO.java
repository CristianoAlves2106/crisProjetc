package persistencia.JPA;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import vo.*;

@Entity
@Table(name = "aluno")
public class AlunoVO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int matricula;
    @Column(length = 40, nullable = false)
    private String nome;
    @Column(length = 40, nullable = false)
    private String nomeMae;
    @Column(length = 40, nullable = false)
    private String nomePai;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    private EnumSexo sexo;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private EnderecoVO endereco;

    public AlunoVO() {
        this.endereco = new EnderecoVO();
        this.matricula = 0;
        this.nome = "";
        this.nomeMae = "";
        this.nomePai = "";
        this.sexo = EnumSexo.FEMININO;
    }

    public AlunoVO(int matricula, String nome, EnumSexo sexo) {
        this();
        this.matricula = matricula;
        this.nome = nome;
        this.sexo = sexo;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public EnumSexo getSexo() {
        return sexo;
    }

    public void setSexo(EnumSexo sexo) {
        this.sexo = sexo;
    }

    public EnderecoVO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoVO endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return matricula + ", " + nome + ", " + sexo + ", residente em: " + endereco;
    }
}
