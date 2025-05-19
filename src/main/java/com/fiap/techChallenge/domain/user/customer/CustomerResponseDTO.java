package com.fiap.techChallenge.domain.user.customer;

import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        String name,
        String cpf,
        String email,
        boolean anonymous
) {}
