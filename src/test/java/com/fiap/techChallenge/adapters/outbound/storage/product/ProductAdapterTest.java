package com.fiap.techChallenge.adapters.outbound.storage.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fiap.techChallenge.domain.enums.ProductStatus;
import com.fiap.techChallenge.domain.product.Product;
import com.fiap.techChallenge.domain.product.ProductRepository;
import com.fiap.techChallenge.domain.product.ProductRequest;
import com.fiap.techChallenge.utils.exceptions.NameAlreadyRegisteredException;

public class ProductAdapterTest {

    private ProductRepository repository;
    private ProductAdapter adapter;

    private final UUID productId = UUID.randomUUID();

    @BeforeEach
    @SuppressWarnings("unused")
    void setup() {
        repository = mock(ProductRepository.class);
        adapter = new ProductAdapter(repository);
    }

    @Test
    public void shouldThrowExceptionWhenNameAlreadyExists() {
        String existingName = "Lanche";

        when(repository.findByName(existingName)).thenReturn(Optional.of(new Product()));
        NameAlreadyRegisteredException exception
                = assertThrows(NameAlreadyRegisteredException.class, () -> adapter.validateName(existingName));

        assertEquals(String.format("O nome '%s' já foi cadastrado", existingName), exception.getMessage());
    }

    @Test
    public void shouldCallValidateNameWhenSavingCategory() {
        String name = "X Tudo";
        ProductRequest request = new ProductRequest(name, "Lanche completo",
                new BigDecimal(20.0), UUID.randomUUID(), ProductStatus.DISPONIVEL, "image.jpg");

        when(repository.findByName(name)).thenReturn(Optional.empty());
        ProductAdapter spyAdapter = spy(new ProductAdapter(repository));

        spyAdapter.save(request);
        verify(spyAdapter).validateName(name);
    }

    @Test
    public void shouldSaveProductSuccessfully() {
        String name = "X Tudo";
        String description = "Lanche completo";
        BigDecimal price = new BigDecimal(20.0);
        UUID categoryId = UUID.randomUUID();
        ProductStatus status = ProductStatus.DISPONIVEL;
        String image = "image.jpg";

        ProductRequest request = new ProductRequest(name, description, price, categoryId, status, image);
        Product newProduct = new Product(name, description, price, categoryId, status, image);

        when(repository.findByName(name)).thenReturn(Optional.empty());
        when(repository.save(any(Product.class))).thenReturn(newProduct);

        Product saved = adapter.save(request);
        assertEquals(name, saved.getName());
    }

    @Test
    public void shouldFindProductById() {
        String name = "X Tudo";

        Product product = new Product(name, "Lanche completo", new BigDecimal(20.0), UUID.randomUUID(), ProductStatus.DISPONIVEL, "image.png");

        when(repository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = adapter.findById(productId);

        assertTrue(result.isPresent());
        assertEquals(name, result.get().getName());
    }

    @Test
    public void shouldListAllProducts() {
        Product firstProduct = new Product("X Tudo", "Lanche completo", new BigDecimal(20.0), UUID.randomUUID(), ProductStatus.DISPONIVEL, "image.png");
        Product secondProduct = new Product("X Bacon", "Lanche de Bacon", new BigDecimal(15.5), UUID.randomUUID(), ProductStatus.DISPONIVEL, "image.png");
        List<Product> products = List.of(firstProduct, secondProduct);

        when(repository.list()).thenReturn(products);

        List<Product> result = adapter.list();

        assertEquals(2, result.size());
        verify(repository).list();
    }

    @Test
    public void shouldListAllAvaiableProducts() {
        ProductStatus avaiableStatus = ProductStatus.DISPONIVEL;

        Product firstProduct = new Product("X Tudo", "Lanche completo", new BigDecimal(20.0), UUID.randomUUID(), ProductStatus.DISPONIVEL, "image.png");
        Product secondProduct = new Product("X Bacon", "Lanche de Bacon", new BigDecimal(15.5), UUID.randomUUID(), ProductStatus.DISPONIVEL, "image.png");
        List<Product> products = List.of(firstProduct, secondProduct);

        when(repository.listByStatus(avaiableStatus)).thenReturn(products);

        List<Product> result = adapter.listAvaiables();

        assertEquals(2, result.size());
    }

    @Test
    public void shouldListProductsByCategoryId() {
        UUID categoryId = UUID.randomUUID();
        Product firstProduct = new Product("X Tudo", "Lanche completo", new BigDecimal(20.0), categoryId, ProductStatus.DISPONIVEL, "image.png");
        Product secondProduct = new Product("X Bacon", "Lanche de Bacon", new BigDecimal(15.5), categoryId, ProductStatus.DISPONIVEL, "image.png");
        List<Product> products = List.of(firstProduct, secondProduct);

        when(repository.listByCategory(categoryId)).thenReturn(products);

        List<Product> result = adapter.listByCategory(categoryId);

        assertEquals(2, result.size());
    }

    @Test
    public void shouldListAllAvaiableProductsByCategoryId() {
        UUID categoryId = UUID.randomUUID();
        ProductStatus avaiableStatus = ProductStatus.DISPONIVEL;

        Product firstProduct = new Product("X Tudo", "Lanche completo", new BigDecimal(20.0), categoryId, ProductStatus.DISPONIVEL, "image.png");
        Product secondProduct = new Product("X Bacon", "Lanche de Bacon", new BigDecimal(15.5), categoryId, ProductStatus.DISPONIVEL, "image.png");
        Product thirdProduct = new Product("X Salada", "Lanche de Salada", new BigDecimal(13.5), categoryId, ProductStatus.DISPONIVEL, "image.png");
        List<Product> products = List.of(firstProduct, secondProduct, thirdProduct);

        when(repository.listByStatusAndCategory(avaiableStatus, categoryId)).thenReturn(products);

        List<Product> result = adapter.listAvaiablesByCategory(categoryId);

        assertEquals(3, result.size());
    }

    @Test
    void shouldDeleteProductById() {
        adapter.delete(productId);

        verify(repository).delete(productId);
    }

    @Test
    void shouldDeleteProductByCategoryId() {
        UUID categoryId = UUID.randomUUID();
        adapter.deleteByCategoryId(categoryId);

        verify(repository).deleteByCategoryId(categoryId);
    }
}
