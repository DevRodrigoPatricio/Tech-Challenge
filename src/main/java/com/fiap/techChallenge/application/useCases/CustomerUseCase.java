package com.fiap.techChallenge.application.useCases;

import com.fiap.techChallenge.domain.customer.Customer;
import com.fiap.techChallenge.domain.customer.CustomerRequestDTO;

import java.util.UUID;

public interface CustomerUseCase {
    Customer createCustomer(CustomerRequestDTO customer);
    Customer searchCustomer(UUID id);
    Customer searchCustomer(String cpf);
}
