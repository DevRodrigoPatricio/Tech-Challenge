package com.fiap.techChallenge.domain.user.attendant;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface AttendantRepository {

    Optional<Attendant> findById(UUID id);

    Optional<Attendant> findByCpf(String cpf);

    List<Attendant> findAll();

    void delete(UUID id);

    Attendant save(Attendant attendant);
}
