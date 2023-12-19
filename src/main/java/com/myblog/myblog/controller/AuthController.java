package com.myblog.myblog.controller;

import com.myblog.myblog.entity.User;
import com.myblog.myblog.payload.LoginDto;
import com.myblog.myblog.payload.SignUpDto;
import com.myblog.myblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    //AuthenticationManager is a Interface it provides the authentication for incoming data
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(
            @RequestBody SignUpDto signUpDto
    ){
        if(userRepo.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email already Exist.."+signUpDto.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(userRepo.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username already Exist.."+signUpDto.getUsername(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user =new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        userRepo.save(user);
        return new ResponseEntity<>("User  Registered",HttpStatus.CREATED);
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> authenticateUser(
//            @RequestBody LoginDto loginDto
//            ){
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            return new ResponseEntity<>("User sign-in successful", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
//        }
//    }




    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(
            @RequestBody LoginDto loginDto   //data comes from postman as JSON Data
    ){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);




    }
}
