
package br.com.brq.projetoecommerce.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import br.com.brq.projetoecommerce.dto.EnderecoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "enderecos")
public class EnderecoEntity implements Serializable {

	private static final long serialVersionUID = 1430541589979579078L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY) 
	private Integer enderecoId;
	private String logradouro;
	private String numero;
	private String complemento;
	private String cep;
	private String bairro;
	private String cidade;
	private String estado;

	public EnderecoDTO toDTO() {
		var mapper = new ModelMapper();
		return mapper.map(this, EnderecoDTO.class);
	}
}

