package com.fiap.techChallenge.utils.mappers;

import com.fiap.techChallenge.adapters.outbound.entities.ProductEntity;
import com.fiap.techChallenge.domain.product.Product;

public class ProductMapper {

    public static Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        Product domain = new Product(entity.getName(), entity.getDescription(), entity.getPrice(), entity.getQuantity(),
                entity.getCategory(), entity.getImage());

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
        entity.setQuantity(domain.getQuantity());
        entity.setCategory(domain.getCategory());
        entity.setImage(domain.getImage());

        return entity;
    }
}
