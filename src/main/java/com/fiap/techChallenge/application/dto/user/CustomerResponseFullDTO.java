package com.fiap.techChallenge.application.dto.user;

import java.util.UUID;

public record CustomerResponseFullDTO(
        UUID id,
        String name,
        String cpf,
        String email,
        boolean anonymous
) implements CustomerResponseDTO {}
