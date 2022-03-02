package br.com.brq.projetoecommerce.controllers;



import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;



import java.nio.charset.StandardCharsets;
import java.util.List;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



import br.com.brq.projetoecommerce.domain.UsuarioEntity;
import br.com.brq.projetoecommerce.dto.EnderecoDTO;
import br.com.brq.projetoecommerce.dto.UsuarioDTO;
import br.com.brq.projetoecommerce.exceptions.ValidationError;
import br.com.brq.projetoecommerce.services.UsuarioService;



@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {



@Autowired
private MockMvc mockMvc;



@MockBean
private UsuarioService serviceMock;



private ObjectMapper objectMapper = new ObjectMapper();



@BeforeEach
void beforeEach() {
this.objectMapper.findAndRegisterModules();
}



@Test
void buscarIdTest() throws Exception {



UsuarioEntity usuarioEncontrado = this.createValidUsuarioEntity();
when(serviceMock.buscarUsuarioId(1)).thenReturn(usuarioEncontrado);

ResultActions response = mockMvc.perform(get("/usuarios/buscar/1").contentType("application/json"));



MvcResult result = response.andReturn();



String resultStr = result.getResponse().getContentAsString();



UsuarioDTO usuarioDTO = objectMapper.readValue(resultStr, UsuarioDTO.class);
System.out.println(usuarioDTO);



assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
assertThat(usuarioDTO.getUsuarioId()).isEqualTo(1);



}



@Test
void buscarTodosUsuariosTest() throws Exception {



ResultActions response = mockMvc.perform(get("/usuarios").contentType("application/json"));
MvcResult result = response.andReturn();



String resultStr = result.getResponse().getContentAsString();



UsuarioDTO[] list = objectMapper.readValue(resultStr, UsuarioDTO[].class);



assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
assertThat(list.length >= 0).isTrue();
}



@Test
void cadastrarTest() throws JsonProcessingException, Exception {
when(serviceMock.salvar(any(UsuarioEntity.class))).thenReturn(this.createValidUsuarioEntity());
UsuarioDTO dto = this.createValidUsuario();

ResultActions response = mockMvc.perform(
post("/usuarios").content(objectMapper.writeValueAsString(dto)).contentType("application/json"));

MvcResult result = response.andReturn();

String objStr = result.getResponse().getContentAsString();

UsuarioDTO dtoResult = objectMapper.readValue(objStr, UsuarioDTO.class);

assertThat(dtoResult.getUsuarioId() > 0).isTrue();
assertThat(dtoResult.getNome()).isEqualTo(dto.getNome());
assertThat(dtoResult.getCelular()).isEqualTo(dto.getCelular());
assertThat(dtoResult.getTelefone()).isEqualTo(dto.getTelefone());
assertThat(dtoResult.getCpf()).isEqualTo(dto.getCpf());
assertThat(dtoResult.getEmail()).isEqualTo(dto.getEmail());
}

@Test
void cadastrarNomeNullTest() throws JsonProcessingException, Exception {
UsuarioDTO usuario = this.createValidUsuario();
usuario.setNome(null);

ResultActions response = mockMvc.perform(
post("/usuarios").content(objectMapper.writeValueAsString(usuario)).contentType("application/json"));

MvcResult result = response.andReturn();




String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);




ValidationError error = objectMapper.readValue(objStr, ValidationError.class);



assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
assertThat(error.getError()).isEqualTo("Erro de Validação");
}

@Test
void cadastrarCpfNullTest() throws JsonProcessingException, Exception {
UsuarioDTO usuario = this.createValidUsuario();
usuario.setCpf(null);

ResultActions response = mockMvc.perform(
post("/usuarios").content(objectMapper.writeValueAsString(usuario)).contentType("application/json"));

MvcResult result = response.andReturn();




String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);




ValidationError error = objectMapper.readValue(objStr, ValidationError.class);



assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
assertThat(error.getError()).isEqualTo("Erro de Validação");
}

@Test
void cadastrarDataDeNascimentoNullTest() throws JsonProcessingException, Exception {
UsuarioDTO usuario = this.createValidUsuario();
usuario.setDataDeNascimento(null);

ResultActions response = mockMvc.perform(
post("/usuarios").content(objectMapper.writeValueAsString(usuario)).contentType("application/json"));

MvcResult result = response.andReturn();




String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);




ValidationError error = objectMapper.readValue(objStr, ValidationError.class);



assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
assertThat(error.getError()).isEqualTo("Erro de Validação");
}



