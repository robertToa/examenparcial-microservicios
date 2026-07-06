package com.util.svcmenu.dto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishResponse {
    private Long id;

    private String name;

    private String description;

    private String category;

    private Double price;

    private Boolean available;
}
