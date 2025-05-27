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

    public AttendantRepositoryImpl(JpaAttendantRepository jpaAttendantRepository) {
        this.jpaAttendantRepository = jpaAttendantRepository;
    }

    @Override
    public Optional<Attendant> findById(UUID id) {
        Optional<AttendantEntity> optEntity = jpaAttendantRepository.findById(id);
        return optEntity.map(AttendantMapper::toDomain);
    }

    @Override
    public Optional<Attendant> findByCpf(String cpf) {
        CPFEmbeddable emb = new CPFEmbeddable(cpf);
        Optional<AttendantEntity> optEntity = jpaAttendantRepository.findByCpf(emb);
        return optEntity.map(AttendantMapper::toDomain);
    }

    @Override
    public Attendant save(Attendant attendant) {
        AttendantEntity attendantEntity = AttendantMapper.toEntity(attendant);
        jpaAttendantRepository.save(attendantEntity);

        return AttendantMapper.toDomain(attendantEntity);
    }
}
