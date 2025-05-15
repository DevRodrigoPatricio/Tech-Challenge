package com.fiap.techChallenge.adapters.outbound.repositories.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fiap.techChallenge.adapters.outbound.entities.ProductEntity;
import com.fiap.techChallenge.domain.enums.Category;
import com.fiap.techChallenge.domain.enums.ProductStatus;
import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.product.ProductRepository;
import com.fiap.techChallenge.utils.mappers.ProductMapper;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository repository;

    public ProductRepositoryImpl(JpaProductRepository jpaRepository) {
        this.repository = jpaRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductMapper.toEntity(product);
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
    public List<Product> listByCategory(Category category) {
        return ProductMapper.toDomainList(repository.findByCategory(category));
    }

    @Override
    public List<Product> listByStatusAndCategory(ProductStatus status, Category category) {
        return ProductMapper.toDomainList(repository.findByStatusAndCategory(status, category));
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByCategory(Category category) {
        repository.deleteByCategory(category);
    }

    @Override
    public List<Category> listAvaiableCategorys() {
        return repository.listCategorysByProductStatus(ProductStatus.DISPONIVEL);
    }

}
