package uasz.sn.gestion_enseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uasz.sn.gestion_enseignement.Authentification.model.Role;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import uasz.sn.gestion_enseignement.Utilisateur.model.Vacataire;
import uasz.sn.gestion_enseignement.Utilisateur.service.EnseignantService;
import uasz.sn.gestion_enseignement.Utilisateur.service.VacataireService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class VacataireController {
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private VacataireService vacataireService;

    @GetMapping(value = "/Vacataire/Accueil")
    public String VacataireAccueil(Model model, Principal principal) {
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "template_Vacataire";
    }

    @PostMapping(value = "/ChefDepartement/ajouterVacataire")
    public String ajouterVacataire(Vacataire vacataire) {
        String password = passwordEncoder.encode("Passer123");
        vacataire.setPassword(password);
        vacataire.setDateCreation(new Date());
        vacataire.setActive(true);
        Role role = utilisateurService.addRole(new Role("Vacataire"));
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        vacataire.setRoles(roles);
        enseignantService.addEnseignant(vacataire);
        return "redirect:/ChefDepartement/Enseignant";
    }

    @PostMapping(value = "/ChefDepartement/modifierVacataire")
    public String modifierVacataire(Vacataire vacataire) {
        Vacataire vacataire1 = vacataireService.getVacataireById(vacataire.getId());
        vacataire1.setNom(vacataire.getNom());
        vacataire1.setPrenom(vacataire.getPrenom());
        vacataire1.setSpecialite(vacataire.getSpecialite());
        vacataire1.setNiveau(vacataire.getNiveau());
        enseignantService.updateEnseignant(vacataire1);
        return "redirect:/ChefDepartement/Enseignant";
    }

    @PostMapping(value = "/ChefDepartement/activerVacataire")
    public String activerVacataire(Long id) {
        enseignantService.activer(id);
        return "redirect:/ChefDepartement/Enseignant";
    }

    @PostMapping(value = "/ChefDepartement/archiverVacataire")
    public String archiverVacataire(Long id) {
        enseignantService.archive(id);
        return "redirect:/ChefDepartement/Enseignant";
    }
}