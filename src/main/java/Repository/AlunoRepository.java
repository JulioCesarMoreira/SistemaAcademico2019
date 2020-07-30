/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import Model.Aluno;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Julio
 */
public interface AlunoRepository extends MongoRepository<Aluno, String> {

    public Aluno findByRa(String string);

    public List<Aluno> findByNomeLikeIgnoreCaseOrRaLikeIgnoreCase(String nome,String ra/*, Pageable pag*/);
    public Page<Aluno> findByNomeLikeIgnoreCaseOrRaLikeIgnoreCase(String nome,String ra, Pageable pag);

    public int countByNomeLikeIgnoreCaseOrRaLikeIgnoreCase(String nome,String ra);
    
    public int countByRaLikeIgnoreCase(String string);

}
