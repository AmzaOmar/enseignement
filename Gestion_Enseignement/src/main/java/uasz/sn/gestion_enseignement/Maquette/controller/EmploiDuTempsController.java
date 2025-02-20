package uasz.sn.gestion_enseignement.Maquette.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import uasz.sn.gestion_enseignement.Maquette.model.EmploiDuTemps;
import uasz.sn.gestion_enseignement.Maquette.model.Formation;
import uasz.sn.gestion_enseignement.Maquette.service.EmploiDuTempsService;

import java.security.Principal;
import java.util.List;

@Controller
public class EmploiDuTempsController {
    private final EmploiDuTempsService emploiDuTempsService;
    private final UtilisateurService utilisateurService;

    public EmploiDuTempsController(EmploiDuTempsService emploiDuTempsService, UtilisateurService utilisateurService) {
        this.emploiDuTempsService = emploiDuTempsService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/ChefDepartement/GestionEmploiDuTemps")
    public String formation(Model model, Principal principal) {
        List<EmploiDuTemps> emploiDuTemps = emploiDuTempsService.findAllEmploiDuTemps();
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("emploi", emploiDuTemps);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "gestionEmploiDuTemps";
    }
}
