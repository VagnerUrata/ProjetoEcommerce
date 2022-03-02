package br.com.brq.projetoecommerce.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.brq.projetoecommerce.domain.EnderecoEntity;
import br.com.brq.projetoecommerce.exceptions.EnderecoNaoEncontradoException;
import br.com.brq.projetoecommerce.repositories.EnderecoRepository;

@SpringBootTest
@AutoConfigureMockMvc
class EnderecoServiceTest {

	@Autowired
	private EnderecoService enderecoService;

	@MockBean
	private EnderecoRepository enderecoRepository;

	@Test
	void listaTodosEnderecosTest() {
 
		List<EnderecoEntity> listMock = new ArrayList<>();

		EnderecoEntity professor = this.createValidEndereco();
		listMock.add(professor);

		when(enderecoRepository.findAll()).thenReturn(listMock);

		List<EnderecoEntity> list = this.enderecoService.listaTodosEnderecos();

		assertThat(list.size() >= 0).isTrue();
		verify(enderecoRepository, times(1)).findAll();
	}

	@Test
	void salvarTest() {

		int id = 1;

		EnderecoEntity endereco = createValidEndereco();

		when(enderecoRepository.save(endereco)).thenReturn(endereco);

		EnderecoEntity resultEndereco = enderecoRepository.save(endereco);
		resultEndereco.setEnderecoId(id);

		assertThat(endereco.getLogradouro()).isEqualTo(resultEndereco.getLogradouro());
		assertThat(endereco.getNumero()).isEqualTo(resultEndereco.getNumero());
		assertThat(endereco.getComplemento()).isEqualTo(resultEndereco.getComplemento());
		assertThat(endereco.getCep()).isEqualTo(resultEndereco.getCep());
		assertThat(endereco.getBairro()).isEqualTo(resultEndereco.getBairro());
		assertThat(endereco.getCidade()).isEqualTo(resultEndereco.getCidade());
		assertThat(endereco.getEstado()).isEqualTo(resultEndereco.getEstado());
		assertThat(resultEndereco.getEnderecoId() >= 0).isTrue();
		verify(enderecoRepository, times(id)).save(endereco);

	}

	@Test
	void buscarEnderecoIdSucessoTest() {

		int id = 1;
		EnderecoEntity endereco = this.createValidEndereco();
		Optional<EnderecoEntity> optional = Optional.of(endereco);

		when(enderecoRepository.findById(id)).thenReturn(optional);

		EnderecoEntity search = this.enderecoService.buscarEnderecoId(id);

		assertThat(search.getLogradouro()).isEqualTo(endereco.getLogradouro());
		assertThat(search.getNumero()).isEqualTo(endereco.getNumero());
		assertThat(search.getComplemento()).isEqualTo(endereco.getComplemento());
		assertThat(search.getCep()).isEqualTo(endereco.getCep());
		assertThat(search.getBairro()).isEqualTo(endereco.getBairro());
		assertThat(search.getCidade()).isEqualTo(endereco.getCidade());
		assertThat(search.getEstado()).isEqualTo(endereco.getEstado());
	}

	@Test
	void buscarEnderecoIdFalhaTest() {

		int id = 1;

		Optional<EnderecoEntity> optional = Optional.empty();
		when(enderecoRepository.findById(id)).thenReturn(optional);

		assertThrows(EnderecoNaoEncontradoException.class, () -> this.enderecoService.buscarEnderecoId(id));
	}

	@Test
	void alterarSucessoTest() {

		int enderecoId = 1;
		EnderecoEntity endereco = this.createValidEndereco();
		endereco.setEnderecoId(enderecoId);

		when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.of(endereco));
		when(enderecoRepository.save(endereco)).thenReturn(endereco);

		EnderecoEntity updated = this.enderecoService.alterar(enderecoId, endereco);

		assertThat(updated.getEnderecoId()).isEqualTo(endereco.getEnderecoId());
		assertThat(updated.getNumero()).isEqualTo(endereco.getNumero());
		assertThat(updated.getComplemento()).isEqualTo(endereco.getComplemento());
		assertThat(updated.getCep()).isEqualTo(endereco.getCep());
		assertThat(updated.getBairro()).isEqualTo(endereco.getBairro());
		assertThat(updated.getCidade()).isEqualTo(endereco.getCidade());
		assertThat(updated.getEstado()).isEqualTo(endereco.getEstado());
	}

	@Test
	void alterarFalhaTest() {

		int enderecoId = 1;
		EnderecoEntity endereco = this.createValidEndereco();
		endereco.setEnderecoId(enderecoId);

		when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> this.enderecoService.alterar(enderecoId, endereco));
	}

	@Test
	void deletarTest() {

		int enderecoId = 1;

		assertDoesNotThrow(() -> enderecoService.excluir(enderecoId));
		verify(enderecoRepository, times(1)).deleteById(enderecoId);
	}

	private EnderecoEntity createValidEndereco() {
		return EnderecoEntity.builder().logradouro("Rua do Anderson").numero("55").complemento("ap 12").cep("91234-567")
				.cidade("Cidade do Anderson").bairro("Bairro dos boladinhos").estado("estado laico").build();
	}

}