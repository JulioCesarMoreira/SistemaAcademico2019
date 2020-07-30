/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static Config.Config.ALTERAR;
//import static Config.Config.BUSCAR;
import static Config.Config.EXCLUIR;
import static Config.Config.INCLUIR;
import static Config.DAO.disciplinaRepository;
import static Config.DAO.professorRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import Model.Professor;
import javafx.scene.control.CheckBox;
import org.springframework.data.domain.Sort;

/**
 * FXML Controller class
 *
 * @author Julio
 */
public class CRUDDisciplinaController implements Initializable {

    @FXML
    private CheckBox chBoxDesvincula;
    @FXML
    private AnchorPane anchorPane;
    private DisciplinaController controllerPai;

//    private List<Disciplina> lstFinal = new ArrayList<Disciplina>();
    @FXML
    private TextField txtFldCodigo;

    @FXML
    private TextField txtFldNome;

    @FXML
    private TextField txtFldAulas;
    @FXML
    private TextField txtFldMaxAlunos;
    @FXML
    private TextField txtFldObservacao;

    @FXML
    private Button btnConfirma;

    @FXML
    private ComboBox<Professor> cmbProfessor;

    @FXML
    private void acCancela() {
        anchorPane.getScene().getWindow().hide();
    }

    @FXML
    private void acConfirma() {
        controllerPai.disciplina.setCodigo(txtFldCodigo.getText());
        controllerPai.disciplina.setNome(txtFldNome.getText());
        controllerPai.disciplina.setAulas(Integer.parseInt(txtFldAulas.getText()));
        controllerPai.disciplina.setObservacao(txtFldObservacao.getText());
        controllerPai.disciplina.setMaxAlunos(Integer.valueOf(txtFldMaxAlunos.getText()));
        if (chBoxDesvincula.isSelected()) {
            controllerPai.disciplina.setProfessor(null);
        } else {
            controllerPai.disciplina.setProfessor(
                    cmbProfessor.getSelectionModel().getSelectedItem());
        }

        try {
            switch (controllerPai.acao) {
                case INCLUIR:
                    disciplinaRepository.insert(controllerPai.disciplina);
                    break;
                case ALTERAR:
                    disciplinaRepository.save(controllerPai.disciplina);
                    break;
                case EXCLUIR:
                    disciplinaRepository.delete(controllerPai.disciplina);
                    break;
            }
            controllerPai.tblVwDisciplina.setItems(
                    FXCollections.observableList(disciplinaRepository.findAll(new Sort(new Sort.Order("nome"))))
            );
            controllerPai.tblVwDisciplina.getSelectionModel().clearSelection();
            controllerPai.tblVwDisciplina.refresh();
            anchorPane.getScene().getWindow().hide();
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cadastro de Disciplina");

            if (e.getMessage().contains("duplicate key")) {
                alert.setContentText("Código já cadastrado");
            } else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();
            anchorPane.getScene().getWindow().hide();
        }

    }

    public void setCadastroController(DisciplinaController controllerPai) {
        this.controllerPai = controllerPai;
        txtFldMaxAlunos.setText(String.valueOf(controllerPai.disciplina.getMaxAlunos()));
        txtFldCodigo.setText(controllerPai.disciplina.getCodigo());
        txtFldNome.setText(controllerPai.disciplina.getNome());
        if (controllerPai.disciplina.getAulas() > 0) {
            txtFldAulas.setText(String.valueOf(controllerPai.disciplina.getAulas()));
        }
        txtFldObservacao.setText(controllerPai.disciplina.getObservacao());

        cmbProfessor.setItems(FXCollections.observableList(
                professorRepository.findAll(new Sort(new Sort.Order("nome")))));
        cmbProfessor.getSelectionModel().select(controllerPai.disciplina.getProfessor());
        
        txtFldCodigo.setDisable(controllerPai.acao == EXCLUIR);
        txtFldNome.setDisable(controllerPai.acao == EXCLUIR);
        txtFldAulas.setDisable(controllerPai.acao == EXCLUIR);
        txtFldObservacao.setDisable(controllerPai.acao == EXCLUIR);
        txtFldMaxAlunos.setDisable(controllerPai.acao == EXCLUIR);
        cmbProfessor.setDisable(controllerPai.acao == EXCLUIR);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnConfirma.disableProperty().bind(txtFldCodigo.textProperty().isEmpty().
                or(txtFldNome.textProperty().isEmpty().or(txtFldAulas.textProperty().isEmpty().or(
                        txtFldObservacao.textProperty().isEmpty()).or(txtFldMaxAlunos.textProperty().isEmpty()))));

    }

}
