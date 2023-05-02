package utc.edu.thesis.service.impl;

import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.dto.StudentDto;
import utc.edu.thesis.domain.dto.TeacherDto;
import utc.edu.thesis.domain.dto.UserRequest;
import utc.edu.thesis.exception.request.UnauthenticatedException;
import utc.edu.thesis.security.jwt.JWTUtils;
import utc.edu.thesis.security.response.AuthenticationResponse;
import utc.edu.thesis.service.AuthenticationService;
import utc.edu.thesis.service.SessionService;
import utc.edu.thesis.service.StudentService;
import utc.edu.thesis.service.TeacherService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final SessionService sessionService;

    @Override
    public AuthenticationResponse createAuthenticationToken(UserRequest userRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword())
            );

        } catch (BadCredentialsException e) {
            throw new UnauthenticatedException("Incorrect username or password");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String jwt = jwtUtils.generateToken(userDetails);
        int expired = jwtUtils.getJwtExpirationInMs();
        final String jwtRefresh = jwtUtils.generateRefreshToken(userDetails);

        long id = 0;
        SearchDto searchDto = new SearchDto(userRequest.getUsername(), "EMAIL");
        List<TeacherDto> teachers = teacherService.getTeacher(searchDto);
        List<StudentDto> student = studentService.getStudent(searchDto);
        if (!teachers.isEmpty()) {
            id = teachers.get(0).getId();
        }
        if(!student.isEmpty()) {
            id = student.get(0).getId();
        }
        long sessionId = sessionService.getSessionActive().getId();

        new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword(), userDetails.getAuthorities());


        return new AuthenticationResponse(jwt, jwtRefresh, userDetails.getUsername(), userDetails.getAuthorities(), expired, id, sessionId);
    }

    @Override
    public AuthenticationResponse refreshToken(HttpServletRequest request) {
        String tokenPrefix = jwtUtils.getTokenPrefix();
        DefaultClaims claims = (DefaultClaims) jwtUtils.extractAllClaims(request.getHeader(AUTHORIZATION).substring(tokenPrefix.length()));

        Map<String, Object> expectedMap = new HashMap<>(claims);
        String token = jwtUtils.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        return new AuthenticationResponse(token);
    }
}