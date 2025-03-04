package com.example.nbc_outsourcingproject.domain.user.controller;

import com.example.nbc_outsourcingproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OwnerController {

    private final UserService userService;

//    @PostMapping("/owner")

}
