package com.fiap.techChallenge.application.useCases.user;

import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.application.dto.user.AttendantRequestDTO;
import com.fiap.techChallenge.application.dto.user.AttendantResponseDTO;
import com.fiap.techChallenge.domain.core.user.attendant.Attendant;

public interface AttendantUseCase {

    AttendantResponseDTO createAttendant(AttendantRequestDTO attendant);

    AttendantResponseDTO updateAttendant(AttendantRequestDTO attendant);

    AttendantResponseDTO searchAttendant(UUID id);

    AttendantResponseDTO searchAttendant(String cpf);

    List<AttendantResponseDTO> list();

    void delete(UUID id);

    Attendant validate(UUID id);

    Attendant validate(String cpf);
}
