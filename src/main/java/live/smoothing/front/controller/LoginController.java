package live.smoothing.front.controller;

import live.smoothing.front.adapter.LoginAdaptor;
import live.smoothing.front.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/login")
    public String doLogin(@RequestBody LoginDto loginDto) {

        HttpStatus status = loginAdaptor.doLogin(loginDto).getStatusCode();

        return (status == HttpStatus.OK) ? "redirect:/" : "redirect:/login";
    }
}
