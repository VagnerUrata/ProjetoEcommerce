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

import br.com.brq.projetoecommerce.domain.VendaEntity;
import br.com.brq.projetoecommerce.dto.VendaDTO;
import br.com.brq.projetoecommerce.services.VendaService;

@RestController
@RequestMapping(path = "venda")
public class VendaController {

	@Autowired
	private VendaService vendaService;

	@GetMapping
	public ResponseEntity<List<VendaDTO>> buscarTodasVendas() {
		List<VendaEntity> enty = vendaService.listaTodasVendas();
		List<VendaDTO> listDTO = enty.stream().map(VendaEntity::toDTO).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<VendaDTO> buscarId(@PathVariable("id") Integer id) {
		VendaEntity enty = vendaService.buscarVendaId(id);
		VendaDTO dto = enty.toDTO();
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<VendaDTO> cadastrar(@Valid @RequestBody VendaDTO dto) {
		VendaEntity enty = vendaService.salvar(dto.toEntity());
		VendaDTO dtoSave = enty.toDTO();
		return ResponseEntity.ok().body(dtoSave);
	}

	@PutMapping(value = "{id}")
	public ResponseEntity<VendaDTO> alterar(@Valid @RequestBody VendaDTO dto, @PathVariable("id") int id) {
		VendaEntity enty = vendaService.alterar(id, dto.toEntity());

		return ResponseEntity.ok().body(enty.toDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {
		this.vendaService.deletar(id);
		return ResponseEntity.ok().build();
	}

}