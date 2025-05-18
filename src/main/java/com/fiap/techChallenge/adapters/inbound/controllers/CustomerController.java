package com.fiap.techChallenge.adapters.inbound.controllers;

import com.fiap.techChallenge.application.services.CustomerServiceImpl;
import com.fiap.techChallenge.domain.customer.Customer;
import com.fiap.techChallenge.domain.customer.CustomerRequestDTO;
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

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    public ResponseEntity<Customer> create(@RequestBody @Valid CustomerRequestDTO customerRequest) {
        Customer customer = customerService.createCustomer(customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Customer> findById(@PathVariable("id") UUID id) {
        Customer customer = customerService.searchCustomer(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Customer> findByCpf(@PathVariable("cpf") String cpf) {
        Customer customer = customerService.searchCustomer(cpf);
        return ResponseEntity.ok(customer);
    }
}
