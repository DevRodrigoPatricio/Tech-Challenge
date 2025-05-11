package com.fiap.techChallenge.adapters.outbound.storage.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fiap.techChallenge.domain.category.Category;
import com.fiap.techChallenge.domain.category.CategoryRepository;
import com.fiap.techChallenge.domain.product.ProductRepository;
import com.fiap.techChallenge.utils.exceptions.CategoryHasProductsException;
import com.fiap.techChallenge.utils.exceptions.NameAlreadyRegisteredException;

@Component
public class CategoryAdapter implements CategoryPort {

    private final CategoryRepository repository;
    private final ProductRepository productRepository;

    public CategoryAdapter(CategoryRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    @Override
    public Category save(Category category) {

        if (category.getId() == null) {
            this.validateName(category.getName());

        } else {
            this.validateUpdate(category);
        }

        return repository.save(category);
    }

    @Override
    public Optional<Category> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Category> list() {
        return repository.list();
    }

    @Override
    public void delete(UUID id) {
        if (this.existsProductInCategory(id)) {
            throw new CategoryHasProductsException();
        }

        repository.delete(id);
    }

    public void validateName(String name) {
        Optional<Category> existingCategory = repository.findByName(name);

        if (!existingCategory.isEmpty()) {
            throw new NameAlreadyRegisteredException(name);
        }
    }

    public Boolean existsProductInCategory(UUID id) {
        return !productRepository.listByCategory(id).isEmpty();
    }

    public void validateUpdate(Category category) {
        Optional<Category> existingCategory = repository.findByName(category.getName());

        if (existingCategory.isPresent() && !existingCategory.get().getId().equals(category.getId())) {
            throw new NameAlreadyRegisteredException(category.getName());
        }
    }
}
