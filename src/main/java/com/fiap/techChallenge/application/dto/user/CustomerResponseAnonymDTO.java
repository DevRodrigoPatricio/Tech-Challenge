package com.fiap.techChallenge.application.dto.user;

import java.util.UUID;

public record CustomerResponseAnonymDTO(
        UUID id,
        boolean anonymous
) implements CustomerResponseDTO {}
