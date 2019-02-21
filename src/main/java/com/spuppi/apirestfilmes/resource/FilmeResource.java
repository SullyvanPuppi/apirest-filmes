package com.spuppi.apirestfilmes.resource;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spuppi.apirestfilmes.model.Filme;
import com.spuppi.apirestfilmes.repository.FilmeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Sullyvan Puppi
 * spuppi.com - spuppi@gmail.com
 * 15 de fev de 2019
 *
 */
@Api(value="API REST Filmes")
@RestController
@RequestMapping("/filmes")
@CrossOrigin(origins="*")
public class FilmeResource {
	
	@Autowired
	private FilmeRepository fr;
	
	@ApiOperation(value = "Retorna a lista de filmes cadastrados")
	@GetMapping(produces = "application/json")
	public @ResponseBody Iterable<Filme> listarFilmes(){
		Iterable<Filme> listaFilmes = fr.findAll();
		return listaFilmes;
	}
	
	@ApiOperation(value = "Retorna o filme pelo parâmetro ID informado na url")
	@GetMapping(value="/{id}")
	public Optional<Filme> buscarFilme(@PathVariable(value="id") Long id){
		Optional<Filme> filme = fr.findById(id);
		return filme;
	}
	
	@ApiOperation(value = "Retorna o filme pelo parâmetro nome informado na url")
	@GetMapping(value="/filme{titulo}")
	public Filme buscarFilmeNome(@RequestParam(value="titulo") String titulo){
		Filme filme = fr.findByTitulo(titulo);
		return filme;
	}
	
	@ApiOperation(value = "Retorna a lista de filmes por Ator informando ID do mesmo na URL")
	@GetMapping(value="/ator/{id}", produces = "application/json")
	public @ResponseBody Iterable<Filme> listarFilmesAtor(@PathVariable long id){
		Iterable<Filme> listaFilmes = fr.findFilmeByAtor(id);
		return listaFilmes;
	}
	
	@ApiOperation(value = "Cadastrar novo filme")
	@PostMapping
	public Filme adicionarFilme(@Valid @RequestBody Filme filme) {
		return fr.save(filme);
	}
	
	@ApiOperation(value = "Atualizar filme por Id")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Filme> atualizarFilme(@PathVariable Long id, @Valid @RequestBody Filme filme) {
		Filme filmeExistente = fr.getOne(id);
		
		if(filmeExistente == null) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(filme, filmeExistente, "id");
		
		filmeExistente = fr.save(filmeExistente);
		
		return ResponseEntity.ok(filmeExistente);		
	}
	
	@ApiOperation(value = "Deletar filme por Id")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> removerFilme(@PathVariable Long id){
		Filme filmeExistente = fr.getOne(id);
		
		if(filmeExistente == null) {
			return ResponseEntity.notFound().build();
		}

		fr.delete(filmeExistente);
		
		return ResponseEntity.noContent().build();
	}	
}
