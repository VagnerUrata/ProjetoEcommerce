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

import br.com.brq.projetoecommerce.domain.ImagemEntity;
import br.com.brq.projetoecommerce.dto.ImagemDTO;
import br.com.brq.projetoecommerce.services.ImagemService;

@RestController
@RequestMapping(path = "imagens")
public class ImagemController {

	@Autowired
	private ImagemService imagemService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ImagemDTO> buscarId(@PathVariable("id") Integer id) {
		ImagemEntity enty = imagemService.buscarImagemId(id);
		ImagemDTO dto = enty.toDTO();
		return ResponseEntity.ok().body(dto);
	}

	@GetMapping
	public ResponseEntity<List<ImagemDTO>> buscarTodasImagens() {
		List<ImagemEntity> enty = imagemService.listaTodasImagens();
		List<ImagemDTO> listDTO = enty.stream().map(ImagemEntity::toDTO).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping
	public ResponseEntity<ImagemDTO> cadastrar(@Valid @RequestBody ImagemDTO dto) {
		ImagemEntity enty = imagemService.salvar(dto.toEntity());
		ImagemDTO dtoSave = enty.toDTO();
		return ResponseEntity.ok().body(dtoSave);

	}

	@PutMapping(value = "{id}")
	public ResponseEntity<ImagemDTO> alterar(@Valid @RequestBody ImagemDTO dto, @PathVariable("id") int id) {

		ImagemEntity enty = imagemService.alterar(id, dto.toEntity());

		return ResponseEntity.ok().body(enty.toDTO());

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		this.imagemService.deletar(id);
		return ResponseEntity.ok().build();
	}
}
