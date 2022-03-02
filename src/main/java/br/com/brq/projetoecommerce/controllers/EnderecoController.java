package br.com.brq.projetoecommerce.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brq.projetoecommerce.domain.EnderecoEntity;
import br.com.brq.projetoecommerce.dto.EnderecoDTO;
import br.com.brq.projetoecommerce.services.EnderecoService;

@RestController
@RequestMapping(path = "enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<EnderecoDTO> buscarID(@PathVariable("id") Integer id) {
		EnderecoEntity endereco = enderecoService.buscarEnderecoId(id);
		EnderecoDTO dto = endereco.toDTO();
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping
	public ResponseEntity<List<EnderecoDTO>> buscarTodosEnderecos() {
		List<EnderecoEntity> endereco = enderecoService.listaTodosEnderecos();
		List<EnderecoDTO> listDTO = endereco.stream().map(EnderecoEntity::toDTO).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping
		public ResponseEntity<EnderecoDTO> cadastrar(@Valid @RequestBody EnderecoDTO dto) {
			EnderecoEntity endereco = enderecoService.salvar(dto.toEntity());
			EnderecoDTO dtoSave = endereco.toDTO();
			return ResponseEntity.ok().body(dtoSave);
	
		}

	@PutMapping(value = "{id}")
	public ResponseEntity<EnderecoDTO> alterar(@Valid @RequestBody EnderecoDTO dto, @PathVariable("id") int id) {

		EnderecoEntity endereco = enderecoService.alterar(id, dto.toEntity());

		return ResponseEntity.ok().body(endereco.toDTO());

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		return ResponseEntity.ok().build();
	}
}