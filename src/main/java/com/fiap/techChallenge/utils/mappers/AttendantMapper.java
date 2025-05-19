package com.fiap.techChallenge.utils.mappers;

import com.fiap.techChallenge.adapters.outbound.entities.AttendantEntity;
import com.fiap.techChallenge.domain.user.attendant.Attendant;
import com.fiap.techChallenge.domain.user.attendant.AttendantRequestDTO;
import com.fiap.techChallenge.domain.user.attendant.AttendantResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AttendantMapper {
    public Attendant toDomain(AttendantEntity entity) {
        if (entity == null) return null;

        return Attendant.create(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getCpf().getNumber()
        );
    }

    public Attendant toDomain(AttendantRequestDTO dto) {
        if (dto == null) return null;

        return Attendant.create(null, dto.name(), dto.email(), dto.cpf());
    }

    public AttendantEntity toEntity(Attendant attendant) {
        if (attendant == null) return null;

        AttendantEntity entity = new AttendantEntity();
        entity.setName(attendant.getName());
        entity.setEmail(attendant.getEmail());
        entity.setCpf(attendant.getCpf());

        return entity;
    }

    public AttendantResponseDTO toDto(Attendant attendant) {
        if (attendant == null) return null;

        return new AttendantResponseDTO(
                attendant.getId(),
                attendant.getName(),
                attendant.getEmail(),
                attendant.getCpf()
        );
    }
}
