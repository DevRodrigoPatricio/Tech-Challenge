package com.fiap.techChallenge.adapters.inbound.controllers.user;

import com.fiap.techChallenge.application.dto.user.CustomerResponseDTO;
import com.fiap.techChallenge.application.services.user.CustomerServiceImpl;
import com.fiap.techChallenge.application.dto.user.CustomerRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/user/customer")
@Tag(name = "Customer", description = "APIs relacionadas aos Clientes")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    public ResponseEntity<CustomerResponseDTO> create(@RequestBody @Valid CustomerRequestDTO customerRequest) {
        CustomerResponseDTO customer = customerService.createCustomer(customerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable("id") UUID id) {
        CustomerResponseDTO customer = customerService.searchCustomer(id);

        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<CustomerResponseDTO> findByCpf(@PathVariable("cpf") String cpf) {
        CustomerResponseDTO customer = customerService.searchCustomer(cpf);

        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
}
