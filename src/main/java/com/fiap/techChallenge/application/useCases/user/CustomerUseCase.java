package com.fiap.techChallenge.application.useCases.user;

import com.fiap.techChallenge.application.dto.user.CustomerResponseDTO;
import com.fiap.techChallenge.application.dto.user.CustomerRequestDTO;

import java.util.UUID;

public interface CustomerUseCase {
    CustomerResponseDTO createCustomer(CustomerRequestDTO customer);
    CustomerResponseDTO searchCustomer(UUID id);
    CustomerResponseDTO searchCustomer(String cpf);
}
