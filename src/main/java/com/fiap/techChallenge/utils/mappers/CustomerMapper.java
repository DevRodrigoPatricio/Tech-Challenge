package com.fiap.techChallenge.utils.mappers;

import com.fiap.techChallenge.adapters.outbound.entities.user.CustomerEntity;
import com.fiap.techChallenge.domain.user.customer.Customer;
import com.fiap.techChallenge.application.dto.user.CustomerRequestDTO;
import com.fiap.techChallenge.application.dto.user.CustomerResponseAnonymDTO;
import com.fiap.techChallenge.application.dto.user.CustomerResponseDTO;
import com.fiap.techChallenge.application.dto.user.CustomerResponseFullDTO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public static Customer toDomain(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }

        return Customer.create(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getCpf() != null ? entity.getCpf().getNumber() : null,
                entity.isAnonymous()
        );
    }

    public static Customer toDomain(CustomerRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return Customer.create(null, dto.name(), dto.email(), dto.cpf(), dto.anonymous());
    }

    public static CustomerEntity toEntity(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail());
        entity.setCpf(customer.getCpf());
        entity.setAnonymous(customer.isAnonymous());

        return entity;
    }

    public static CustomerResponseFullDTO toDTO(Customer entity) {
        if (entity == null) {
            return null;
        }

        return new CustomerResponseFullDTO(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                entity.getEmail(),
                entity.isAnonymous()
        );
    }

    public static CustomerResponseAnonymDTO toAnonymousDTO(Customer entity) {
        if (entity == null) {
            return null;
        }

        return new CustomerResponseAnonymDTO(
                entity.getId(),
                entity.getName(),
                entity.isAnonymous()
        );
    }

    public static List<CustomerResponseDTO> toDtoList(List<Customer> domains) {
        List<CustomerResponseDTO> dtoList = new ArrayList<>();

        dtoList.addAll(
                domains.stream()
                        .map(CustomerMapper::toDTO)
                        .toList());

        return dtoList;
    }

    public static List<Customer> toDomainList(List<CustomerEntity> entities) {
        List<Customer> domainList = new ArrayList<>();

        domainList.addAll(
                entities.stream()
                        .map(CustomerMapper::toDomain)
                        .toList());

        return domainList;
    }
}
