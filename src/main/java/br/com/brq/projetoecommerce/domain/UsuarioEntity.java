
package br.com.brq.projetoecommerce.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import br.com.brq.projetoecommerce.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class UsuarioEntity implements Serializable {
	
	private static final long serialVersionUID = 6984190767295670823L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY) 
	private Integer usuarioId;
	private String nome;
	private String cpf;
	private String dataDeNascimento;
	private String celular;
	private String telefone;
	private String email;

	
	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinTable(name = "REL_USUARIO_ENDERECO", joinColumns = { @JoinColumn(name = "usuarioId") }, inverseJoinColumns = {
			@JoinColumn(name = "enderecoId") })
	List<EnderecoEntity> enderecos;
	
	@OneToMany
	private List<ItemVendaEntity> itemVenda;

	@ManyToOne
	private UsuarioEntity usuario;
	
	public UsuarioDTO toDTO() {
		var modelMapper = new ModelMapper();
		return modelMapper.map(this, UsuarioDTO.class);
	}
}
