package com.example.nbc_outsourcingproject.domain.user.service;

import com.example.nbc_outsourcingproject.global.resolver.PasswordEncoder;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    void updatePassword() {

    }

}