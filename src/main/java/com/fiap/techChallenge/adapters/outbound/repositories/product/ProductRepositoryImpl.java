package com.fiap.techChallenge.adapters.outbound.repositories.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fiap.techChallenge.adapters.outbound.entities.CategoryEntity;
import com.fiap.techChallenge.adapters.outbound.entities.ProductEntity;
import com.fiap.techChallenge.domain.category.CategoryRepository;
import com.fiap.techChallenge.domain.enums.ProductStatus;
import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.product.ProductRepository;
import com.fiap.techChallenge.utils.mappers.ProductMapper;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductRepositoryImpl(JpaProductRepository jpaRepository, CategoryRepository categoryRepository) {
        this.repository = jpaRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product save(Product product) {
        CategoryEntity category = categoryRepository.validate(product.getCategoryId());

        ProductEntity entity = ProductMapper.toEntity(product, category);
        entity = repository.save(entity);

        return ProductMapper.toDomain(entity);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return repository.findById(id).map(ProductMapper::toDomain);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return repository.findByName(name).map(ProductMapper::toDomain);
    }

    @Override
    public List<Product> list() {
        return ProductMapper.toDomainList(repository.findAll());
    }

    @Override
    public List<Product> listByStatus(ProductStatus status) {
        return ProductMapper.toDomainList(repository.findByStatus(status));
    }

    @Override
    public List<Product> listByCategory(UUID categoryId) {
        return ProductMapper.toDomainList(repository.findByCategory_Id(categoryId));
    }

    @Override
    public List<Product> listByStatusAndCategory(ProductStatus status, UUID categoryId) {
        return ProductMapper.toDomainList(repository.findByStatusAndCategory_Id(status, categoryId));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
