package com.example.yingxue.category.controller;

import com.example.yingxue.common.category.entity.Category;
import com.example.yingxue.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CategoryController{
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/categories")
    public ArrayList<Category> list(){
        ArrayList<Category> categories=(ArrayList<Category>)categoryService.list();
        Category category=new Category();
        category.setId(-1);
        addChildren(categories,category);
        return category.getChildren();
    }
    private void addChildren(ArrayList<Category> categories,Category parentCategory){
        for(Category currentCategory: categories){
            if(currentCategory.getParentId()
                              .equals(parentCategory.getId())){
                if(parentCategory.getChildren()==null){
                    parentCategory.setChildren(new ArrayList<>());
                }
                parentCategory.getChildren()
                              .add(currentCategory);
                addChildren(categories,currentCategory);
            }
        }
    }
    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable("id") Integer id){
        return categoryService.getById(id);
    }
}
