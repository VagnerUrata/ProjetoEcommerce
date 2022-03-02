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

import br.com.brq.projetoecommerce.domain.ProdutoEntity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProdutoRepositoryTest {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void findByIdExistsTest() {

		ProdutoEntity produto = this.createValidProduto();

		this.testEntityManager.persist(produto);

		Optional<ProdutoEntity> response = this.produtoRepository.findById(1);

		assertThat(response).isNotNull();
	}

	@Test
	void findByIdNotExistsTest() {

		Optional<ProdutoEntity> response = this.produtoRepository.findById(1);

		assertThat(response).isEmpty();
	}

	@Test
	void findAllTest() {
	
		ProdutoEntity produto = this.createValidProduto();

		this.testEntityManager.persist(produto);

		List<ProdutoEntity> list = this.produtoRepository.findAll();

		assertThat(list.size()).isEqualTo(1);
	}

	@Test
	void saveTest() {
		
		ProdutoEntity produto = this.createValidProduto();

		ProdutoEntity saved = this.produtoRepository.save(produto);

		assertThat(saved.getIdProduto()).isNotNull();
		assertThat(saved.getNome()).isEqualTo(produto.getNome());
		assertThat(saved.getPreco()).isEqualTo(produto.getPreco());
		assertThat(saved.getDescricao()).isEqualTo(produto.getDescricao());
	}

	@Test
	void deleteByIdTest() {

		ProdutoEntity produto = this.createValidProduto();

		ProdutoEntity saved = testEntityManager.persist(produto);

		this.produtoRepository.deleteById(saved.getIdProduto());

		Optional<ProdutoEntity> search = this.produtoRepository.findById(saved.getIdProduto());

		assertThat(search).isEmpty();
	}	

	private ProdutoEntity createValidProduto() {
		return ProdutoEntity.builder().preco(2000).nome("Xiaomi").descricao("Celular").build();
	}

}