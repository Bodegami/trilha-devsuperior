package com.devsuperior.dscatalog.resources;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;

@WebMvcTest(ProductResource.class)
public class ProductResourceTests {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService service;
	
	private long existingId;
	private long nonExistingId;
	private ProductDTO productDTO;
	private PageImpl<ProductDTO> page;
	
	@BeforeEach
	void setup() throws Exception {
		
		existingId = 1L;
		nonExistingId = 1000L;
		productDTO = Factory.createProductDTO();
		page = new PageImpl<>(List.of(productDTO));
		
		Mockito.when(service.findAllPaged(ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(service.findById(existingId)).thenReturn(productDTO);
		Mockito.when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		
	}
	
	@Test
	public void findByIdShouldReturnProductWhenIdExists() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", existingId)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.description").exists());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdExists() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", nonExistingId)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(MockMvcResultMatchers.status().isNotFound());	
	}
	
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(MockMvcRequestBuilders.get("/products")
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
}
