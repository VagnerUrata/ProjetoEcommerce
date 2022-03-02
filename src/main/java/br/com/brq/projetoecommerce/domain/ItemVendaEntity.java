package br.com.brq.projetoecommerce.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import br.com.brq.projetoecommerce.dto.ItemVendaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "itemVenda")
public class ItemVendaEntity implements Serializable{
	 
	private static final long serialVersionUID = 6755240900315097949L;
//	
//	@GeneratedValue (strategy = GenerationType.SEQUENCE, 
//			generator = "ITEMVENDA_GEN_SEQ")
//			@SequenceGenerator (sequenceName = "ITEMVENDA_SEQ", allocationSize = 1, 
//			name = "ITEMVENDA_GEN_SEQ")
//	@Id
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY) 
	private Integer idItemVenda;
	
	@ManyToMany
	@JoinTable(name= "REL_PRODUTO_ITEMPRODUTO", 
		joinColumns = {@JoinColumn(name = "idItemVenda")},
		inverseJoinColumns = {@JoinColumn(name = "idProduto")})
	private List <ProdutoEntity> itemProduto;
	
	private Integer itemQuantidade;
	
	public ItemVendaDTO toDTO () {
		var mapper = new ModelMapper();
		return mapper.map(this, ItemVendaDTO.class);
	}
}
