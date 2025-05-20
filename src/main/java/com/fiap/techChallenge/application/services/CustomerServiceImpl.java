package com.fiap.techChallenge.application.services;

import com.fiap.techChallenge.application.useCases.CustomerUseCase;
import com.fiap.techChallenge.domain.user.customer.Customer;
import com.fiap.techChallenge.domain.user.customer.CustomerRepository;
import com.fiap.techChallenge.domain.user.customer.CustomerRequestDTO;
import com.fiap.techChallenge.utils.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.utils.exceptions.UserAlreadyExistsException;
import com.fiap.techChallenge.utils.mappers.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Customer createCustomer(CustomerRequestDTO customer) {
        Customer customerToDomain = customerMapper.toDomain(customer);

        Optional<Customer> optCustomer = customerRepository.findByCPF(customer.cpf());

        if (optCustomer.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        return customerRepository.save(customerToDomain);
    }

    @Override
    public Customer searchCustomer(UUID id) {
        Optional<Customer> optCustomer = customerRepository.findById(id);

        if (optCustomer.isEmpty()) {
            throw new EntityNotFoundException("Customer");
        }

        return optCustomer.get();
    }

    @Override
    public Customer searchCustomer(String cpf) {
        Optional<Customer> optCustomer = customerRepository.findByCPF(cpf);

        if (optCustomer.isEmpty()) {
            throw new EntityNotFoundException("Customer");
        }

        return optCustomer.get();
    }
}
