/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static Config.Config.ALTERAR;
import static Config.Config.EXCLUIR;
import static Config.Config.INCLUIR;
import static Config.DAO.alunoRepository;
import static Config.DAO.cidadeRepository;
import static Config.DAO.disciplinaRepository;
import static Config.DAO.professorRepository;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.data.domain.Sort;
import Model.Cidade;
import Model.Disciplina;
import Model.Matricula;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SpinnerValueFactory;
import org.controlsfx.control.ListSelectionView;

/**
 * FXML Controller class
 *
 * @author Julio
 */
public class CRUDAlunoController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    private AlunoController controllerPai;

    @FXML
    private TextField txtFldRA;

    @FXML
    private TextField txtFldNome;

    @FXML
    private TextField txtFldEmail;

    @FXML
    private TextField txtFldDataNacimento;

    @FXML
    private Button btnConfirma;
    @FXML
    private ComboBox<Cidade> cmbCidade;
    @FXML
    private ListSelectionView<Disciplina> lstSelDisciplinas;

    @FXML
    private void acCancela() {
        anchorPane.getScene().getWindow().hide();
    }

    @FXML
    private void acConfirma() {
        controllerPai.aluno.setRa(txtFldRA.getText());
        controllerPai.aluno.setNome(txtFldNome.getText());
        controllerPai.aluno.setEmail(txtFldEmail.getText());
        controllerPai.aluno.setCidade(
                cmbCidade.getSelectionModel().getSelectedItem());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        controllerPai.aluno.setDataDeNascimento(LocalDate.parse(txtFldDataNacimento.getText(), formatter));
        List<Matricula> tmpMatriculas = new ArrayList<>();

        if (controllerPai.aluno.getMatriculas() != null) {
            for (Matricula ant : controllerPai.aluno.getMatriculas()) {
                if (lstSelDisciplinas.getTargetItems().contains(ant.getDisciplina())) {
                    tmpMatriculas.add(ant);
                    lstSelDisciplinas.getTargetItems().remove(ant.getDisciplina());
                }
            }

        }
        for (Disciplina nv : lstSelDisciplinas.getTargetItems()) {
            Matricula mat = new Matricula(nv, 0, 0, 0, 0);
            tmpMatriculas.add(mat);
        }

        controllerPai.aluno.setMatriculas(tmpMatriculas);

        try {
            switch (controllerPai.acao) {
                case INCLUIR:
                    if ((int) alunoRepository.count() % controllerPai.tamLst == 0) {
                        controllerPai.contaPagina(1); 
                        controllerPai.rangeSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, controllerPai.numeroPags);
                        controllerPai.spnPaginas.setValueFactory(controllerPai.rangeSpinner);
                        controllerPai.tblVwAluno.refresh();
                        controllerPai.buscaPagina(controllerPai.numeroPags);
                    }
                    alunoRepository.insert(controllerPai.aluno);
                    break;
                case ALTERAR:
                    alunoRepository.save(controllerPai.aluno);
                    break;
                case EXCLUIR:
                    alunoRepository.delete(controllerPai.aluno);
                    if (controllerPai.tblVwAluno.getItems().size() == 1) {
                        controllerPai.buscaPagina(controllerPai.spnPaginas.getValue() - 1);
                        controllerPai.contaPagina(0);
                        controllerPai.rangeSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, controllerPai.numeroPags);
                        controllerPai.spnPaginas.setValueFactory(controllerPai.rangeSpinner);
                        controllerPai.spnPaginas.getValueFactory().setValue(controllerPai.spnPaginas.getValue() - 1);
                    }
                    controllerPai.numeroPags = (int) alunoRepository.count() / controllerPai.tamLst;
                    if ((int) alunoRepository.count() > controllerPai.tamLst && ((int) alunoRepository.count() % controllerPai.tamLst) > 0) {
                        controllerPai.numeroPags++;
                    } else if (controllerPai.numeroPags < 1) {
                        controllerPai.numeroPags = 1;
                    }
                    controllerPai.rangeSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, controllerPai.numeroPags);
                    controllerPai.spnPaginas.setValueFactory(controllerPai.rangeSpinner);
                    controllerPai.spnPaginas.getValueFactory().setValue(controllerPai.spnPaginas.getValue() - 1);
                    break;
            }

            controllerPai.buscaPagina(controllerPai.spnPaginas.getValueFactory().getValue());
            controllerPai.tblVwAluno.getSelectionModel().clearSelection();
            controllerPai.tblVwAluno.refresh();
            anchorPane.getScene().getWindow().hide();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cadastro de Aluno");

            if (e.getMessage().contains("duplicate key")) {
                alert.setContentText("RA já cadastrado");
            } else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();
            anchorPane.getScene().getWindow().hide();
        }
    }

    public void setCadastroController(AlunoController controllerPai) {
        this.controllerPai = controllerPai;
        List<Disciplina> todasDiscilplinas
                = disciplinaRepository.findAll(new Sort(new Sort.Order("nome")));
        txtFldNome.setText(controllerPai.aluno.getNome());
        txtFldEmail.setText(controllerPai.aluno.getEmail());
        if (controllerPai.aluno.getDataDeNascimento() != null) {
            List<Disciplina> lstAtual = new ArrayList();
            for (Matricula nv : controllerPai.aluno.getMatriculas()) {
                lstAtual.add(nv.getDisciplina());
            }
            todasDiscilplinas.removeAll(lstAtual);
            lstSelDisciplinas.getTargetItems().addAll(todasDiscilplinas);
            LocalDate dataBanco = controllerPai.aluno.getDataDeNascimento();
            String dataFormatada = dataBanco.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            txtFldDataNacimento.setText(dataFormatada);
        }

        lstSelDisciplinas.getSourceItems().addAll(todasDiscilplinas);
        txtFldRA.setText(controllerPai.aluno.getRa());
        cmbCidade.setItems(FXCollections.observableList(
                cidadeRepository.findAll(new Sort(new Sort.Order("nome")))));
        cmbCidade.getSelectionModel().select(controllerPai.aluno.getCidade());
        txtFldRA.setDisable(controllerPai.acao == EXCLUIR);
        txtFldEmail.setDisable(controllerPai.acao == EXCLUIR);
        txtFldNome.setDisable(controllerPai.acao == EXCLUIR);
        txtFldDataNacimento.setDisable(controllerPai.acao == EXCLUIR);
        lstSelDisciplinas.setDisable(controllerPai.acao == EXCLUIR);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        btnConfirma.disableProperty().bind(txtFldNome.textProperty().isEmpty().or(txtFldEmail.textProperty().isEmpty()).or(
//                txtFldRA.textProperty().isEmpty()).or(calendario.valueProperty().isNull()));
        btnConfirma.disableProperty().bind(txtFldNome.textProperty().isEmpty().or(txtFldEmail.textProperty().isEmpty()).or(
                txtFldRA.textProperty().isEmpty()).or(txtFldDataNacimento.textProperty().isEmpty()));
    }

}
