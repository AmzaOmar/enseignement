package uasz.sn.gestion_enseignement.Utilisateur.controller;

import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.gestion_enseignement.Authentification.model.Role;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import uasz.sn.gestion_enseignement.Utilisateur.model.Permanent;
import uasz.sn.gestion_enseignement.Utilisateur.repository.PermanentRepository;
import uasz.sn.gestion_enseignement.Utilisateur.service.EnseignantService;
import uasz.sn.gestion_enseignement.Utilisateur.service.PermanentService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller

@AllArgsConstructor
public class PermanentController {

    private final UtilisateurService utilisateurService;

    private final PasswordEncoder passwordEncoder;

    private final EnseignantService enseignantService;

    private final PermanentService permanentService;

    private final PermanentRepository permanentRepository;

    @GetMapping(value = "/Permanent/Accueil")
    public String accueilPermanent(Model model, Principal principal) {
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "template_Permanent";
    }

    @GetMapping("/permanent")
    public List<Permanent> listPermanent(){
        return permanentRepository.findAll();
    }
    @GetMapping("/permanent/{id}")
    public Optional<Permanent> getPermanent(@PathVariable Long id){
        return permanentRepository.findById(id);
    }
    @PostMapping("/permanent/ajouter")
    public Permanent savePermanent(@RequestBody Permanent permanent){
        return permanentRepository.save(permanent);
    }

//    @GetMapping(value = "/ChefDepartement/Accueil")
//    public String accueilChefDepartement(Model model, Principal principal) {
//        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
//        model.addAttribute("nom", user.getNom());
//        model.addAttribute("prenom", user.getPrenom().charAt(0));
//        return "template_ChefDepartement";
//    }

    @PostMapping(value = "/ChefDepartement/ajouterPermanent")
    public String ajouterPermanent(Permanent permanent) {
        String password = passwordEncoder.encode("Passer123");
        permanent.setPassword(password);
        permanent.setDateCreation(new Date());
        permanent.setActive(true);
        Role role = utilisateurService.addRole(new Role("Permanent"));
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        permanent.setRoles(roles);
        enseignantService.addEnseignant(permanent);
        return "redirect:/ChefDepartement/Enseignant";
    }

    @PostMapping(value = "/ChefDepartement/modifierPermanent")
    public String modifierPermanent(Permanent permanent) {
        Permanent permanent1 = permanentService.getPermanentById(permanent.getId());
        permanent1.setMatricule(permanent.getMatricule());
        permanent1.setNom(permanent.getNom());
        permanent1.setPrenom(permanent.getPrenom());
        permanent1.setSpecialite(permanent.getSpecialite());
        permanent1.setGrade(permanent.getGrade());
        enseignantService.updateEnseignant(permanent1);
        return "redirect:/ChefDepartement/Enseignant";
    }

    @PostMapping(value = "/ChefDepartement/activerPermanent")
    public String activerPermanent(Long id) {
        enseignantService.activer(id);
        return "redirect:/ChefDepartement/Enseignant";
    }

    @PostMapping(value = "/ChefDepartement/archiverPermanent")
    public String archiverPermanent(Long id) {
        enseignantService.archive(id);
        return "redirect:/ChefDepartement/Enseignant";
    }
}