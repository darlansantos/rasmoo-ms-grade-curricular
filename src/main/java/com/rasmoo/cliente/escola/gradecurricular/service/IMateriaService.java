package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.List;

import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;

public interface IMateriaService {
	
	public List<MateriaEntity> listarTodos();
	
	public MateriaEntity buscarPorId(final Long id);
	
	public Boolean salvar(final MateriaEntity materia);
	
	public Boolean atualizar(final MateriaEntity materia);
	
	public Boolean excluir(final Long id);

}
