package uasz.sn.gestion_enseignement.Maquette.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import uasz.sn.gestion_enseignement.Maquette.model.Classe;
import uasz.sn.gestion_enseignement.Maquette.model.Formation;
import uasz.sn.gestion_enseignement.Maquette.service.ClasseService;
import uasz.sn.gestion_enseignement.Maquette.service.FormationService;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
public class ClasseController {
    @Autowired
    ClasseService classeService;
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    private FormationService formationService;

    @GetMapping("/ChefDepartement/Formation/{formationid}/Classe")
    public String classe(@PathVariable("formationid") String id, Model model, Principal principal) {
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        Long Id = Long.parseLong(id);
        Formation formation=formationService.findById(Id);
        List<Classe> classes = formation.getClasses();
        model.addAttribute("formation", formation);
        model.addAttribute("classes", classeService.findClasseByFormation(formation));
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "classe";
    }
    @PostMapping("/ChefDepartement/Formation/{formationid}/Classe/modifierClasse")
    public String modifierClasse(@PathVariable("formationid") String id, Classe classe) {
        Long Id = Long.parseLong(id);
        Formation formation=formationService.findById(Id);
        classe.setFormation(formation);
        classeService.updateClasse(classe);
        List<Classe> classes = formation.getClasses();
        classes.removeIf(e -> Objects.equals(e.getId(), classe.getId()));
        classes.add(classe);
        formation.setClasses(classes);
        formationService.updateFormation(formation);
        return "redirect:/ChefDepartement/Formation/" + id + "/Classe";
    }

    @PostMapping("/ChefDepartement/Formation/{formationid}/Classe/supprimerClasse")
    public String supprimerClasse(@PathVariable("formationid") String id, Classe classe) {
        Long Id = Long.parseLong(id);
        Formation formation=formationService.findById(Id);
        classeService.deleteClasse(classe.getId());
        List<Classe> classes = formation.getClasses();
        classes.removeIf(e -> Objects.equals(e.getId(), classe.getId()));
        formation.setClasses(classes);
        formationService.updateFormation(formation);
        return "redirect:/ChefDepartement/Formation/" + id + "/Classe";
    }

    @PostMapping("/ChefDepartement/Formation/{formationid}/Classe/ajouterClasse")
    public String ajouterClasse(@PathVariable("formationid") String id,Classe classe) {
        Long Id = Long.parseLong(id);
        Formation formation=formationService.findById(Id);
        classe.setFormation(formation);

        classeService.addClasse(classe);
        List<Classe> classes =formation.getClasses();
        classes.add(classe);
        formation.setClasses(classes);
        formationService.updateFormation(formation);
        return "redirect:/ChefDepartement/Formation/" + id + "/Classe";

    }
}
