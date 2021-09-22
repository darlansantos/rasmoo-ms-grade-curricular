package com.rasmoo.cliente.escola.gradecurricular.controller;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.service.IMateriaService;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@WebMvcTest(controllers = MateriaController.class)
class MateriaControllerTest {

	private static final MediaType JSON = MediaType.APPLICATION_JSON;
	private static final String API = "/materia/";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IMateriaService materiaService;
	
	private static MateriaDTO materiaDTO;

	@BeforeAll
	public static void init() {	
		criarMateriaDTO();
	}
	
	@Test
	void testListarMaterias() throws Exception {
		
		// Cenario	
		Mockito.when(materiaService.listarTodos()).thenReturn(new ArrayList<MateriaDTO>());
		
		// Execucao e Verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.get(API)
													.accept(JSON)
													.contentType(JSON);
		
		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
		;	
	}
	
	@Test
	void testConsultarMateriaPorId() throws Exception {
		
		// Cenario	
		MateriaDTO dto = criarMateriaDTO();
		Mockito.when(materiaService.buscarPorId(1L)).thenReturn(dto);
		
		// Execucao e Verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.get(API.concat("1"))
													.accept(JSON)
													.contentType(JSON);
		
		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
		;	
	}
	
	@Test
	void testCadastrarMaterias() throws Exception {
		
		// Cenario
		MateriaDTO dto = criarMateriaDTO();	
		Mockito.when(this.materiaService.salvar(materiaDTO)).thenReturn(Boolean.TRUE);
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// Execucao e Verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(API)
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		
		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isCreated())
		;	
	}
	
	@Test
	void testAtualizarMaterias() throws Exception {
		
		// Cenario
		MateriaDTO dto = criarMateriaDTO();	
		Mockito.when(this.materiaService.atualizar(dto)).thenReturn(Boolean.TRUE);
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// Execucao e Verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.put(API)
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		
		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
		;	
	}
	
	@Test
	void testExcluitMaterias() throws Exception {
		
		// Cenario
		MateriaDTO dto = criarMateriaDTO();
		Mockito.when(this.materiaService.excluir(1L)).thenReturn(Boolean.TRUE);
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// Execucao e Verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.delete(API.concat("1"))
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		
		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
		;	
	}
		
	@Test
	void testConsultarMateriasPorHoraMinima() throws Exception {
		
		// Cenario
		MateriaDTO dto = criarMateriaDTO();
		Mockito.when(materiaService.listarPorHorarioMinimo(64)).thenReturn(new ArrayList<MateriaDTO>());
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// Execucao e Verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.get(API.concat("horario-minimo/64"))
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		
		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
		;	
	}
		
	@Test
	void testConsultarMateriasPorFrequencia() throws Exception {
		
		// Cenario
		MateriaDTO dto = criarMateriaDTO();
		Mockito.when(materiaService.listarPorFrequencia(1)).thenReturn(new ArrayList<MateriaDTO>());
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// Execucao e Verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.get(API.concat("frequencia/1"))
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		
		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
		;	
	}
	
	private static MateriaDTO criarMateriaDTO() {
		MateriaDTO dto = new MateriaDTO();
		dto.setId(1L);
		dto.setCodigo("ILP");
		dto.setFrequencia(1);
		dto.setHoras(64);
		dto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");
		return dto;
	}

}
