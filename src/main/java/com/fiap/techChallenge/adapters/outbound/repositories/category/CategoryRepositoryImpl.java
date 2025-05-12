package com.fiap.techChallenge.adapters.outbound.repositories.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fiap.techChallenge.adapters.outbound.entities.CategoryEntity;
import com.fiap.techChallenge.domain.category.Category;
import com.fiap.techChallenge.domain.category.CategoryRepository;
import com.fiap.techChallenge.utils.exceptions.DomainException;
import com.fiap.techChallenge.utils.mappers.CategoryMapper;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JpaCategoryRepository repository;

    public CategoryRepositoryImpl(JpaCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category save(Category category) {
        CategoryEntity entity = CategoryMapper.toEntity(category);
        entity = repository.save(entity);

        return CategoryMapper.toDomain(entity);
    }

    @Override
    public List<Category> saveAll(List<Category> categoryList) {
        List<CategoryEntity> entities = CategoryMapper.toEntityList(categoryList);
        return CategoryMapper.toDomainList(repository.saveAll(entities));
    }

    @Override
    public int findLastDisplayOrder() {
        Optional<CategoryEntity> optional = repository.findTopByOrderByDisplayOrderDesc();

        if (optional.isPresent()) {
            CategoryEntity category = repository.findTopByOrderByDisplayOrderDesc().orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));
            return category.getDisplayOrder();
        }

        return 0;
    }

    @Override
    public Optional<Category> findById(UUID id) {
        return repository.findById(id).map(CategoryMapper::toDomain);
    }

    @Override
    public Boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public Category validate(UUID id) {
        return CategoryMapper.toDomain(repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada pelo ID: " + id)));
    }

    @Override
    public Optional<Category> findByName(String name) {
        return repository.findByName(name).map(CategoryMapper::toDomain);
    }

    @Override
    public List<Category> list() {
        return CategoryMapper.toDomainList(repository.findAllByOrderByDisplayOrderAsc());
    }

    @Override
    public List<Category> listByDisplayOrder(int displayOrder) {
        return CategoryMapper.toDomainList(repository.findByDisplayOrderGreaterThanEqualOrderByDisplayOrderAsc(displayOrder));
    }

    @Override
    public List<Category> findByDisplayOrderRange(int start, int end) {
        return CategoryMapper.toDomainList(repository.findByDisplayOrderRange(start, end));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public class CategoryNotFoundException extends DomainException {

        public CategoryNotFoundException(String message) {
            super(message);
        }
    }
}
