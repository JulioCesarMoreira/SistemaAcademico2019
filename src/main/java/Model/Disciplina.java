/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
public class Disciplina {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String codigo;
    
    @Indexed
    private String nome;
    private int aulas;
    private String observacao;
    private int maxAlunos;

    public int getMaxAlunos() {
        return maxAlunos;
    }

    public void setMaxAlunos(int maxAlunos) {
        this.maxAlunos = maxAlunos;
    }
    @DBRef
    private Professor professor;
    

    public Disciplina() {
    }

    public Disciplina(String codigo, String nome, int aulas, String observacao, Professor professor) {
        this.codigo = codigo;
        this.nome = nome;
        this.aulas = aulas;
        this.observacao = observacao;
        this.professor = professor;
    } 
    
    public Disciplina(String codigo, String nome, int aulas, String observacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.aulas = aulas;
        this.observacao = observacao;
    }

   
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAulas(int aulas) {
        this.aulas = aulas;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public String getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getAulas() {
        return aulas;
    }

    public String getObservacao() {
        return observacao;
    }

    public Professor getProfessor() {
        return professor;
    }
public String getProfessorNome() {
        if (this.getProfessor() == null) {
            return " - ";
        } else {
            return this.getProfessor().getNome();
        }
    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final Disciplina other = (Disciplina) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Disciplina{" + "nome=" + nome + '}';
    }
    
    
    
}
