package com.fiap.techChallenge.application.useCases;

import com.fiap.techChallenge.domain.user.attendant.Attendant;
import com.fiap.techChallenge.domain.user.attendant.AttendantRequestDTO;

import java.util.UUID;

public interface AttendantUseCase {
    Attendant createAttendant(AttendantRequestDTO attendant);
    Attendant searchAttendant(UUID id);
}
