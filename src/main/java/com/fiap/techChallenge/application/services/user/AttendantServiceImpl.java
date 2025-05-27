package com.fiap.techChallenge.application.services.user;

import com.fiap.techChallenge.application.dto.user.AttendantResponseDTO;
import com.fiap.techChallenge.application.useCases.user.AttendantUseCase;
import com.fiap.techChallenge.domain.user.attendant.Attendant;
import com.fiap.techChallenge.domain.user.attendant.AttendantRepository;
import com.fiap.techChallenge.application.dto.user.AttendantRequestDTO;
import com.fiap.techChallenge.domain.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.domain.exceptions.user.UserAlreadyExistsException;
import com.fiap.techChallenge.utils.mappers.AttendantMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AttendantServiceImpl implements AttendantUseCase {

    private final AttendantRepository attendantRepository;

    public AttendantServiceImpl(AttendantRepository attendantRepository) {
        this.attendantRepository = attendantRepository;
    }

    @Override
    public AttendantResponseDTO createAttendant(AttendantRequestDTO attendant) {

        Attendant attendantToDomain = AttendantMapper.toDomain(attendant);
        Optional<Attendant> optAttendant = attendantRepository.findByCpf(attendant.cpf());

        if (optAttendant.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        Attendant savedAttendant = attendantRepository.save(attendantToDomain);
        return AttendantMapper.toDto(savedAttendant);
    }

    @Override
    public AttendantResponseDTO searchAttendant(UUID id) {
        Optional<Attendant> optAttendant = attendantRepository.findById(id);

        if (optAttendant.isEmpty()) {
            throw new EntityNotFoundException("Attendant");
        }

        return AttendantMapper.toDto(optAttendant.get());
    }

    @Override
    public Attendant validate(UUID id) {
        Attendant attendant = attendantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Attendant"));

        return attendant;
    }

    @Override
    public AttendantResponseDTO searchAttendant(String cpf) {
        Optional<Attendant> optAttendant = attendantRepository.findByCpf(cpf);

        if (optAttendant.isEmpty()) {
            throw new EntityNotFoundException("Attendant");
        }

        return AttendantMapper.toDto(optAttendant.get());
    }
}
