package com.rasmoo.cliente.escola.gradecurricular.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.service.IMateriaService;

@RestController
@RequestMapping(value = "/materia")
public class MateriaController {
		
	@Autowired
	private IMateriaService materiaService;
	
	@GetMapping
	public ResponseEntity<List<MateriaDTO>> listarMaterias() {
		return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.listarTodos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MateriaDTO> consultarMateria(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.buscarPorId(id));
	}
	
	@PostMapping
	public ResponseEntity<Boolean> cadatrarMateria(@Valid @RequestBody MateriaDTO materiaDTO) {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.materiaService.salvar(materiaDTO));			
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> excluirMateria(@PathVariable Long id) {
			return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.excluir(id));
	}
	
	@PutMapping
	public ResponseEntity<Boolean> atualizarMateria(@RequestBody MateriaDTO materiaDTO) {			
			return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.atualizar(materiaDTO));
	}

}
