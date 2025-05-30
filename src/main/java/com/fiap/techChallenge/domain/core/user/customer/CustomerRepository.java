package com.fiap.techChallenge.domain.core.user.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findByCPF(String cpf);

    Optional<Customer> findById(UUID uuid);

    List<Customer> list();

    void delete(UUID id);
}
