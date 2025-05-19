package com.fiap.techChallenge.domain.user.customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findByCPF(String cpf);
    Optional<Customer> findById(UUID uuid);
}
