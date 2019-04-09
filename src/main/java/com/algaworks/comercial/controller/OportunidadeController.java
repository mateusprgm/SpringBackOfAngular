package com.algaworks.comercial.controller;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.algaworks.comercial.model.Oportunidade;
import com.algaworks.comercial.repository.OportunidadeRepository;

@CrossOrigin
@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {
	
	@Autowired
	private OportunidadeRepository oportunidades;
	
	
	@GetMapping
	public List<Oportunidade> listar() {
		return oportunidades.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Oportunidade> buscar(@PathVariable Long id) {
		Optional<Oportunidade> oportunidade = oportunidades.findById(id);
		
		if(!oportunidade.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oportunidade.get());
	}
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Oportunidade adicionar(@Valid @RequestBody Oportunidade oportunidade) {
		Optional<Oportunidade> oportunidadeExistente = oportunidades
				.findByDescricaoAndNomeProspectoAndValor(oportunidade.getDescricao(), oportunidade.getNomeProspecto(), oportunidade.getValor());
		
		if(oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Já existe uma oportunidade para este prospecto com a mesma descrição");
		}else if(oportunidade.getValor() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Você não inseriu o valor");
		}
		
		return oportunidades.save(oportunidade);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Oportunidade deletar(Oportunidade oportunidade) {
		
		Optional<Oportunidade> oportunidadeExistente = oportunidades.findById(oportunidade.getId());
		
		if(!oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"O registro não foi encontrado!");
		}
		oportunidades.delete(oportunidade);
		return oportunidade;
	}
	
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Oportunidade atualizar(@RequestBody Oportunidade oportunidade) {
		
		if(oportunidade.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Não foi passado o objeto para requisição!");
		}else {
			Optional<Oportunidade> oportunidadeExistente = oportunidades.findById(oportunidade.getId());
			if(!oportunidadeExistente.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"O registro não foi encontrado!");
			}
		}
		
		return oportunidades.save(oportunidade);
	}
}