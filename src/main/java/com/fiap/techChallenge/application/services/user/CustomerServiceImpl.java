package com.fiap.techChallenge.application.services.user;

import com.fiap.techChallenge.application.dto.user.CustomerResponseDTO;
import com.fiap.techChallenge.application.useCases.user.CustomerUseCase;
import com.fiap.techChallenge.domain.user.customer.Customer;
import com.fiap.techChallenge.domain.user.customer.CustomerRepository;
import com.fiap.techChallenge.application.dto.user.CustomerRequestDTO;
import com.fiap.techChallenge.domain.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.domain.exceptions.user.UserAlreadyExistsException;
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
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customer) {
        Customer customerToDomain = customerMapper.toDomain(customer);

        Optional<Customer> optCustomer = customerRepository.findByCPF(customer.cpf());

        if (optCustomer.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        Customer savedAttendant = customerRepository.save(customerToDomain);

        if (savedAttendant.isAnonymous()) return customerMapper.toAnonymousDTO(savedAttendant);
        return customerMapper.toDTO(savedAttendant);
    }

    @Override
    public CustomerResponseDTO searchCustomer(UUID id) {
        Optional<Customer> optCustomer = customerRepository.findById(id);

        if (optCustomer.isEmpty()) {
            throw new EntityNotFoundException("Customer");
        }

        if (optCustomer.get().isAnonymous()) return customerMapper.toAnonymousDTO(optCustomer.get());
        return customerMapper.toDTO(optCustomer.get());
    }

    @Override
    public CustomerResponseDTO searchCustomer(String cpf) {
        Optional<Customer> optCustomer = customerRepository.findByCPF(cpf);

        if (optCustomer.isEmpty()) {
            throw new EntityNotFoundException("Customer");
        }

        if (optCustomer.get().isAnonymous()) return customerMapper.toAnonymousDTO(optCustomer.get());
        return customerMapper.toDTO(optCustomer.get());
    }
}
