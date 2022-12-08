package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private PageImpl<Product> page;
	private Product product;
	private Category category;
	private ProductDTO productDto;
	
	
	@BeforeEach
	void setup() throws Exception {
		//O valor em si tanto faz, o que vai definir a resposta é chamada do Mockito
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 3L;
		product = Factory.createProduct();
		productDto = Factory.createProductDTO();
		category = Factory.createCategory();
		page = new PageImpl<>(List.of(product));
		
		//Find all
		Mockito.when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
		
		//Save
		Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);
		
		//Find by id
		Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		//Product Get One
		Mockito.when(productRepository.getOne(existingId)).thenReturn(product);
		Mockito.when(productRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		//Category Get One
		Mockito.when(categoryRepository.getOne(existingId)).thenReturn(category);
		Mockito.when(categoryRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.doNothing().when(productRepository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, productDto);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).getOne(nonExistingId);
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExist() {
		
		ProductDTO result = service.update(existingId, productDto);
		
		Assertions.assertNotNull(result);
		
		Mockito.verify(productRepository, Mockito.times(1)).getOne(existingId);
		
	}

	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).findById(nonExistingId);
		
	}
	
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExist() {
		
		ProductDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);;
		
		Mockito.verify(productRepository, Mockito.times(1)).findById(existingId);
		
	}
	
	
	@Test
	public void findAllPagedShouldReturnPage() {
		
		Pageable pageable = PageRequest.of(0, 10);
	
		Page<ProductDTO> result = service.findAllPaged(pageable);
		
		Assertions.assertNotNull(result);
		Mockito.verify(productRepository, Mockito.times(1)).findAll(pageable);
		
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		
		Assertions.assertThrows(DatabaseException.class ,() -> {
			service.delete(dependentId);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependentId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class ,() -> {
			service.delete(nonExistingId);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(nonExistingId);
	}
	
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);
	}

}
