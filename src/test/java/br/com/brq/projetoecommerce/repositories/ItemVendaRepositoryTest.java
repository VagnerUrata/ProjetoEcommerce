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

import br.com.brq.projetoecommerce.domain.ItemVendaEntity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ItemVendaRepositoryTest {
	
	@Autowired
	private ItemVendaRepository itemVendaRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void findByIdExistsTest() {

		ItemVendaEntity entity = this.testEntityManager.persist(new ItemVendaEntity());

		Optional<ItemVendaEntity> response = this.itemVendaRepository.findById(entity.getIdItemVenda());

		assertThat(response).isNotNull();
	}

	@Test
	void findByIdNotExistsTest() {

		Optional<ItemVendaEntity> response = this.itemVendaRepository.findById(1);

		assertThat(response).isEmpty();
	}

	@Test
	void findAllTest() {		
		
		ItemVendaEntity entity = this.testEntityManager.persist(new ItemVendaEntity());		
		
		this.testEntityManager.persist(entity);

		List<ItemVendaEntity> list = this.itemVendaRepository.findAll();

		assertThat(list.size()).isEqualTo(1);
	}

	@Test
	void saveTest() {

		ItemVendaEntity itemVenda = this.createValidItemVenda();

		ItemVendaEntity saved = this.itemVendaRepository.save(itemVenda);

		assertThat(saved.getIdItemVenda()).isNotNull();
		assertThat(saved.getItemQuantidade() >= 0).isTrue();
		
	}

	@Test
	void deleteByIdTest() {		

		ItemVendaEntity saved = testEntityManager.persist(new ItemVendaEntity());

		this.itemVendaRepository.deleteById(saved.getIdItemVenda());

		Optional<ItemVendaEntity> search = this.itemVendaRepository.findById(saved.getIdItemVenda());

		assertThat(search).isEmpty();		
	}

	private ItemVendaEntity createValidItemVenda() {
		return ItemVendaEntity.builder().idItemVenda(1).itemQuantidade(1).build();
	}
}