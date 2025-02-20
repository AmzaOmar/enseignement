package uasz.sn.gestion_enseignement.Utilisateur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;

import java.security.Principal;


@Controller
public class chefDepartementController {

    private final UtilisateurService utilisateurService;

    public chefDepartementController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping(value = "/ChefDepartement/Accueil")
    public String accueilChefDepartement(Model model, Principal principal){
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));

        return "template_ChefDepartement";
    }

 }
