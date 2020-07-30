/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static Config.Config.ALTERAR;
import static Config.Config.EXCLUIR;
import static Config.Config.INCLUIR;
import static Config.DAO.disciplinaRepository;
import static Config.DAO.professorRepository;
import Model.Disciplina;
import java.time.LocalDateTime;
import Repository.ProfessorRepository;
import java.time.LocalDate;
import java.sql.Time;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.data.domain.Sort;

/**
 * FXML Controller class
 *
 * @author Julio
 */
public class CRUDProfessorController implements Initializable {

    int dia;
    int mes;
    int ano;
    String data;
    @FXML
    public AnchorPane anchorPane;
    private ProfessorController controllerPai;

    @FXML
    private TextField txtFldCPF;

    @FXML
    private TextField txtFldNome;

    @FXML
    private TextField txtFldEmail;

    @FXML
    private Button btnConfirma;
    LocalDateTime dataCadastro;

    List<Disciplina> lstDisc = new ArrayList<Disciplina>();

    @FXML
    private void acCancela() {
        anchorPane.getScene().getWindow().hide();
    }

    @FXML
    private void acConfirma() {
        controllerPai.professor.setCpf(txtFldCPF.getText());
        controllerPai.professor.setNome(txtFldNome.getText());
        controllerPai.professor.setEmail(txtFldEmail.getText());
        try {
            switch (controllerPai.acao) {
                case INCLUIR:
                    if (!validCPF(txtFldCPF.getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText("CPF inválido!!");
                        alert.setContentText("CPF inválido!!");
                        alert.showAndWait();
                        txtFldCPF.setText("");
                        txtFldCPF.requestFocus();
                        return;
                    }
                    controllerPai.professor.setDataCadastro(LocalDate.now());
                    professorRepository.insert(controllerPai.professor);
                    break;
                case ALTERAR:
                    if (!validCPF(txtFldCPF.getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erro");
                        alert.setHeaderText("CPF inválido!!");
                        alert.setContentText("CPF inválido!!");
                        alert.showAndWait();
                        txtFldCPF.setText("");
                        txtFldCPF.requestFocus();
                        return;
                    }
                    professorRepository.save(controllerPai.professor);
                    break;
                case EXCLUIR:
                    lstDisc = FXCollections.observableList(disciplinaRepository.findAll());

                    for (Disciplina t : lstDisc) {
                        if (t.getProfessor() != null && t.getProfessor().equals(controllerPai.professor)) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erro");
                            alert.setHeaderText("Erro ao excluir cadastro de Professor");
                            alert.setContentText("O Professor " + controllerPai.professor.getNome()
                                    + "\n está vinculado com a Disciplina: \n" + t.getNome());
                            alert.showAndWait();
                            anchorPane.getScene().getWindow().hide();
                            return;
                        }
                    }
                    anchorPane.getScene().getWindow().hide();
                    professorRepository.delete(controllerPai.professor);
                    break;
            }
            controllerPai.tblVwProfessor.setItems(FXCollections.observableList(
                    professorRepository.findAll(new Sort(new Sort.Order("nome")))));
            controllerPai.tblVwProfessor.getSelectionModel().clearSelection();
            controllerPai.tblVwProfessor.refresh();
            anchorPane.getScene().getWindow().hide();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cadastro de Professor");

            if (e.getMessage().contains("duplicate key")) {
                alert.setContentText("CPF já cadastrado");
            } else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();
            anchorPane.getScene().getWindow().hide();
        }

    }

    private boolean validCPF(String CPF) {
        int soma1 = 0, soma2 = 0, num;
        int multiplicador1 = 10, multiplicador2 = 11;
        if (CPF.equals("00000000000")
                || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")
                || CPF.length() != 11) {
            return false;
        }
        for (int i = 0; i <= 8; i++) {
            num = (int) (CPF.charAt(i) - 48);
            soma1 = soma1 + (num * multiplicador1);
            multiplicador1--;
        }
        for (int i = 0; i <= 9; i++) {
            num = (int) (CPF.charAt(i) - 48);
            soma2 = soma2 + (num * multiplicador2);
            multiplicador2--;
        }
        int auxVerif1 = 11 - (soma1 % 11);
        if(auxVerif1 >= 10){
            auxVerif1 = 0;
        }
        int auxVerif2 = 11 - (soma2 % 11);
        if(auxVerif2 >= 10){
            auxVerif2 = 0;
        }
        int verif1 = (int) (CPF.charAt(9) - 48);
        int verif2 = (int) (CPF.charAt(10) - 48);
        if (verif1 != auxVerif1 || verif2 != auxVerif2) {
            return false;
        } else {
            return true;
        }
    }
    
    private boolean validaCNPJ(String cnpj){
        int multiplicador11 =5, multiplicador12=9;
        int multiplicador21 =6, multiplicador22=9;
        int soma1 = 0, soma2 = 0, num;
        if (cnpj.equals("00000000000000")
                || cnpj.equals("11111111111111")
                || cnpj.equals("22222222222222") || cnpj.equals("33333333333333")
                || cnpj.equals("44444444444444") || cnpj.equals("55555557775555")
                || cnpj.equals("66666666666666") || cnpj.equals("77777777777777")
                || cnpj.equals("88888888888888") || cnpj.equals("99999999999999")
                || cnpj.length() != 14) {
            return false;
        }
        for(int i = 0; i <=3; i++){
            num = (int) (cnpj.charAt(i) - 48);
            soma1 = soma1 + (num* multiplicador11);
            multiplicador11--;
        }
        for(int i = 4;i<=11;i++){
            num = (int) (cnpj.charAt(i) - 48);
            soma1 = soma1 + (num* multiplicador12);
            multiplicador12--;
        }
        for(int i = 0; i <=4; i++){
            num = (int) (cnpj.charAt(i) - 48);
            soma2 = soma2 + (num* multiplicador21);
            multiplicador21--;
        }
        for(int i = 5;i<=12;i++){
            num = (int) (cnpj.charAt(i) - 48);
            soma2 = soma2 + (num* multiplicador22);
            multiplicador22--;
        }
        int auxVerif1 = 11 - (soma1 % 11);
        if(auxVerif1 >= 10){
            auxVerif1 = 0;
        }
        int auxVerif2 = 11 - (soma2 % 11);
        if(auxVerif2 >= 10){
            auxVerif2 = 0;
        }
        int verif1 = (int) (cnpj.charAt(12) - 48);
        int verif2 = (int) (cnpj.charAt(13) - 48);
        if (verif1 != auxVerif1 || verif2 != auxVerif2) {
            return false;
        } else {
            return true;
        }
    }

    public void setCadastroController(ProfessorController controllerPai) {
        this.controllerPai = controllerPai;
        txtFldCPF.setText(controllerPai.professor.getCpf());
        txtFldNome.setText(controllerPai.professor.getNome());
        txtFldEmail.setText(controllerPai.professor.getEmail());

        txtFldCPF.setDisable(controllerPai.acao == EXCLUIR);
        txtFldNome.setDisable(controllerPai.acao == EXCLUIR);
        txtFldEmail.setDisable(controllerPai.acao == EXCLUIR);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        txtFldCPF.textProperty().addListener(
//                (observable, oldValue, newValue) -> {
//                    if (!newValue.matches("[0-9]*")) {
//                        txtFldCPF.setText(oldValue);
//                    } else {
//                        txtFldCPF.setText(newValue);
//                    }
//                });
        btnConfirma.disableProperty().bind(txtFldCPF.textProperty().isEmpty().or(txtFldNome.textProperty().isEmpty())
                .or(txtFldEmail.textProperty().isEmpty()));
        System.out.println(String.valueOf(validaCNPJ("14516770000105")));
        
    }
}
