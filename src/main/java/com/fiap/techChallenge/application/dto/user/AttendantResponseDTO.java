package com.fiap.techChallenge.application.dto.user;

import java.util.UUID;

public record AttendantResponseDTO(
        UUID id,
        String name,
        String email,
        String cpf
) {}
