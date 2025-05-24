package com.fiap.techChallenge.adapters.outbound.repositories.user;

import com.fiap.techChallenge.adapters.outbound.entities.user.CPFEmbeddable;
import com.fiap.techChallenge.adapters.outbound.entities.user.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    Optional<CustomerEntity> findByCpf(CPFEmbeddable cpf);
}
