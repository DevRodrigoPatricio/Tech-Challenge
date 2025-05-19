package com.fiap.techChallenge.adapters.inbound.controllers;

import com.fiap.techChallenge.application.services.CustomerServiceImpl;
import com.fiap.techChallenge.domain.user.customer.Customer;
import com.fiap.techChallenge.domain.user.customer.CustomerRequestDTO;
import com.fiap.techChallenge.utils.mappers.CustomerMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/customer")
@Tag(name = "Customer", description = "APIs relacionadas aos Clientes")
public class CustomerController {

    private final CustomerServiceImpl customerService;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerServiceImpl customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid CustomerRequestDTO customerRequest) {
        Customer customer = customerService.createCustomer(customerRequest);

        if (customer.isAnonymous()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.toAnonymousDTO(customer));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.toDTO(customer));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") UUID id) {
        Customer customer = customerService.searchCustomer(id);

        if (customer.isAnonymous()) {
            return ResponseEntity.status(HttpStatus.OK).body(customerMapper.toAnonymousDTO(customer));
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerMapper.toDTO(customer));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> findByCpf(@PathVariable("cpf") String cpf) {
        Customer user = customerService.searchCustomer(cpf);

        if (user.isAnonymous()) {
            return ResponseEntity.status(HttpStatus.OK).body(customerMapper.toAnonymousDTO(user));
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerMapper.toDTO(user));
    }
}
