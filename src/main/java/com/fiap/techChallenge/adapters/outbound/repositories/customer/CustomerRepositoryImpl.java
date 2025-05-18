package com.fiap.techChallenge.adapters.outbound.repositories.customer;

import com.fiap.techChallenge.adapters.outbound.entities.CPFEmbeddable;
import com.fiap.techChallenge.adapters.outbound.entities.CustomerEntity;
import com.fiap.techChallenge.domain.customer.Customer;
import com.fiap.techChallenge.domain.customer.CustomerRepository;
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
        CustomerEntity customerEntity = new CustomerEntity(customer);
        jpaCustomerRepository.save(customerEntity);

        return new Customer(customerEntity);
    }

    @Override
    public Optional<Customer> findByCPF(String cpf) {
        CPFEmbeddable emb = new CPFEmbeddable(cpf);
        Optional<CustomerEntity> optEntity = jpaCustomerRepository.findByCpf(emb);
        return optEntity.map(Customer::new);
    }

    @Override
    public Optional<Customer> findById(UUID uuid) {
        Optional<CustomerEntity> optEntity = jpaCustomerRepository.findById(uuid);
        return optEntity.map(Customer::new);
    }
}
