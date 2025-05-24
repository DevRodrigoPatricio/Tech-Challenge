package com.fiap.techChallenge.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AttendantRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,
        @NotBlank(message = "Email é obrigatório") @Email(message = "Email em formato inválido")
        String email,
        @NotBlank(message = "CPF é obrigatório") @Size(min = 11, max = 11, message = "CPF deve possuir 11 caracteres")
        String cpf
) {}
