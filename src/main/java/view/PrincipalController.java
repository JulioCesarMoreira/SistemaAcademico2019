package view;

import static Config.DAO.alunoRepository;
import static Config.DAO.disciplinaRepository;
import static Config.DAO.professorRepository;
import static Config.DAO.cidadeRepository;
import Model.Aluno;
import Model.Disciplina;
import Model.Matricula;
import Model.Professor;
import Model.Cidade;
import Repository.AlunoRepository;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class PrincipalController implements Initializable {

    Disciplina disciplina;
    Professor prof;
    Aluno aluno;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        // TODO
//        disciplinaRepository.insert(new Disciplina("D001", "LP", 50, "Java"));
//        disciplinaRepository.insert(new Disciplina("D002", "Estrutura de Dados", 40, "observação"));
//        disciplinaRepository.insert(new Disciplina("D003", "OAC", 30, "observação"));
//        System.out.println(disciplinaRepository.findAll());

//         professorRepository.insert(new Professor("Idomar Augusto", "idomar@email.com", "09090909090"));
//         professorRepository.insert(new Professor("Marcio de Souza", "marcio@email.com", "08080808080"));
//         professorRepository.insert(new Professor("Rodrigo Silva", "rodrigo@email.com", "07070707070"));
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        alunoRepository.insert(new Aluno("18000001", "Julio Birula", "julio@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000002", "Larissa Gomes", "lari@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000003", "Pedro Laques", "pedro@email.com", LocalDate.parse("15/08/1999",formatter)));
//        alunoRepository.insert(new Aluno("18000004", "Buda Batista", "buda@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000005", "Leticia Anjo", "let@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000006", "Maria Taque", "maria@email.com", LocalDate.parse("15/08/1999",formatter)));
//
//        alunoRepository.insert(new Aluno("18000007", "Julio Batista", "julio@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000008", "Laisa Marias", "lari@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000009", "Sebastiao Laques", "seba@email.com", LocalDate.parse("15/08/1999",formatter)));
//        alunoRepository.insert(new Aluno("18000010", "Lindomar Batista", "lindo@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000011", "Jubileu Anjo", "jub@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000012", "Brasilionda Taque", "brasi@email.com", LocalDate.parse("15/08/1999",formatter)));
//
//        alunoRepository.insert(new Aluno("18000013", "Amarula Silva", "amarula@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000014", "Gabriel Gomes", "gabi@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000015", "Guilherme Laques", "gui@email.com", LocalDate.parse("15/08/1999",formatter)));
//        alunoRepository.insert(new Aluno("18000016", "Gabriela Batista", "gabi@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000017", "Zadra Anjo", "zadra@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000018", "Zainab Taque", "zainab@email.com", LocalDate.parse("15/08/1999",formatter)));
//
//        alunoRepository.insert(new Aluno("18000019", "Emauel Birula", "emanuel@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000020", "Leonardo Gomes", "leo@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000021", "Henri Laques", "henri@email.com", LocalDate.parse("15/08/1999",formatter)));
//        alunoRepository.insert(new Aluno("18000022", "Henry Batista", "henry@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000023", "Dantas Anjo", "dantas@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000024", "Kleisom Taque", "kley@email.com", LocalDate.parse("15/08/1999",formatter)));
//
//        alunoRepository.insert(new Aluno("18000025", "Maycon Birula", "mayc@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000026", "Laise Gomes", "laise@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000027", "Laurito Laques", "laurito@email.com", LocalDate.parse("15/08/1999",formatter)));
//        alunoRepository.insert(new Aluno("18000028", "Mauricio Batista", "laurito@email.com", LocalDate.parse("15/08/1999",formatter)));
//        alunoRepository.insert(new Aluno("18000029", "Diolete Batista", "diolete@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000030", "Marcos Anjo", "marc@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000031", "Alciran Taque", "alciran@email.com", LocalDate.parse("15/08/1999",formatter)));
//
//        alunoRepository.insert(new Aluno("18000032", "Pedro Birula", "pedro@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000033", "Larissa Gazeb", "larig@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000034", "Pedro Guizueik", "pedro@email.com", LocalDate.parse("15/08/1999",formatter)));
//        alunoRepository.insert(new Aluno("18000035", "Buda Zelest", "buda@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000036", "Osni Anjo", "osni@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000037", "Maria Taque", "maria@email.com", LocalDate.parse("15/08/1999",formatter)));
//
//        alunoRepository.insert(new Aluno("18000038", "Xaxin Biralo", "xaxin@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000039", "Xafurdio Gomes", "xafurdio@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000040", "Silical Laques", "sili@email.com", LocalDate.parse("15/08/1999",formatter)));
//        alunoRepository.insert(new Aluno("18000041", "Iraque Batista", "iraq@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000042", "Samir Anjo", "samir@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000043", "Angelo Taque", "ang@email.com", LocalDate.parse("15/08/1999",formatter)));
//
//        alunoRepository.insert(new Aluno("18000044", "Angela Birula", "angela@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000045", "Angelina Gomes", "angelina@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000046", "Naizaré Laques", "naizare@email.com", LocalDate.parse("15/08/1999",formatter)));
//        alunoRepository.insert(new Aluno("18000047", "Neto Batista", "neto@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000048", "Luiz Anjo", "luiz@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000049", "Silvia Taque", "silvia@email.com", LocalDate.parse("15/08/1999",formatter)));
//
//        alunoRepository.insert(new Aluno("18000050", "Patricia Birula", "pat@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000051", "Maria Gazebuc", "mari@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000052", "Eduarda Laques", "eduarda@email.com", LocalDate.parse("15/08/1999",formatter)));
//        alunoRepository.insert(new Aluno("18000053", "Nelson Batista", "nelson@email.com", LocalDate.parse("01/01/1995",formatter)));
//        alunoRepository.insert(new Aluno("18000054", "Bianca Anjo", "bia@email.com", LocalDate.parse("25/11/2000",formatter)));
//        alunoRepository.insert(new Aluno("18000055", "Diego Taque", "diego@email.com", LocalDate.parse("15/08/1999",formatter)));
//        alunoRepository.insert(new Aluno("18000056", "Almenta Taques", "almeida@email.com", LocalDate.parse("15/08/1999",formatter)));
//        System.out.println(alunoRepository.findByRa("18000055"));


//        prof = professorRepository.findByCpf("09090909090");
//        disciplina = disciplinaRepository.findByCodigo("D001");
//        disciplina.setProfessor(prof);
//        disciplinaRepository.save(disciplina);
//        System.out.println(prof + " " + disciplina);

//prof = professorRepository.findOne("5d790ed944ae4cf6a90e00bb");
//disciplina = disciplinaRepository.findByCodigo("D002");
//disciplina.setProfessor(prof);
//System.out.println(prof + " " + disciplina);
//disciplinaRepository.save(disciplina);
//

   // cidadeRepository.insert(new Cidade("Ponta Grossa", "PG"));
    // cidadeRepository.insert(new Cidade("Curitiba", "CB"));
    // cidadeRepository.insert(new Cidade("Londrina", "LD"));
    // cidadeRepository.insert(new Cidade("Castro", "CT"));
     //cidadeRepository.insert(new Cidade("São Paulo", "SP"));
        
    }
}
