package br.com.brq.projetoecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brq.projetoecommerce.domain.EnderecoEntity;
import br.com.brq.projetoecommerce.exceptions.EnderecoNaoEncontradoException;
import br.com.brq.projetoecommerce.repositories.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;

	public List<EnderecoEntity> listaTodosEnderecos() {
		return enderecoRepository.findAll();
	}

	public EnderecoEntity salvar(EnderecoEntity endereco) {
		return enderecoRepository.save(endereco); 
	}

	public EnderecoEntity buscarEnderecoId(Integer id) {
		Optional<EnderecoEntity> endereco = enderecoRepository.findById(id);
		return endereco.orElseThrow(() -> new EnderecoNaoEncontradoException("Endereço não encontrado"));
	}
	

	public EnderecoEntity alterar(int enderecoId, EnderecoEntity entity) {
		Optional<EnderecoEntity> endereco = enderecoRepository.findById(enderecoId);

		if (endereco.isPresent()) {
			EnderecoEntity enderecoExistente = endereco.get();

			enderecoExistente.setLogradouro(entity.getLogradouro());
			enderecoExistente.setNumero(entity.getNumero());
			enderecoExistente.setComplemento(entity.getComplemento());
			enderecoExistente.setBairro(entity.getBairro());
			enderecoExistente.setCep(entity.getCep());
			enderecoExistente.setCidade(entity.getCidade());
			enderecoExistente.setEstado(entity.getEstado());

			return this.enderecoRepository.save(enderecoExistente);
		} else {
			throw new EnderecoNaoEncontradoException("Endereço não encontrado");
		}
	}

	public void excluir(Integer id) {
		enderecoRepository.deleteById(id);
	}
}