package com.fiap.techChallenge.adapters.outbound.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fiap.techChallenge.adapters.outbound.entities.ProductEntity;
import com.fiap.techChallenge.adapters.outbound.mappers.ProductMapper;
import com.fiap.techChallenge.adapters.outbound.repositories.SpringProductRepository;
import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.product.ProductRepository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final SpringProductRepository springRepository;

    public ProductRepositoryImpl(SpringProductRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Product save(Product produto) {
        ProductEntity entity = new ProductEntity(produto);
        return ProductMapper.toDomain(springRepository.save(entity));
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return springRepository.findById(id).map(ProductMapper::toDomain);
    }

    @Override
    public void delete(Product product) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Product> list() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Product> listAvaiables() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Product> listByCategory(String category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Product> listAvaiablesByCategory(String category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
