package tech.noetzold.ecommerce.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import tech.noetzold.ecommerce.model.Category;
import tech.noetzold.ecommerce.repository.Categoryrepository;

@Service
@Transactional
@Cacheable("category")
public class CategoryService {

	private final Categoryrepository categoryrepository;

	public CategoryService(Categoryrepository categoryrepository) {
		this.categoryrepository = categoryrepository;
	}

	public List<Category> listCategories() {
		return categoryrepository.findAll();
	}

	public void createCategory(Category category) {
		categoryrepository.save(category);
	}

	public Category readCategory(String categoryName) {
		return categoryrepository.findByCategoryName(categoryName);
	}

	public Optional<Category> readCategory(Integer categoryId) {
		return categoryrepository.findById(categoryId);
	}
	@CacheEvict(value = "category", allEntries = true)
	public void updateCategory(Integer categoryID, Category newCategory) {
		Category category = categoryrepository.findById(categoryID).get();
		category.setCategoryName(newCategory.getCategoryName());
		category.setDescription(newCategory.getDescription());
		category.setProducts(newCategory.getProducts());
		category.setImageUrl(newCategory.getImageUrl());

		categoryrepository.save(category);
	}
}
