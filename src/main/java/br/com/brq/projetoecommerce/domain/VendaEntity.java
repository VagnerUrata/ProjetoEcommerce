package br.com.brq.projetoecommerce.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import br.com.brq.projetoecommerce.dto.VendaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "venda")
public class VendaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
//	@Id
//	@GeneratedValue (strategy = GenerationType.SEQUENCE, 
//	generator = "VENDA_GEN_SEQ")
//	@SequenceGenerator (sequenceName = "VENDA_SEQ", allocationSize = 1, 
//	name = "VENDA_GEN_SEQ")
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY) 
	private Integer idVenda;

	private String dataVenda;

	@OneToMany
	private List<ItemVendaEntity> itens;

	@ManyToOne
	private UsuarioEntity usuario;

	public VendaDTO toDTO() {
		var mapper = new ModelMapper();
		return mapper.map(this, VendaDTO.class);
	}
}