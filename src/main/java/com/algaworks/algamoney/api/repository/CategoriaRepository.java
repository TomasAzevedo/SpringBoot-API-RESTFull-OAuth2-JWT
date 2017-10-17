/**
 * 
 */
package com.algaworks.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algamoney.api.model.Categoria;

/**
 * @author Tomás
 *
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
