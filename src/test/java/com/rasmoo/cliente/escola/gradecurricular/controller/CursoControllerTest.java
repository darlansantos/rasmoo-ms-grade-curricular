package com.rasmoo.cliente.escola.gradecurricular.controller;

import java.util.ArrayList;

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
import com.rasmoo.cliente.escola.gradecurricular.dto.CursoDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rasmoo.cliente.escola.gradecurricular.service.ICursoService;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@WebMvcTest(controllers = CursoController.class)
class CursoControllerTest {
	
	private static final MediaType JSON = MediaType.APPLICATION_JSON;
	private static final String API = "/curso/";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ICursoService cursoService;
	
	//private static CursoDTO cursoDTO;
	
	@Test
	void testCadastrarCursos() throws Exception {
		
		// Cenario
		CursoDTO dto = criarCursoDTO();
		
		Mockito.when(this.cursoService.cadastrar(dto)).thenReturn(Boolean.TRUE);
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// Execucao e Verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(API)
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		
		this.mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
	    ;
	}
	
	@Test
	void testListarCursos() throws Exception {
		
		// Cenario	
		Mockito.when(this.cursoService.listar()).thenReturn(new ArrayList<CursoEntity>());
		
		// Execucao e Verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.get(API)
													.accept(JSON)
													.contentType(JSON);
		
		this.mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
		;	
	}
	
	
	@Test
	void testConsultarCursosPorMateria() throws Exception {
		
		// Cenario	
		Mockito.when(this.cursoService.consultarPorCodigo("POO")).thenReturn(new CursoEntity());
		
		// Execucao e Verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.get(API.concat("POO"))
													.accept(JSON)
													.contentType(JSON);
		
		this.mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
		;	
	}
	
	@Test
	void testAtualizarCursos() throws Exception {
		
		// Cenario
		CursoDTO dto = criarCursoDTO();
		Mockito.when(this.cursoService.atualizar(dto)).thenReturn(Boolean.TRUE);
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// Execucao e Verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.put(API)
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		
		this.mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
		;	
	}
	
	@Test
	void testExcluirCursos() throws Exception {
		
		// Cenario
		CursoDTO dto = criarCursoDTO();
		Mockito.when(this.cursoService.atualizar(dto)).thenReturn(Boolean.TRUE);
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// Execucao e Verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.delete(API+1L)
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		
		this.mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
		;	
	}

	private static CursoDTO criarCursoDTO() {
		CursoDTO dto = new CursoDTO();
		//dto.setId(1L);
		dto.setNome("Sistemas da Informação");
		dto.setCodCurso("POO");
		//dto.setMaterias(new ArrayList<Long>(Arrays.asList(1L, 2L, 3L)));
		return dto;
	}
	
}
