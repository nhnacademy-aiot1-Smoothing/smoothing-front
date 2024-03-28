package live.smoothing.front.controller;

import live.smoothing.front.adapter.LoginAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginAdaptor loginAdaptor;

    @Autowired
    public LoginController(LoginAdaptor loginAdaptor) {

        this.loginAdaptor = loginAdaptor;
    }

    @GetMapping("/login")
    public String loginForm() {

        return loginAdaptor.loginForm();
    }

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody String userId,
                                     @RequestBody String userPassword) {

        HttpStatus status = loginAdaptor.doLogin(userId, userPassword).getStatusCode();
        return new ResponseEntity<>(status);
    }
}
