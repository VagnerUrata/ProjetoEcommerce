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

import br.com.brq.projetoecommerce.domain.ProdutoEntity;
import br.com.brq.projetoecommerce.dto.ProdutoDTO;
import br.com.brq.projetoecommerce.services.ProdutoService;

@RestController
@RequestMapping(path = "produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProdutoDTO> buscarId(@PathVariable("id") Integer id) {
		ProdutoEntity enty = produtoService.buscarProdutoId(id);
		ProdutoDTO dto = enty.toDTO();
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> buscarTodosProdutos() {
		List<ProdutoEntity> enty = produtoService.listaTodosProdutos();
		List<ProdutoDTO> listDTO = enty.stream().map(ProdutoEntity::toDTO).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping
	public ResponseEntity<ProdutoDTO> cadastrar(@Valid @RequestBody ProdutoDTO dto) {
		ProdutoEntity enty = produtoService.salvar(dto.toEntity());
		ProdutoDTO dtoSave = enty.toDTO();
		return ResponseEntity.ok().body(dtoSave);

	}

	@PutMapping(value = "{id}")
	public ResponseEntity<ProdutoDTO> alterar(@Valid @RequestBody ProdutoDTO dto, @PathVariable("id") int id) {

		ProdutoEntity enty = produtoService.alterar(id, dto.toEntity());

		return ResponseEntity.ok().body(enty.toDTO());

	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		this.produtoService.deletar(id);
		return ResponseEntity.ok().build();
	}
}
