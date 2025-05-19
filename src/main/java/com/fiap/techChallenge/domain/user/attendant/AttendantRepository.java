package com.fiap.techChallenge.domain.user.attendant;

import java.util.Optional;
import java.util.UUID;

public interface AttendantRepository {
    Optional<Attendant> findById(UUID id);
    Optional<Attendant> findByCpf(String cpf);
    Attendant save(Attendant attendant);
}
