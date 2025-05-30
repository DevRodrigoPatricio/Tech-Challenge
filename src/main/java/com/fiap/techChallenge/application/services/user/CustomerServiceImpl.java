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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerMapper customerMapper) {
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

        if (savedAttendant.isAnonymous()) {
            return customerMapper.toAnonymousDTO(savedAttendant);
        }
        return customerMapper.toDTO(savedAttendant);
    }

    @Override
    public CustomerResponseDTO updateCustomer(CustomerRequestDTO customer) {
        Customer domain = customerMapper.toDomain(customer);

        domain.setName(customer.name());
        domain.setCpf(customer.cpf());
        domain.setEmail(customer.email());
        domain = customerRepository.save(domain);

        return customerMapper.toDTO(domain);
    }

    @Override
    public CustomerResponseDTO searchCustomer(UUID id) {
        Optional<Customer> optCustomer = customerRepository.findById(id);

        if (optCustomer.isEmpty()) {
            throw new EntityNotFoundException("Customer");
        }

        if (optCustomer.get().isAnonymous()) {
            return customerMapper.toAnonymousDTO(optCustomer.get());
        }
        return customerMapper.toDTO(optCustomer.get());
    }

    @Override
    public Customer validate(UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente"));

        return customer;
    }

    @Override
    public Customer validate(String cpf) {
        Customer customer = customerRepository.findByCPF(cpf).orElseThrow(() -> new EntityNotFoundException("Cliente"));

        return customer;
    }

    @Override
    public CustomerResponseDTO searchCustomer(String cpf) {
        Optional<Customer> optCustomer = customerRepository.findByCPF(cpf);

        if (optCustomer.isEmpty()) {
            throw new EntityNotFoundException("Customer");
        }

        if (optCustomer.get().isAnonymous()) {
            return customerMapper.toAnonymousDTO(optCustomer.get());
        }
        return customerMapper.toDTO(optCustomer.get());
    }

    @Override
    public List<CustomerResponseDTO> list() {
        return customerMapper.toDtoList(customerRepository.list());
    }

    @Override
    public void delete(UUID id) {
        customerRepository.delete(id);
    }
}
