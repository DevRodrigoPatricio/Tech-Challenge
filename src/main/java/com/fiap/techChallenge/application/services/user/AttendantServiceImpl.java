package com.fiap.techChallenge.application.services.user;

import java.util.List;

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

    public AttendantServiceImpl(AttendantRepository attendantRepository,
                                AttendantMapper attendantMapper) {
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
    public AttendantResponseDTO updateAttendant(AttendantRequestDTO attendant) {
        Attendant domain = this.validate(attendantMapper.toDomain(attendant).getUnformattedCpf());

        domain.setName(attendant.name());
        domain.setCpf(attendant.cpf());
        domain.setEmail(attendant.email());
        domain = attendantRepository.save(domain);
        
        return attendantMapper.toDto(domain);
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
    public Attendant validate(String cpf) {
        return attendantRepository.findByCpf(cpf).orElseThrow(() -> new EntityNotFoundException("Attendant"));
    }

    @Override
    public Attendant validate(UUID id) {
        return attendantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Attendant"));
    }

    @Override
    public AttendantResponseDTO searchAttendant(String cpf
    ) {
        Optional<Attendant> optAttendant = attendantRepository.findByCpf(cpf);

        if (optAttendant.isEmpty()) {
            throw new EntityNotFoundException("Attendant");
        }

        return attendantMapper.toDto(optAttendant.get());
    }

    @Override
    public List<AttendantResponseDTO> list() {
        return attendantMapper.toDtoList(attendantRepository.findAll());
    }

    @Override
    public void delete(UUID id
    ) {
        attendantRepository.delete(id);
    }
}
