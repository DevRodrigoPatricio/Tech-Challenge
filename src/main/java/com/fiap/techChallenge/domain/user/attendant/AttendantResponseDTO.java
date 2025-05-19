package com.fiap.techChallenge.domain.user.attendant;

import java.util.UUID;

public record AttendantResponseDTO(
        UUID id,
        String name,
        String email,
        String cpf
) {}
