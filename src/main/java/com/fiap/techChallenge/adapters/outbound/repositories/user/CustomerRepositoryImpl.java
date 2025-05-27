package com.fiap.techChallenge.adapters.outbound.repositories.user;

import java.util.List;

import com.fiap.techChallenge.adapters.outbound.entities.user.CPFEmbeddable;
import com.fiap.techChallenge.adapters.outbound.entities.user.CustomerEntity;
import com.fiap.techChallenge.domain.user.customer.Customer;
import com.fiap.techChallenge.domain.user.customer.CustomerRepository;
import com.fiap.techChallenge.utils.mappers.CustomerMapper;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;

    public CustomerRepositoryImpl(JpaCustomerRepository jpaCustomerRepository) {
        this.jpaCustomerRepository = jpaCustomerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity customerEntity = CustomerMapper.toEntity(customer);
        jpaCustomerRepository.save(customerEntity);

        return CustomerMapper.toDomain(customerEntity);
    }

    @Override
    public Optional<Customer> findByCPF(String cpf) {
        CPFEmbeddable emb = new CPFEmbeddable(cpf);
        Optional<CustomerEntity> optEntity = jpaCustomerRepository.findByCpf(emb);

        return optEntity.map(CustomerMapper::toDomain);
    }

    @Override
    public Optional<Customer> findById(UUID uuid) {
        Optional<CustomerEntity> optEntity = jpaCustomerRepository.findById(uuid);

        return optEntity.map(CustomerMapper::toDomain);
    }

    @Override
    public List<Customer> list() {
        return CustomerMapper.toDomainList(jpaCustomerRepository.findByAnonymousFalse());
    }

    @Override
    public void delete(UUID id) {
        jpaCustomerRepository.deleteById(id);
    }
}
