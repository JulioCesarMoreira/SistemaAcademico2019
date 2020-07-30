/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import Repository.*;

/**
 *
 * @author Julio
 */
public class DAO {
    private static final AnnotationConfigApplicationContext ctx
            = new AnnotationConfigApplicationContext(DBConfig.class);
    public static DisciplinaRepository disciplinaRepository = ctx.getBean(DisciplinaRepository.class);
    public static AlunoRepository alunoRepository = ctx.getBean(AlunoRepository.class);
    public static ProfessorRepository professorRepository = ctx.getBean(ProfessorRepository.class);
    public static CidadeRepository cidadeRepository = ctx.getBean(CidadeRepository.class);
}
