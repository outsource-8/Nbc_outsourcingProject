package com.example.nbc_outsourcingproject.domain.user.service;

import com.example.nbc_outsourcingproject.global.resolver.PasswordEncoder;
import com.example.nbc_outsourcingproject.global.exception.auth.InvalidRequestException;
import com.example.nbc_outsourcingproject.domain.user.dto.request.UpdatePassword;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void updatePassword(long userId, UpdatePassword updatePassword) {

        User user = userRepository.findById(userId).orElseThrow(
                ()-> new InvalidRequestException("해당 유저 없습니다.")
        );

        if (!passwordEncoder.matches(updatePassword.getOldPassword(), user.getPassword())) {
            throw new InvalidRequestException("잘못된 비밀번호 입니다.");
        }

        if (passwordEncoder.matches(updatePassword.getNewPassword(), user.getPassword())) {
            throw new InvalidRequestException("새 비밀번호는 기존 비밀번호와 같을 수 없습니다.");
        }

        user.updatePassword(passwordEncoder.encode(updatePassword.getNewPassword()));
    }
}
