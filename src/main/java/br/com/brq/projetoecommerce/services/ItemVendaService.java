package br.com.brq.projetoecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brq.projetoecommerce.domain.ItemVendaEntity;
import br.com.brq.projetoecommerce.exceptions.ObjetoNaoEncontradoException;
import br.com.brq.projetoecommerce.repositories.ItemVendaRepository;

@Service
public class ItemVendaService {
	
	@Autowired
	private ItemVendaRepository itemVendaRepository;
	
	public List<ItemVendaEntity> listaTodosItemsVenda() {
		return itemVendaRepository.findAll();
	}

	public ItemVendaEntity salvar(ItemVendaEntity itemVenda) {
		return itemVendaRepository.save(itemVenda);
	}

	public ItemVendaEntity buscarItemVendaId(Integer id) {
		Optional<ItemVendaEntity> itemVenda = itemVendaRepository.findById(id);
		return itemVenda
				.orElseThrow( () -> new ObjetoNaoEncontradoException("Item não encontrado.") );
	}

	public ItemVendaEntity alterar(Integer id, ItemVendaEntity itemVendaAlterada) {
		var itemVendaEntity = buscarItemVendaId(id);
		itemVendaEntity.setIdItemVenda(itemVendaAlterada.getIdItemVenda());
		itemVendaEntity.setItemProduto(itemVendaAlterada.getItemProduto());
		itemVendaEntity.setItemQuantidade(itemVendaAlterada.getItemQuantidade());
		return salvar(itemVendaEntity);
	}

	public void deletar(Integer id) {
		this.itemVendaRepository.deleteById(id);
	}
}