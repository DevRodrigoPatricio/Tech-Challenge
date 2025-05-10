package com.fiap.techChallenge.adapters.outbound.storage.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fiap.techChallenge.domain.category.Category;
import com.fiap.techChallenge.domain.category.CategoryRepository;
import com.fiap.techChallenge.domain.category.CategoryRequest;
import com.fiap.techChallenge.utils.exceptions.NameAlreadyRegisteredException;

@Component
public class CategoryAdapter implements CategoryPort {

    private final CategoryRepository repository;

    public CategoryAdapter(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category save(CategoryRequest request) {
        Category category = new Category(request.getName());

        this.validateName(request.getName());

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
        // LÓGICA PARA NÃO DELETAR CATEGORIA QUE POSSUI PRODUTO CADASTRADO

        repository.delete(id);
    }

    private void validateName(String name) {
        Optional<Category> existingCategory = repository.findByName(name);

        if (!existingCategory.isEmpty()) {
            throw new NameAlreadyRegisteredException(name);
        }
    }
}
