package org.burgas.replicashard.controller.second;

import lombok.RequiredArgsConstructor;
import org.burgas.replicashard.dto.second.EmployeeRequest;
import org.burgas.replicashard.dto.second.EmployeeResponse;
import org.burgas.replicashard.service.second.EmployeeService;
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
@RequestMapping(value = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(this.employeeService.findAll());
    }

    @GetMapping(value = "/by-id")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@RequestParam Long employeeId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(this.employeeService.findById(employeeId));
    }

    @PostMapping(value = "/create-update")
    public ResponseEntity<EmployeeResponse> createOrUpdateEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(this.employeeService.createOrUpdate(employeeRequest));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteEmployee(@RequestParam Long employeeId) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(this.employeeService.delete(employeeId));
    }
}
