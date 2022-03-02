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

import br.com.brq.projetoecommerce.dto.ItemVendaDTO;
import br.com.brq.projetoecommerce.exceptions.ValidationError;
import br.com.brq.projetoecommerce.utils.MockUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ItemVendaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MockUtil mockUtil;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void buscarIdTest() throws Exception {

		ItemVendaDTO dto = mockUtil.itemControllerMock();

		ResultActions response = mockMvc.perform(get("/itemVenda/" + dto.getIdItemVenda())
				.content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String resultStr = result.getResponse().getContentAsString();

		ItemVendaDTO itemVendaDTO = objectMapper.readValue(resultStr, ItemVendaDTO.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(itemVendaDTO.getIdItemVenda()).isEqualTo(dto.getIdItemVenda());

	}

	@Test
	void buscarTodasVendasTest() throws Exception {

		ResultActions response = mockMvc.perform(get("/itemVenda").contentType("application/json"));
		MvcResult result = response.andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void cadastrarTest() throws JsonProcessingException, Exception {
		ItemVendaDTO dto = mockUtil.itemControllerMock();

		ResultActions response = mockMvc.perform(
				post("/itemVenda").content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString();

		ItemVendaDTO dtoResult = objectMapper.readValue(objStr, ItemVendaDTO.class);

		assertThat(dtoResult.getIdItemVenda() > 0).isTrue();
		assertThat(dtoResult.getItemQuantidade() >= 0).isTrue();

	}

	@Test
	void cadastrarItemProdutoNullTest() throws JsonProcessingException, Exception {
		ItemVendaDTO dto = this.mockUtil.itemControllerMock();
		dto.setItemProduto(null);

		ResultActions response = mockMvc.perform(
				post("/itemVenda").content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ValidationError error = objectMapper.readValue(objStr, ValidationError.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(error.getError()).isEqualTo("Erro de Validação");
	}

	@Test
	void cadastrarItemQuantidadeNullTest() throws JsonProcessingException, Exception {
		ItemVendaDTO dto = this.mockUtil.itemControllerMock();
		dto.setItemQuantidade(null);

		ResultActions response = mockMvc.perform(
				post("/itemVenda").content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ValidationError error = objectMapper.readValue(objStr, ValidationError.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(error.getError()).isEqualTo("Erro de Validação");
	}

	@Test
	void alterarTest() throws Exception {
		ItemVendaDTO dto = this.mockUtil.itemControllerMock();

		dto.setIdItemVenda(dto.getIdItemVenda());

		ResultActions response = mockMvc.perform(put("/itemVenda/" + dto.getIdItemVenda())
				.content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String resultStr = result.getResponse().getContentAsString();

		ItemVendaDTO updated = objectMapper.readValue(resultStr, ItemVendaDTO.class);

		assertThat(updated.getIdItemVenda()).isEqualTo(dto.getIdItemVenda());
		assertThat(updated.getItemQuantidade() >= 0).isTrue();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	void deleteTest() throws Exception {
		ItemVendaDTO dto = this.mockUtil.itemControllerMock();

		ResultActions response = mockMvc
				.perform(delete("/itemVenda/" + dto.getIdItemVenda()).contentType("application/json"));

		MvcResult result = response.andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}
}
