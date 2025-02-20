package uasz.sn.gestion_enseignement.Maquette.controller;


import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.repository.UtilisateurRepository;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import uasz.sn.gestion_enseignement.Maquette.dto.ChoixDto;
import uasz.sn.gestion_enseignement.Maquette.model.*;
import uasz.sn.gestion_enseignement.Maquette.repository.ChoixRepository;
import uasz.sn.gestion_enseignement.Maquette.repository.ECRepository;
import uasz.sn.gestion_enseignement.Maquette.repository.EnseignementRepository;
import uasz.sn.gestion_enseignement.Utilisateur.model.Permanent;
import uasz.sn.gestion_enseignement.Utilisateur.model.Vacataire;
import uasz.sn.gestion_enseignement.Utilisateur.repository.EnseignantRepository;
import uasz.sn.gestion_enseignement.Utilisateur.service.EnseignantService;
import uasz.sn.gestion_enseignement.Utilisateur.service.PermanentService;
import uasz.sn.gestion_enseignement.Utilisateur.service.VacataireService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Controller
public class ChoixController {
    private final EnseignantRepository enseignantRepository;
    private final ChoixRepository choixRepository;
    private final EnseignementRepository enseignementRepository;
    private final EnseignantService enseignantService;
    private final UtilisateurRepository utilisateurRepository;
    private final VacataireService vacataireService;
    private final PermanentService permanentService;
    private final ECRepository ecRepository;
    private final UtilisateurService utilisateurService;


    @PostMapping("/soumettre-choix")
    public String ajouterChoix( ChoixDto choixDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        System.out.println("proff:"+email);
        Utilisateur utilisateur =utilisateurRepository.findByUsername(email);
        List<Vacataire> vacataires=vacataireService.getAllVacataires();
        List<Permanent> permanents=permanentService.getAllPermanents();
        System.out.println("choix"+permanents);
        Choix choix = new Choix();
        choix.setDate(LocalDate.now());
        if(utilisateur.getRoles().getFirst().getRole().equals("Permanent")){
            for(Permanent permanent:permanents){
                if(utilisateur.getUsername().equals(permanent.getUsername())){
                    choix.setEnseignant(permanent);
                    break;
                }
            }


        }else if(utilisateur.getRoles().getFirst().getRole().equals("Vacataire")){


            for(Vacataire vacataire:vacataires){
                if(utilisateur.getUsername().equals(vacataire.getUsername())){
                    choix.setEnseignant(vacataire);
                    break;
                }
            }
        }else if(utilisateur.getRoles().getFirst().getRole().equals("ChefDepartement")){
            for(Permanent permanent:permanents){
                if(utilisateur.getUsername().equals(permanent.getUsername())){
                    choix.setEnseignant(permanent);
                    break;
                }
            }
        }



        if(choixDto.getType().equals("cm")){
            choix.setType("cm");

        }else if(choixDto.getType().equals("td")){
            choix.setType("td");

        } else if (choixDto.getType().equals("tp")) {
            choix.setType("tp");

        }


        EC ec = ecRepository.findById(choixDto.getIdEc()).orElse(null);
        choix.setFormation(ec.getUe().getMaquette().getClasse().getFormation());
        choix.setClasse(ec.getUe().getMaquette().getClasse());
       choix.setEc(ec);



        choixRepository.save(choix);







        return "genererMaquette";
    }

    @GetMapping("/ChefDepartement/Choix")
    public String choix(Model model, Principal principal) {
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        List<Choix> choix =choixRepository.findAll();
        model.addAttribute("choix", choix);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));

        return "choix";
    }
    @PostMapping("/ChefDepartement/Choix/Accepter/{choixId}")
    public String AccepterChoix(@PathVariable("choixId") Long choixId, Model model, Principal principal) {
        Choix choix = choixRepository.findById(choixId).orElse(null);
        choix.setAccepteParChef(true);
        choixRepository.save(choix);
        Enseignement enseignement=new Enseignement();

        enseignement.setValide(true);
        enseignement.setChoix(choix);
        enseignementRepository.save(enseignement);
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        List<Choix> choixList =choixRepository.findAll();
        model.addAttribute("choix", choixList);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));

        return "choix";
    }
    @PostMapping("/ChefDepartement/Choix/Refuser/{choixId}")
    public String RefuserChoix(@PathVariable("choixId") Long choixId, Model model, Principal principal) {
        Choix choix = choixRepository.findById(choixId).orElse(null);
        if (choix != null) {
            Enseignement enseignement=enseignementRepository.findByChoix(choix).get();
            if(enseignement!=null){
                enseignementRepository.delete(enseignement);
            }
            choix.setAccepteParChef(false);
            choixRepository.save(choix);
        }

        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        List<Choix> choixList = choixRepository.findAll();
        model.addAttribute("choix", choixList);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));

        return "choix"; // Retourne la même page pour mise à jour
    }

}
