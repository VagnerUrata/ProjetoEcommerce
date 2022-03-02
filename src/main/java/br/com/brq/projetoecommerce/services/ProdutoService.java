package br.com.brq.projetoecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brq.projetoecommerce.domain.ProdutoEntity;
import br.com.brq.projetoecommerce.exceptions.ObjetoNaoEncontradoException;
import br.com.brq.projetoecommerce.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public List<ProdutoEntity> listaTodosProdutos() {
		return produtoRepository.findAll();
	}

	public ProdutoEntity salvar(ProdutoEntity produto) {
		return produtoRepository.save(produto);
	}

	public ProdutoEntity buscarProdutoId(Integer id) {
		Optional<ProdutoEntity> produto = produtoRepository.findById(id);
		return produto.orElseThrow( () -> new ObjetoNaoEncontradoException("Produto não encontrado."));
	}

	public ProdutoEntity alterar(Integer id, ProdutoEntity produtoAlterado){
		var produtoEntity = buscarProdutoId(id);
		produtoEntity.setNome(produtoAlterado.getNome());
		produtoEntity.setCategorias(produtoAlterado.getCategorias());
		produtoEntity.setImagens(produtoAlterado.getImagens());
		return salvar(produtoEntity);
	}


	public void deletar(Integer id) {
		this.produtoRepository.deleteById(id);
	}
}