package br.com.brq.projetoecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brq.projetoecommerce.domain.ImagemEntity;
import br.com.brq.projetoecommerce.exceptions.ObjetoNaoEncontradoException;
import br.com.brq.projetoecommerce.repositories.ImagemRepository;

@Service
public class ImagemService {

	@Autowired
	private ImagemRepository imagemRepository;

	public List<ImagemEntity> listaTodasImagens() {
		return imagemRepository.findAll();
	}

	public ImagemEntity salvar(ImagemEntity imagem) {
		return imagemRepository.save(imagem);
	}

	public ImagemEntity buscarImagemId(Integer id) {
		Optional<ImagemEntity> imagem = imagemRepository.findById(id);
		return imagem
				.orElseThrow( () -> new ObjetoNaoEncontradoException("Imagem não encontrada.") );
	}

	public ImagemEntity alterar(Integer id, ImagemEntity imagemAlterada) {
		var imagemEntity = buscarImagemId(id);
		imagemEntity.setImagemProduto(imagemAlterada.getImagemProduto());
		return salvar(imagemEntity);
	}

	public void deletar(Integer id) {
		this.imagemRepository.deleteById(id);
	}
}
