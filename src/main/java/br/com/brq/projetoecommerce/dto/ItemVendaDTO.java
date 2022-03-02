package br.com.brq.projetoecommerce.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import br.com.brq.projetoecommerce.domain.ItemVendaEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemVendaDTO {

	private Integer idItemVenda; 
	
	@NotNull(message = "Campo quantidade é obrigatório.")
	private Integer itemQuantidade;
	
	@NotNull(message = "Campo produto é obrigatório.")
	private List <ProdutoDTO> itemProduto;
	
	public ItemVendaEntity toEntity() {
		var mapper = new ModelMapper();
		return mapper.map(this, ItemVendaEntity.class);
	}
	
}
