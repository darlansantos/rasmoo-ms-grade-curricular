package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.List;

import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rasmoo.cliente.escola.gradecurricular.model.CursoModel;

public interface ICursoService {

	Boolean cadastrar(CursoModel cursoModel);

	List<CursoEntity> listar();

	CursoEntity consultarPorCodigo(String codCurso);

	Boolean atualizar(CursoModel curso);

	Boolean excluir(Long cursoId);

}
