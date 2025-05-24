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
    private final AttendantMapper attendantMapper;

    public AttendantServiceImpl(AttendantRepository attendantRepository, AttendantMapper attendantMapper) {
        this.attendantRepository = attendantRepository;
        this.attendantMapper = attendantMapper;
    }

    @Override
    public AttendantResponseDTO createAttendant(AttendantRequestDTO attendant) {

        Attendant attendantToDomain = attendantMapper.toDomain(attendant);
        Optional<Attendant> optAttendant = attendantRepository.findByCpf(attendant.cpf());

        if (optAttendant.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        Attendant savedAttendant = attendantRepository.save(attendantToDomain);
        return attendantMapper.toDto(savedAttendant);
    }

    @Override
    public AttendantResponseDTO searchAttendant(UUID id) {
        Optional<Attendant> optAttendant = attendantRepository.findById(id);

        if (optAttendant.isEmpty()) {
            throw new EntityNotFoundException("Attendant");
        }

        return attendantMapper.toDto(optAttendant.get());
    }

    @Override
    public AttendantResponseDTO searchAttendant(String cpf) {
        Optional<Attendant> optAttendant = attendantRepository.findByCpf(cpf);

        if (optAttendant.isEmpty()) {
            throw new EntityNotFoundException("Attendant");
        }

        return attendantMapper.toDto(optAttendant.get());
    }
}
