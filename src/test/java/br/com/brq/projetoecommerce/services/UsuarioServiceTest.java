package br.com.brq.projetoecommerce.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.brq.projetoecommerce.domain.EnderecoEntity;
import br.com.brq.projetoecommerce.domain.UsuarioEntity;
import br.com.brq.projetoecommerce.dto.EnderecoDTO;
import br.com.brq.projetoecommerce.dto.UsuarioDTO;
import br.com.brq.projetoecommerce.exceptions.EnderecoNaoEncontradoException;
import br.com.brq.projetoecommerce.exceptions.UsuarioNaoEncontradoException;
import br.com.brq.projetoecommerce.repositories.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioServiceTest {

	@Autowired
	private UsuarioService usuarioService;
	
	@MockBean
	private EnderecoService enderecoService;

	@MockBean
	private UsuarioRepository usuarioRepository;

	@Test
	void listaTodosUsuariosTest() {

		List<UsuarioEntity> listMock = new ArrayList<>();

		UsuarioEntity professor = this.createValidUsuario();
		listMock.add(professor);

		when(usuarioRepository.findAll()).thenReturn(listMock);

		List<UsuarioEntity> list = this.usuarioService.listaTodosUsuarios();

		assertThat(list.size() >= 0).isTrue();
		verify(usuarioRepository, times(1)).findAll();
	}

	@Test
	void salvarTest() {

		int id = 1;

		UsuarioEntity usuario = createValidUsuario();

		when(usuarioRepository.save(usuario)).thenReturn(usuario);
		when(enderecoService.salvar(this.createValidEnderecoEntity())).thenReturn(this.createValidEnderecoEntity());
		UsuarioEntity resultUsuario = usuarioRepository.save(usuario);
		resultUsuario.setUsuarioId(id);

		usuarioService.salvar(usuario);
		assertThat(usuario.getNome()).isEqualTo(resultUsuario.getNome());
		assertThat(usuario.getCpf()).isEqualTo(resultUsuario.getCpf());
		assertThat(usuario.getEmail()).isEqualTo(resultUsuario.getEmail());
		assertThat(usuario.getDataDeNascimento()).isEqualTo(resultUsuario.getDataDeNascimento());
		assertThat(usuario.getEmail()).isEqualTo(resultUsuario.getEmail());
		assertThat(usuario.getTelefone()).isEqualTo(resultUsuario.getTelefone());
		assertThat(resultUsuario.getUsuarioId() >= 0).isTrue();
	}
	
	@Test
	void salvarTestException() {
		
		UsuarioEntity usuario = createUsuarioSemEndereco();
		
		assertThrows(UsuarioNaoEncontradoException.class, 
				() -> this.usuarioService.salvar(usuario));
	} 

	@Test
	void buscarUsuarioIdSucessoTest() {

		int id = 1;
		UsuarioEntity usuario = this.createValidUsuario();
		Optional<UsuarioEntity> optional = Optional.of(usuario);

		when(usuarioRepository.findById(id)).thenReturn(optional);

		UsuarioEntity search = this.usuarioService.buscarUsuarioId(id);

		assertThat(search.getNome()).isEqualTo(usuario.getNome());
		assertThat(search.getEmail()).isEqualTo(usuario.getEmail());
		assertThat(search.getDataDeNascimento()).isEqualTo(usuario.getDataDeNascimento());
		assertThat(search.getEmail()).isEqualTo(usuario.getEmail());
		assertThat(search.getTelefone()).isEqualTo(usuario.getTelefone());
	}

	@Test
	void buscarUsuarioIdFalhaTest() {

		int id = 1;

		Optional<UsuarioEntity> optional = Optional.empty();
		when(usuarioRepository.findById(id)).thenReturn(optional);

		assertThrows(UsuarioNaoEncontradoException.class, () -> this.usuarioService.buscarUsuarioId(id));
	}

	@Test
	void alterarUsuarioEEnderecosTest() {
		int usuarioId = 1;

		EnderecoEntity endereco = new EnderecoEntity();
		
		UsuarioEntity usuarioEntity = this.createValidUsuario();
		UsuarioDTO usuario = this.createValidUsuarioDTO();
		usuario.setUsuarioId(usuarioId);
		
		List<EnderecoDTO> enderecos = new ArrayList<>();
		enderecos.add(this.createValidEndereco());
		usuario.setEnderecos(enderecos);

		
		when(enderecoService.buscarEnderecoId(1)).thenReturn(endereco);
 		when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioEntity));
		when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);

		UsuarioEntity updated = this.usuarioService.alterar(usuarioId, usuario);

		assertThat(updated.getNome()).isEqualTo(usuario.getNome());
		assertThat(updated.getEmail()).isEqualTo(usuario.getEmail());
		assertThat(updated.getEmail()).isEqualTo(usuario.getEmail());
		assertThat(updated.getTelefone()).isEqualTo(usuario.getTelefone());
	}
	
	
	
	
	@Test
	void alterarUsuarioEEnderecosFalhaTest() { 
		int usuarioId = 1;

		EnderecoEntity endereco = new EnderecoEntity();
		
		
		UsuarioEntity usuarioEntity = this.createValidUsuario();
		UsuarioDTO usuario = this.createValidUsuarioDTO();
		usuario.setUsuarioId(usuarioId);
		usuario.setEnderecos(null);
		
		List<EnderecoDTO> enderecos = new ArrayList<>();

		usuario.setEnderecos(enderecos);
		
 		when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioEntity));
		when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntity);


		assertThrows(EnderecoNaoEncontradoException.class, () -> this.usuarioService.alterar(usuarioId, usuario));
	}
	
	@Test
	void alterarFalhaTest() {

		int idUsuario = 1;
		UsuarioEntity usuarioEntity = this.createValidUsuario();
		usuarioEntity.setUsuarioId(idUsuario);
		UsuarioDTO usuario = this.createValidUsuarioDTO();

		when(usuarioRepository.findById(3)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> this.usuarioService.alterar(idUsuario, usuario));
	}

	@Test
	void deletarTest() {

		int idUsuario = 1;

		assertDoesNotThrow(() -> usuarioService.excluir(idUsuario));
		verify(usuarioRepository, times(1)).deleteById(idUsuario);
	}

	private UsuarioEntity createValidUsuario() {
		return UsuarioEntity.builder().nome("Anderson").cpf("123.123.123-55")
				.dataDeNascimento("1998, 05, 18").celular("91234-5678").telefone("1234-5678")
				.email("boladinho@hotmail.com").enderecos(List.of(this.createValidEnderecoEntity())).build();
	}
	
	private UsuarioEntity createUsuarioSemEndereco() {
		return UsuarioEntity.builder().nome("Anderson").cpf("123.123.123-55")
				.dataDeNascimento("1998, 05, 18").celular("91234-5678").telefone("1234-5678")
				.email("boladinho@hotmail.com").build();
	}

	private UsuarioDTO createValidUsuarioDTO() {
		return UsuarioDTO.builder().nome("Karina")
				.cpf("12345678911")
				.celular("11222223333")
				.telefone("22223333")
				.email("boladinhos@gmail.com")
				.dataDeNascimento("1998, 05, 18")
				.build();
	}
	
	private EnderecoDTO createValidEndereco() {
		return EnderecoDTO.builder()
				.enderecoId(1)
				.logradouro("Rua do Anderson").numero("55").complemento("ap 12").cep("91234-567")
				.cidade("Cidade do Anderson").bairro("Bairro dos boladinhos").estado("estado laico").build();
	}

	private EnderecoEntity createValidEnderecoEntity() {
		return EnderecoEntity.builder().logradouro("Rua do Anderson").numero("55").complemento("ap 12").cep("91234-567")
				.cidade("Cidade do Anderson").bairro("Bairro dos boladinhos").estado("estado laico").build();
	}
}