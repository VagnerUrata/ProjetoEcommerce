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

import br.com.brq.projetoecommerce.domain.ImagemEntity;
import br.com.brq.projetoecommerce.exceptions.ObjetoNaoEncontradoException;
import br.com.brq.projetoecommerce.repositories.ImagemRepository;
import br.com.brq.projetoecommerce.utils.MockUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ImagemServiceTest {
	
	@Autowired
	private ImagemService imagemService;
	
	@Autowired
	private MockUtil mockUtil;

	@MockBean
	private ImagemRepository imagemRepository;

	@Test	
	void listaTodasImagensTest() {

		List<ImagemEntity> listMock = new ArrayList<>();

		ImagemEntity imagem = this.mockUtil.imagemMock();
		listMock.add(imagem);

		when(imagemRepository.findAll()).thenReturn(listMock);
		
		List<ImagemEntity> list = this.imagemService.listaTodasImagens();
		
		assertThat(list.size() >= 0).isTrue();
		verify(imagemRepository, times(1)).findAll();
	}
	
	@Test
	void salvarTest() {

		int id = 1;

		ImagemEntity imagem = this.mockUtil.imagemMock();

		when(imagemRepository.save(imagem)).thenReturn(imagem);

		ImagemEntity resultImagem = imagemRepository.save(imagem);
		resultImagem.setIdImagem(id);
		
		assertThat(imagem.getImagemProduto()).isEqualTo(resultImagem.getImagemProduto());		
		assertThat(resultImagem.getIdImagem() >= 0).isTrue();
		verify(imagemRepository, times(id)).save(imagem);

	}

	@Test
	void buscarImagemIdSucessoTest() {
		
		int id = 1;
		ImagemEntity imagem = this.mockUtil.imagemMock();
		Optional<ImagemEntity> optional = Optional.of(imagem);

		when(imagemRepository.findById(id)).thenReturn(optional);

		ImagemEntity search = this.imagemService.buscarImagemId(id);

		assertThat(search.getIdImagem()).isEqualTo(imagem.getIdImagem());		
	}

	@Test
	void buscarImagemIdFalhaTest() {
		
		int id = 1;

		Optional<ImagemEntity> optional = Optional.empty();
		when(imagemRepository.findById(id)).thenReturn(optional);

		assertThrows(ObjetoNaoEncontradoException.class, () -> this.imagemService.buscarImagemId(id));
	}	

	@Test
	void alterarSucessoTest() {
		
		int idImagem = 1;
		ImagemEntity imagem = this.mockUtil.imagemMock();
		imagem.setIdImagem(idImagem);

		when(imagemRepository.findById(idImagem)).thenReturn(Optional.of(imagem));
		when(imagemRepository.save(imagem)).thenReturn(imagem);
		
		ImagemEntity updated = this.imagemService.alterar(idImagem, imagem);
		
		assertThat(updated.getIdImagem()).isEqualTo(imagem.getIdImagem());
		assertThat(updated.getImagemProduto()).isEqualTo(imagem.getImagemProduto());		
		verify(imagemRepository, times(1)).save(imagem);
	}

	@Test
	void alterarFalhaTest() {
		
		int idImagem = 1;
		ImagemEntity imagem = this.mockUtil.imagemMock();
		imagem.setIdImagem(idImagem);

		when(imagemRepository.findById(idImagem)).thenReturn(Optional.empty());

		assertThrows(ObjetoNaoEncontradoException.class, () -> this.imagemService.alterar(idImagem, imagem));
	}

	@Test
	void deletarTest() {
		
		int idImagem = 1;

		assertDoesNotThrow(() -> imagemService.deletar(idImagem));
		verify(imagemRepository, times(1)).deleteById(idImagem);
	}	
}