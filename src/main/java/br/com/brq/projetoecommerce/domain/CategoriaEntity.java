package br.com.brq.projetoecommerce.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import br.com.brq.projetoecommerce.dto.CategoriaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Entity
@Data
@Builder
@NoArgsConstructor
@Table(name = "categoria")
public class CategoriaEntity implements Serializable {

	private static final long serialVersionUID = 6033287835050072042L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY) 
	private Integer idCategoria;
	private String nomeCategoria;

	public CategoriaDTO toDTO() {
		var mapper = new ModelMapper();
		return mapper.map(this, CategoriaDTO.class);

	}
}

