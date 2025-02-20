package uasz.sn.gestion_enseignement.Maquette.controller;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import uasz.sn.gestion_enseignement.Maquette.model.Formation;
import uasz.sn.gestion_enseignement.Maquette.service.FormationService;

import java.security.Principal;
import java.util.List;

@Controller
public class    FormationController {
    @Autowired
    private FormationService formationService;
    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/ChefDepartement/Formation")
    public String formation(Model model, Principal principal) {
        List<Formation> formations = formationService.findAllFormation();
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("formations", formations);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "formation";
    }

    @PostMapping("/ChefDepartement/Formation/ajouterFormation")
    public String ajouterFormation(Formation formation) {
        formationService.addFormation(formation);
        return "redirect:/ChefDepartement/Formation";
    }

    @PostMapping("/ChefDepartement/Formation/modifierFormation")
    public String modifierFormation(Formation formation) {
        formationService.updateFormation(formation);
        return "redirect:/ChefDepartement/Formation";
    }

    @PostMapping("/ChefDepartement/Formation/supprimerFormation")
    public String supprimerFormation(Formation formation) {
        formationService.deleteFormation(formation.getId());
        return "redirect:/ChefDepartement/Formation";
    }
}
