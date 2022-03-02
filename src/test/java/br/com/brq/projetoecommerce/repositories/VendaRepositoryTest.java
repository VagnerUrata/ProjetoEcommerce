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

import br.com.brq.projetoecommerce.domain.VendaEntity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class VendaRepositoryTest {
	
	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void findByIdExistsTest() {

		VendaEntity venda = this.createValidVenda();

		this.testEntityManager.persist(venda);

		Optional<VendaEntity> response = this.vendaRepository.findById(1);

		assertThat(response).isNotNull();
	}

	@Test
	void findByIdNotExistsTest() {

		Optional<VendaEntity> response = this.vendaRepository.findById(1);

		assertThat(response).isEmpty();
	}

	@Test
	void findAllTest() {
	
		VendaEntity venda = this.createValidVenda();

		this.testEntityManager.persist(venda);

		List<VendaEntity> list = this.vendaRepository.findAll();

		assertThat(list.size()).isEqualTo(1);
	}

	@Test
	void saveTest() {
		
		VendaEntity venda = this.createValidVenda();

		VendaEntity saved = this.vendaRepository.save(venda);

		assertThat(saved.getIdVenda()).isNotNull();	
	}

	@Test
	void deleteByIdTest() {

		VendaEntity venda = this.createValidVenda();

		VendaEntity saved = testEntityManager.persist(venda);

		this.vendaRepository.deleteById(saved.getIdVenda());

		Optional<VendaEntity> search = this.vendaRepository.findById(saved.getIdVenda());

		assertThat(search).isEmpty();
	}	

	private VendaEntity createValidVenda() {
		return VendaEntity.builder().build();
	}

}