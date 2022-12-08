package com.devsuperior.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository productRepository;
	
	private long existingId;
	private long nonExistingId;
	
	
	@BeforeEach
	void setup() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		
		Mockito.doNothing().when(productRepository).deleteById(existingId);
		Mockito.doThrow(ResourceNotFoundException.class).when(productRepository).deleteById(nonExistingId);
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
