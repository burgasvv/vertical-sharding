package org.burgas.replicashard.controller.second;

import lombok.RequiredArgsConstructor;
import org.burgas.replicashard.dto.second.DepartmentRequest;
import org.burgas.replicashard.dto.second.DepartmentResponse;
import org.burgas.replicashard.service.second.DepartmentService;
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
@RequestMapping(value = "/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(this.departmentService.findAll());
    }

    @GetMapping(value = "/by-id")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@RequestParam Long departmentId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(this.departmentService.findById(departmentId));
    }

    @PostMapping(value = "/create-update")
    public ResponseEntity<DepartmentResponse> createOrUpdateDepartment(@RequestBody DepartmentRequest departmentRequest) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(this.departmentService.createOrUpdate(departmentRequest));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteDepartment(@RequestParam Long departmentId) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(this.departmentService.delete(departmentId));
    }
}
