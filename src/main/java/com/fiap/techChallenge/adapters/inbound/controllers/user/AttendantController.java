package com.fiap.techChallenge.adapters.inbound.controllers.user;

import com.fiap.techChallenge.application.dto.user.AttendantRequestDTO;
import com.fiap.techChallenge.application.dto.user.AttendantResponseDTO;
import com.fiap.techChallenge.application.services.user.AttendantServiceImpl;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/user/attendant")
@Tag(name = "Attendant", description = "APIs relacionadas aos Atendentes")
public class AttendantController {

    private final AttendantServiceImpl attendantService;

    public AttendantController(AttendantServiceImpl attendantService) {
        this.attendantService = attendantService;
    }

    @PostMapping()
    public ResponseEntity<AttendantResponseDTO> create(@RequestBody @Valid AttendantRequestDTO attendantRequest) {
        AttendantResponseDTO attendant = attendantService.createAttendant(attendantRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(attendant);
    }

    @PostMapping("/update")
    public ResponseEntity<AttendantResponseDTO> update(@RequestBody @Valid AttendantRequestDTO attendantRequest) {
        AttendantResponseDTO attendant = attendantService.updateAttendant(attendantRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(attendant);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendantResponseDTO> findById(@PathVariable("id") UUID id) {
        AttendantResponseDTO attendant = attendantService.searchAttendant(id);

        return ResponseEntity.ok(attendant);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<AttendantResponseDTO> findByCpf(@PathVariable("cpf") String cpf) {
        AttendantResponseDTO attendant = attendantService.searchAttendant(cpf);

        return ResponseEntity.ok(attendant);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AttendantResponseDTO>> list() {
        return ResponseEntity.ok(attendantService.list());
    }

}
