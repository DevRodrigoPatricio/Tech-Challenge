package com.fiap.techChallenge.adapters.outbound.storage.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fiap.techChallenge.domain.enums.ProductStatus;
import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.product.ProductRepository;
import com.fiap.techChallenge.domain.product.ProductRequest;
import com.fiap.techChallenge.utils.exceptions.NameAlreadyRegisteredException;

@Component
public class ProductAdapter implements ProductPort {

    private final ProductRepository repository;

    public ProductAdapter(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(ProductRequest request) {
        Product product = new Product(request.getName(), request.getDescription(), request.getPrice(),
                request.getCategory(), request.getStatus(), request.getImage());

        this.validateName(request.getName());

        return repository.save(product);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Product> list() {
        return repository.list();
    }

    @Override
    public List<Product> listAvaiables() {
        return repository.listByStatus(ProductStatus.DISPONIVEL);
    }

    private void validateName(String name) {
        Optional<Product> existingProduct = repository.findByName(name);

        if (!existingProduct.isEmpty()) {
            throw new NameAlreadyRegisteredException(name);
        }

    }
}
