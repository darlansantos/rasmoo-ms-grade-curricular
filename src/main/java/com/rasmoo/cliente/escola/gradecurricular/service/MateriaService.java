package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;

@Service
public class MateriaService implements IMateriaService {
	
	@Autowired
	private IMateriaRepository materiaRepository;
	
		@Override
		public List<MateriaEntity> listarTodos() {
			return this.materiaRepository.findAll();
		}

		@Override
		public MateriaEntity buscarPorId(Long id) {
			return this.materiaRepository.findById(id).get();
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
				//buscamos pela materia que gostaríamos de atualizar
				MateriaEntity materiaEntityAtualizada = this.materiaRepository.findById(materia.getId()).get();
				
				//atualizamos todos os valores
				materiaEntityAtualizada.setNome(materia.getNome());
				materiaEntityAtualizada.setCodigo(materia.getCodigo());
				materiaEntityAtualizada.setHoras(materia.getHoras());
				materiaEntityAtualizada.setFrequencia(materia.getFrequencia());
				
				//salvamos as alteracoes
				this.materiaRepository.save(materiaEntityAtualizada);	
				return true;
			
			}catch (Exception e) {
				return false;
			}
		}

	@Override
	public Boolean excluir(Long id) {
		try {
			this.materiaRepository.deleteById(id);
			return true;
			
		}catch (Exception e) {
			return false;
		}
	}

}
