package com.fiap.techChallenge.application.services.product;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fiap.techChallenge.application.useCases.product.ProductUseCase;
import com.fiap.techChallenge.domain.core.product.Product;
import com.fiap.techChallenge.domain.core.product.ProductRepository;
import com.fiap.techChallenge.domain.enums.Category;
import com.fiap.techChallenge.domain.enums.ProductStatus;
import com.fiap.techChallenge.domain.exceptions.EntityNotFoundException;
import com.fiap.techChallenge.domain.exceptions.product.NameAlreadyRegisteredException;
import com.fiap.techChallenge.domain.exceptions.product.ProductNotAvaiableException;

@Service
public class ProductServiceImpl implements ProductUseCase {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {

        if (product.getId() == null) {
            this.validateName(product.getName());

        } else {
            this.validateUpdate(product);
        }

        return repository.save(product);
    }

    @Override
    public Product findById(UUID id) {
        return repository.findById(id).orElse(Product.empty());
    }

    @Override
    public Product validate(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto"));
    }

    @Override
    public Product findByName(String name) {
        return repository.findByName(name).orElse(Product.empty());
    }

    @Override
    public Product findAvailableProductById(UUID productId) {
        Product product = repository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Produto"));

        if (product.getStatus().compareTo(ProductStatus.DISPONIVEL) != 0) {
            throw new ProductNotAvaiableException();
        }

        return product;
    }

    @Override
    public List<Product> list() {
        return repository.list();
    }

    @Override
    public List<Product> listAvailables() {
        return repository.listByStatus(ProductStatus.DISPONIVEL);
    }

    @Override
    public List<Product> listByCategory(Category category) {
        return repository.listByCategory(category);
    }

    @Override
    public List<Product> listAvailablesByCategory(Category category) {
        return repository.listByStatusAndCategory(ProductStatus.DISPONIVEL, category);
    }

    @Override
    public void delete(UUID id) {
        repository.delete(id);
    }

    @Override
    public void deleteByCategory(Category category) {
        repository.deleteByCategory(category);
    }

    @Override
    public List<Category> listAvailableCategorys() {
        List<Category> availableCategories = repository.listAvailableCategorys();

        List<Category> orderedCategories = Arrays.stream(Category.values())
                .filter(availableCategories::contains)
                .toList();

        return orderedCategories;
    }

    public void validateName(String name) {
        Optional<Product> existingProduct = repository.findByName(name);

        if (!existingProduct.isEmpty()) {
            throw new NameAlreadyRegisteredException(name);
        }
    }

    public void validateUpdate(Product product) {
        Optional<Product> existingProduct = repository.findByName(product.getName());

        if (!existingProduct.isPresent()) {
            throw new EntityNotFoundException("Produto");
        }

        if (!existingProduct.get().getId().equals(product.getId())) {
            throw new NameAlreadyRegisteredException(product.getName());
        }
    }

}
