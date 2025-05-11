package com.fiap.techChallenge.adapters.outbound.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fiap.techChallenge.adapters.outbound.storage.category.CategoryAdapter;
import com.fiap.techChallenge.domain.category.Category;
import com.fiap.techChallenge.domain.category.CategoryRepository;
import com.fiap.techChallenge.domain.category.CategoryRequest;
import com.fiap.techChallenge.domain.product.ProductRepository;
import com.fiap.techChallenge.utils.exceptions.NameAlreadyRegisteredException;

public class CategoryAdapterTest {

    private CategoryRepository repository;
    private ProductRepository productRepository;
    private CategoryAdapter adapter;

    private final UUID categoryId = UUID.randomUUID();

    @BeforeEach
    @SuppressWarnings("unused")
    void setup() {
        repository = mock(CategoryRepository.class);
        productRepository = mock(ProductRepository.class);
        adapter = new CategoryAdapter(repository, productRepository);
    }

    @Test
    public void shouldThrowExceptionWhenNameAlreadyExists() {
        String existingName = "Lanche";

        when(repository.findByName(existingName)).thenReturn(Optional.of(new Category()));
        NameAlreadyRegisteredException exception
                = assertThrows(NameAlreadyRegisteredException.class, () -> adapter.validateName(existingName));

        assertEquals(String.format("O nome '%s' já foi cadastrado", existingName), exception.getMessage());
    }

    @Test
    public void shouldCallValidateNameWhenSavingCategory() {
        String name = "Lanche";
        CategoryRequest request = new CategoryRequest(name);

        when(repository.findByName(name)).thenReturn(Optional.empty());
        CategoryAdapter spyAdapter = spy(new CategoryAdapter(repository, productRepository));

        spyAdapter.save(request);
        verify(spyAdapter).validateName(name);
    }

    @Test
    public void shouldSaveCategorySuccessfully() {
        String name = "Lanche";
        CategoryRequest request = new CategoryRequest(name);
        Category newCategory = new Category(name);

        when(repository.findByName(name)).thenReturn(Optional.empty());
        when(repository.save(any(Category.class))).thenReturn(newCategory);

        Category saved = adapter.save(request);

        assertEquals(name, saved.getName());
    }

    @Test
    public void shouldFindCategoryById() {
        Category category = new Category("Lanche");

        when(repository.findById(categoryId)).thenReturn(Optional.of(category));

        Optional<Category> result = adapter.findById(categoryId);

        assertTrue(result.isPresent());
        assertEquals("Lanche", result.get().getName());
    }

    @Test
    public void shouldListAllCategories() {
        List<Category> categories = List.of(new Category("Lanche"), new Category("Bebida"));

        when(repository.list()).thenReturn(categories);

        List<Category> result = adapter.list();

        assertEquals(2, result.size());
        verify(repository).list();
    }

    @Test
    void shouldDeleteCategoryById() {
        adapter.delete(categoryId);

        verify(repository).delete(categoryId);
    }

    @Test
    public void shouldCallExistsProductInCategoryWhenDeletingCategory() {
        CategoryAdapter spyAdapter = spy(new CategoryAdapter(repository, productRepository));

        doReturn(false).when(spyAdapter).existsProductInCategory(categoryId);
        spyAdapter.delete(categoryId);

        verify(spyAdapter).existsProductInCategory(categoryId);
    }
}
