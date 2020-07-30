/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import Model.Professor;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Julio
 */
public interface ProfessorRepository extends MongoRepository<Professor, String>{
    public Professor findByCpf(String string);
}
