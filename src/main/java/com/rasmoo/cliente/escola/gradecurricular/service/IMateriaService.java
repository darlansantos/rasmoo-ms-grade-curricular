package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.List;

import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;

public interface IMateriaService {
	
	public List<MateriaDTO> listarTodos();
	
	public MateriaDTO buscarPorId(Long id);
	
	public Boolean salvar(MateriaDTO materiaDTO);
	
	public Boolean atualizar(MateriaDTO materiaDTO);
	
	public Boolean excluir(Long id);

	public List<MateriaDTO> listarPorHorarioMinimo(int horaMinima);

	public List<MateriaDTO> listarPorFrequencia(int frequencia);

}
