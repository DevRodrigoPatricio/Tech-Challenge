package com.fiap.techChallenge.adapters.inbound.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.techChallenge.adapters.outbound.entities.ProductEntity;
import com.fiap.techChallenge.application.services.ProductService;
import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.utils.mappers.ProductMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produto")
@Tag(name = "Produto", description = "APIs relacionadas ao Produto")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save",
            description = "Cria um novo produto")
    public ProductEntity save(@RequestBody @Valid Product product) {
        return ProductMapper.toEntity(service.save(product));
    }

}
