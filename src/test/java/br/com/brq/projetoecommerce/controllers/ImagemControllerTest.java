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

import br.com.brq.projetoecommerce.dto.ImagemDTO;
import br.com.brq.projetoecommerce.exceptions.ValidationError;
import br.com.brq.projetoecommerce.utils.MockUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ImagemControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	MockUtil mockUtil;

	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	void buscarIdTest() throws Exception {
		
		ImagemDTO dto = this.mockUtil.imagemControllerMock();
		ResultActions response = mockMvc.perform(
				get("/imagens/" + dto.getIdImagem()).content(objectMapper.writeValueAsString(dto)).contentType("application/json"));		

		MvcResult result = response.andReturn();

		String resultStr = result.getResponse().getContentAsString();

		ImagemDTO imagemDTO = objectMapper.readValue(resultStr, ImagemDTO.class);
	
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(imagemDTO.getIdImagem()).isEqualTo(dto.getIdImagem());
		assertThat(imagemDTO.getImagemProduto()).isEqualTo(dto.getImagemProduto());

	}
	
	@Test
	void buscarTodasImagensTest() throws Exception {

		ResultActions response = mockMvc.perform(get("/imagens").contentType("application/json"));
		MvcResult result = response.andReturn();

		String resultStr = result.getResponse().getContentAsString();

		ImagemDTO[] list = objectMapper.readValue(resultStr, ImagemDTO[].class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(list.length >= 0).isTrue();
	}
	
	@Test
	void cadastrarTest() throws JsonProcessingException, Exception {
		ImagemDTO dto = this.mockUtil.imagemControllerMock();

		ResultActions response = mockMvc.perform(
				post("/imagens").content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString();

		ImagemDTO dtoResult = objectMapper.readValue(objStr, ImagemDTO.class);

		assertThat(dtoResult.getIdImagem() > 0).isTrue();
		assertThat(dtoResult.getImagemProduto()).isEqualTo(dto.getImagemProduto());
		}
	
	@Test
	void cadastrarImagemNullTest() throws JsonProcessingException, Exception {
		ImagemDTO dto = this.mockUtil.imagemControllerMock();
		dto.setImagemProduto(null);

	
		ResultActions response = mockMvc.perform(
				post("/imagens").content(objectMapper.writeValueAsString(dto)).contentType("application/json"));
	
		MvcResult result = response.andReturn();

		
		String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		
		ValidationError error = objectMapper.readValue(objStr, ValidationError.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(error.getError()).isEqualTo("Erro de Validação");
	}
	
	@Test	
	void alterarTest() throws Exception {
		ImagemDTO dto = this.mockUtil.imagemControllerMock();

		ResultActions response = mockMvc.perform(
				put("/imagens/" + dto.getIdImagem()).content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String resultStr = result.getResponse().getContentAsString();

		ImagemDTO updated = objectMapper.readValue(resultStr, ImagemDTO.class);

		assertThat(updated.getIdImagem()).isEqualTo(dto.getIdImagem());
		assertThat(updated.getImagemProduto()).isEqualTo(dto.getImagemProduto());
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

	}
	
	@Test	
	void deleteTest() throws Exception {
		ImagemDTO dto = this.mockUtil.imagemControllerMock();

		ResultActions response = mockMvc.perform(delete("/imagens/" + dto.getIdImagem()).contentType("application/json"));

		MvcResult result = response.andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
}