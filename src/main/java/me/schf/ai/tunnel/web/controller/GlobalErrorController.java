package me.schf.ai.tunnel.web.controller;

import java.time.LocalDate;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GlobalErrorController implements ErrorController {
    
    private final String currentYear;

    public GlobalErrorController() {
        super();
		this.currentYear = String.valueOf(LocalDate.now().getYear());
    }
    
    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        var status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("year", currentYear);
        
        int statusCode = 0;
        String errorMessage;
                
        if (status != null) {
            statusCode = Integer.parseInt(status.toString());
            model.addAttribute("errorCode", statusCode);
            
            switch (statusCode) {
                case 404:
                    errorMessage = "page not found.";
                    break;
                case 400:
                    errorMessage = "bad request.";
                    break;
                case 429:
                    errorMessage = "you're doing that too much. please slow down and try again later.";
                    break;
                case 500:
                    errorMessage = "internal server error.";
                    break;
                default:
                    errorMessage = "unexpected error.";
                    break;
            }
        } else {
            model.addAttribute("errorCode", "???");
            errorMessage = "unknown error.";
        }
        
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
