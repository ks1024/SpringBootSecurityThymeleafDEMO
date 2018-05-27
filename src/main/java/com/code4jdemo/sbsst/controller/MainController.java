package com.code4jdemo.sbsst.controller;

import com.code4jdemo.sbsst.domain.Result;
import com.code4jdemo.sbsst.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    MessageService messageService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/index")
    public String index1() {
        return "index";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/login")
    public String login() { return "login";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping(value = "/send_code", method = RequestMethod.POST)
    @ResponseBody
    public Result sendCode(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        if (phone == null || phone.isEmpty()) {
            return new Result(Result.ExceptionMsg.PhoneEmptyError);
        }
        String code = messageService.sendVerificationCode(request, phone);
        return new Result(code);
    }
}
