package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.exception.MateriaException;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;

@Service
public class MateriaService implements IMateriaService {
	
	@Autowired
	private IMateriaRepository materiaRepository;
	
		@Override
		public List<MateriaEntity> listarTodos() {
			try {
				return this.materiaRepository.findAll();
			} catch (Exception e) {
				return new ArrayList<>();
			}
		}

		@Override
		public MateriaEntity buscarPorId(Long id) {
			try {
				Optional<MateriaEntity> materiaOptional = this.materiaRepository.findById(id);
				if (materiaOptional.isPresent()) {
					return materiaOptional.get();
				}
				throw new MateriaException("Materia não encontrada", HttpStatus.NOT_FOUND);
			} catch (MateriaException m) {
				throw m;
			} catch (Exception e) {
				throw new MateriaException("Erro interno identificado. Contate o suporte", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		@Override
		public Boolean salvar(MateriaEntity materia) {
			try {
				this.materiaRepository.save(materia);
				return true;				
			} catch (Exception e) {
				return false;
			}
		}
		
		@Override
		public Boolean atualizar(MateriaEntity materia) {
			try {	
				//Invocamos o método buscarPorId, que irá fazer a verificação da existência o obj.
		        //Caso não haja, retornará uma exceção.
				MateriaEntity materiaEntityAtualizada = this.buscarPorId(materia.getId());
								
				//atualizamos todos os valores
				materiaEntityAtualizada.setNome(materia.getNome());
				materiaEntityAtualizada.setCodigo(materia.getCodigo());
				materiaEntityAtualizada.setHoras(materia.getHoras());
				materiaEntityAtualizada.setFrequencia(materia.getFrequencia());
					
				//salvamos as alteracoes
				this.materiaRepository.save(materiaEntityAtualizada);	
				return true;				
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
