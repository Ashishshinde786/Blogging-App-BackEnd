package com.ashish.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashish.blog.entities.Category;
import com.ashish.blog.exceptions.ResourceNotFoundException;
import com.ashish.blog.peyloads.CategoryDto;
import com.ashish.blog.repositories.CategoryRepo;
import com.ashish.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat,CategoryDto.class);
	}

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        // Find the existing category by ID
        Category cat = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category ","Category Id",categoryId));

        // Update the existing category's fields with new values
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());

        // Save the updated category
        Category updatedCat = categoryRepo.save(cat);

        // Map to DTO and return
        return modelMapper.map(updatedCat, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        // Check if the category exists
        Category cat = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category ","Category Id",categoryId));

        // Delete the category
        categoryRepo.delete(cat);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        // Find the category by ID
        Category cat = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category ","Category Id",categoryId));

        // Map to DTO and return
        return this.modelMapper.map(cat, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        // Get all categories
        List<Category> categories = categoryRepo.findAll();

//        // Map to DTOs and return
//        return categories.stream()
//                .map(category -> modelMapper.map(category, CategoryDto.class))
//                .collect(Collectors.toList());
        List<CategoryDto> catDtos = categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        
        return catDtos;
    }
	

}
