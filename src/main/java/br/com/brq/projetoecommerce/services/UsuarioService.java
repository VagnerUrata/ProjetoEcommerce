package br.com.brq.projetoecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brq.projetoecommerce.domain.EnderecoEntity;
import br.com.brq.projetoecommerce.domain.UsuarioEntity;
import br.com.brq.projetoecommerce.dto.EnderecoDTO;
import br.com.brq.projetoecommerce.dto.UsuarioDTO;
import br.com.brq.projetoecommerce.exceptions.EnderecoNaoEncontradoException;
import br.com.brq.projetoecommerce.exceptions.UsuarioNaoEncontradoException;
import br.com.brq.projetoecommerce.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EnderecoService enderecoService;

	public UsuarioEntity salvar(UsuarioEntity usuario) { 

		if (usuario.getEnderecos() == null) {
			
			throw new UsuarioNaoEncontradoException("Endereço não encontrado");
		} else {
			for (var i=0; i < usuario.getEnderecos().size(); i++) {
				EnderecoEntity endereco = usuario.getEnderecos().get(i);
				enderecoService.salvar(endereco);
			}
		}
		  
			return usuarioRepository.save(usuario); 
	}

	public List<UsuarioEntity> listaTodosUsuarios() {
		return usuarioRepository.findAll();
	}

	public UsuarioEntity buscarUsuarioId(Integer id) {
		Optional<UsuarioEntity> usuario = usuarioRepository.findById(id);

		return usuario.orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario não encontrado"));
	}

	public UsuarioEntity alterar(int usuarioId, UsuarioDTO alteracao) {
		Optional<UsuarioEntity> usuario = usuarioRepository.findById(usuarioId);

		if (usuario.isPresent()) {
			UsuarioEntity usuarioExistente = usuario.get();

			usuarioExistente.setNome(alteracao.getNome());
			usuarioExistente.setCpf(alteracao.getCpf());
			usuarioExistente.setEmail(alteracao.getEmail());
			usuarioExistente.setCelular(alteracao.getCelular());
			usuarioExistente.setTelefone(alteracao.getTelefone());
			
      
			if (!alteracao.getEnderecos().isEmpty()) {
				int enderecoId = alteracao.getEnderecos().get(0).getEnderecoId();
				List<EnderecoEntity> enderecos = alteracao.getEnderecos().stream().map(EnderecoDTO::toEntity)
						.collect(Collectors.toList());

				this.enderecoService.alterar(enderecoId, enderecos.get(0));
				return this.usuarioRepository.save(usuarioExistente);
			}else {
				throw new EnderecoNaoEncontradoException("Endereço Vazio");
			}
			
		} else {

			throw new UsuarioNaoEncontradoException("Usuario(a) nao encontrado(a)");
		}
	}

	public void excluir(Integer id) {
		usuarioRepository.deleteById(id);
	}

}