package com.example.applestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    private String name;

    private int categoryId;

    private double price;

    private double weight;

    private String description;

    private String imageUrl;

    private MultipartFile fileImage;

}
