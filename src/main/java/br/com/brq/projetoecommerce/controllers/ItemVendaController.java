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

import br.com.brq.projetoecommerce.domain.ItemVendaEntity;
import br.com.brq.projetoecommerce.dto.ItemVendaDTO;
import br.com.brq.projetoecommerce.services.ItemVendaService;

@RestController
@RequestMapping(path="itemVenda")
public class ItemVendaController {

	
	@Autowired
	private ItemVendaService itemVendaService;
	
	@GetMapping
	public ResponseEntity <List<ItemVendaDTO>> buscarTodosItemsVendas () {
		List <ItemVendaEntity> enty = itemVendaService.listaTodosItemsVenda();
		List <ItemVendaDTO> listDTO = enty.stream().map(ItemVendaEntity::toDTO).collect(Collectors.toList());	
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ItemVendaDTO> buscarItemVendaId(@PathVariable("id") Integer id){
		ItemVendaEntity enty = itemVendaService.buscarItemVendaId(id);
		ItemVendaDTO dto = enty.toDTO();
		return ResponseEntity.ok().body(dto);
}
	@PostMapping
	public ResponseEntity<ItemVendaDTO> cadastrar(@Valid @RequestBody ItemVendaDTO dto){
		ItemVendaEntity enty = itemVendaService.salvar(dto.toEntity());
		ItemVendaDTO dtoSave = enty.toDTO();
		return ResponseEntity.ok().body(dtoSave);
	}
	
	@PutMapping(value = "{id}")
	public ResponseEntity<ItemVendaDTO> alterar(@Valid @RequestBody ItemVendaDTO dto, @PathVariable("id") int id){
		ItemVendaEntity enty = itemVendaService.alterar(id, dto.toEntity());
		return ResponseEntity.ok().body(enty.toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id){
		this.itemVendaService.deletar(id);
		return ResponseEntity.ok().build();
	}
}

