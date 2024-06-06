package com.web.registry.controller;

import com.web.registry.dto.AuthenticationRequest;
import com.web.registry.dto.AuthenticationResponse;
import com.web.registry.entities.User;
import com.web.registry.repository.UserRepository;
import com.web.registry.service.user.UserService;
import com.web.registry.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @PostMapping("/authenticate")

    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws BadCredentialsException,DisabledException, UsernameNotFoundException, IOException, JSONException, ServletException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));

        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        } catch (DisabledException disabledException){
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,"User is not activated");
            return;
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(authenticationRequest.getUsername());


 //      return new AuthenticationResponse(jwt);

        response.getWriter().write(new JSONObject()
                .put("userId",user.getId())
                .put("role",user.getUserRole())
                        .toString()
        );
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers","Authorization,X-PINGGOTHER,Origin,X-Requested-With,Content-Type,Accept,X-Custom-Header");
        response.addHeader(HEADER_STRING,TOKEN_PREFIX + jwt);

    }

}
