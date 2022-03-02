package br.com.brq.projetoecommerce.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.brq.projetoecommerce.dto.CategoriaDTO;
import br.com.brq.projetoecommerce.exceptions.ValidationError;
import br.com.brq.projetoecommerce.utils.MockUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
 class CategoriaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MockUtil mockUtil;

	private ObjectMapper objectMapper = new ObjectMapper();


	@Test
	void buscarIdTest() throws Exception {

		CategoriaDTO dto = mockUtil.categoriaControllerMock();

		ResultActions response = mockMvc.perform(get("/categorias/" + dto.getIdCategoria())
				.content(objectMapper.writeValueAsString(dto)).contentType("application/json"));
		MvcResult result = response.andReturn();

		String resultStr = result.getResponse().getContentAsString();

		CategoriaDTO professorDTO = objectMapper.readValue(resultStr, CategoriaDTO.class);

		CategoriaDTO categoriaDTO = objectMapper.readValue(resultStr, CategoriaDTO.class);

// apenas comparando o status da resposta
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(professorDTO.getIdCategoria()).isEqualTo(dto.getIdCategoria());

	}

	@Test
	void buscarTodasCategoriasTest() throws Exception {

		CategoriaDTO dto = mockUtil.categoriaControllerMock();

		ResultActions response = mockMvc.perform(
				get("/categorias").content(objectMapper.writeValueAsString(dto)).contentType("application/json"));
		MvcResult result = response.andReturn();

		String resultStr = result.getResponse().getContentAsString();

		CategoriaDTO[] list = objectMapper.readValue(resultStr, CategoriaDTO[].class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(list.length > 0).isTrue();
	}

	@Test
	void cadastrarTest() throws JsonProcessingException, Exception {
		CategoriaDTO dto = mockUtil.categoriaControllerMock();

		ResultActions response = mockMvc.perform(
				post("/categorias").content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString();

		CategoriaDTO dtoResult = objectMapper.readValue(objStr, CategoriaDTO.class);

		assertThat(dtoResult.getIdCategoria() > 0).isTrue();
		assertThat(dtoResult.getNomeCategoria()).isEqualTo(dto.getNomeCategoria());

	}


	@Test
	void cadastrarCategoriaNullTest() throws JsonProcessingException, Exception {
		CategoriaDTO dto = this.mockUtil.categoriaControllerMock();
		dto.setNomeCategoria(null);

	
		ResultActions response = mockMvc.perform(
				post("/categorias").content(objectMapper.writeValueAsString(dto)).contentType("application/json"));
	
		MvcResult result = response.andReturn();

		
		String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		
		ValidationError error = objectMapper.readValue(objStr, ValidationError.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(error.getError()).isEqualTo("Erro de Validação");
	}

	@Test
	void alterarTest() throws Exception {
		CategoriaDTO dto = mockUtil.categoriaControllerMock();

		ResultActions response = mockMvc.perform(put("/categorias/" + dto.getIdCategoria())
				.content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String resultStr = result.getResponse().getContentAsString();

		CategoriaDTO updated = objectMapper.readValue(resultStr, CategoriaDTO.class);

		assertThat(updated.getIdCategoria()).isEqualTo(dto.getIdCategoria());
		assertThat(updated.getNomeCategoria()).isEqualTo(dto.getNomeCategoria());
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	void deleteTest() throws Exception {
		CategoriaDTO dto = mockUtil.categoriaControllerMock();

		ResultActions response = mockMvc
				.perform(delete("/categorias/" + dto.getIdCategoria()).contentType("application/json"));

		MvcResult result = response.andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}

}