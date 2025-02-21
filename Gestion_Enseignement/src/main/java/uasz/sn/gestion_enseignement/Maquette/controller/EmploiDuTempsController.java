package uasz.sn.gestion_enseignement.Maquette.controller;


import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.repository.UtilisateurRepository;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import uasz.sn.gestion_enseignement.Maquette.dto.EmploiDto;
import uasz.sn.gestion_enseignement.Maquette.dto.EmploiPersoDto;
import uasz.sn.gestion_enseignement.Maquette.dto.LigneEmploiDto;
import uasz.sn.gestion_enseignement.Maquette.model.*;
import uasz.sn.gestion_enseignement.Maquette.repository.EmploiDuTempsRepository;
import uasz.sn.gestion_enseignement.Maquette.repository.EnseignementRepository;
import uasz.sn.gestion_enseignement.Maquette.repository.LigneEmploiDuTempsRepository;
import uasz.sn.gestion_enseignement.Maquette.repository.SalleRepository;
import uasz.sn.gestion_enseignement.Maquette.service.EmploiDuTempsService;
import uasz.sn.gestion_enseignement.Maquette.service.EnseignementService;
import uasz.sn.gestion_enseignement.Utilisateur.service.PermanentService;
import uasz.sn.gestion_enseignement.Utilisateur.service.VacataireService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmploiDuTempsController {
    private final EmploiDuTempsService emploiDuTempsService;
    private final UtilisateurService utilisateurService;
    private final SalleRepository salleRepository;
    private final EnseignementService enseignementService;
    private final EnseignementRepository enseignementRepository;
    private final EmploiDuTempsRepository emploiDuTempsRepository;
    private final LigneEmploiDuTempsRepository ligneEmploiDuTempsRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final VacataireService vacataireService;
    private final PermanentService permanentService;

    public EmploiDuTempsController(EmploiDuTempsService emploiDuTempsService, UtilisateurService utilisateurService, SalleRepository salleRepository, EnseignementService enseignementService, EnseignementRepository enseignementRepository, EmploiDuTempsRepository emploiDuTempsRepository, LigneEmploiDuTempsRepository ligneEmploiDuTempsRepository, UtilisateurRepository utilisateurRepository, VacataireService vacataireService, PermanentService permanentService) {
        this.emploiDuTempsService = emploiDuTempsService;
        this.utilisateurService = utilisateurService;
        this.salleRepository = salleRepository;
        this.enseignementService = enseignementService;
        this.enseignementRepository = enseignementRepository;
        this.emploiDuTempsRepository = emploiDuTempsRepository;
        this.ligneEmploiDuTempsRepository = ligneEmploiDuTempsRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.vacataireService = vacataireService;
        this.permanentService = permanentService;
    }
    @GetMapping("/ChefDepartement/EmploiDuTemps")
    public String affiche(Model model, Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        System.out.println("proff:"+email);
        Utilisateur utilisateur =utilisateurRepository.findByUsername(email);
        List<EmploiPersoDto> emploiPersoDtos=new ArrayList<>();
        List<EmploiDuTemps> emploiDuTemps=emploiDuTempsRepository.findAll();
        List<String> jours = List.of("lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi");
        model.addAttribute("jours", jours);
        for(EmploiDuTemps emploi:emploiDuTemps){
            for(LigneEmploiDuTemps ligneEmploiDuTemps:emploi.getLigneEmploiDuTemps()){

                if(ligneEmploiDuTemps.getEnseignement().getChoix().getEnseignant().getUsername().equals(utilisateur.getUsername())){
                    EmploiPersoDto emploiPersoDto=new EmploiPersoDto();
                    System.out.println("emploissssss:"+ligneEmploiDuTemps.getHeureDebut());
                    emploiPersoDto.setEc(ligneEmploiDuTemps.getEnseignement().getChoix().getEc().getLibelle());
                    emploiPersoDto.setSalle(ligneEmploiDuTemps.getSalle().getNom());
                    emploiPersoDto.setType(ligneEmploiDuTemps.getEnseignement().getChoix().getType());
                    emploiPersoDto.setDebut(ligneEmploiDuTemps.getHeureDebut());
                    emploiPersoDto.setJour(ligneEmploiDuTemps.getJour());
                    emploiPersoDto.setFin(ligneEmploiDuTemps.getHeureFin());
                    emploiPersoDtos.add(emploiPersoDto);
                }
            }
        }


        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("emplois", emploiPersoDtos);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "emploiDuTempsChef";
    }

    @GetMapping("/Vacataire/EmploiDuTemps")
    public String afficheVac(Model model, Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        System.out.println("proff:"+email);
        Utilisateur utilisateur =utilisateurRepository.findByUsername(email);
        List<EmploiPersoDto> emploiPersoDtos=new ArrayList<>();
        List<EmploiDuTemps> emploiDuTemps=emploiDuTempsRepository.findAll();
        List<String> jours = List.of("lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi");
        model.addAttribute("jours", jours);
        for(EmploiDuTemps emploi:emploiDuTemps){
            for(LigneEmploiDuTemps ligneEmploiDuTemps:emploi.getLigneEmploiDuTemps()){

                if(ligneEmploiDuTemps.getEnseignement().getChoix().getEnseignant().getUsername().equals(utilisateur.getUsername())){
                    EmploiPersoDto emploiPersoDto=new EmploiPersoDto();
                    System.out.println("emploissssss:"+ligneEmploiDuTemps.getHeureDebut());
                    emploiPersoDto.setEc(ligneEmploiDuTemps.getEnseignement().getChoix().getEc().getLibelle());
                    emploiPersoDto.setSalle(ligneEmploiDuTemps.getSalle().getNom());
                    emploiPersoDto.setType(ligneEmploiDuTemps.getEnseignement().getChoix().getType());
                    emploiPersoDto.setDebut(ligneEmploiDuTemps.getHeureDebut());
                    emploiPersoDto.setJour(ligneEmploiDuTemps.getJour());
                    emploiPersoDto.setFin(ligneEmploiDuTemps.getHeureFin());
                    emploiPersoDtos.add(emploiPersoDto);
                }
            }
        }


        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("emplois", emploiPersoDtos);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "emploiDuTempsVacataire";
    }
    @GetMapping("/Permanent/EmploiDuTemps")
    public String affichePer(Model model, Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        System.out.println("proff:"+email);
        Utilisateur utilisateur =utilisateurRepository.findByUsername(email);
        List<EmploiPersoDto> emploiPersoDtos=new ArrayList<>();
        List<EmploiDuTemps> emploiDuTemps=emploiDuTempsRepository.findAll();
        List<String> jours = List.of("lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi");
        model.addAttribute("jours", jours);
        for(EmploiDuTemps emploi:emploiDuTemps){
            for(LigneEmploiDuTemps ligneEmploiDuTemps:emploi.getLigneEmploiDuTemps()){

                if(ligneEmploiDuTemps.getEnseignement().getChoix().getEnseignant().getUsername().equals(utilisateur.getUsername())){
                    EmploiPersoDto emploiPersoDto=new EmploiPersoDto();
                    System.out.println("emploissssss:"+ligneEmploiDuTemps.getHeureDebut());
                    emploiPersoDto.setEc(ligneEmploiDuTemps.getEnseignement().getChoix().getEc().getLibelle());
                    emploiPersoDto.setSalle(ligneEmploiDuTemps.getSalle().getNom());
                    emploiPersoDto.setType(ligneEmploiDuTemps.getEnseignement().getChoix().getType());
                    emploiPersoDto.setDebut(ligneEmploiDuTemps.getHeureDebut());
                    emploiPersoDto.setJour(ligneEmploiDuTemps.getJour());
                    emploiPersoDto.setFin(ligneEmploiDuTemps.getHeureFin());
                    emploiPersoDtos.add(emploiPersoDto);
                }
            }
        }


        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("emplois", emploiPersoDtos);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "emploiDuTempsPermanent";
    }

    @GetMapping("/ChefDepartement/GestionEmploiDuTemps")
    public String emploi(Model model, Principal principal) {
        List<EmploiDuTemps> emploiDuTemps = emploiDuTempsRepository.findAll();
        List<Enseignement> enseignementsDisponibles = enseignementRepository.findEnseignementsNonUtilises();

        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("enseignements",enseignementsDisponibles);
        model.addAttribute("salles",salleRepository.findAll());
        model.addAttribute("emplois", emploiDuTemps);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "gestionEmploiDuTemps";
    }

    @PostMapping("/ChefDepartement/GestionEmploiDuTemps/ajouter")
    public String addEmploi(@Valid @ModelAttribute @RequestParam("dateDebut")  LocalDate dateDebut,
                            @RequestParam("dateFin")  LocalDate dateFin, EmploiDto emploiDto, BindingResult result, Model model, Principal principal){
        System.out.println("Emploi du temps:"+emploiDto);
        List<LigneEmploiDuTemps> ligneEmploiDuTemps=new ArrayList<>();
        for(LigneEmploiDto ligneEmploiDto:emploiDto.getLigneEmploiDtos()){
            LigneEmploiDuTemps ligneEmploiDuTemps1=new LigneEmploiDuTemps();
            Salle salle=salleRepository.findById(ligneEmploiDto.getSalleId()).get();
            Enseignement enseignement=enseignementRepository.findById(ligneEmploiDto.getEnseignementId()).get();
            ligneEmploiDuTemps1.setEnseignement(enseignement);
            ligneEmploiDuTemps1.setSalle(salle);
            ligneEmploiDuTemps1.setHeureDebut(ligneEmploiDto.getHeureDebut());
            ligneEmploiDuTemps1.setHeureFin(ligneEmploiDto.getHeureFin());
            ligneEmploiDuTemps1.setJour(ligneEmploiDto.getJour());
            ligneEmploiDuTemps.add(ligneEmploiDuTemps1);
        }
        EmploiDuTemps emploiDuTemps=new EmploiDuTemps();
        emploiDuTemps.setDateDebut(dateDebut);
        emploiDuTemps.setDateFin(dateFin);
        emploiDuTempsRepository.save(emploiDuTemps);
        for(LigneEmploiDuTemps ligneEmploiDuTemps1:ligneEmploiDuTemps){
            ligneEmploiDuTemps1.setEmploiDuTemps(emploiDuTemps);
            ligneEmploiDuTempsRepository.save(ligneEmploiDuTemps1);
        }
        emploiDuTemps.setLigneEmploiDuTemps(ligneEmploiDuTemps);
        emploiDuTempsRepository.save(emploiDuTemps);
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        List<Enseignement> enseignementsDisponibles = enseignementRepository.findEnseignementsNonUtilises();
        List<EmploiDuTemps> emploiDuTemps1 = emploiDuTempsService.findAllEmploiDuTemps();
        model.addAttribute("enseignements",enseignementsDisponibles);
        model.addAttribute("salles",salleRepository.findSallesNonUtilises());
        model.addAttribute("emplois", emploiDuTemps1);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "gestionEmploiDuTemps";
    }

    @GetMapping("/ChefDepartement/GestionEmploiDuTemps/supprimer/{id}")
    public String delete(@PathVariable Long id, Model model, Principal principal) {
        EmploiDuTemps emploiDuTemps = emploiDuTempsRepository.findById(id).orElse(null);

        if (emploiDuTemps != null) {
            emploiDuTemps.getLigneEmploiDuTemps().clear();  // Supprime les références avant de supprimer
            emploiDuTempsRepository.delete(emploiDuTemps);
        }

        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        List<Enseignement> enseignementsDisponibles = enseignementRepository.findEnseignementsNonUtilises();
        List<EmploiDuTemps> emploiDuTemps1 = emploiDuTempsService.findAllEmploiDuTemps();

        model.addAttribute("enseignements", enseignementsDisponibles);
        model.addAttribute("salles", salleRepository.findAll());
        model.addAttribute("emplois", emploiDuTemps1);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));

        return "gestionEmploiDuTemps";
    }



}
