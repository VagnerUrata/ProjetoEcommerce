package br.com.brq.projetoecommerce.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import br.com.brq.projetoecommerce.domain.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
	
	private Integer idProduto; 
	
	@NotNull(message = "Campo nome é obrigatório.")
	private String nome; 
	
	@NotNull(message = "Campo preço é obrigatório.")
	private double preco;
	
	@NotNull(message = "Campo descrição é obrigatório.")
	private String descricao;
	
	@NotNull(message = "Campo categoria é obrigatório.")
	private List<CategoriaDTO> categorias;
	
	@NotNull(message = "Campo imagem é obrigatório.")
	private List <ImagemDTO> imagens;
	
	public ProdutoEntity toEntity() {
		var mapper = new ModelMapper();
		return mapper.map(this, ProdutoEntity.class);

	}
}