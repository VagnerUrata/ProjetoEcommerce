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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.brq.projetoecommerce.domain.VendaEntity;
import br.com.brq.projetoecommerce.exceptions.ObjetoNaoEncontradoException;
import br.com.brq.projetoecommerce.repositories.VendaRepository;
import br.com.brq.projetoecommerce.utils.MockUtil;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class VendaServiceTest {
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private MockUtil mockUtil;

	@MockBean
	private VendaRepository vendaRepository;

	@Test	
	void listaTodasVendasTest() {

		List<VendaEntity> listMock = new ArrayList<>();

		VendaEntity venda = this.mockUtil.vendaMock();
		listMock.add(venda);

		when(vendaRepository.findAll()).thenReturn(listMock);
		
		List<VendaEntity> list = this.vendaService.listaTodasVendas();
		
		assertThat(list.size() >= 0).isTrue();
		verify(vendaRepository, times(1)).findAll();
	}
	
	@Test
	void salvarTest() {

		int id = 1;

		VendaEntity venda = this.mockUtil.vendaMock();

		when(vendaRepository.save(venda)).thenReturn(venda);

		VendaEntity resultVenda = vendaRepository.save(venda);
		resultVenda.setIdVenda(id);
		
		assertThat(venda.getIdVenda()).isEqualTo(resultVenda.getIdVenda());		
		verify(vendaRepository, times(id)).save(venda);

	}

	@Test
	void buscarVendaIdSucessoTest() {
		
		int id = 1;
		VendaEntity venda = this.mockUtil.vendaMock();
		Optional<VendaEntity> optional = Optional.of(venda);

		when(vendaRepository.findById(id)).thenReturn(optional);

		VendaEntity search = this.vendaService.buscarVendaId(id);

		assertThat(search.getIdVenda()).isEqualTo(venda.getIdVenda());		
	}

	@Test
	void buscarVendaIdFalhaTest() {
		
		int id = 1;

		Optional<VendaEntity> optional = Optional.empty();
		when(vendaRepository.findById(id)).thenReturn(optional);

		assertThrows(ObjetoNaoEncontradoException.class, () -> this.vendaService.buscarVendaId(id));
	}	

	@Test
	void alterarSucessoTest() {
		
		int idVenda = 1;
		VendaEntity venda = this.mockUtil.vendaMock();
		venda.setIdVenda(idVenda);

		when(vendaRepository.findById(idVenda)).thenReturn(Optional.of(venda));
		when(vendaRepository.save(venda)).thenReturn(venda);
		
		VendaEntity updated = this.vendaService.alterar(idVenda, venda);
		
		assertThat(updated.getIdVenda()).isEqualTo(venda.getIdVenda());	
		verify(vendaRepository, times(1)).save(venda);
	}

	@Test
	void alterarFalhaTest() {
		
		int idVenda = 1;
		VendaEntity venda = this.mockUtil.vendaMock();
		venda.setIdVenda(idVenda);

		when(vendaRepository.findById(idVenda)).thenReturn(Optional.empty());

		assertThrows(ObjetoNaoEncontradoException.class, () -> this.vendaService.alterar(idVenda, venda));
	}

	@Test
	void deletarTest() {
		
		int idVenda = 1;

		assertDoesNotThrow(() -> vendaService.deletar(idVenda));
		verify(vendaRepository, times(1)).deleteById(idVenda);
	}	
}