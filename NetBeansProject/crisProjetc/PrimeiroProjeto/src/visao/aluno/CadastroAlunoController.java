package visao.aluno;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import negocio.AlunoNegocio;
import negocio.NegocioException;
import visao.base.MenssagemUtil;
import visao.base.OpCadastroEnum;
import vo.AlunoVO;
import vo.EnumSexo;
import vo.EnumUF;

public class CadastroAlunoController implements Initializable {

    //---------Classes de Negócio e Controle da Lógica----------
    private AlunoNegocio alunoNegocio;
    private OpCadastroEnum opCadastro;
    private Stage stage;
    private List<AlunoVO> listaAluno;

    //---------Componentes Visuais---------
    @FXML
    private Button botaoIncluir;
    @FXML
    private Button botaoAlterar;
    @FXML
    private Button botaoExcluir;
    @FXML
    private Button botaoSalvar;
    @FXML
    private Button botaoCancelar;
    @FXML
    private Button botaoSair;
    @FXML
    private Label labelRodape;
    @FXML
    private TabPane tabDados;
    @FXML
    private GridPane gridCampos;
    @FXML
    private TextField campoMatricula;
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoNomeMae;
    @FXML
    private TextField campoNomePai;
    @FXML
    private ComboBox campoSexo;
    @FXML
    private TextField campoLogradouro;
    @FXML
    private TextField campoNumero;
    @FXML
    private TextField campoBairro;
    @FXML
    private TextField campoCidade;
    @FXML
    private ComboBox campoUF;
    @FXML
    private TableView tabelaDados;
    @FXML
    private TextField campoPesquisaNome;

    public CadastroAlunoController() {
        try {
            this.alunoNegocio = new AlunoNegocio();
        } catch (NegocioException ex) {
            MenssagemUtil.mensagemAlerta("Camada de Negocio nao Iniciada!!");
            this.sair();
        }
    }

    public void setPalcoOrigem(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.gridCampos.setDisable(true);
        this.campoMatricula.setDisable(true);
        this.opCadastro = OpCadastroEnum.CONSULTAR;

        this.TrataBotoes();
        this.iniciarDadosTableView();
    }

    //=====================Trata a Lógica da Interface de Cadastro=========
    private void iniciarDadosTableView() {
        try {
            this.listaAluno = this.alunoNegocio.pesquisaParteNome("");
        } catch (NegocioException ex) {
            MenssagemUtil.mensagemAlerta("Dados não localizados!!");
        }
        TableColumn coluna1 = new TableColumn("Matricula");
        TableColumn coluna2 = new TableColumn("Nome");
        TableColumn coluna3 = new TableColumn("Nome Mae");
        TableColumn coluna4 = new TableColumn("Sexo");
        coluna1.setMinWidth(100);
        coluna2.setMinWidth(200);
        coluna3.setMinWidth(200);
        coluna4.setMinWidth(50);
        coluna1.setCellValueFactory(new PropertyValueFactory("matricula"));
        coluna2.setCellValueFactory(new PropertyValueFactory("nome"));
        coluna3.setCellValueFactory(new PropertyValueFactory("nomeMae"));
        coluna4.setCellValueFactory(new PropertyValueFactory("sexo"));
        this.tabelaDados.getColumns().addAll(coluna1, coluna2, coluna3, coluna4);
        this.tabelaDados.setItems(FXCollections.observableArrayList(this.listaAluno));

        this.campoSexo.setItems(FXCollections.observableArrayList(EnumSexo.values()));
        this.campoUF.setItems(FXCollections.observableArrayList(EnumUF.values()));
    }

    private void atualizarDadosTableView() {
        this.tabelaDados.getItems().clear();
        this.tabelaDados.setItems(FXCollections.observableArrayList(this.listaAluno));
        this.tabelaDados.refresh();
    }

    private AlunoVO obterVOTableView() {
        AlunoVO alunoVO = null;
        if (this.tabelaDados.getSelectionModel().getSelectedItem() != null) {
            TableViewSelectionModel selectionModel = this.tabelaDados.getSelectionModel();
            int pos = selectionModel.getSelectedIndex();
            alunoVO = (AlunoVO) selectionModel.getSelectedItem();
        }
        return alunoVO;
    }

    private void iniciarInclusao() {
        this.opCadastro = OpCadastroEnum.INCLUIR;
        this.TrataBotoes();
        this.limparCampos();
        this.tabDados.getSelectionModel().selectLast();
        this.gridCampos.setDisable(false);
        this.campoNome.requestFocus();
        this.labelRodape.setText("Inclusão em andamento...");
    }

