package com.util.svcmenu.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishRequest {

    @NotBlank(message = "Nombre es requerido")
    @Size(max = 100, message = "El nombre no puede exceder de 100 caracteres")
    private String name;

    @Size(max = 255, message = "La descripcion no puede exceder de 255 caracteres")
    private String description;

    @NotBlank(message = "Categoria es requerido")
    private String category;

    @NotNull(message = "Precio es requerido")
    @Min(value = 0, message = "El precio debe ser igual o mayor a cero")
    private Double price;

    private Boolean available;

}
