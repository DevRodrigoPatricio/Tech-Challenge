package com.fiap.techChallenge.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record CustomerRequestDTO(
        String name,
        @Email(message = "Email em formato inválido") String email,
        @Pattern(regexp = "^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$",
                message = "CPF deve estar no formato XXX.XXX.XXX-XX")
        String cpf,
        boolean anonymous
) {}
