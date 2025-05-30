package com.fiap.techChallenge.adapters.inbound.controllers.product;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.fiap.techChallenge.application.services.product.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fiap.techChallenge.domain.core.product.Product;
import com.fiap.techChallenge.domain.enums.Category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product", description = "APIs relacionadas ao Produto")
public class ProductController {

    private final ProductServiceImpl service;

    public ProductController(ProductServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/save")
    @Operation(summary = "Save",
            description = "Salva um produto")
    public ResponseEntity<Product> save(@RequestBody @Valid Product product) {
        return ResponseEntity.ok(service.save(product));
    }

    @GetMapping("/find-by-id/{id}")
    @Operation(summary = "Find By ID",
            description = "Encontra um produto pelo ID Informado")
    public ResponseEntity<Product> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-by-name/{name}")
    @Operation(summary = "Find By Name", description = "Encontra um produto pelo Nome Informado")
    public ResponseEntity<Product> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping("/list-categorys")
    @Operation(summary = "List Categorys",
            description = "Lista todas as categoriasdisponiveis")
    public ResponseEntity<List<Category>> listCategorys() {
        return ResponseEntity.ok(Arrays.asList(Category.values()));
    }

    @GetMapping("/list-availables-categorys")
    @Operation(summary = "List Availables Categorys",
            description = "Lista todas as categorias com produtos disponiveis")
    public ResponseEntity<List<Category>> listAvailableCategorys() {
        return ResponseEntity.ok(service.listAvailableCategorys());
    }

    @GetMapping("/list")
    @Operation(summary = "List",
            description = "Lista todos os produtos")
    public ResponseEntity<List<Product>> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/list-availables")
    @Operation(summary = "List Availables",
            description = "Lista todos os produtos disponiveis")
    public ResponseEntity<List<Product>> listAvaiables() {
        return ResponseEntity.ok(service.listAvailables());
    }

    @GetMapping("/list-by-category/{category}")
    @Operation(summary = "List By Category", description = "Lista todos os produtos da categoria informada")
    public ResponseEntity<List<Product>> listByCategory(@PathVariable Category category
    ) {
        return ResponseEntity.ok(service.listByCategory(category));
    }

    @GetMapping("/list-availables-by-category/{category}")
    @Operation(summary = "List Availables",
            description = "Lista todos os produtos disponiveis da categoria informada")
    public ResponseEntity<List<Product>> listAvaiablesByCategory(@PathVariable Category category
    ) {
        return ResponseEntity.ok(service.listAvailablesByCategory(category));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete",
            description = "Deleta um produto")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok("Produto deletado com sucesso");
    }

    @DeleteMapping("/delete-by-category-id/{category}")
    @Operation(summary = "Delete By Category",
            description = "Deleta os produtos da categoria informada")
    public ResponseEntity<String> deleteByCategory(@PathVariable Category category) {
        service.deleteByCategory(category);
        return ResponseEntity.ok("Produtos deletados com sucesso");
    }

}