    private void iniciarAlteracao() {
        AlunoVO alunoVO = this.obterVOTableView();
        if (alunoVO != null) {
            try {
                alunoVO = this.alunoNegocio.pesquisaMatricula(alunoVO.getMatricula());
            } catch (NegocioException ex) {
                MenssagemUtil.mensagemAlerta("Erro ao localizar o Aluno" + ex.getMessage());
            }
            if (alunoVO != null) {
                this.opCadastro = OpCadastroEnum.ALTERAR;
                this.TrataBotoes();
                this.preecheCampos(alunoVO);
                this.tabDados.getSelectionModel().selectLast();
                this.gridCampos.setDisable(false);
                this.campoUF.requestFocus();
                this.labelRodape.setText("Alteracao em andamento...");
            } else {
                MenssagemUtil.mensagemAlerta("Item não localizado!!");
            }
        } else {
            MenssagemUtil.mensagemAlerta("Nenhum item selecionado!!");
        }
    }

    private void processarInclusao() {
        AlunoVO alunoVO = this.criarVODados();
        if (alunoVO != null) {
            try {
                this.alunoNegocio.inserir(alunoVO);
                this.opCadastro = OpCadastroEnum.SALVAR;
                this.TrataBotoes();
                this.gridCampos.setDisable(true);
                this.labelRodape.setText("Inclusao realizada com sucesso!");
                this.listaAluno = this.alunoNegocio.pesquisaParteNome("");
                this.atualizarDadosTableView();
                this.tabDados.getSelectionModel().selectFirst();
            } catch (NegocioException ex) {
                MenssagemUtil.mensagemErro("Erro de Inclusao", ex.getMessage());
                this.opCadastro = OpCadastroEnum.INCLUIR;
                this.TrataBotoes();
                this.campoNome.requestFocus();
            }
        }
    }

    private void processarAlteracao() {

        AlunoVO alunoVO = this.criarVODados();
        if (alunoVO != null) {
            try {
                this.alunoNegocio.alterar(alunoVO);
                this.opCadastro = OpCadastroEnum.SALVAR;
                this.TrataBotoes();
                this.gridCampos.setDisable(true);
                this.labelRodape.setText("Alteracao realizada com sucesso!");
                this.listaAluno = this.alunoNegocio.pesquisaParteNome("");
                this.atualizarDadosTableView();
                this.tabDados.getSelectionModel().selectFirst();
            } catch (NegocioException ex) {
                MenssagemUtil.mensagemErro("Erro de Alteracao", ex.getMessage());
                this.opCadastro = OpCadastroEnum.ALTERAR;
                this.TrataBotoes();
                this.campoNome.requestFocus();
            }
        }
    }

    private void processarExclusao() {
        AlunoVO alunoVO = this.obterVOTableView();
        if (alunoVO != null) {
            try {
                alunoVO = this.alunoNegocio.pesquisaMatricula(alunoVO.getMatricula());
                this.opCadastro = OpCadastroEnum.EXCLUIR;
                this.TrataBotoes();
                this.labelRodape.setText("Exclusao em andamento...");
                if (MenssagemUtil.mensagemConfirmacao("Confirmação", "Confirma a exclusão de " + alunoVO)) {
                    try {
                        this.alunoNegocio.excluir(alunoVO.getMatricula());
                        this.TrataBotoes();
                        this.labelRodape.setText("Exclusao realizada com sucesso!");
                        this.listaAluno = this.alunoNegocio.pesquisaParteNome("");
                        this.atualizarDadosTableView();
                    } catch (NegocioException ex) {
                        MenssagemUtil.mensagemErro("Erro de Exclusao", ex.getMessage());
                    }
                    this.opCadastro = OpCadastroEnum.CONSULTAR;
                }
            } catch (NegocioException ex) {
                MenssagemUtil.mensagemAlerta("Item não localizado!!");
            }
            if (alunoVO != null) {

            } else {

            }
        } else {
            MenssagemUtil.mensagemAlerta("Nenhum item selecionado!!");
        }
    }

    private void processarCancelamento() {
        this.opCadastro = OpCadastroEnum.CANCELAR;
        this.TrataBotoes();
        this.tabDados.getSelectionModel().selectFirst();
        this.gridCampos.setDisable(true);
        this.labelRodape.setText("Operação Cancelada...");
    }

