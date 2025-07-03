package org.burgas.verticalsharding.controller.first;

import lombok.RequiredArgsConstructor;
import org.burgas.verticalsharding.dto.first.CategoryRequest;
import org.burgas.verticalsharding.dto.first.CategoryResponse;
import org.burgas.verticalsharding.service.first.CategoryService;
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
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(this.categoryService.findAll());
    }

    @GetMapping(value = "/by-id")
    public ResponseEntity<CategoryResponse> getCategoryById(@RequestParam Long categoryId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(this.categoryService.findById(categoryId));
    }

    @PostMapping(value = "/create-update")
    public ResponseEntity<CategoryResponse> createOrUpdateCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = this.categoryService.createOrUpdate(categoryRequest);
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(categoryResponse);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteCategory(@RequestParam Long categoryId) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(this.categoryService.delete(categoryId));
    }
}
