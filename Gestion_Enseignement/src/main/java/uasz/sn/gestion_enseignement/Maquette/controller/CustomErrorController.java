package uasz.sn.gestion_enseignement.Maquette.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Retourne une vue personnalisée pour les erreurs
        return "error/404"; // Supposons que tu as une vue 404.html
    }


    public String getErrorPath() {
        return "/error";
    }
}

