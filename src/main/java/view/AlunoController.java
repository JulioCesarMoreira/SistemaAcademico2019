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
import static Config.DAO.disciplinaRepository;
import Model.Aluno;
import Model.Disciplina;
import Model.Matricula;
import Utility.XPopOver;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * FXML Controller class
 *
 * @author Julio
 */
public class AlunoController implements Initializable {

    @FXML
    public TableView<Aluno> tblVwAluno;

    @FXML
    private JFXButton btnIncluir;

    @FXML
    private JFXButton btnAlterar;

    @FXML
    private JFXButton btnBusca;

    @FXML
    private JFXButton btnClearBusca;

    @FXML
    private JFXButton btnExcluir;

    @FXML
    private MenuItem menuItemAlterar;

    @FXML
    private MenuItem menuItemExcluir;

    @FXML
    private JFXTextField txtFldBusca;

    @FXML
    public Spinner<Integer> spnPaginas;
    SpinnerValueFactory<Integer> rangeSpinner;

    Aluno aluno;
    char acao;
    int numeroPags;
    char acaoBusca = 'C';
    int tamLst = 10;
    String sort = "nome";

    private Page<Aluno> alunos;

    List<Disciplina> lstDisc = new ArrayList<>();
    List<Matricula> lstMatr = new ArrayList<>();
    List<Aluno> lstAluno = new ArrayList<>();

    MatriculaController controllerMat;

    @FXML
    private void tblVwClick(Event event) {
        MouseEvent me = null;
        if (event.getEventType() == MOUSE_CLICKED) {
            me = (MouseEvent) event;

            if (me.getClickCount() == 2) {
                aluno = tblVwAluno.getSelectionModel().getSelectedItem();
                showTbVw();
            }
        }
    }

