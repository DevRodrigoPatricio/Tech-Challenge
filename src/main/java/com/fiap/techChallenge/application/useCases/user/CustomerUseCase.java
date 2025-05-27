package com.fiap.techChallenge.application.useCases.user;

import com.fiap.techChallenge.application.dto.user.CustomerResponseDTO;
import com.fiap.techChallenge.application.dto.user.CustomerRequestDTO;

import java.util.UUID;

import com.fiap.techChallenge.domain.user.customer.Customer;

public interface CustomerUseCase {
    CustomerResponseDTO createCustomer(CustomerRequestDTO customer);
    CustomerResponseDTO searchCustomer(UUID id);
    CustomerResponseDTO searchCustomer(String cpf);
    Customer validate(UUID id);
}
