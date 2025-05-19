package com.fiap.techChallenge.utils.mappers;

import com.fiap.techChallenge.adapters.outbound.entities.CustomerEntity;
import com.fiap.techChallenge.domain.user.customer.Customer;
import com.fiap.techChallenge.domain.user.customer.CustomerRequestDTO;
import com.fiap.techChallenge.domain.user.customer.CustomerResponseAnonymDTO;
import com.fiap.techChallenge.domain.user.customer.CustomerResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toDomain(CustomerEntity entity) {
        if (entity == null) return null;

        return Customer.create(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getCpf() != null ? entity.getCpf().getNumber() : null,
                entity.isAnonymous()
        );
    }

    public Customer toDomain(CustomerRequestDTO dto) {
        if (dto == null) return null;

        return Customer.create(null, dto.name(), dto.email(), dto.cpf(), dto.anonymous());
    }

    public CustomerEntity toEntity(Customer customer) {
        if (customer == null) return null;

        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail());
        entity.setCpf(customer.getCpf());
        entity.setAnonymous(customer.isAnonymous());

        return entity;
    }

    public CustomerResponseDTO toDTO(Customer entity) {
        if (entity == null) return null;

        return new CustomerResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getCpf(),
                entity.isAnonymous()
        );
    }

    public CustomerResponseAnonymDTO toAnonymousDTO(Customer entity) {
        if (entity == null) return null;

        return new CustomerResponseAnonymDTO(
                entity.getId(),
                entity.isAnonymous()
        );
    }
}
