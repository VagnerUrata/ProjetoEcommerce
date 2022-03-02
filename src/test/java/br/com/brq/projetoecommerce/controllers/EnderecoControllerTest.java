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

import br.com.brq.projetoecommerce.dto.EnderecoDTO;
import br.com.brq.projetoecommerce.dto.UsuarioDTO;
import br.com.brq.projetoecommerce.exceptions.ValidationError;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class EnderecoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void buscarIdTest() throws Exception {

		ResultActions response = mockMvc.perform(get("/enderecos/1").contentType("application/json"));

		MvcResult result = response.andReturn();

		String resultStr = result.getResponse().getContentAsString();

		EnderecoDTO enderecoDTO = objectMapper.readValue(resultStr, EnderecoDTO.class);
		System.out.println(enderecoDTO);

// apenas comparando o status da resposta
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(enderecoDTO.getEnderecoId()).isEqualTo(1);

	}

	@Test
	void buscarTodosEnderecosTest() throws Exception {

		ResultActions response = mockMvc.perform(get("/enderecos").contentType("application/json"));
		MvcResult result = response.andReturn();

		String resultStr = result.getResponse().getContentAsString();

		EnderecoDTO[] list = objectMapper.readValue(resultStr, EnderecoDTO[].class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(list.length >= 0).isTrue();
	}

	@Test
	void cadastrarTest() throws JsonProcessingException, Exception {
		EnderecoDTO dto = this.createValidEndereco();

		ResultActions response = mockMvc.perform(
				post("/enderecos").content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString();

		EnderecoDTO dtoResult = objectMapper.readValue(objStr, EnderecoDTO.class);

		assertThat(dtoResult.getEnderecoId() > 0).isTrue();
		assertThat(dtoResult.getLogradouro()).isEqualTo(dto.getLogradouro());
		assertThat(dtoResult.getNumero()).isEqualTo(dto.getNumero());
		assertThat(dtoResult.getComplemento()).isEqualTo(dto.getComplemento());
		assertThat(dtoResult.getCep()).isEqualTo(dto.getCep());
		assertThat(dtoResult.getBairro()).isEqualTo(dto.getBairro());
		assertThat(dtoResult.getCidade()).isEqualTo(dto.getCidade());
		assertThat(dtoResult.getEstado()).isEqualTo(dto.getEstado());
	}

	@Test
	void cadastrarLogradouroNullTest() throws JsonProcessingException, Exception {
		EnderecoDTO endereco = this.createValidEndereco();
		endereco.setLogradouro(null);

		ResultActions response = mockMvc.perform(
				post("/enderecos").content(objectMapper.writeValueAsString(endereco)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ValidationError error = objectMapper.readValue(objStr, ValidationError.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(error.getError()).isEqualTo("Erro de Validação");
	}

	@Test
	void cadastrarNumeroNullTest() throws JsonProcessingException, Exception {
		EnderecoDTO endereco = this.createValidEndereco();
		endereco.setNumero(null);

		ResultActions response = mockMvc.perform(
				post("/enderecos").content(objectMapper.writeValueAsString(endereco)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ValidationError error = objectMapper.readValue(objStr, ValidationError.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(error.getError()).isEqualTo("Erro de Validação");
	}

	@Test
	void cadastrarComplementoNullTest() throws JsonProcessingException, Exception {
		EnderecoDTO endereco = this.createValidEndereco();
		endereco.setComplemento(null);

		ResultActions response = mockMvc.perform(
				post("/enderecos").content(objectMapper.writeValueAsString(endereco)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ValidationError error = objectMapper.readValue(objStr, ValidationError.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(error.getError()).isEqualTo("Erro de Validação");
	}

	@Test
	void cadastrarCepNullTest() throws JsonProcessingException, Exception {
		EnderecoDTO endereco = this.createValidEndereco();
		endereco.setCep(null);

		ResultActions response = mockMvc.perform(
				post("/enderecos").content(objectMapper.writeValueAsString(endereco)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ValidationError error = objectMapper.readValue(objStr, ValidationError.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(error.getError()).isEqualTo("Erro de Validação");
	}

	@Test
	void cadastrarBairroNullTest() throws JsonProcessingException, Exception {
		EnderecoDTO endereco = this.createValidEndereco();
		endereco.setBairro(null);

		ResultActions response = mockMvc.perform(
				post("/enderecos").content(objectMapper.writeValueAsString(endereco)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ValidationError error = objectMapper.readValue(objStr, ValidationError.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(error.getError()).isEqualTo("Erro de Validação");
	}

	@Test
	void cadastrarCidadeNullTest() throws JsonProcessingException, Exception {
		EnderecoDTO endereco = this.createValidEndereco();
		endereco.setCidade(null);

		ResultActions response = mockMvc.perform(
				post("/enderecos").content(objectMapper.writeValueAsString(endereco)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ValidationError error = objectMapper.readValue(objStr, ValidationError.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(error.getError()).isEqualTo("Erro de Validação");
	}

	@Test
	void cadastrarEstadoNullTest() throws JsonProcessingException, Exception {
		EnderecoDTO endereco = this.createValidEndereco();
		endereco.setEstado(null);

		ResultActions response = mockMvc.perform(
				post("/enderecos").content(objectMapper.writeValueAsString(endereco)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		ValidationError error = objectMapper.readValue(objStr, ValidationError.class);

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertThat(error.getError()).isEqualTo("Erro de Validação");
	}

	@Test
	void alterarTest() throws Exception {
		EnderecoDTO dto = this.createValidEndereco();

		int id = 1;

		ResultActions response = mockMvc.perform(
				put("/enderecos/" + id).content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

		MvcResult result = response.andReturn();

		String resultStr = result.getResponse().getContentAsString();

		EnderecoDTO updated = objectMapper.readValue(resultStr, EnderecoDTO.class);

		assertThat(updated.getEnderecoId()).isEqualTo(id);
		assertThat(updated.getLogradouro()).isEqualTo(dto.getLogradouro());
		assertThat(updated.getNumero()).isEqualTo(dto.getNumero());
		assertThat(updated.getComplemento()).isEqualTo(dto.getComplemento());
		assertThat(updated.getCep()).isEqualTo(dto.getCep());
		assertThat(updated.getBairro()).isEqualTo(dto.getBairro());
		assertThat(updated.getCidade()).isEqualTo(dto.getCidade());
		assertThat(updated.getEstado()).isEqualTo(dto.getEstado());
		;

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

	}

	@Test
	void deleteTest() throws Exception {
		int id = 1;

		ResultActions response = mockMvc.perform(delete("/enderecos/" + id).contentType("application/json"));

		MvcResult result = response.andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	private EnderecoDTO createValidEndereco() {
		return EnderecoDTO.builder().logradouro("Rua do Anderson").numero("55").complemento("ap 12").cep("91234-567")
				.cidade("Cidade do Anderson").bairro("Bairro dos boladinhos").estado("estado laico").build();

	}
}