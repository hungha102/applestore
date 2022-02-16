package com.example.applestore.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.applestore.dto.ProductDTO;
import com.example.applestore.model.Category;
import com.example.applestore.model.Product;
import com.example.applestore.service.CategoryService;
import com.example.applestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }//page admin home

    //Categories session
    @GetMapping("/admin/categories")
    public String getCat(Model model){
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }//view all categories

    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model model){
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }//form add new category

    @PostMapping("/admin/categories/add")
    public String postCatAdd(@ModelAttribute("category") Category category){
        categoryService.updateCategory(category);
        return "redirect:/admin/categories";
    }//form add new category > do add

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id){
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }//delete 1 category

    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }else {
            return "404";
        }
    }//form edit category, fill old data into form

    //Products session
    @GetMapping("/admin/products")
    public String getPro(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "products";
    }//view all products

    @GetMapping("/admin/products/add")
    public String getProAdd(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }// form add new product

    @PostMapping("/admin/products/add")
    public String postProAdd(@ModelAttribute("productDTO") ProductDTO productDTO) throws IOException{
        //convert dto > entity
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());

            String imageUrl = "";
            if (!productDTO.getFileImage().isEmpty()) {
                Map result = this.cloudinary.uploader().upload(productDTO.getFileImage().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                imageUrl = (String) result.get("secure_url");
            }

            product.setImageUrl(imageUrl);
            productService.updateProduct(product);
            return "redirect:/admin/products";
        }//form add new product > do add

        @GetMapping("/admin/products/delete/{id}")
        public String deletePro ( @PathVariable long id){
            productService.removeProductById(id);
            return "redirect:/admin/products";
        }//delete 1 product

        @GetMapping("/admin/products/update/{id}")
        public String updatePro (@PathVariable long id, Model model){
            Optional<Product> opProduct = productService.getProductById(id);
            if (opProduct.isPresent()) {
                Product product = opProduct.get();
                //convert entity > dto
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(product.getId());
                productDTO.setName(product.getName());
                productDTO.setCategoryId(product.getCategory().getId());
                productDTO.setPrice(product.getPrice());
                productDTO.setWeight(product.getWeight());
                productDTO.setDescription(product.getDescription());
                productDTO.setImageUrl(product.getImageUrl());

                model.addAttribute("productDTO", productDTO);
                model.addAttribute("categories", categoryService.getAllCategory());
                return "productsAdd";
            } else {
                return "404";
            }

        }//form edit product, fill old data into form

    }