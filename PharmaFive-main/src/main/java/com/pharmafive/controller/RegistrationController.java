package com.pharmafive.controller;

import com.pharmafive.dto.LoginRequestDTO;
import com.pharmafive.dto.LoginResponseDTO;
import com.pharmafive.dto.RegistrationRequestDTO;
import com.pharmafive.dto.RegistrationResponseDTO;
import com.pharmafive.dto.UpdateStatusRequestDTO;
import com.pharmafive.model.Registration;
import com.pharmafive.service.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService service;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDTO> registerUser(@Valid @RequestBody RegistrationRequestDTO requestDTO) {
        RegistrationResponseDTO responseDTO = service.saveUser(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    
    @PutMapping("/update-status")
    public ResponseEntity<RegistrationResponseDTO> updateStatus(
            @Valid @RequestBody UpdateStatusRequestDTO requestDTO
    ) {
        RegistrationResponseDTO responseDTO =
            service.updateStatusByEmail(requestDTO.getEmail(), requestDTO.getStatus());
        return ResponseEntity.ok(responseDTO);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Registration>> searchRegistration(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "status", required = false) Registration.Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Registration> result = service.searchRegistrations(search, status, page, size);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        LoginResponseDTO response = service.login(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
