package com.rasmoo.cliente.escola.gradecurricular.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class MateriaServiceImplTest {
	
	@Mock
	private IMateriaRepository materiaRepository;
	
	@InjectMocks
	private MateriaServiceImpl materiaService;
	
	private static MateriaEntity materiaEntity;
	
	@BeforeAll
	static void init() {
		materiaEntity = new MateriaEntity();
		materiaEntity.setId(1L);
		materiaEntity.setCodigo("ILP");
		materiaEntity.setFrequencia(1);
		materiaEntity.setHoras(64);
		materiaEntity.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");
	}
	
	
	/*
	 * CENARIOS DE SUCESSO
	 */
	
	@Test
	void testListarSucesso() {
		List<MateriaEntity> listMateria = new ArrayList<>();
		listMateria.add(materiaEntity);
		
		Mockito.when(this.materiaRepository.findAll()).thenReturn(listMateria);
		
		List<MateriaDTO> listMateriaDTO = this.materiaService.listarTodos();
		
		assertNotNull(listMateriaDTO);
		assertEquals("ILP", listMateriaDTO.get(0).getCodigo());
		assertEquals(1, listMateriaDTO.get(0).getId());
		assertEquals("/materia/1", listMateriaDTO.get(0).getLinks().getRequiredLink("self").getHref());
		assertEquals(1, listMateriaDTO.size());
		
		Mockito.verify(this.materiaRepository, times(1)).findAll();
	}
	
	@Test
	void testListarPorHorarioMinimoSucesso() {
		List<MateriaEntity> listMateria = new ArrayList<>();
		listMateria.add(materiaEntity);
		
		Mockito.when(this.materiaRepository.findByHoraMinima(64)).thenReturn(listMateria);
		
		List<MateriaDTO> listMateriaDTO = this.materiaService.listarPorHorarioMinimo(64);
		
		assertNotNull(listMateriaDTO);
		assertEquals("ILP", listMateriaDTO.get(0).getCodigo());
		assertEquals(1, listMateriaDTO.get(0).getId());
		assertEquals(1, listMateriaDTO.size());
		
		Mockito.verify(this.materiaRepository, times(1)).findByHoraMinima(64);
	}
	
	@Test
	void testListarPorFrequenciaSucesso() {
		List<MateriaEntity> listMateria = new ArrayList<>();
		listMateria.add(materiaEntity);
		
		Mockito.when(this.materiaRepository.findByFrequencia(1)).thenReturn(listMateria);
		
		List<MateriaDTO> listMateriaDTO = this.materiaService.listarPorFrequencia(1);
		
		assertNotNull(listMateriaDTO);
		assertEquals("ILP", listMateriaDTO.get(0).getCodigo());
		assertEquals(1, listMateriaDTO.get(0).getId());
		assertEquals(1, listMateriaDTO.size());
		
		Mockito.verify(this.materiaRepository, times(1)).findByFrequencia(1);
	}

}
