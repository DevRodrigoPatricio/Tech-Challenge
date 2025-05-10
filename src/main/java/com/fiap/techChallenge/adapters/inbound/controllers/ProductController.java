package com.fiap.techChallenge.adapters.inbound.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fiap.techChallenge.application.services.ProductService;
import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.product.ProductRequest;
import com.fiap.techChallenge.utils.exceptions.NameAlreadyRegisteredException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product", description = "APIs relacionadas ao Produto")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/save")
    @Operation(summary = "Save",
            description = "Cria um novo produto")
    public ResponseEntity<Product> save(@RequestBody ProductRequest product) {
        return ResponseEntity.ok(service.save(product));
    }

    @GetMapping("/find-by-id/{id}")
    @Operation(summary = "Find By ID",
            description = "Encontra um produto pelo ID Informado")
    public ResponseEntity<Optional<Product>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-by-name/{name}")
    @Operation(summary = "Find By Name",
            description = "Encontra um produto pelo Nome Informado")
    public ResponseEntity<Optional<Product>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping("/list")
    @Operation(summary = "List",
            description = "Lista todos os produtos")
    public ResponseEntity<List<Product>> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/list-avaiables")
    @Operation(summary = "List Avaiables",
            description = "Lista todos os produtos disponiveis")
    public ResponseEntity<List<Product>> listAvaiables() {
        return ResponseEntity.ok(service.listAvaiables());
    }

    @GetMapping("/list-by-category/{categoryId}")
    @Operation(summary = "List By Category",
            description = "Lista todos os produtos da categoria informada")
    public ResponseEntity<List<Product>> listByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(service.listByCategory(categoryId));
    }

    @GetMapping("/list-avaiables-by-category/{categoryId}")
    @Operation(summary = "List Avaiables",
            description = "Lista todos os produtos disponiveis da categoria informada")
    public ResponseEntity<List<Product>> listAvaiablesByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(service.listAvaiablesByCategory(categoryId));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleInvalidEnumValueException(HttpMessageNotReadableException e) {
        String message = e.getMessage();

        if (e.getCause() instanceof InvalidFormatException formatEx && formatEx.getTargetType().isEnum()) {
            Class<?> enumClass = formatEx.getTargetType();
            Object[] constants = enumClass.getEnumConstants();
            String correctValues = Arrays.toString(constants);
            String field = formatEx.getPath().get(0).getFieldName();

            message = String.format(
                    "Valor inválido para o campo '%s'. Valores aceitos: %s",
                    field,
                    correctValues
            );
        }

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler({NameAlreadyRegisteredException.class})
    public ResponseEntity<String> handleNameAlreadyRegisteredException(NameAlreadyRegisteredException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
