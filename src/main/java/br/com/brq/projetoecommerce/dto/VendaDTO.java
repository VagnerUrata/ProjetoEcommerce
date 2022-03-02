package br.com.brq.projetoecommerce.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import br.com.brq.projetoecommerce.domain.VendaEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendaDTO {

	private Integer idVenda;
	@NotNull(message = "Campo data é obrigatório.")
	private String dataVenda;

	@NotNull(message = "Campo produto é obrigatório.")
	private List<ItemVendaDTO> itens;

	@NotNull(message = "Campo usuário é obrigatório.")
	private UsuarioDTO usuario;

	public VendaEntity toEntity() {
		var mapper = new ModelMapper();
		return mapper.map(this, VendaEntity.class);
	}

}
