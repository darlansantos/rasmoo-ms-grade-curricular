package com.rasmoo.cliente.escola.gradecurricular.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.exception.MateriaException;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;
import com.rasmoo.cliente.escola.gradecurricular.service.IMateriaService;

@Service
public class MateriaService implements IMateriaService {
	
	@Autowired
	private IMateriaRepository materiaRepository;
	
	@Autowired
	private ModelMapper mapper;
	
		@Override
		public List<MateriaDTO> listarTodos() {
			try {
				List<MateriaEntity> list = this.materiaRepository.findAll();
				List<MateriaDTO> listDTO = list.stream().map(entity -> mapper.map(entity, MateriaDTO.class)).collect(Collectors.toList());
				return listDTO;
			} catch (Exception e) {
				return new ArrayList<>();
			}
		}

		@Override
		public MateriaDTO buscarPorId(Long id) {
			try {
				Optional<MateriaEntity> materiaOptional = this.materiaRepository.findById(id);
				if (materiaOptional.isPresent()) {
					return mapper.map(materiaOptional.get(), MateriaDTO.class);
				}
				throw new MateriaException("Materia não encontrada", HttpStatus.NOT_FOUND);
			} catch (MateriaException m) {
				throw m;
			} catch (Exception e) {
				throw new MateriaException("Erro interno identificado. Contate o suporte", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@Override
		public Boolean salvar(MateriaDTO materiaDTO) {
			try {
				MateriaEntity materiaEntity = mapper.map(materiaDTO, MateriaEntity.class);
				this.materiaRepository.save(materiaEntity);
				return true;				
			} catch (Exception e) {
				return false;
			}
		}
		
		@Override
		public Boolean atualizar(MateriaDTO materiaDTO) {
			try {	
				Optional<MateriaEntity> materiaOptional = this.materiaRepository.findById(materiaDTO.getId());				
				if (materiaOptional.isPresent()) {
					MateriaEntity materiaEntityAtualizada = mapper.map(materiaDTO, MateriaEntity.class);
					this.materiaRepository.save(materiaEntityAtualizada);						
					return true;				
				}
				return false;
			} catch (MateriaException m) {
				throw m;
			} catch (Exception e) {
				throw e;
			}
		}

		@Override
		public Boolean excluir(Long id) {
			try {
				this.buscarPorId(id);
				this.materiaRepository.deleteById(id);
				return true;	
			} catch (MateriaException m) {
				throw m;
			} catch (Exception e) {
				throw e;
			}
		}
		
}
