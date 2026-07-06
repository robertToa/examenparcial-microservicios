package com.util.svcorders.dto;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisResponse {
    private Long id;

    private String name;

    private String description;

    private String category;

    private Double price;

    private Boolean available;
}