package com.example.banking.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute("javax.servlet.error.status_code");
        String message = "An unexpected error occurred";

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            switch (statusCode) {
                case 400:
                    message = "Bad Request";
                    break;
                case 401:
                    message = "Unauthorized";
                    break;
                case 403:
                    message = "Forbidden";
                    break;
                case 404:
                    message = "Page Not Found";
                    break;
                case 500:
                    message = "Internal Server Error";
                    break;
                default:
                    message = "An unexpected error occurred";
                    break;
            }
        }

        model.addAttribute("message", message);
        return "error";
    }


}