package visao;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Evandro
 */
public class MenuPrinciapal extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Stage stageTemp = new Stage(StageStyle.UNIFIED);
        Parent parent = FXMLLoader.load(getClass().getResource("MenuPrincipalFXML.fxml"));
        Scene scene = new Scene(parent);
        stageTemp.setScene(scene);
        stageTemp.setTitle("Menu Principal");
        stageTemp.setResizable(false);
        stageTemp.showAndWait();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
