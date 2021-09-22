package com.rasmoo.cliente.escola.gradecurricular.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rasmoo.cliente.escola.gradecurricular.constant.MessagesConstant;
import com.rasmoo.cliente.escola.gradecurricular.dto.CursoDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.exception.CursoException;
import com.rasmoo.cliente.escola.gradecurricular.repository.ICursoRepository;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;
import com.rasmoo.cliente.escola.gradecurricular.service.ICursoService;

@CacheConfig(cacheNames = "curso")
@Service
public class CursoServiceImpl implements ICursoService {
	
	private ICursoRepository cursoRepository;

	private IMateriaRepository materiaRepository;

	@Autowired
	public CursoServiceImpl(ICursoRepository cursoRepository, IMateriaRepository materiaRepository) {
		this.cursoRepository = cursoRepository;
		this.materiaRepository = materiaRepository;
	}

	@Override
	public Boolean cadastrar(CursoDTO cursoDTO) {
		try {
			// O id não pode ser informado no cadastro
			if (cursoDTO.getId() != null) {
				throw new CursoException(MessagesConstant.ERRO_ID_INFORMADO.getValor(), HttpStatus.BAD_REQUEST);
			}
		
			// Não permite fazer cadastro de cursos com mesmos códigos.
			if (this.cursoRepository.findCursoByCodigo(cursoDTO.getCodCurso()) != null) {
				throw new CursoException(MessagesConstant.ERRO_CURSO_CADASTRADO_ANTERIORMENTE.getValor(), HttpStatus.BAD_REQUEST);
			}
			return this.cadastrarOuAtualizar(cursoDTO);
		}catch (CursoException c) {
			throw c;
		}
		catch (Exception e) {
			throw new CursoException(MessagesConstant.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean atualizar(CursoDTO cursoDTO) {
		
		try {
			this.consultarPorCodigo(cursoDTO.getCodCurso());
			return this.cadastrarOuAtualizar(cursoDTO);
		} catch (CursoException c) {
			throw c;
		} catch (Exception e) {
			throw e;
		}
	}

	@CachePut(key = "#codCurso")
	@Override
	public CursoEntity consultarPorCodigo(String codCurso) {
		
		try {
			CursoEntity curso = this.cursoRepository.findCursoByCodigo(codCurso);
			if (curso == null) {
				throw new CursoException(MessagesConstant.ERRO_CURSO_NAO_ENCONTRADO.getValor(), HttpStatus.NOT_FOUND);
			}
			return curso;
		} catch (CursoException c) {
			throw c;
		} catch (Exception e) {
			throw new CursoException(MessagesConstant.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CachePut(unless = "#result.size()<3")
	@Override
	public List<CursoEntity> listar() {
		return this.cursoRepository.findAll();
	}
	
	@Override
	public Boolean excluir(Long cursoId) {
		
		try {
			if(this.cursoRepository.findById(cursoId).isPresent()) {
				this.cursoRepository.deleteById(cursoId);
				return Boolean.TRUE;
			}
			throw new CursoException(MessagesConstant.ERRO_CURSO_NAO_ENCONTRADO.getValor(), HttpStatus.NOT_FOUND);
		}catch (CursoException c) {
			throw c;
		}catch (Exception e) {
			throw new CursoException(MessagesConstant.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// O cadastrar e atualizar tem comportamentos semelhantes então centralizamos esse comportamento.
	private Boolean cadastrarOuAtualizar(CursoDTO cursoDTO) {
		List<MateriaEntity> listMateriaEntity = new ArrayList<>();

		if (!cursoDTO.getMaterias().isEmpty()) {

			cursoDTO.getMaterias().forEach(materia -> {
				if (this.materiaRepository.findById(materia).isPresent())
					listMateriaEntity.add(this.materiaRepository.findById(materia).get());
			});
		}

		CursoEntity cursoEntity = new CursoEntity();
		if(cursoDTO.getId()!=null) {
			cursoEntity.setId(cursoDTO.getId());
		}
		cursoEntity.setCodigo(cursoDTO.getCodCurso());
		cursoEntity.setNome(cursoDTO.getNome());
		cursoEntity.setMaterias(listMateriaEntity);

		this.cursoRepository.save(cursoEntity);
		return Boolean.TRUE;
	}

}
