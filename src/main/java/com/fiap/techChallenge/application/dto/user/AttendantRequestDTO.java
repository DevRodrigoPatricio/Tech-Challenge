package com.fiap.techChallenge.application.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AttendantRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,
        @NotBlank(message = "Email é obrigatório") @Email(message = "Email em formato inválido")
        String email,
        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$",
                message = "CPF deve estar no formato XXX.XXX.XXX-XX")
        String cpf
) {}
