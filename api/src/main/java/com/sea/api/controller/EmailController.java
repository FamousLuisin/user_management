package com.sea.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sea.api.controller.docs.EmailControllerDocs;
import com.sea.api.dto.request.EmailRequestDTO;
import com.sea.api.dto.response.EmailResponseDTO;
import com.sea.api.service.EmailService;

@RestController
@RequestMapping(path = "/api/email")
public class EmailController implements EmailControllerDocs {
    
    @Autowired
    private EmailService emailService;

    @PostMapping("/client/{id}")
    public ResponseEntity<EmailResponseDTO> addEmailToClient(@PathVariable("id") Long id, @RequestBody @Valid EmailRequestDTO email) {
        EmailResponseDTO response = emailService.addEmail(id, email);
        
        return ResponseEntity.ok(response);
    }
    

    @PutMapping(path = "/{id}")
    public ResponseEntity<EmailResponseDTO> updateEmail(@PathVariable("id") Long id, @RequestBody @Valid EmailRequestDTO email) {
        EmailResponseDTO response = emailService.updateEmail(id, email);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<EmailResponseDTO> deleteEmail(@PathVariable("id") Long id) {
        emailService.deleteEmail(id);
        
        return ResponseEntity.noContent().build();
    }
}
