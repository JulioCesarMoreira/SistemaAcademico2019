/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 *
 * @author Julio
 */
public class Matricula {

    @DBRef
    private Disciplina disciplina;

    private int nota1Sem = 0;
    private int nota2Sem = 0;
    private int notaExame = 0;
    private int faltas = 0;

    public Matricula(Disciplina disciplina, int nota1Sem, int nota2Sem, int notaExame, int faltas) {
        setDisciplina(disciplina);
        setFaltas(faltas);
        setNota1Sem(nota1Sem);
        setNota2Sem(nota2Sem);
        setNotaExame(notaExame);
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public void setNota1Sem(int nota1Sem) {
        if (nota1Sem > 100) {
            this.nota1Sem = 100;
        } else if (nota1Sem < 0) {
            this.nota1Sem = 0;
        } else {
            this.nota1Sem = nota1Sem;
        }
    }

    public void setNota2Sem(int nota2Sem) {
        if (nota2Sem > 100) {
            this.nota2Sem = 100;
        } else if (nota2Sem < 0) {
            this.nota2Sem = 0;
        } else {
            this.nota2Sem = nota2Sem;
        }

    }

    public void setNotaExame(int notaExame) {
        if (notaExame > 100) {
            this.notaExame = 100;
        } else if (notaExame < 0) {
            this.notaExame = 0;
        } else {
            this.notaExame = notaExame;
        }
    }

    public void setFaltas(int faltas) {
        if (faltas > disciplina.getAulas()) {
            this.faltas = disciplina.getAulas();
        } else if (faltas < 0) {
            this.faltas = 0;
        } else {
            this.faltas = faltas;
        }
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public int getNota1Sem() {
        return nota1Sem;
    }

    public int getNota2Sem() {
        return nota2Sem;
    }

    public int getNotaExame() {
        return notaExame;
    }

    public int getFaltas() {
        return faltas;
    }

    public double getPercFrequencia() {
        if (disciplina.getAulas() > 0) {
            return 100 - (faltas * 100) / (disciplina.getAulas());
        } else {
            return 0.0;
        }
    }

    public double getMedia() {
        double media;
        media = (nota1Sem + nota2Sem) / 2.0;
        if (media >= 70.0) {
            return media;
        } else {
            media = (nota1Sem + nota2Sem + notaExame) / 3.0;
            return media;
        }
    }

    public String getStatus() {
        if (getPercFrequencia() < 75) {
            return "REP FT";
        } else if (getMedia() >= 70 && ((this.getNota1Sem()+this.getNota2Sem())/2)>=70) {
            return "APROV";
        } else if (((this.getNota1Sem()+this.getNota2Sem()+this.getNotaExame())/3)>=60) {
            return "APROV EXAM";
        }else{
           return "REPROV NT";
        }

    }

    //TABLE VIEW
    public String getDisciplinaNome() {
        return this.disciplina.getNome();
    }

    public String getNota1S() {
        if (this.getNota1Sem() < 0) {
            return "Erro -";
        } else if (this.getNota1Sem() > 100) {
            return "Erro + "+String.valueOf(this.getNota1Sem());
        } else {
            return String.valueOf(this.getNota1Sem());
        }
    }

    public String getNota2S() {
        if (this.getNota2Sem() < 0) {
            return "Erro -";
        } else if (this.getNota2Sem() > 100) {
            return "Erro + " + String.valueOf(this.getNota2Sem());
        } else {
            return String.valueOf(this.getNota2Sem());
        }
    }

    public String getNotaE() {
        if (this.getNotaExame() < 0) {
            return "Erro -";
        } else if (this.getNotaExame() > 100) {
            return "Erro + " + String.valueOf(this.getNotaExame());
        } else {
            return String.valueOf(this.getNotaExame());
        }
    }

    public String getMediaFormatado() {
            return String.format("%.2f",this.getMedia());
        }


    public String getFaltaFormatado() {
        if (this.getFaltas() < 0) {
            return "Erro -";
        } else if (this.getFaltas() > disciplina.getAulas()) {
            return String.valueOf(this.getFaltas());
        } else {
            return String.valueOf(this.getFaltas());
        }

    }

    public String getAulas() {
        return String.valueOf(this.getDisciplina().getAulas());
    }

    public String getFrequencia() {
        if (this.getPercFrequencia() < 0) {
            return "Erro -";
        } else if (this.getPercFrequencia() > 100) {
            return "Erro + " + String.valueOf(this.getPercFrequencia());
        } else {
            return String.valueOf(this.getPercFrequencia());
        }

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.disciplina);
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
        final Matricula other = (Matricula) obj;
        if (!Objects.equals(this.disciplina, other.disciplina)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Matricula{" + "disciplina=" + disciplina + '}';
    }

}
