package com.fiap.techChallenge.adapters.outbound.repositories.customer;

import com.fiap.techChallenge.adapters.outbound.entities.CPFEmbeddable;
import com.fiap.techChallenge.adapters.outbound.entities.CustomerEntity;
import com.fiap.techChallenge.domain.user.customer.Customer;
import com.fiap.techChallenge.domain.user.customer.CustomerRepository;
import com.fiap.techChallenge.utils.mappers.CustomerMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerMapper customerMapper;

    public CustomerRepositoryImpl(JpaCustomerRepository jpaCustomerRepository, CustomerMapper customerMapper) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity customerEntity = customerMapper.toEntity(customer);
        jpaCustomerRepository.save(customerEntity);

        return customerMapper.toDomain(customerEntity);
    }

    @Override
    public Optional<Customer> findByCPF(String cpf) {
        CPFEmbeddable emb = new CPFEmbeddable(cpf);
        Optional<CustomerEntity> optEntity = jpaCustomerRepository.findByCpf(emb);

        return optEntity.map(customerMapper::toDomain);
    }

    @Override
    public Optional<Customer> findById(UUID uuid) {
        Optional<CustomerEntity> optEntity = jpaCustomerRepository.findById(uuid);

        return optEntity.map(customerMapper::toDomain);
    }
}
