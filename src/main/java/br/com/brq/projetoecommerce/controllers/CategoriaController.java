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

import br.com.brq.projetoecommerce.domain.CategoriaEntity;
import br.com.brq.projetoecommerce.dto.CategoriaDTO;
import br.com.brq.projetoecommerce.services.CategoriaService;

@RestController
@RequestMapping(path = "categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoriaDTO> buscarId(@PathVariable("id") Integer id) {
		CategoriaEntity enty = categoriaService.buscarCategoriaId(id); 
		CategoriaDTO dto = enty.toDTO(); 
		return ResponseEntity.ok().body(dto); 
	}

	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> buscarTodasCategorias() {
		List<CategoriaEntity> enty = categoriaService.listaTodasCategorias();
		List<CategoriaDTO> listDTO = enty.stream().map(CategoriaEntity::toDTO).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping
	public ResponseEntity<CategoriaDTO> cadastra(@Valid @RequestBody CategoriaDTO dto) {
		CategoriaEntity enty = categoriaService.salvar(dto.toEntity());
		CategoriaDTO dtoSave = enty.toDTO();
		return ResponseEntity.ok().body(dtoSave);
	}

	@PutMapping(value = "{id}")
	public ResponseEntity<CategoriaDTO> alterar(@Valid @RequestBody CategoriaDTO dto, @PathVariable("id") int id) {
		CategoriaEntity enty = categoriaService.alterar(id, dto.toEntity());
		return ResponseEntity.ok().body(enty.toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		this.categoriaService.deletar(id);
		return ResponseEntity.ok().build();
	}
}
	

