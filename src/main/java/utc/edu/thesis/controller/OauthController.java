package utc.edu.thesis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc.edu.thesis.domain.dto.TokenDto;
import utc.edu.thesis.service.IOauthService;

@RestController
@RequestMapping("/oauth")
@CrossOrigin
@RequiredArgsConstructor
public class OauthController {
    private final IOauthService iOauthService;

    @PostMapping("/facebook")
    public ResponseEntity<TokenDto> facebook(@RequestBody TokenDto tokenDto) {
        return ResponseEntity.ok(iOauthService.getTokenFacebook(tokenDto));
    }

}
