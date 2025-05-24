package com.fiap.techChallenge.adapters.outbound.repositories.user;

import com.fiap.techChallenge.adapters.outbound.entities.user.AttendantEntity;
import com.fiap.techChallenge.adapters.outbound.entities.user.CPFEmbeddable;
import com.fiap.techChallenge.domain.user.attendant.Attendant;
import com.fiap.techChallenge.domain.user.attendant.AttendantRepository;
import com.fiap.techChallenge.utils.mappers.AttendantMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AttendantRepositoryImpl implements AttendantRepository {

    private final JpaAttendantRepository jpaAttendantRepository;
    private final AttendantMapper attendantMapper;

    public AttendantRepositoryImpl(JpaAttendantRepository jpaAttendantRepository, AttendantMapper attendantMapper) {
        this.jpaAttendantRepository = jpaAttendantRepository;
        this.attendantMapper = attendantMapper;
    }

    @Override
    public Optional<Attendant> findById(UUID id) {
        Optional<AttendantEntity> optEntity = jpaAttendantRepository.findById(id);
        return optEntity.map(attendantMapper::toDomain);
    }

    @Override
    public Optional<Attendant> findByCpf(String cpf) {
        CPFEmbeddable emb = new CPFEmbeddable(cpf);
        Optional<AttendantEntity> optEntity = jpaAttendantRepository.findByCpf(emb);
        return optEntity.map(attendantMapper::toDomain);
    }

    @Override
    public Attendant save(Attendant attendant) {
        AttendantEntity attendantEntity = attendantMapper.toEntity(attendant);
        jpaAttendantRepository.save(attendantEntity);

        return attendantMapper.toDomain(attendantEntity);
    }
}
