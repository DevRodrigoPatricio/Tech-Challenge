package com.fiap.techChallenge.adapters.outbound.storage.category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fiap.techChallenge.domain.category.Category;
import com.fiap.techChallenge.domain.category.CategoryRepository;
import com.fiap.techChallenge.domain.product.ProductRepository;
import com.fiap.techChallenge.utils.exceptions.CategoryHasProductsException;
import com.fiap.techChallenge.utils.exceptions.NameAlreadyRegisteredException;

@Component
public class CategoryAdapter implements CategoryPort {

    private final CategoryRepository repository;
    private final ProductRepository productRepository;

    public CategoryAdapter(CategoryRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    @Override
    public Category save(Category category) {

        if (category.getId() == null) {
            this.validateName(category.getName());

        } else {
            this.validateUpdate(category);
        }

        this.organizeDisplayOrderToSave(category);
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
        if (this.existsProductInCategory(id)) {
            throw new CategoryHasProductsException();
        }

        this.organizeDisplayOrderToDelete(repository.validate(id));
        repository.delete(id);
    }

    public void validateName(String name) {
        Optional<Category> existingCategory = repository.findByName(name);

        if (!existingCategory.isEmpty()) {
            throw new NameAlreadyRegisteredException(name);
        }
    }

    public Boolean existsProductInCategory(UUID id) {
        return !productRepository.listByCategory(id).isEmpty();
    }

    public void validateUpdate(Category category) {
        Optional<Category> existingCategory = repository.findByName(category.getName());

        if (existingCategory.isPresent() && !existingCategory.get().getId().equals(category.getId())) {
            throw new NameAlreadyRegisteredException(category.getName());
        }
    }

    private void organizeDisplayOrderToSave(Category category) {
        int newDisplayOrder = category.getDisplayOrder();
        int lastDisplayOrder = repository.findLastDisplayOrder();

        if (newDisplayOrder <= 0) {
            newDisplayOrder = 1;

        } else if (newDisplayOrder > lastDisplayOrder + 1) {
            newDisplayOrder = lastDisplayOrder + 1;
        }

        boolean isUpdate = category.getId() != null && repository.existsById(category.getId());
        Integer currentDisplayOrder = isUpdate
                ? repository.findById(category.getId())
                        .map(Category::getDisplayOrder)
                        .orElse(null)
                : null;

        if (isUpdate && currentDisplayOrder != null) {
            if (newDisplayOrder == currentDisplayOrder) {
                return;
            }

            List<Category> affected;

            if (newDisplayOrder < currentDisplayOrder) {
                affected = repository.findByDisplayOrderRange(newDisplayOrder, currentDisplayOrder - 1);
                affected.forEach(c -> c.setDisplayOrder(c.getDisplayOrder() + 1));
            } else {
                affected = repository.findByDisplayOrderRange(currentDisplayOrder + 1, newDisplayOrder);
                affected.forEach(c -> c.setDisplayOrder(c.getDisplayOrder() - 1));
            }

            repository.saveAll(removeCurrentCategoryFromAffectedList(affected, category.getId()));
        } else {
            if (newDisplayOrder > lastDisplayOrder) {
                newDisplayOrder = lastDisplayOrder + 1;
            } else {
                List<Category> affected = repository.findByDisplayOrderRange(newDisplayOrder, lastDisplayOrder);
                affected.forEach(c -> c.setDisplayOrder(c.getDisplayOrder() + 1));

                repository.saveAll(removeCurrentCategoryFromAffectedList(affected, category.getId()));
            }
        }

        category.setDisplayOrder(newDisplayOrder);
    }

    private void organizeDisplayOrderToDelete(Category category) {
        int removedDisplayOrder = category.getDisplayOrder();
        int lastDisplayOrder = repository.findLastDisplayOrder();

        if (removedDisplayOrder < lastDisplayOrder) {
            List<Category> affected = repository.findByDisplayOrderRange(removedDisplayOrder + 1, lastDisplayOrder);
            affected.forEach(c -> c.setDisplayOrder(c.getDisplayOrder() - 1));
            repository.saveAll(affected);
        }
    }

    private List<Category> removeCurrentCategoryFromAffectedList(List<Category> affectedList, UUID currentCategoryId) {
        return affectedList.stream()
                .filter(c -> !c.getId().equals(currentCategoryId))
                .toList();
    }
}
