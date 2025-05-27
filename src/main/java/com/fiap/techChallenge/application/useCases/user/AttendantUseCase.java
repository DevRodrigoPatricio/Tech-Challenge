package com.fiap.techChallenge.application.useCases.user;

import java.util.UUID;

import com.fiap.techChallenge.application.dto.user.AttendantRequestDTO;
import com.fiap.techChallenge.application.dto.user.AttendantResponseDTO;
import com.fiap.techChallenge.domain.user.attendant.Attendant;

public interface AttendantUseCase {

    AttendantResponseDTO createAttendant(AttendantRequestDTO attendant);

    AttendantResponseDTO searchAttendant(UUID id);

    AttendantResponseDTO searchAttendant(String cpf);

    Attendant validate(UUID id);
}
