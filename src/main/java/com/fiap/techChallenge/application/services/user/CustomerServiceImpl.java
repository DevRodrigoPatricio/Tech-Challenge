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

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customer) {
        Customer customerToDomain = CustomerMapper.toDomain(customer);

        Optional<Customer> optCustomer = customerRepository.findByCPF(customer.cpf());

        if (optCustomer.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        Customer savedAttendant = customerRepository.save(customerToDomain);

        if (savedAttendant.isAnonymous()) {
            return CustomerMapper.toAnonymousDTO(savedAttendant);
        }
        return CustomerMapper.toDTO(savedAttendant);
    }

    @Override
    public CustomerResponseDTO updateCustomer(CustomerRequestDTO customer) {
        Customer domain = CustomerMapper.toDomain(customer);

        domain.setName(customer.name());
        domain.setCpf(customer.cpf());
        domain.setEmail(customer.email());
        domain = customerRepository.save(domain);

        return CustomerMapper.toDTO(domain);
    }

    @Override
    public CustomerResponseDTO searchCustomer(UUID id) {
        Optional<Customer> optCustomer = customerRepository.findById(id);

        if (optCustomer.isEmpty()) {
            throw new EntityNotFoundException("Customer");
        }

        if (optCustomer.get().isAnonymous()) {
            return CustomerMapper.toAnonymousDTO(optCustomer.get());
        }
        return CustomerMapper.toDTO(optCustomer.get());
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
            return CustomerMapper.toAnonymousDTO(optCustomer.get());
        }
        return CustomerMapper.toDTO(optCustomer.get());
    }

    @Override
    public List<CustomerResponseDTO> list() {
        return CustomerMapper.toDtoList(customerRepository.list());
    }

    @Override
    public void delete(UUID id) {
        customerRepository.delete(id);
    }
}
