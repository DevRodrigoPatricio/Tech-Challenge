package com.fiap.techChallenge.adapters.outbound.repositories.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fiap.techChallenge.adapters.outbound.entities.ProductEntity;
import com.fiap.techChallenge.domain.enums.ProductStatus;
import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.product.ProductRepository;
import com.fiap.techChallenge.utils.mappers.ProductMapper;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository jpaRepository;

    public ProductRepositoryImpl(JpaProductRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = new ProductEntity(product);
        entity = jpaRepository.save(entity);

        return ProductMapper.toDomain(entity);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id).map(ProductMapper::toDomain);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return jpaRepository.findByName(name).map(ProductMapper::toDomain);
    }

    @Override
    public List<Product> list() {
        return ProductMapper.toDomainList(jpaRepository.findAll());
    }

    @Override
    public List<Product> listByStatus(ProductStatus status) {
        return ProductMapper.toDomainList(jpaRepository.findByStatus(status));
    }

    @Override
    public List<Product> listByCategory(String category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Product> listByStatusAndCategory(ProductStatus status, String category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Product product) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
