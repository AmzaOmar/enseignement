package uasz.sn.gestion_enseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import java.security.Principal;

@Controller
public class EtudiantController {
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping(value = "/Etudiant/Accueil")
    public String accueilEtudiant(Model model, Principal principal) {
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "template_Etudiant";
    }

    @GetMapping(value = "/ResponsableClasse/Accueil")
    public String accueilResponsableClasse(Model model, Principal principal) {
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "template_ResponsableClasse";
    }
}