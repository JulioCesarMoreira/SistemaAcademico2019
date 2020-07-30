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
import Model.Disciplina;
import Utility.XPopOver;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import org.springframework.data.domain.Sort;

/**
 * FXML Controller class
 *
 * @author Julio
 */
public class DisciplinaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public TableView<Disciplina> tblVwDisciplina;

    @FXML
    private JFXButton btnIncluir;

    @FXML
    private JFXButton btnAlterar;

    @FXML
    private JFXButton btnExcluir;
    
    @FXML
    private MenuItem menuItemAlterar;
    
    @FXML
    private MenuItem menuItemExcluir;

    Disciplina disciplina;
    char acao;

    List<Disciplina> lstDisc = new ArrayList<Disciplina>();

    @FXML
    public void acIncluir() {
        acao = INCLUIR;
        disciplina = new Disciplina();
        showCRUD();
    }

    @FXML
    public void acAlterar() {
        acao = ALTERAR;
        disciplina = tblVwDisciplina.getSelectionModel().getSelectedItem();
        showCRUD();
    }

    @FXML
    public void acExcluir() {
        acao = EXCLUIR;
        disciplina = tblVwDisciplina.getSelectionModel().getSelectedItem();
        showCRUD();
    }
    
    private void showCRUD() {
        String cena = "/fxml/CRUDDisciplina.fxml";
        XPopOver popOver = null;
        switch (acao) {
            case INCLUIR:
                popOver = new XPopOver(cena, "Inclusão de Disciplina", null);
                break;
            case ALTERAR:
                popOver = new XPopOver(cena, "Alteração de Disciplina", null);
                break;
            case EXCLUIR:
//                popOver = new XPopOver(cena, "Exclusão Disciplina", btnExcluir);
                popOver = new XPopOver(cena, "Exclusão Disciplina", null);
                break;
        }
        CRUDDisciplinaController controllerFilho
                = popOver.getLoader().getController();
        controllerFilho.setCadastroController(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnExcluir.visibleProperty().bind((tblVwDisciplina.getSelectionModel().selectedItemProperty().isNotNull()));
        btnAlterar.visibleProperty().bind((tblVwDisciplina.getSelectionModel().selectedItemProperty().isNotNull()));
        menuItemAlterar.visibleProperty().bind((tblVwDisciplina.getSelectionModel().selectedItemProperty().isNotNull()));
        menuItemExcluir.visibleProperty().bind((tblVwDisciplina.getSelectionModel().selectedItemProperty().isNotNull()));
        
        tblVwDisciplina.setItems(
                FXCollections.observableList(
                        disciplinaRepository.findAll(new Sort(new Sort.Order("nome")))));
       
    }

}
