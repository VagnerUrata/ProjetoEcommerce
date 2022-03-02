package br.com.brq.projetoecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.brq.projetoecommerce.domain.ProdutoEntity;

@Repository
public interface ProdutoRepository extends JpaRepository <ProdutoEntity, Integer>{

}
