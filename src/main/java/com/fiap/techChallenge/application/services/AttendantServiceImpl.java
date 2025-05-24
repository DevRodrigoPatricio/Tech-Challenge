package com.fiap.techChallenge.application.services;

import com.fiap.techChallenge.application.useCases.AttendantUseCase;
import com.fiap.techChallenge.domain.user.attendant.Attendant;
import com.fiap.techChallenge.domain.user.attendant.AttendantRepository;
import com.fiap.techChallenge.domain.user.attendant.AttendantRequestDTO;
import com.fiap.techChallenge.utils.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.utils.exceptions.UserAlreadyExistsException;
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
    public Attendant createAttendant(AttendantRequestDTO attendant) {

        Attendant attendantToDomain = attendantMapper.toDomain(attendant);
        Optional<Attendant> optAttendant = attendantRepository.findByCpf(attendant.cpf());

        if (optAttendant.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        return attendantRepository.save(attendantToDomain);
    }

    @Override
    public Attendant searchAttendant(UUID id) {
        Optional<Attendant> optAttendant = attendantRepository.findById(id);

        if (optAttendant.isEmpty()) {
            throw new EntityNotFoundException("Attendant");
        }

        return optAttendant.get();
    }

    @Override
    public Attendant searchAttendant(String cpf) {
        Optional<Attendant> optAttendant = attendantRepository.findByCpf(cpf);

        if (optAttendant.isEmpty()) {
            throw new EntityNotFoundException("Attendant");
        }

        return optAttendant.get();
    }
}
