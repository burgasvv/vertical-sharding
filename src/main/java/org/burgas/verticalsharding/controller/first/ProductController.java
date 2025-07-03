package org.burgas.verticalsharding.controller.first;

import lombok.RequiredArgsConstructor;
import org.burgas.verticalsharding.dto.first.ProductRequest;
import org.burgas.verticalsharding.dto.first.ProductResponse;
import org.burgas.verticalsharding.service.first.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(this.productService.findAll());
    }

    @GetMapping(value = "/by-id")
    public ResponseEntity<ProductResponse> getProductById(@RequestParam Long productId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(this.productService.findById(productId));
    }

    @PostMapping(value = "/create-update")
    public ResponseEntity<ProductResponse> createOrUpdate(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = this.productService.createOrUpdate(productRequest);
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(productResponse);
    }

    @PostMapping(value = "/create-update-list")
    public ResponseEntity<List<ProductResponse>> createOrUpdateProductList(@RequestBody List<ProductRequest> productRequests) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(this.productService.createOrUpdateProducts(productRequests));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam Long productId) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(this.productService.delete(productId));
    }
}