    private void processarFiltroPorNome(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            this.listaAluno.clear();
            try {
                this.listaAluno = this.alunoNegocio.pesquisaParteNome(this.campoPesquisaNome.getText().trim());
            } catch (NegocioException ex) {
                MenssagemUtil.mensagemErro("Erro durante a consulta de dados", ex.getMessage());
            }
            this.atualizarDadosTableView();
        }
    }

    private void sair() {
        this.stage.close();

    }

    private void TrataBotoes() {
        if (this.opCadastro == OpCadastroEnum.INCLUIR || this.opCadastro == OpCadastroEnum.ALTERAR) {
            this.botaoIncluir.setDisable(true);
            this.botaoAlterar.setDisable(true);
            this.botaoExcluir.setDisable(true);
            this.botaoSalvar.setDisable(false);
            this.botaoCancelar.setDisable(false);
        } else if (this.opCadastro == OpCadastroEnum.SALVAR
                || this.opCadastro == OpCadastroEnum.CANCELAR
                || this.opCadastro == OpCadastroEnum.CONSULTAR) {
            this.botaoIncluir.setDisable(false);
            this.botaoAlterar.setDisable(false);
            this.botaoExcluir.setDisable(false);
            this.botaoSalvar.setDisable(true);
            this.botaoCancelar.setDisable(true);
        }
    }

    private void limparCampos() {
        this.campoMatricula.clear();
        this.campoNome.clear();
        this.campoNomeMae.clear();
        this.campoNomePai.clear();
        this.campoSexo.getSelectionModel().select(0);
        this.campoLogradouro.clear();
        this.campoNumero.clear();
        this.campoBairro.clear();
        this.campoCidade.clear();
        this.campoUF.getSelectionModel().select(0);

    }

    private void preecheCampos(AlunoVO alunoVO) {
        this.campoMatricula.setText(String.valueOf(alunoVO.getMatricula()));
        this.campoNome.setText(alunoVO.getNome());
        this.campoNomeMae.setText(alunoVO.getNomeMae());
        this.campoNomePai.setText(alunoVO.getNomePai());
        this.campoSexo.getSelectionModel().select(alunoVO.getSexo());
        this.campoLogradouro.setText(alunoVO.getEndereco().getLogradouro());
        this.campoNumero.setText(String.valueOf(alunoVO.getEndereco().getNumero()));
        this.campoBairro.setText(alunoVO.getEndereco().getBairro());
        this.campoCidade.setText(alunoVO.getEndereco().getCidade());
        this.campoUF.getSelectionModel().select(alunoVO.getEndereco().getUf());
    }

    private AlunoVO criarVODados() {
        AlunoVO alunoVO;

        try {
            alunoVO = new AlunoVO();
            if (this.opCadastro == OpCadastroEnum.ALTERAR) {
                alunoVO.setMatricula(Integer.parseInt(this.campoMatricula.getText()));
            }
            alunoVO.setNome(this.campoNome.getText());
            alunoVO.setNomeMae(this.campoNomeMae.getText());
            alunoVO.setNomePai(this.campoNomePai.getText());
            alunoVO.setSexo((EnumSexo) this.campoSexo.getSelectionModel().getSelectedItem());
            alunoVO.getEndereco().setLogradouro(this.campoLogradouro.getText());
            if (!this.campoNumero.getText().isEmpty()) {
                alunoVO.getEndereco().setNumero(Integer.parseInt(this.campoNumero.getText()));
            }
            alunoVO.getEndereco().setBairro(this.campoBairro.getText());
            alunoVO.getEndereco().setCidade(this.campoCidade.getText());
            alunoVO.getEndereco().setUf((EnumUF) this.campoUF.getSelectionModel().getSelectedItem());
        } catch (Exception ex) {
            alunoVO = null;
            MenssagemUtil.mensagemErro("Dados inconsistentes");
        }
        return alunoVO;
    }

//======================Tratamento de Eventos=====================
    @FXML
    private void botaoIncluirAction(ActionEvent event) {
        this.iniciarInclusao();
    }

    @FXML
    private void botaoAlterarAction(ActionEvent event) {
        this.iniciarAlteracao();
    }

    @FXML
    private void botaoExcluirAction(ActionEvent event) {
        this.processarExclusao();
    }

    @FXML
    private void botaoSalvarAction(ActionEvent event) {
        if (this.opCadastro == OpCadastroEnum.INCLUIR) {
            this.processarInclusao();
        } else if (this.opCadastro == OpCadastroEnum.ALTERAR) {
            this.processarAlteracao();
        }
    }

    @FXML
    private void botaoCancelarAction(ActionEvent event) {
        this.processarCancelamento();
    }

    @FXML
    private void botaoSairAction(ActionEvent event) {
        this.sair();
    }

    @FXML
    private void campoPesquisaNomeOnKeyPressed(KeyEvent keyEvent) {
        this.processarFiltroPorNome(keyEvent);
    }
}
