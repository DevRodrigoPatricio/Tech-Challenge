package com.fiap.techChallenge.utils.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fiap.techChallenge.adapters.outbound.entities.product.ProductEntity;
import com.fiap.techChallenge.domain.core.product.Product;

public class ProductMapper {

    public static Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        Product domain = new Product(entity.getName(), entity.getDescription(), entity.getPrice(),
                entity.getCategory(), entity.getStatus(), entity.getImage());

        domain.setId(entity.getId());
        return domain;
    }

    public static ProductEntity toEntity(Product domain) {
        if (domain == null) {
            return null;
        }

        ProductEntity entity = new ProductEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setStatus(domain.getStatus());
        entity.setCategory(domain.getCategory());
        entity.setImage(domain.getImage());

        return entity;
    }

    public static List<Product> toDomainList(List<ProductEntity> entities) {
        List<Product> domainList = new ArrayList<>();

        domainList.addAll(
                entities.stream()
                        .map(ProductMapper::toDomain)
                        .collect(Collectors.toList())
        );

        return domainList;
    }
}
