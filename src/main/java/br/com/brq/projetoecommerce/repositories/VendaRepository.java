package br.com.brq.projetoecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.brq.projetoecommerce.domain.VendaEntity;

@Repository
public interface VendaRepository extends JpaRepository <VendaEntity, Integer> {

}
