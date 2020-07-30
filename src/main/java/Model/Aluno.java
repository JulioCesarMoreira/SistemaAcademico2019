/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Julio
 */
@Document
public class Aluno {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String ra;
    
    @Indexed
    private String nome;
    private String email;
    private LocalDate dataDeNascimento;
    private List<Matricula> matriculas = new ArrayList<>();
    @DBRef
    private Cidade cidade;

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    public Aluno() {
    }

    public Aluno(String ra, String nome, String email, LocalDate dataDeNascimento) {
        this.ra = ra;
        this.nome = nome;
        this.email = email;
        this.dataDeNascimento = dataDeNascimento;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getId() {
        return id;
    }

    public String getRa() {
        return ra;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
    public String getCidadeNome() {
        if (this.getCidade() == null) {
            return " - ";
        } else {
            return this.getCidade().getNome();
        }
    }
    public String getDataDeNascimentoString() {
        LocalDate dataBanco = this.dataDeNascimento;
        String dataFormatada = dataBanco.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return dataFormatada;
    }
    public String getAprovacao(){
       int total=0;
       int aprovado=0;
        for(Matricula a : this.matriculas){
           if(a.getStatus().equals("APROV") || a.getStatus().equals("APROV EXAM")){
               aprovado++;
           
           }
           total++;
       }
    return "A: "+String.valueOf(aprovado)+"/T: "+String.valueOf(total);
}
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Aluno other = (Aluno) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Aluno{" + "nome=" + nome + '}';
    }

   
    
    
}
