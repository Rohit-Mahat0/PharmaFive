package com.pharmafive.service;

import com.pharmafive.dto.LoginRequestDTO;
import com.pharmafive.dto.LoginResponseDTO;
import com.pharmafive.dto.RegistrationRequestDTO;
import com.pharmafive.dto.RegistrationResponseDTO;
import com.pharmafive.exception.RegistrationException;
import com.pharmafive.model.Registration;
import com.pharmafive.model.Registration.Status;
import com.pharmafive.repository.RegistrationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository repository;

    @Autowired
    private JavaMailSender mailSender;  // Injected by Spring Boot

    /**
     * Main method to save a new user registration.
     */
    public RegistrationResponseDTO saveUser(RegistrationRequestDTO requestDTO) {
        // 1) Business check: is the email already used?
        Optional<Registration> existing = repository.findByEmail(requestDTO.getEmail());
        if (existing.isPresent()) {
            throw new RegistrationException("Email is already in use: " + requestDTO.getEmail());
        }

        // 2) Convert DTO to Entity
        Registration registration = new Registration();
        registration.setName(requestDTO.getName());
        registration.setMobileNumber(requestDTO.getMobileNumber());
        registration.setEmail(requestDTO.getEmail());
        registration.setOrganisationName(requestDTO.getOrganisationName());
        registration.setPassword(requestDTO.getPassword());
        registration.setStatus(Status.Pending);  // Default status

        // 3) Save to DB
        Registration savedRegistration = repository.save(registration);

        // 4) Send Email after saving
        try {
            sendEmail(
                "rohitmahato0506@gmail.com",  // or any target address where you want notifications
                "New Registration Created",
                buildEmailBody(savedRegistration)
            );
        } catch (Exception e) {
            e.printStackTrace();
            // Optionally throw or handle the exception (we're re-throwing as a RegistrationException here)
            throw new RegistrationException("Failed to send email: " + e.getMessage());
        }

        // 5) Prepare the response DTO
        RegistrationResponseDTO responseDTO = new RegistrationResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage("Registration successful.");
        responseDTO.setData(savedRegistration); 
        // Alternatively, you can store only the ID or a subset: savedRegistration.getSno()

        return responseDTO;
    }

    /**
     * Sends a simple text email using JavaMailSender.
     */
    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 'from' should match your SMTP account in most cases
        message.setFrom("info@pharmafive.org"); 
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    /**
     * Utility method to build a simple email message body.
     */
    private String buildEmailBody(Registration savedReg) {
        return "Hello,\n\nA new user has registered:\n"
            + "Name: " + savedReg.getName() + "\n"
            + "Email: " + savedReg.getEmail() + "\n"
            + "Mobile: " + savedReg.getMobileNumber() + "\n"
            + "Organisation: " + savedReg.getOrganisationName() + "\n"
            + "\nRegards,\nPharmaFive Team";
    }

    public RegistrationResponseDTO updateStatusByEmail(String email, Registration.Status newStatus) {
        Registration registration = repository.findByEmail(email)
            .orElseThrow(() -> new RegistrationException("No user found with email: " + email));

        registration.setStatus(newStatus);
        Registration updated = repository.save(registration);

        RegistrationResponseDTO responseDTO = new RegistrationResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setMessage("Status updated successfully.");
        responseDTO.setData(updated);  // or just the ID, etc.
        return responseDTO;
    }

    public Page<Registration> searchRegistrations(String search, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        if (search == null || search.trim().isEmpty()) {
            // No search term: return all records (paged)
            return repository.findAll(pageRequest);
        } else {
            // With search term: filter by name/email/org
            String keyword = search.trim();
            return repository.searchByKeyword(keyword, pageRequest);
        }
    }
    
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        // 1) Find user by email
        Registration registration = repository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new RegistrationException("No user found with email: " + loginRequest.getEmail()));

        // 2) Check password match (assuming plain text in DB for this example)
        if (!registration.getPassword().equals(loginRequest.getPassword())) {
            throw new RegistrationException("Incorrect password.");
        }

        // 3) Check status
        if (!registration.getStatus().equals(Status.Active)) {
            throw new RegistrationException("Account is not ACTIVE. Current status: " + registration.getStatus());
        }

        // 4) Success
        LoginResponseDTO response = new LoginResponseDTO();
        response.setSuccess(true);
        response.setMessage("Login successful.");
        response.setData(registration); // or a sanitized user object if you prefer
        return response;
    }
}
