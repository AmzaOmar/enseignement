package uasz.sn.gestion_enseignement.Authentification.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import java.security.Principal;
import uasz.sn.gestion_enseignement.Authentification.model.*;

@Controller
public class UtilisateurController {
    @Autowired
    private UtilisateurService utilisateurService;

    @RequestMapping(value = "/login")
    public String index() {
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout=true";
    }

    @PostMapping(value = "/modifierUtilisateur")
    public String modifierUtilisateur(Utilisateur utilisateur) {
        utilisateurService.updateUtilisateur(utilisateur);
        return "redirect:/profil";
    }

    @RequestMapping("/")
    public String login(Principal principal) {
        String url = "";
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        if(user.isActive()) {
            if (user.getRoles().getFirst().getRole().equals("Permanent")) {
                url = "redirect:/Permanent/Accueil";
            } else if (user.getRoles().getFirst().getRole().equals("Vacataire")) {
                url = "redirect:/Vacataire/Accueil";
            } else if (user.getRoles().getFirst().getRole().equals("ChefDepartement")) {
                url = "redirect:/ChefDepartement/Accueil";
            } else if (user.getRoles().getFirst().getRole().equals("Etudiant")) {
                url = "redirect:/Etudiant/Accueil";
            } else if (user.getRoles().getFirst().getRole().equals("ResponsableClasse")) {
                url = "redirect:/ResponsableClasse/Accueil";
            }
        }else {
            url = "redirect:/login?logout=true";
        }
        return url;
    }

    @GetMapping(value = "/profil")
    public String profilEtudiant(Model model, Principal principal) {
        Utilisateur utilisateur = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));
        return "profil";
    }
}