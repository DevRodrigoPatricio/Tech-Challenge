package com.fiap.techChallenge.domain.user.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record CustomerRequestDTO(
        String name,
        @Email(message = "Email em formato inválido") String email,
        @Size(min = 11, max = 11, message = "CPF deve possuir 11 caracteres") String cpf,
        boolean anonymous
) {}
