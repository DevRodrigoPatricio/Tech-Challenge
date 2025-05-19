package com.fiap.techChallenge.domain.user.customer;

import java.util.UUID;

public record CustomerResponseAnonymDTO(
        UUID id,
        boolean anonymous
) {}
