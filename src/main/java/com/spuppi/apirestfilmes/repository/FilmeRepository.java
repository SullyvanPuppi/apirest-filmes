package com.spuppi.apirestfilmes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spuppi.apirestfilmes.model.Filme;

/**
 * @author Sullyvan Puppi
 * spuppi.com - spuppi@gmail.com
 * 15 de fev de 2019
 *
 */
public interface FilmeRepository extends JpaRepository<Filme, Long>{

	Filme findById(long id);
	
	Filme findByTitulo(String titulo);	
	
	@Query(value = "SELECT A.* FROM filme A JOIN filme_atores B ON B.atores_id = :id AND A.id = B.filme_id", nativeQuery = true)
	Iterable<Filme> findFilmeByAtor(@Param("id") long id);
}