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

import br.com.brq.projetoecommerce.domain.CategoriaEntity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CategoriaRepositoryTest {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void findByIdExistsTest() {

		CategoriaEntity categoria = this.createValidCategoria();

		this.testEntityManager.persist(categoria);

		Optional<CategoriaEntity> response = this.categoriaRepository.findById(1);

		assertThat(response).isNotNull();
	}

	@Test
	void findByIdNotExistsTest() {

		Optional<CategoriaEntity> response = this.categoriaRepository.findById(1);

		assertThat(response).isEmpty();
	}

	@Test
	void findAllTest() {

		CategoriaEntity categoria = this.createValidCategoria();

		this.testEntityManager.persist(categoria);

		List<CategoriaEntity> list = this.categoriaRepository.findAll();

		assertThat(list.size()).isEqualTo(1);
	}

	@Test
	void saveTest() {

		CategoriaEntity categoria = this.createValidCategoria();

		CategoriaEntity saved = this.categoriaRepository.save(categoria);

		assertThat(saved.getIdCategoria()).isNotNull();
		assertThat(saved.getNomeCategoria()).isEqualTo(categoria.getNomeCategoria());

	}

	@Test
	void deleteByIdTest() {

		CategoriaEntity categoria = this.createValidCategoria();

		CategoriaEntity saved = testEntityManager.persist(categoria);

		this.categoriaRepository.deleteById(saved.getIdCategoria());

		Optional<CategoriaEntity> search = this.categoriaRepository.findById(saved.getIdCategoria());

		assertThat(search).isEmpty();
	}

	private CategoriaEntity createValidCategoria() {
		return CategoriaEntity.builder().nomeCategoria("Eletronico").build();
	}
}
