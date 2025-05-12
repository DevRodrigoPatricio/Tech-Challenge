package com.fiap.techChallenge.utils.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fiap.techChallenge.adapters.outbound.entities.CategoryEntity;
import com.fiap.techChallenge.domain.category.Category;

public class CategoryMapper {

    public static Category toDomain(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }

        Category domain = new Category(entity.getName(), entity.getDisplayOrder());

        domain.setId(entity.getId());
        return domain;
    }

    public static CategoryEntity toEntity(Category domain) {
        if (domain == null) {
            return null;
        }

        CategoryEntity entity = new CategoryEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDisplayOrder(domain.getDisplayOrder());

        return entity;
    }

    public static List<Category> toDomainList(List<CategoryEntity> entities) {
        List<Category> domainList = new ArrayList<>();

        domainList.addAll(
                entities.stream()
                        .map(CategoryMapper::toDomain)
                        .collect(Collectors.toList())
        );

        return domainList;
    }

    public static List<CategoryEntity> toEntityList(List<Category> domainList) {
        List<CategoryEntity> entities = new ArrayList<>();

        entities.addAll(
                domainList.stream()
                        .map(CategoryMapper::toEntity)
                        .collect(Collectors.toList())
        );

        return entities;
    }
}
