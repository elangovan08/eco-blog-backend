package com.webApp.blog.service;

import com.webApp.blog.dto.request.LoginRequestDTO;
import com.webApp.blog.dto.request.SignupRequestDTO;
import com.webApp.blog.dto.response.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO signup(SignupRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);
}
