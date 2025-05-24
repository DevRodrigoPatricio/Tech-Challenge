package com.fiap.techChallenge.adapters.inbound.controllers;

import com.fiap.techChallenge.application.services.AttendantServiceImpl;
import com.fiap.techChallenge.domain.user.attendant.Attendant;
import com.fiap.techChallenge.domain.user.attendant.AttendantRequestDTO;
import com.fiap.techChallenge.domain.user.attendant.AttendantResponseDTO;
import com.fiap.techChallenge.utils.mappers.AttendantMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/attendant")
@Tag(name = "Attendant", description = "APIs relacionadas aos Atendentes")
public class AttendantController {

    private final AttendantServiceImpl attendantService;
    private final AttendantMapper attendantMapper;

    public AttendantController(AttendantServiceImpl attendantService, AttendantMapper attendantMapper) {
        this.attendantService = attendantService;
        this.attendantMapper = attendantMapper;
    }

    @PostMapping()
    public ResponseEntity<AttendantResponseDTO> create(@RequestBody @Valid AttendantRequestDTO attendantRequest) {
        Attendant attendant = attendantService.createAttendant(attendantRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(attendantMapper.toDto(attendant));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AttendantResponseDTO> findById(@PathVariable("id") UUID id) {
        Attendant attendant = attendantService.searchAttendant(id);
        return ResponseEntity.ok(attendantMapper.toDto(attendant));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<AttendantResponseDTO> findByCpf(@PathVariable("cpf") String cpf) {
        Attendant attendant = attendantService.searchAttendant(cpf);
        return ResponseEntity.ok(attendantMapper.toDto(attendant));
    }
}
