package com.example.nbc_outsourcingproject.domain.user.repository;

import com.example.nbc_outsourcingproject.domain.user.entity.User;
import com.example.nbc_outsourcingproject.domain.user.exception.details.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>findByEmail(String email);
    boolean existsByEmail(String email);

    default User findByIdOrElseThrow (Long userId){
        return findById(userId).orElseThrow(UserNotFoundException::new);
    }

}
