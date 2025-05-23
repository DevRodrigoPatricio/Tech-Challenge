package com.fiap.techChallenge.application.useCases.user;

import com.fiap.techChallenge.application.dto.user.AttendantRequestDTO;
import com.fiap.techChallenge.application.dto.user.AttendantResponseDTO;

import java.util.UUID;

public interface AttendantUseCase {
    AttendantResponseDTO createAttendant(AttendantRequestDTO attendant);
    AttendantResponseDTO searchAttendant(UUID id);
}
