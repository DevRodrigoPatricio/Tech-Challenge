package com.fiap.techChallenge.adapters.outbound.repositories.user;

import com.fiap.techChallenge.adapters.outbound.entities.user.AttendantEntity;
import com.fiap.techChallenge.adapters.outbound.entities.user.CPFEmbeddable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaAttendantRepository extends JpaRepository<AttendantEntity, UUID> {
    Optional<AttendantEntity> findByCpf(CPFEmbeddable cpf);
}
