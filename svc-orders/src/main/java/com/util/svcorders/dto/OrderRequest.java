package com.util.svcorders.dto;
import com.util.svcorders.model.OrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "Se requiere el ID del plato.")
    @Min(value = 1, message = "ID del plato debe ser mayor que 0.")
    private Long dishId;

    @NotBlank(message = "Nombre de la mesa es obligatorio.")
    @Size(max = 100, message = "El nombre de la mesa no puede exceder de 100 caracteres")
    private String customerName;

    @NotNull(message = "Cantidad es obligatoria.")
    @Min(value = 1, message = "La cantidad debe ser de al menos 1 ")
    private Integer quantity;
}
