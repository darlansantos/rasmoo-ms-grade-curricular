package com.rasmoo.cliente.escola.gradecurricular.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rasmoo.cliente.escola.gradecurricular.dto.CursoDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.repository.ICursoRepository;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class CursoServiceImplTest {
	
	@Mock
	private IMateriaRepository materiaRepository;

	@Mock
	private ICursoRepository cursoRepository;
	
	@InjectMocks
	private CursoServiceImpl cursoService;
	
	private static MateriaEntity materiaEntity;

	private static CursoEntity cursoEntity;
	
	@BeforeAll
	public static void init() {
		materiaEntity = new MateriaEntity();
		materiaEntity.setId(1L);
		materiaEntity.setCodigo("ILP");
		materiaEntity.setFrequencia(1);
		materiaEntity.setHoras(64);
		materiaEntity.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");
		
		List<MateriaEntity> listMat = new ArrayList<>();
		listMat.add(materiaEntity);
		
		cursoEntity = new CursoEntity();
		cursoEntity.setId(1L);
		cursoEntity.setCodigo("CC");
		cursoEntity.setNome("Ciencia da Computacao");
		cursoEntity.setMaterias(listMat);
	}
	
	/* 
	 * CENARIOS DE SUCESSO
	 */

	@Test
	void testListarSucesso() {
		
		List<CursoEntity> listCurso = new ArrayList<>();
		listCurso.add(cursoEntity);
		
		Mockito.when(this.cursoRepository.findAll()).thenReturn(listCurso);
		
		List<CursoEntity> listCursoEntity = this.cursoService.listar();
		
		assertNotNull(listCursoEntity);
		assertEquals(1, listCursoEntity.size());
		assertEquals(1, listCursoEntity.get(0).getId());
		assertEquals("CC", listCursoEntity.get(0).getCodigo());
		assertEquals("Ciencia da Computacao", listCursoEntity.get(0).getNome());
		
		Mockito.verify(this.cursoRepository, times(1)).findAll();
	}
	
	@Test
	void testConsultarSucesso() {
		
		Mockito.when(this.cursoRepository.findCursoByCodigo("CC")).thenReturn(cursoEntity);	
		CursoEntity cursoEntity = this.cursoService.consultarPorCodigo("CC");
		
		assertNotNull(cursoEntity);
		assertEquals(1, cursoEntity.getId());
		assertEquals("CC", cursoEntity.getCodigo());
		
		Mockito.verify(this.cursoRepository, times(1)).findCursoByCodigo("CC");
	}
	
	@Test
	void testCadastrarSucesso() {
		
		List<Long> listId = new ArrayList<Long>();
		listId.add(1L);
		
		CursoEntity novoCursoEntity = new CursoEntity();
		novoCursoEntity.setCodigo("CC");
		novoCursoEntity.setMaterias(new ArrayList<>());
		novoCursoEntity.setNome("ENGENHARIA DA COMPUTACAO");

		CursoDTO cursoDTO = new CursoDTO();
		cursoDTO.setCodCurso("CC");
		cursoDTO.setNome("ENGENHARIA DA COMPUTACAO");
		cursoDTO.setMaterias(listId);
		
		Mockito.when(this.cursoRepository.findCursoByCodigo("CC")).thenReturn(null);
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());
		Mockito.when(this.cursoRepository.save(novoCursoEntity)).thenReturn(novoCursoEntity);

		Boolean sucesso = this.cursoService.cadastrar(cursoDTO);

		assertTrue(sucesso);

		Mockito.verify(this.cursoRepository, times(1)).findCursoByCodigo("CC");
		Mockito.verify(this.cursoRepository, times(1)).save(novoCursoEntity);
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);	
	}
	
	@Test
	void testAtualizarSucesso() {
		
		List<Long> listId = new ArrayList<Long>();
		listId.add(1L);
		
		CursoDTO cursoDTO = new CursoDTO();
		cursoDTO.setId(1L);
		cursoDTO.setCodCurso("CC");
		cursoDTO.setNome("Ciencia da Computacao");
		cursoDTO.setMaterias(listId);
		
		Mockito.when(this.cursoRepository.findCursoByCodigo("CC")).thenReturn(cursoEntity);
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.when(this.cursoRepository.save(cursoEntity)).thenReturn(cursoEntity);
		
		Boolean sucesso = this.cursoService.atualizar(cursoDTO);
		
		assertTrue(sucesso);
		
		Mockito.verify(this.cursoRepository, times(1)).findCursoByCodigo("CC");
		Mockito.verify(this.cursoRepository, times(1)).save(cursoEntity);
		Mockito.verify(this.materiaRepository, times(2)).findById(1L);	
	}
	
	@Test
	void testExcluirSucesso() {
		
		Mockito.when(this.cursoRepository.findById(1L)).thenReturn(Optional.of(cursoEntity));
		Boolean sucesso = this.cursoService.excluir(1L);
		
		assertTrue(sucesso);
		
		Mockito.verify(this.cursoRepository, times(1)).findById(1L);
		Mockito.verify(this.cursoRepository, times(1)).deleteById(1L);
	}
	

}
