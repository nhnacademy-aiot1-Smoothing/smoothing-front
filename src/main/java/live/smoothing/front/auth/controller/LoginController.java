package live.smoothing.front.auth.controller;

import live.smoothing.front.adapter.AuthAdaptor;
import live.smoothing.front.auth.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final AuthAdaptor authAdaptor;

    @Autowired
    public LoginController(AuthAdaptor authAdaptor) {

        this.authAdaptor = authAdaptor;
    }

    @PostMapping("/login")
    public String doLogin(@RequestBody LoginDto loginDto) {

        HttpStatus status = authAdaptor.doLogin(loginDto).getStatusCode();

        return (status == HttpStatus.OK) ? "redirect:/" : "redirect:/login";
    }
}
