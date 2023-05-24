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
import utc.edu.thesis.service.*;

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
    private final UserService userService;
    private final AssignmentService assignmentService;

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

        long studentId = 0, teacherId = 0, sessionId = 0;
        if (!"admin".equals(userRequest.getUsername())) {
            sessionId = sessionService.getSessionActive().getId();
        }

        String email = userService.getUser(userRequest.getUsername()).getEmail() != null
                ? userService.getUser(userRequest.getUsername()).getEmail() : "admin";
        SearchDto searchDto = new SearchDto(email, "EMAIL");
        List<TeacherDto> teachers = teacherService.getTeacher(searchDto);
        List<StudentDto> student = studentService.getStudent(searchDto);
        if (!teachers.isEmpty()) {
            teacherId = teachers.get(0).getId();
        }
        if (!student.isEmpty()) {
            studentId = student.get(0).getId();
            teacherId = assignmentService
                    .getAssign(new SearchDto("%d,%d".formatted(studentId, sessionId), "TEACHER"))
                    .get(0)
                    .getTeacher().getId();
        }

        new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword(), userDetails.getAuthorities());


        return new AuthenticationResponse(jwt, jwtRefresh, userDetails.getUsername(), userDetails.getAuthorities(), expired, studentId, teacherId, sessionId);
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