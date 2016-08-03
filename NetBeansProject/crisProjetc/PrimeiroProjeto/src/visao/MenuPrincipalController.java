package visao;

import visao.base.MenssagemUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import visao.aluno.CadastroAlunoController;

public class MenuPrincipalController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void menuItemCadastroAlunoAction(ActionEvent event) {
        Stage stage = new Stage(StageStyle.DECORATED);
        Parent parent = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("aluno/CadastroAlunoFXML.fxml"));
            parent = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            MenssagemUtil.mensagemErro("Erro ao iniciar a tela de cadastro de Aluno", ex.getMessage());
        }
        CadastroAlunoController controller = (CadastroAlunoController) fxmlLoader.getController();
        controller.setPalcoOrigem(stage);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Cadastro de Alunos");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
