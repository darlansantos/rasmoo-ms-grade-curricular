package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.List;

import com.rasmoo.cliente.escola.gradecurricular.dto.CursoDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;

public interface ICursoService {

	Boolean cadastrar(CursoDTO cursoDTO);

	List<CursoEntity> listar();

	CursoEntity consultarPorCodigo(String codCurso);

	Boolean atualizar(CursoDTO curso);

	Boolean excluir(Long cursoId);

}
