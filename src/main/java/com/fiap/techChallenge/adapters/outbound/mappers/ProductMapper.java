package com.fiap.techChallenge.adapters.outbound.mappers;

import com.fiap.techChallenge.adapters.outbound.entities.ProductEntity;
import com.fiap.techChallenge.domain.product.Product;

public class ProductMapper {

    public static Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        Product domain = new Product(entity.getNome(), entity.getDescricao(), entity.getPreco(), entity.getQuantidade(),
                entity.getCategoria(), entity.getImagem());

        return domain;
    }

    public static ProductEntity toEntity(Product domain) {
        if (domain == null) {
            return null;
        }

        ProductEntity entity = new ProductEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setDescricao(domain.getDescricao());
        entity.setPreco(domain.getPreco());
        entity.setQuantidade(domain.getQuantidade());
        entity.setCategoria(domain.getCategoria());
        entity.setImagem(domain.getImagem());

        return entity;
    }
}
