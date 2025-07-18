package com.fiap.techChallenge.utils.mappers;

import java.util.ArrayList;
import java.util.List;

import com.fiap.techChallenge.adapters.outbound.entities.user.AttendantEntity;
import com.fiap.techChallenge.application.dto.user.AttendantRequestDTO;
import com.fiap.techChallenge.application.dto.user.AttendantResponseDTO;
import com.fiap.techChallenge.domain.core.user.attendant.Attendant;

import org.springframework.stereotype.Component;

@Component
public class AttendantMapper {

    public Attendant toDomain(AttendantEntity entity) {
        if (entity == null) {
            return null;
        }

        return Attendant.create(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getCpf().getNumber()
        );
    }

    public Attendant toDomain(AttendantRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return Attendant.create(null, dto.name(), dto.email(), dto.cpf());
    }

    public AttendantEntity toEntity(Attendant attendant) {
        if (attendant == null) {
            return null;
        }

        AttendantEntity entity = new AttendantEntity();
        entity.setId(attendant.getId());
        entity.setName(attendant.getName());
        entity.setEmail(attendant.getEmail());
        entity.setCpf(attendant.getUnformattedCpf());

        return entity;
    }

    public AttendantResponseDTO toDto(Attendant attendant) {
        if (attendant == null) {
            return null;
        }

        return new AttendantResponseDTO(
                attendant.getId(),
                attendant.getName(),
                attendant.getEmail(),
                attendant.getFormattedCpf()
        );
    }

    public List<AttendantResponseDTO> toDtoList(List<Attendant> domains) {
        List<AttendantResponseDTO> dtoList = new ArrayList<>();

        dtoList.addAll(
                domains.stream()
                        .map(this::toDto)
                        .toList());

        return dtoList;
    }

    public List<Attendant> toDomainList(List<AttendantEntity> entities) {
        List<Attendant> domainList = new ArrayList<>();

        domainList.addAll(
                entities.stream()
                        .map(this::toDomain)
                        .toList());

        return domainList;
    }
}
