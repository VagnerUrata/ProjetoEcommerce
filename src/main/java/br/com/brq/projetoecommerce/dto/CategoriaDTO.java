package br.com.brq.projetoecommerce.dto;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import br.com.brq.projetoecommerce.domain.CategoriaEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

	private Integer idCategoria;
	
	@NotNull(message = "Campo nome é obrigatório.")
	private String nomeCategoria;

	public CategoriaEntity toEntity() {
		var mapper = new ModelMapper();
		return mapper.map(this, CategoriaEntity.class);
		
		

	}
}
