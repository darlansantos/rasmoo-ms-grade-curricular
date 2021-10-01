package com.rasmoo.cliente.escola.gradecurricular.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rasmoo.cliente.escola.gradecurricular.constant.MessagesConstant;
import com.rasmoo.cliente.escola.gradecurricular.controller.MateriaController;
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.exception.MateriaException;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;
import com.rasmoo.cliente.escola.gradecurricular.service.IMateriaService;

@CacheConfig(cacheNames = "materia")
@Service
public class MateriaServiceImpl implements IMateriaService {	
	
	private IMateriaRepository materiaRepository;
	private ModelMapper mapper;
		
	public MateriaServiceImpl(IMateriaRepository materiaRepository) {
		this.materiaRepository = materiaRepository;
		this.mapper = new ModelMapper();
	}

		@CachePut(unless = "#result.size()<3")
		@Override
		public List<MateriaDTO> listarTodos() {
			
			try {
					List<MateriaEntity> list = this.materiaRepository.findAll();
					List<MateriaDTO> listDTO = list.stream().map(entity -> this.mapper.map(entity, MateriaDTO.class)).collect(Collectors.toList());		
					listDTO.forEach(materia -> 
						materia.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultarMateria(materia.getId())).withSelfRel())
					);
				return listDTO;
			} catch (Exception e) {
				throw new MateriaException(MessagesConstant.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@CachePut(key = "#id")
		@Override
		public MateriaDTO buscarPorId(Long id) {
			
			try {
					Optional<MateriaEntity> materiaOptional = this.materiaRepository.findById(id);
					if (materiaOptional.isPresent()) {
						return this.mapper.map(materiaOptional.get(), MateriaDTO.class);
					}
					throw new MateriaException(MessagesConstant.ERRO_MATERIA_NAO_ENCONTRADA.getValor(), HttpStatus.NOT_FOUND);
			} catch (MateriaException m) {
				throw m;
			} catch (Exception e) {
				throw new MateriaException(MessagesConstant.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@Override
		public Boolean salvar(MateriaDTO materiaDTO) {
			
			try {
					if (materiaDTO.getId() != null) {
						throw new MateriaException(MessagesConstant.ERRO_ID_INFORMADO.getValor(), HttpStatus.BAD_REQUEST);
					}
					
					if (this.materiaRepository.findByCodigo(materiaDTO.getCodigo()) != null) {
						throw new MateriaException(MessagesConstant.ERRO_MATERIA_CADASTRADA_ANTERIORMENTE.getValor(), HttpStatus.BAD_REQUEST);
					}
					return this.cadastrarOuAtualizar(materiaDTO);		
			} catch (MateriaException m) {
				throw m;
			} catch (Exception e) {
				throw new MateriaException(MessagesConstant.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@Override
		public Boolean atualizar(MateriaDTO materiaDTO) {
			
			try {	
					this.buscarPorId(materiaDTO.getId());								
					return this.cadastrarOuAtualizar(materiaDTO);							
			} catch (MateriaException m) {
				throw m;
			} catch (Exception e) {
				throw new MateriaException(MessagesConstant.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@Override
		public Boolean excluir(Long id) {
			
			try {
					this.buscarPorId(id);
					this.materiaRepository.deleteById(id);
					return Boolean.TRUE;	
			} catch (MateriaException m) {
				throw m;
			} catch (Exception e) {
				throw new MateriaException(MessagesConstant.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@Override
		public List<MateriaDTO> listarPorHorarioMinimo(int horaMinima) {
			
			try {
				List<MateriaEntity> list = this.materiaRepository.findByHoraMinima(horaMinima);
				return list.stream().map(entity -> this.mapper.map(entity, MateriaDTO.class)).collect(Collectors.toList());				
			} catch (Exception e) {
				throw new MateriaException(MessagesConstant.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@Override
		public List<MateriaDTO> listarPorFrequencia(int frequencia) {
			
			try {
				List<MateriaEntity> list = this.materiaRepository.findByFrequencia(frequencia);
				return list.stream().map(entity -> this.mapper.map(entity, MateriaDTO.class)).collect(Collectors.toList());				
			} catch (Exception e) {
				throw new MateriaException(MessagesConstant.ERRO_GENERICO.getValor(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		private Boolean cadastrarOuAtualizar(MateriaDTO materiaDTO) {
				MateriaEntity materiaEnt = this.mapper.map(materiaDTO, MateriaEntity.class);
				this.materiaRepository.save(materiaEnt);
				return Boolean.TRUE;				
	    } 		
		
}