@Test
void cadastrarCelularNullTest() throws JsonProcessingException, Exception {
UsuarioDTO usuario = this.createValidUsuario();
usuario.setCelular(null);

ResultActions response = mockMvc.perform(
post("/usuarios").content(objectMapper.writeValueAsString(usuario)).contentType("application/json"));

MvcResult result = response.andReturn();




String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);




ValidationError error = objectMapper.readValue(objStr, ValidationError.class);



assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
assertThat(error.getError()).isEqualTo("Erro de Validação");
}



@Test
void cadastrarTelefoneNullTest() throws JsonProcessingException, Exception {
UsuarioDTO usuario = this.createValidUsuario();
usuario.setTelefone(null);

ResultActions response = mockMvc.perform(
post("/usuarios").content(objectMapper.writeValueAsString(usuario)).contentType("application/json"));

MvcResult result = response.andReturn();




String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);




ValidationError error = objectMapper.readValue(objStr, ValidationError.class);



assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
assertThat(error.getError()).isEqualTo("Erro de Validação");
}

@Test
void cadastrarEmailNullTest() throws JsonProcessingException, Exception {
UsuarioDTO usuario = this.createValidUsuario();
usuario.setEmail(null);

ResultActions response = mockMvc.perform(
post("/usuarios").content(objectMapper.writeValueAsString(usuario)).contentType("application/json"));

MvcResult result = response.andReturn();




String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);




ValidationError error = objectMapper.readValue(objStr, ValidationError.class);



assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
assertThat(error.getError()).isEqualTo("Erro de Validação");
}

@Test
void cadastrarEnderecoNullTest() throws JsonProcessingException, Exception {
UsuarioDTO usuario = this.createValidUsuario();
usuario.setEnderecos(null);

ResultActions response = mockMvc.perform(
post("/usuarios").content(objectMapper.writeValueAsString(usuario)).contentType("application/json"));

MvcResult result = response.andReturn();




String objStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);




ValidationError error = objectMapper.readValue(objStr, ValidationError.class);



assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
assertThat(error.getError()).isEqualTo("Erro de Validação");
}


@Test
void alterarTest() throws Exception {
when(serviceMock.alterar(anyInt(), any(UsuarioDTO.class))).thenReturn(this.createValidUsuarioEntity());
UsuarioDTO dto = this.createValidUsuario();



int id = 1;

ResultActions response = mockMvc.perform(
put("/usuarios/" + id).content(objectMapper.writeValueAsString(dto)).contentType("application/json"));



MvcResult result = response.andReturn();



String resultStr = result.getResponse().getContentAsString();



UsuarioDTO updated = objectMapper.readValue(resultStr, UsuarioDTO.class);



assertThat(updated.getUsuarioId()).isEqualTo(id);
assertThat(updated.getNome()).isEqualTo(dto.getNome());
assertThat(updated.getCpf()).isEqualTo(dto.getCpf());
assertThat(updated.getCelular()).isEqualTo(dto.getCelular());
assertThat(updated.getTelefone()).isEqualTo(dto.getTelefone());
assertThat(updated.getDataDeNascimento()).isEqualTo(dto.getDataDeNascimento());
assertThat(updated.getEmail()).isEqualTo(dto.getEmail());



assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());



}



@Test
void deleteTest() throws Exception {



// PREPARO DOS DADOS E DOS MOCKS
int id = 1;
ResultActions response = mockMvc.perform(delete("/usuarios/" + id).contentType("application/json"));
MvcResult result = response.andReturn();



assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
}



private UsuarioDTO createValidUsuario() {
return UsuarioDTO.builder().usuarioId(12345)
.nome("Karina").cpf("123.123.123-55")
.celular("91234-5678").telefone("1234-5678")
.email("boladinho@hotmail.com")
.dataDeNascimento("1998, 05, 18")
.enderecos(List.of(this.createValidEndereco())).build();
}



private EnderecoDTO createValidEndereco() {
return EnderecoDTO.builder().enderecoId(1).logradouro("Rua do Anderson").numero("55").complemento("ap 12")
.cep("91234-567").cidade("Cidade do Anderson").bairro("Bairro dos boladinhos").estado("estado laico")
.build();



}



private UsuarioEntity createValidUsuarioEntity() {
return UsuarioEntity.builder()
.usuarioId(1)
.nome("Karina").cpf("123.123.123-55")
.dataDeNascimento("1998, 05, 18").celular("91234-5678").telefone("1234-5678")
.email("boladinho@hotmail.com").build();
}



}