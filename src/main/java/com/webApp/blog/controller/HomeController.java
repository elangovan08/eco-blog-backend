package com.webApp.blog.controller;

import com.webApp.blog.dto.response.ApiMessageResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/health")
    public ResponseEntity<ApiMessageResponseDTO> health() {
        return ResponseEntity.ok(new ApiMessageResponseDTO("EcoBlog API is running"));
    }

    @GetMapping("/about")
    public ResponseEntity<ApiMessageResponseDTO> about() {
        return ResponseEntity.ok(new ApiMessageResponseDTO("EcoBlog is an eco-conscious blogging platform."));
    }
}
