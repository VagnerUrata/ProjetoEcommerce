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

import br.com.brq.projetoecommerce.domain.UsuarioEntity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void findByIdExistTest() {

		UsuarioEntity usuario = this.createValidUsuario();

		this.testEntityManager.persist(usuario);

		Optional<UsuarioEntity> response = this.usuarioRepository.findById(1);

		assertThat(response).isNotNull();
	}

	@Test
	void findByIdNotExistsTest() {

		Optional<UsuarioEntity> response = this.usuarioRepository.findById(1);

		assertThat(response).isEmpty();
	}

	@Test
	void findAllTest() {

		UsuarioEntity usuario = this.createValidUsuario();

		this.testEntityManager.persist(usuario);

		List<UsuarioEntity> list = this.usuarioRepository.findAll();

		assertThat(list.size()).isEqualTo(1);
	}

	@Test
	void saveTest() {

		UsuarioEntity usuario = this.createValidUsuario();

		UsuarioEntity saved = this.usuarioRepository.save(usuario);

		assertThat(saved.getUsuarioId()).isNotNull();
		assertThat(saved.getNome()).isEqualTo(usuario.getNome());
		assertThat(saved.getCpf()).isEqualTo(usuario.getCpf());
		assertThat(saved.getCelular()).isEqualTo(usuario.getCelular());
		assertThat(saved.getEmail()).isEqualTo(usuario.getEmail());
		assertThat(saved.getTelefone()).isEqualTo(usuario.getTelefone());
		assertThat(saved.getDataDeNascimento()).isEqualTo(usuario.getDataDeNascimento());
	}

	private UsuarioEntity createValidUsuario() {
		return UsuarioEntity.builder().nome("Anderson").cpf("123.123.123-55")
				.dataDeNascimento("1998, 05, 18").celular("91234-5678").telefone("1234-5678")
				.email("boladinho@hotmail.com").build();
	}
}