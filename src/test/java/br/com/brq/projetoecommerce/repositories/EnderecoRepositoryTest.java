package br.com.brq.projetoecommerce.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brq.projetoecommerce.domain.EnderecoEntity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class EnderecoRepositoryTest {

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void findByIdExistTest() {

		EnderecoEntity endereco = this.createValidEndereco();

		this.testEntityManager.persist(endereco);

		Optional<EnderecoEntity> response = this.enderecoRepository.findById(1);

		assertThat(response).isNotNull();
	}

	@Test
	void findByIdNotExistsTest() {

		Optional<EnderecoEntity> response = this.enderecoRepository.findById(1);

		assertThat(response).isEmpty();
	}

	@Test
	void findAllTest() {

		EnderecoEntity endereco = this.createValidEndereco();

		this.testEntityManager.persist(endereco);

		List<EnderecoEntity> list = this.enderecoRepository.findAll();

		assertThat(list.size()).isEqualTo(1);
	}

	@Test
	void saveTest() {

		EnderecoEntity endereco = this.createValidEndereco();

		EnderecoEntity saved = this.enderecoRepository.save(endereco);

		assertThat(saved.getEnderecoId()).isNotNull();
		assertThat(saved.getLogradouro()).isEqualTo(endereco.getLogradouro());
		assertThat(saved.getCep()).isEqualTo(endereco.getCep());
		assertThat(saved.getNumero()).isEqualTo(endereco.getNumero());
		assertThat(saved.getBairro()).isEqualTo(endereco.getBairro());
		assertThat(saved.getCidade()).isEqualTo(endereco.getCidade());
		assertThat(saved.getEstado()).isEqualTo(endereco.getEstado());
		assertThat(saved.getComplemento()).isEqualTo(endereco.getComplemento());
	}

	private EnderecoEntity createValidEndereco() {
		return EnderecoEntity.builder().logradouro("Rua do Anderson").numero("55")
				.complemento("ap 12").cep("91234-567").cidade("Cidade do Anderson")
				.bairro("Bairro dos boladinhos").estado("estado laico").build();
	}
}