    @FXML
    private void acRefreshTbVw() {
        contaPagina(0);
        rangeSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, numeroPags, 1);
        spnPaginas.setValueFactory(rangeSpinner);
        buscaPagina(1);
        txtFldBusca.setText("");
        acaoBusca = 'C';
    }

    List<Aluno> lstBusca = new ArrayList<>();

    @FXML
    private void acBusca() {
        if (!txtFldBusca.getText().isEmpty()) {
            acaoBusca = 'B';
            int numeroAlunos = alunoRepository.countByNomeLikeIgnoreCaseOrRaLikeIgnoreCase(txtFldBusca.getText(), txtFldBusca.getText());
            contaPagina(0);
            rangeSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, numeroPags);
            spnPaginas.setValueFactory(rangeSpinner);
            spnPaginas.getValueFactory().setValue(1);
            mecanismoBuscaPagina(1);
        }
    }

    private void mecanismoBuscaPagina(Integer pag) {
        final Sort s = new Sort(new Sort.Order(Sort.Direction.ASC, sort));
        int numAlun = alunoRepository.countByNomeLikeIgnoreCaseOrRaLikeIgnoreCase(txtFldBusca.getText(),txtFldBusca.getText());
        numeroPags = numAlun / tamLst;
        if (numAlun > tamLst && (numAlun % tamLst) > 0) {
            numeroPags++;
        } else if (numeroPags < 1) {
            numeroPags = 1;
        }
        rangeSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, numeroPags);
        spnPaginas.setValueFactory(rangeSpinner);
        alunos = alunoRepository.findByNomeLikeIgnoreCaseOrRaLikeIgnoreCase(txtFldBusca.getText(), txtFldBusca.getText(), new PageRequest(pag - 1, tamLst, s));
        tblVwAluno.setItems(FXCollections.observableList(alunos.getContent()));
    }

    Matricula matricula;

    private void matriculaTd() {
        Random rand = new Random();
        lstAluno = alunoRepository.findAll();
        lstDisc = disciplinaRepository.findAll();

        for (Aluno a : lstAluno) {
            for (Disciplina d : lstDisc) {
                matricula = new Matricula(d, rand.nextInt(100), rand.nextInt(100), rand.nextInt(100), rand.nextInt(d.getAulas()));
//                lstMatr.add(new Matricula(d, rand.nextInt(200)-50, rand.nextInt(200)-50, rand.nextInt(200)-50, rand.nextInt(200)-50);
                if (((matricula.getNota1Sem() + matricula.getNota2Sem()) / 2) >= 70) {
                    matricula.setNotaExame(0);
                }
                lstMatr.add(matricula);
            }

            a.setMatriculas(lstMatr);
            alunoRepository.save(a);
            lstMatr.removeAll(lstMatr);
        }
    }

    private void showTbVw() {
        String cena = "/fxml/Matricula.fxml";
        XPopOver popOver = null;

        popOver = new XPopOver(cena, "nome do aluno", null);

        MatriculaController controllerFilho
                = popOver.getLoader().getController();
        controllerFilho.setMatriculaController(this);

    }

    @FXML
    public void acIncluir() {
        acao = INCLUIR;
        aluno = new Aluno();
        showCRUD();
        
    }

    @FXML
    public void acAlterar() {
        acao = ALTERAR;
        aluno = tblVwAluno.getSelectionModel().getSelectedItem();
        showCRUD();        
    }

    @FXML
    public void acExcluir() {
        acao = EXCLUIR;
        aluno = tblVwAluno.getSelectionModel().getSelectedItem();
        showCRUD();
        
    }

    private void showCRUD() {
        String cena = "/fxml/CRUDAluno.fxml";
        XPopOver popOver = null;
        switch (acao) {
            case INCLUIR:
                popOver = new XPopOver(cena, "Inclusão de Aluno", null);
                break;
            case ALTERAR:
                popOver = new XPopOver(cena, "Alteração de Aluno", null);
                break;
            case EXCLUIR:
                popOver = new XPopOver(cena, "Exclusão de Aluno", null);
                break;
        }
        CRUDAlunoController controllerFilho
                = popOver.getLoader().getController();
        controllerFilho.setCadastroController(this);
    }

    private void habilitaCalcular() {
        if (txtFldBusca.getText().isEmpty()) {
            btnBusca.setDisable(true);
        } else {
            btnBusca.setDisable(false);
        }
    }

    private void adicionaListener(TextField texto) {
        texto.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (!newValue.matches("[1-9A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ]*")
                    && !newValue.isEmpty()) {
                        texto.setText(oldValue);
                    } else {
                        texto.setText(newValue);
                    }
                    habilitaCalcular();
                });
    }

    @FXML
    private void acSpinner() {
        switch (acaoBusca) {
            case 'C':
                buscaPagina(spnPaginas.getValue());
                break;
            case 'B':
                mecanismoBuscaPagina(spnPaginas.getValue());
                break;
        }

    }

    @FXML
    private void acMinPag() {
        switch (acaoBusca) {
            case 'C':
                buscaPagina(1);
                break;
            case 'B':
                mecanismoBuscaPagina(1);
                break;
        }
        spnPaginas.getValueFactory().setValue(1);
    }

    @FXML
    private void acMaxPag() {
        switch (acaoBusca) {
            case 'C':
                buscaPagina(numeroPags);
                break;
            case 'B':
                mecanismoBuscaPagina(numeroPags);
                break;
        }
        spnPaginas.getValueFactory().setValue(numeroPags);
    }

    public void buscaPagina(Integer pag) {
        final Sort s = new Sort(new Sort.Order(Sort.Direction.ASC, sort));

        alunos = alunoRepository.findAll(new PageRequest(pag - 1, tamLst, s));
        tblVwAluno.setItems(FXCollections.observableList(alunos.getContent()));
    }
    public void contaPagina(int i){
        numeroPags = ((int)alunoRepository.count()+i) / tamLst;
        if (((int)alunoRepository.count()+i) > tamLst && (((int)alunoRepository.count()+i) % tamLst) > 0) {
            numeroPags++;
        } else if (numeroPags < 1) {
            numeroPags = 1;
        }
    }

    @FXML
    private void onKeyOPressBusca(KeyEvent evt){
        mecanismoBuscaPagina(spnPaginas.getValue());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //matriculaTd();
        //adicionaListener(txtFldBusca);
        btnAlterar.visibleProperty().bind(tblVwAluno.getSelectionModel().selectedItemProperty().isNotNull());
        btnExcluir.visibleProperty().bind(tblVwAluno.getSelectionModel().selectedItemProperty().isNotNull());
        menuItemAlterar.visibleProperty().bind((tblVwAluno.getSelectionModel().selectedItemProperty().isNotNull()));
        menuItemExcluir.visibleProperty().bind((tblVwAluno.getSelectionModel().selectedItemProperty().isNotNull()));
        contaPagina(0);
        rangeSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, numeroPags, 1);
        spnPaginas.setValueFactory(rangeSpinner);
        buscaPagina(1);
        acaoBusca = 'C';
        spnPaginas.setEditable(true); 
        spnPaginas.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue > numeroPags ) {
                        acMaxPag();
                    } else if(newValue < 1){
                        acMinPag();
                    }else{
                        acSpinner();
                    }
                });
    }

}
