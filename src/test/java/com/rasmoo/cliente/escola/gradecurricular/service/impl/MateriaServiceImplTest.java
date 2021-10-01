package com.rasmoo.cliente.escola.gradecurricular.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.http.HttpStatus;

import com.rasmoo.cliente.escola.gradecurricular.constant.MessagesConstant;
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.exception.MateriaException;
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
		assertEquals(1, listMateriaDTO.size());
		assertEquals(1, listMateriaDTO.get(0).getId());
		assertEquals("ILP", listMateriaDTO.get(0).getCodigo());
		assertEquals(1, listMateriaDTO.get(0).getFrequencia());
		assertEquals(64, listMateriaDTO.get(0).getHoras());
		assertEquals("INTRODUCAO A LINGUAGEM DE PROGRAMACAO", listMateriaDTO.get(0).getNome());
		assertEquals("/materia/1", listMateriaDTO.get(0).getLinks().getRequiredLink("self").getHref());
		
		Mockito.verify(this.materiaRepository, times(1)).findAll();
	}
	
	@Test
	void testListarPorHorarioMinimoSucesso() {
		List<MateriaEntity> listMateria = new ArrayList<>();
		listMateria.add(materiaEntity);
		
		Mockito.when(this.materiaRepository.findByHoraMinima(64)).thenReturn(listMateria);
		
		List<MateriaDTO> listMateriaDTO = this.materiaService.listarPorHorarioMinimo(64);
		
		assertNotNull(listMateriaDTO);
		assertEquals(1, listMateriaDTO.size());
		assertEquals(1, listMateriaDTO.get(0).getId());
		assertEquals("ILP", listMateriaDTO.get(0).getCodigo());
		assertEquals(1, listMateriaDTO.get(0).getFrequencia());
		assertEquals(64, listMateriaDTO.get(0).getHoras());
		assertEquals("INTRODUCAO A LINGUAGEM DE PROGRAMACAO", listMateriaDTO.get(0).getNome());
		
		Mockito.verify(this.materiaRepository, times(1)).findByHoraMinima(64);
	}
	
	@Test
	void testListarPorFrequenciaSucesso() {
		List<MateriaEntity> listMateria = new ArrayList<>();
		listMateria.add(materiaEntity);
		
		Mockito.when(this.materiaRepository.findByFrequencia(1)).thenReturn(listMateria);
		
		List<MateriaDTO> listMateriaDTO = this.materiaService.listarPorFrequencia(1);
		
		assertNotNull(listMateriaDTO);
		assertEquals(1, listMateriaDTO.size());
		assertEquals(1, listMateriaDTO.get(0).getId());
		assertEquals("ILP", listMateriaDTO.get(0).getCodigo());
		assertEquals(1, listMateriaDTO.get(0).getFrequencia());
		assertEquals(64, listMateriaDTO.get(0).getHoras());
		assertEquals("INTRODUCAO A LINGUAGEM DE PROGRAMACAO", listMateriaDTO.get(0).getNome());
		
		Mockito.verify(this.materiaRepository, times(1)).findByFrequencia(1);
	}
	
	@Test
	void testConsultarSucesso() {
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		MateriaDTO materiaDTO = this.materiaService.buscarPorId(1L);
		
		assertNotNull(materiaDTO);
		assertEquals(1, materiaDTO.getId());
		assertEquals("ILP", materiaDTO.getCodigo());
		assertEquals(1, materiaDTO.getFrequencia());
		assertEquals(64, materiaDTO.getHoras());
		assertEquals("INTRODUCAO A LINGUAGEM DE PROGRAMACAO", materiaDTO.getNome());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
	}
	
	@Test
	void testSalvarSucesso() {

		MateriaDTO materiaDTO = new MateriaDTO();
		materiaDTO.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");
		materiaDTO.setCodigo("ILP");
		materiaDTO.setFrequencia(1);
		materiaDTO.setHoras(64);
		
		materiaEntity.setId(null);
		
		Mockito.when(this.materiaRepository.findByCodigo("ILP")).thenReturn(null);
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);
		
		Boolean sucesso = this.materiaService.salvar(materiaDTO);
		
		assertTrue(sucesso);
		
		Mockito.verify(this.materiaRepository, times(1)).findByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);
		
		materiaEntity.setId(1L);
	}
	
	@Test
	void testAtualizarSucesso() {
		
		MateriaDTO materiaDTO = new MateriaDTO();
		materiaDTO.setId(1L);
		materiaDTO.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");
		materiaDTO.setCodigo("ILP");
		materiaDTO.setFrequencia(1);
		materiaDTO.setHoras(64);
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);
		
		Boolean sucesso = this.materiaService.atualizar(materiaDTO);
		
		assertTrue(sucesso);
		
		Mockito.verify(this.materiaRepository, times(0)).findByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);		
	}
	
	@Test
	void testExcluirSucesso() {
			
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Boolean sucesso = this.materiaService.excluir(1L);
		
		assertTrue(sucesso);
		
		Mockito.verify(this.materiaRepository, times(0)).findByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).deleteById(1L);
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);
	}
	
	
	/*
	 * CENARIOS DE THROW-MATERIA-EXCEPTION
	 */
	
	@Test
	void testAtualizarThrowMateriaException() {
		
		MateriaDTO materiaDTO = new MateriaDTO();
		materiaDTO.setId(1L);
		materiaDTO.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");
		materiaDTO.setCodigo("ILP");
		materiaDTO.setFrequencia(1);
		materiaDTO.setHoras(64);
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());
		
		MateriaException materiaException = assertThrows(MateriaException.class, () -> this.materiaService.atualizar(materiaDTO));
		
		assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
		assertEquals(MessagesConstant.ERRO_MATERIA_NAO_ENCONTRADA.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);		
	}
		
	@Test
	void testExcluirThrowMateriaException() {
				
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());
		
		MateriaException materiaException = assertThrows(MateriaException.class, () -> this.materiaService.excluir(1L));
		
		assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
		assertEquals(MessagesConstant.ERRO_MATERIA_NAO_ENCONTRADA.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(0)).deleteById(1L);;		
	}
	
	@Test
	void testCadastrarComIdThrowMateriaException() {
		
		MateriaDTO materiaDTO = new MateriaDTO();
		materiaDTO.setId(1L);
		materiaDTO.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");
		materiaDTO.setCodigo("ILP");
		materiaDTO.setFrequencia(1);
		materiaDTO.setHoras(64);
						
		MateriaException materiaException = assertThrows(MateriaException.class, () -> this.materiaService.salvar(materiaDTO));
		
		assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
		assertEquals(MessagesConstant.ERRO_ID_INFORMADO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(0)).findByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);		
	}
	
	@Test
	void testCadastrarComCodigoExistenteThrowMateriaException() {
		
		MateriaDTO materiaDTO = new MateriaDTO();
		materiaDTO.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");
		materiaDTO.setCodigo("ILP");
		materiaDTO.setFrequencia(1);
		materiaDTO.setHoras(64);
		
		Mockito.when(this.materiaRepository.findByCodigo("ILP")).thenReturn(materiaEntity);
		
		MateriaException materiaException = assertThrows(MateriaException.class, () -> this.materiaService.salvar(materiaDTO));
								
		assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
		assertEquals(MessagesConstant.ERRO_MATERIA_CADASTRADA_ANTERIORMENTE.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);		
	}
	
	
	/*
	 * CENARIOS DE THROW EXCEPTION
	 */
	
	@Test
	void testAtualizarThrowException() {
		
		MateriaDTO materiaDTO = new MateriaDTO();
		materiaDTO.setId(1L);
		materiaDTO.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");
		materiaDTO.setCodigo("ILP");
		materiaDTO.setFrequencia(1);
		materiaDTO.setHoras(64);
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenThrow(IllegalStateException.class);
		
		MateriaException materiaException = assertThrows(MateriaException.class, () -> this.materiaService.atualizar(materiaDTO));
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MessagesConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);		
	}
	
	@Test
    void testCadastrarThrowException() {
		
		MateriaDTO materiaDTO = new MateriaDTO();
		materiaDTO.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");
		materiaDTO.setCodigo("ILP");
		materiaDTO.setFrequencia(1);
		materiaDTO.setHoras(64);
		
		materiaEntity.setId(null);

		Mockito.when(this.materiaRepository.findByCodigo("ILP")).thenReturn(null);
		Mockito.when(this.materiaRepository.save(materiaEntity)).thenThrow(IllegalStateException.class);
			
		MateriaException materiaException = assertThrows(MateriaException.class, () -> this.materiaService.salvar(materiaDTO));
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MessagesConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findByCodigo("ILP");
		Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);
		
		materiaEntity.setId(1L);	
	}
	
	@Test
    void testConsultarThrowException() {
		
		Mockito.when(this.materiaRepository.findById(1L)).thenThrow(IllegalStateException.class);
				
		MateriaException materiaException = assertThrows(MateriaException.class, () -> this.materiaService.buscarPorId(1L));
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MessagesConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);	
	}
	
	@Test
	void testListarThrowException() {
		
		Mockito.when(this.materiaRepository.findAll()).thenThrow(IllegalStateException.class);
		
		MateriaException materiaException = assertThrows(MateriaException.class, () -> this.materiaService.listarTodos());
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MessagesConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findAll();	
	}
	
	@Test
	void testListarPorHorarioMinimoThrowException() {
		
		Mockito.when(this.materiaRepository.findByHoraMinima(64)).thenThrow(IllegalStateException.class);
			
		MateriaException materiaException = assertThrows(MateriaException.class, () -> this.materiaService.listarPorHorarioMinimo(64));
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MessagesConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findByHoraMinima(64);	
	}
	
	@Test
	void testListarPorFrequenciaThrowException() {
		
		Mockito.when(this.materiaRepository.findByFrequencia(1)).thenThrow(IllegalArgumentException.class);
		
		MateriaException materiaException = assertThrows(MateriaException.class, () -> this.materiaService.listarPorFrequencia(1));
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MessagesConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findByFrequencia(1);
	}
	
	@Test
	void testExcluirThrowException() {
		
		Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
		Mockito.doThrow(IllegalStateException.class).when(this.materiaRepository).deleteById(1L);
			
		MateriaException materiaException = assertThrows(MateriaException.class, () -> this.materiaService.excluir(1L));
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
		assertEquals(MessagesConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());
		
		Mockito.verify(this.materiaRepository, times(1)).findById(1L);
		Mockito.verify(this.materiaRepository, times(1)).deleteById(1L);	
	}

}
