package br.com.brq.projetoecommerce.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import br.com.brq.projetoecommerce.domain.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
	private Integer usuarioId;
	
	@NotNull(message = "Campo obrigatório")
	private String nome;
	@NotNull(message = "Campo obrigatório")
	private String cpf;
	@NotNull(message = "Campo obrigatório")
	private String dataDeNascimento;
	@NotNull(message = "Campo obrigatório")
	private String celular;
	@NotNull(message = "Campo obrigatório")
	private String telefone;
	@NotNull(message = "Campo obrigatório")
	private String email;

	@NotNull(message = "Campo obrigatório")
	private List<EnderecoDTO> enderecos = new ArrayList<>();

	public UsuarioEntity toEntity() {
		var mapper = new ModelMapper();
		return mapper.map(this, UsuarioEntity.class);

	}

}