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

import br.com.brq.projetoecommerce.dto.VendaDTO;
import br.com.brq.projetoecommerce.exceptions.ValidationError;
import br.com.brq.projetoecommerce.utils.MockUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class VendaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private MockUtil mockUtil;
	
	@Test
	void BuscarIdVendaTest() throws Exception{
		VendaDTO dto = this.mockUtil.vendaControllerMock();

		ResultActions response = mockMvc.perform(
				get("/venda/" + dto.getIdVenda()).content(objectMapper.writeValueAsString(dto)).contentType("application/json"));
		
		
		MvcResult result = response.andReturn();
		
		String resultStr = result.getResponse().getContentAsString();
				
		VendaDTO vendaDTO = objectMapper.readValue(resultStr, VendaDTO.class);
	
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(vendaDTO.getIdVenda()).isEqualTo(dto.getIdVenda());
		
	}

	@Test
	void buscarTodasVendasTest() throws Exception {

		ResultActions response = mockMvc.perform(get("/venda").contentType("application/json"));
		MvcResult result = response.andReturn();

		String resultStr = result.getResponse().getContentAsString();

		VendaDTO[] list = objectMapper.readValue(resultStr, VendaDTO[].class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(list.length >= 0).isTrue();
	}
	
	@Test
	void cadastrarTest() throws JsonProcessingException, Exception {
		VendaDTO dto = this.mockUtil.vendaControllerMock();

		ResultActions response = mockMvc.perform(
				post("/venda").content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString();

		VendaDTO dtoResult = objectMapper.readValue(objStr, VendaDTO.class);

		assertThat(dtoResult.getIdVenda() >= 0).isTrue();
		assertThat(dtoResult.getDataVenda()).isEqualTo(dto.getDataVenda());
		
	}
	
	@Test
	void cadastrarDataVendaNullTest() throws JsonProcessingException, Exception {
		VendaDTO dto = this.mockUtil.vendaControllerMock();
		dto.setDataVenda(null);

	
		ResultActions response = mockMvc.perform(
				post("/categorias").content(objectMapper.writeValueAsString(dto)).contentType("application/json"));
	
		MvcResult result = response.andReturn();

		
		String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		
		ValidationError error = objectMapper.readValue(objStr, ValidationError.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(error.getError()).isEqualTo("Erro de Validação");
	}
	
	@Test
	void cadastrarItensVendaNullTest() throws JsonProcessingException, Exception {
		VendaDTO dto = this.mockUtil.vendaControllerMock();
		dto.setItens(null);

	
		ResultActions response = mockMvc.perform(
				post("/categorias").content(objectMapper.writeValueAsString(dto)).contentType("application/json"));
	
		MvcResult result = response.andReturn();

		
		String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		
		ValidationError error = objectMapper.readValue(objStr, ValidationError.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(error.getError()).isEqualTo("Erro de Validação");
	}
	
	@Test
	void cadastrarUsuarioVendaNullTest() throws JsonProcessingException, Exception {
		VendaDTO dto = this.mockUtil.vendaControllerMock();
		dto.setUsuario(null);

	
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
		VendaDTO dto = this.mockUtil.vendaControllerMock();
	
		ResultActions response = mockMvc.perform(
				put("/venda/" + dto.getIdVenda()).content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String resultStr = result.getResponse().getContentAsString();

		VendaDTO updated = objectMapper.readValue(resultStr, VendaDTO.class);

		assertThat(updated.getIdVenda()).isEqualTo(dto.getIdVenda());

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

	}
	
	
	@Test	
	void deleteTest() throws Exception {
		VendaDTO dto = this.mockUtil.vendaControllerMock();

		ResultActions response = mockMvc.perform(delete("/venda/" + dto.getIdVenda()).contentType("application/json"));

		MvcResult result = response.andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}

}
