package view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static Config.Config.ALTERAR;
import static Config.Config.EXCLUIR;
import static Config.Config.INCLUIR;
import static Config.DAO.professorRepository;
import Model.Professor;
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
public class ProfessorController implements Initializable {

    @FXML
    public TableView<Professor> tblVwProfessor;

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
    

    Professor professor;
    char acao;

    List<Professor> lstDisc = new ArrayList<Professor>();

    @FXML
    public void acIncluir() {
        acao = INCLUIR;
        professor = new Professor();
        showCRUD();
    }
    
    @FXML
    public void acAlterar() {
        acao = ALTERAR;
        professor = tblVwProfessor.getSelectionModel().getSelectedItem();
        showCRUD();
    }

    @FXML
    public void acExcluir() {
        acao = EXCLUIR;
        professor = tblVwProfessor.getSelectionModel().getSelectedItem();
        showCRUD();
    }
    
    private void showCRUD() {
        String cena = "/fxml/CRUDProfessor.fxml";
        XPopOver popOver = null;
        switch (acao) {
            case INCLUIR:
                popOver = new XPopOver(cena, "Inclusão de Professor", null);
                break;
            case ALTERAR:
                popOver = new XPopOver(cena, "Alteração de Professor", null);
                break;
            case EXCLUIR:
                popOver = new XPopOver(cena, "Exclusão de Professor", null);
                break;
        }
        CRUDProfessorController controllerFilho
                = popOver.getLoader().getController();
        controllerFilho.setCadastroController(this);
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnAlterar.visibleProperty().bind(tblVwProfessor.getSelectionModel().selectedItemProperty().isNotNull());
        btnExcluir.visibleProperty().bind(tblVwProfessor.getSelectionModel().selectedItemProperty().isNotNull());
        menuItemAlterar.visibleProperty().bind((tblVwProfessor.getSelectionModel().selectedItemProperty().isNotNull()));
        menuItemExcluir.visibleProperty().bind((tblVwProfessor.getSelectionModel().selectedItemProperty().isNotNull()));
        
        tblVwProfessor.setItems(
                FXCollections.observableList(
                        professorRepository.findAll(new Sort(new Sort.Order("nome")))));
    }

}
