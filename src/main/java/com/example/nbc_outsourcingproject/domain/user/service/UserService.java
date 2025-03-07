package com.example.nbc_outsourcingproject.domain.user.service;

import com.example.nbc_outsourcingproject.domain.user.dto.request.DeleteUser;
import com.example.nbc_outsourcingproject.global.exception.auth.NotFoundUserException;
import com.example.nbc_outsourcingproject.global.exception.auth.WrongPasswordException;
import com.example.nbc_outsourcingproject.global.exception.user.NewpasswordDifferentOldpasswordException;
import com.example.nbc_outsourcingproject.global.resolver.PasswordEncoder;

import com.example.nbc_outsourcingproject.domain.user.dto.request.UpdatePassword;
import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updatePassword(long userId, UpdatePassword updatePassword) {
        log.info("여기 지나는지 확인");
        User user = userRepository.findById(userId).orElseThrow(
                NotFoundUserException::new
        );
        log.info("User found: {}", user);

        if (!passwordEncoder.matches(updatePassword.getOldPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }

        log.info("Old password matched, updating password");
        if (passwordEncoder.matches(updatePassword.getNewPassword(), user.getPassword())) {
            throw new NewpasswordDifferentOldpasswordException();
        }

        log.info("Password updated successfully");
        user.updatePassword(passwordEncoder.encode(updatePassword.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(long userId, DeleteUser deleteUser) {
        log.info("여기 지나는지 확인");
        User user = userRepository.findById(userId).orElseThrow(
                NotFoundUserException::new
        );
        log.info("User found: {}", user);

        if (!passwordEncoder.matches(deleteUser.getPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }
        log.info("기존 비밀번호화 일치 확인");

        userRepository.delete(user);
    }
}
