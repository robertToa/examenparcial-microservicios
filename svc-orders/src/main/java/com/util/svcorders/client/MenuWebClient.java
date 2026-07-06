package com.util.svcorders.client;

import com.util.svcorders.dto.DisResponse;
import com.util.svcorders.exception.CatalogServiceException;
import com.util.svcorders.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@Slf4j
public class MenuWebClient {
    private final WebClient webClient;

    public MenuWebClient(WebClient webClient){
        this.webClient = webClient;
    }

    public DisResponse getDishById(long dishId){
        log.info("WebClient - Llamando menu-service: GET {/api/menu/dishes/{}}", dishId);
        try{
            return webClient
                    .get()
                    .uri("/api/menu/dishes/{dishId}", dishId)
                    .retrieve()
                    .onStatus(
                            status -> status.value() == 404,
                            response -> response.bodyToMono(String.class)
                                    .map( body -> new ResourceNotFoundException(
                                            "Plato no encontrado en el menu-service con id: " + dishId))
                    )
                    .onStatus(
                            status -> status.is4xxClientError(),
                            response -> response.bodyToMono(String.class)
                                    .map( body -> new CatalogServiceException(
                                            "Error de cliente desde el menu-service: " + body))
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .map( body -> new CatalogServiceException(
                                            "Error del servidor desde el menu-service: " + body))
                    )
                    .bodyToMono(DisResponse.class)
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("WebClient -Error HHTP desde menu-service: {} {}",  ex.getStatusCode(), ex.getMessage());
            throw new CatalogServiceException("Error al llamar al menu-service: "+ ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("WebClient - No se logro conectar con menu-service: {} ", ex.getMessage());
            throw new CatalogServiceException("No se logro conectar con menu-service: "+ ex.getMessage(), ex);
        }
    }
}
