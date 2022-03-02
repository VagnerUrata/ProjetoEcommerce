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

import br.com.brq.projetoecommerce.domain.ImagemEntity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ImagemRepositoryTest {

	@Autowired
	private ImagemRepository imagemRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void findByIdExistsTest() {

		ImagemEntity imagem = this.createValidImagem();

		this.testEntityManager.persist(imagem);

		Optional<ImagemEntity> response = this.imagemRepository.findById(1);

		assertThat(response).isNotNull();
	}

	@Test
	void findByIdNotExistsTest() {

		Optional<ImagemEntity> response = this.imagemRepository.findById(1);

		assertThat(response).isEmpty();
	}

	@Test
	void findAllTest() {

		ImagemEntity imagem = this.createValidImagem();

		this.testEntityManager.persist(imagem);

		List<ImagemEntity> list = this.imagemRepository.findAll();

		assertThat(list.size()).isEqualTo(1);
	}

	@Test
	void saveTest() {

		ImagemEntity imagem = this.createValidImagem();

		ImagemEntity saved = this.imagemRepository.save(imagem);

		assertThat(saved.getIdImagem()).isNotNull();
		assertThat(saved.getImagemProduto()).isEqualTo(imagem.getImagemProduto());

	}

	@Test
	void deleteByIdTest() {

		ImagemEntity imagem = this.createValidImagem();

		ImagemEntity saved = testEntityManager.persist(imagem);

		this.imagemRepository.deleteById(saved.getIdImagem());

		Optional<ImagemEntity> search = this.imagemRepository.findById(saved.getIdImagem());

		assertThat(search).isEmpty();
	}

	private ImagemEntity createValidImagem() {
		return ImagemEntity.builder().imagemProduto("kjoidfnoiadf").build();
	}
}
