/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static Config.DAO.alunoRepository;
import static Config.DAO.disciplinaRepository;
import Model.Aluno;
import Model.Disciplina;
import Model.Matricula;
import Utility.XPopOver;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.springframework.data.domain.Sort;

/**
 * FXML Controller class
 *
 * @author Julio
 */
public class MatriculaController implements Initializable {

    @FXML
    public TableView<Matricula> tblVwMatriculas;

    @FXML
    private JFXButton btnFechar;

    @FXML
    private AnchorPane anchorPane;
    private AlunoController controllerPai;

    @FXML
    private Label lbNome;

    Aluno aluno;

    @FXML
    private void acCancela() {
        anchorPane.getScene().getWindow().hide();
    }

    public void setMatriculaController(AlunoController controllerPai) {
        this.controllerPai = controllerPai;
        aluno = controllerPai.aluno;
        lbNome.setText(aluno.getNome());
        tblVwMatriculas.setItems(
                FXCollections.observableList(aluno.getMatriculas()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
    }

}
