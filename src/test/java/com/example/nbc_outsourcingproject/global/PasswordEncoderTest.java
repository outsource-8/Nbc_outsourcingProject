package com.example.nbc_outsourcingproject.global;

import com.example.nbc_outsourcingproject.global.resolver.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PasswordEncoderTest {

    @InjectMocks
    private PasswordEncoder passwordEncoder;

    @Test
    void matches_메소드_정상작동() {
        //given
        String rawPassword = "testPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        //when
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        //then
        assertTrue(matches);
    }
}