package com.rasmoo.cliente.escola.gradecurricular.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursoDTO {
	
	private Long id;
	
	@NotBlank(message = "nome deve ser preenchido entre 10 á 30 caracteres")
	@Size(min = 10, max = 30)
	private String nome;
	
	@NotBlank(message = "código do curso deve ser preenchido entre 3 á 5 caracteres")
	@Size(min = 3, max = 5)
	private String codCurso;
	
	private List<Long> materias;

}
