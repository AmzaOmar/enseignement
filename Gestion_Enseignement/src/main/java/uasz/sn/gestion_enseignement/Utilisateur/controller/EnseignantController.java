package uasz.sn.gestion_enseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import uasz.sn.gestion_enseignement.Utilisateur.model.Permanent;
import uasz.sn.gestion_enseignement.Utilisateur.model.Vacataire;
import uasz.sn.gestion_enseignement.Utilisateur.service.PermanentService;
import uasz.sn.gestion_enseignement.Utilisateur.service.VacataireService;
import java.security.Principal;
import java.util.List;

@Controller
public class EnseignantController {
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PermanentService permanentService;
    @Autowired
    private VacataireService vacataireService;

    @GetMapping(value = "/ChefDepartement/Enseignant")
    public String chefDepartement_Enseignant(Model model, Principal principal) {
        List<Permanent> permanents = permanentService.getAllPermanents();
        List<Vacataire> vacataires = vacataireService.getAllVacataires();
        Utilisateur utilisateur = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("permanents", permanents);
        model.addAttribute("vacataires", vacataires);
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));
        return "chefDepartement_Enseignant";
    }
}