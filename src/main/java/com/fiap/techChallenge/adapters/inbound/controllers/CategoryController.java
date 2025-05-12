package com.fiap.techChallenge.adapters.inbound.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.techChallenge.adapters.outbound.repositories.category.CategoryRepositoryImpl.CategoryNotFoundException;
import com.fiap.techChallenge.application.services.CategoryService;
import com.fiap.techChallenge.domain.category.Category;
import com.fiap.techChallenge.utils.exceptions.NameAlreadyRegisteredException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/category")
@Tag(name = "Category", description = "APIs relacionadas às Categorias")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping("/save")
    @Operation(summary = "Save",
            description = "Salva uma categoria")
    public ResponseEntity<Category> save(@RequestBody Category category) {
        return ResponseEntity.ok(service.save(category));
    }

    @GetMapping("/find-by-id/{id}")
    @Operation(summary = "Find By ID",
            description = "Encontra uma categoria pelo ID Informado")
    public ResponseEntity<Optional<Category>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/list")
    @Operation(summary = "List",
            description = "Lista todas as categorias")
    public ResponseEntity<List<Category>> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "Delete",
            description = "Deleta a Categoria informada")
    public ResponseEntity<Optional<Category>> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({NameAlreadyRegisteredException.class})
    public ResponseEntity<String> handleNameAlreadyRegisteredException(NameAlreadyRegisteredException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({CategoryNotFoundException.class})
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
