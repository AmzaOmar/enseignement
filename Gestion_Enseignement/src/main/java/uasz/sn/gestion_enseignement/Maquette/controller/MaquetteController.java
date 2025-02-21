package uasz.sn.gestion_enseignement.Maquette.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import uasz.sn.gestion_enseignement.Maquette.model.*;
import uasz.sn.gestion_enseignement.Maquette.repository.ClasseRepository;
import uasz.sn.gestion_enseignement.Maquette.repository.ECRepository;
import uasz.sn.gestion_enseignement.Maquette.repository.MaquetteRepository;
import uasz.sn.gestion_enseignement.Maquette.service.*;
import uasz.sn.gestion_enseignement.Maquette.service.MaquetteService;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class MaquetteController {

    @Autowired
    private ClasseService classeService;

    @Autowired
    private MaquetteService maquetteService;

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private MaquetteRepository maquetteRepository;
    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private FormationService formationService;
    private  final ECService ecService;
    private final ECRepository ecRepository;

    @GetMapping("/ChefDepartement/Maquette")
    public String genererMaquette(Model model, Principal principal) {
        List<Formation> formations = formationService.findAllFormation();
        List<Classe> classes=classeService.findAllClasse();
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("classes",classes);
        model.addAttribute("formations", formations);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "genererMaquette";
    }
    @GetMapping("/Vacataire/Maquette")
    public String genererMaquetteVacataire(Model model, Principal principal) {
        List<Formation> formations = formationService.findAllFormation();
        List<Classe> classes=classeService.findAllClasse();
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("classes",classes);
        model.addAttribute("formations", formations);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "genererMaquetteVacataire";
    }
    @GetMapping("/Permanent/Maquette")
    public String genererMaquettePermanent(Model model, Principal principal) {
        List<Formation> formations = formationService.findAllFormation();
        List<Classe> classes=classeService.findAllClasse();
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("classes",classes);
        model.addAttribute("formations", formations);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "genererMaquettePermanent";
    }
    // Afficher une classe avec sa maquette associée
    @GetMapping("/ChefDepartement/Classe/{classeId}/Maquette")
    public String maquette(@PathVariable("classeId") Long classeId, Model model, Principal principal) {
        Classe classe = classeService.findById(classeId);
        Maquette maquette = classe.getMaquette();
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());

        model.addAttribute("classe", classe);
        model.addAttribute("maquettes", maquetteRepository.findByClasse(classe));
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "maquette";
    }
    @PostMapping("/ChefDepartement/Classe/{classeid}/Maquette/modifierMaquette")
    public String modifierMaquette(@PathVariable("classeid") String id, Maquette maquette) {
        Long Id = Long.parseLong(id);
        Classe classe=classeService.findById(Id);
        maquetteService.updateMaquette(maquette);
        Maquette maquette1=classe.getMaquette();


        classe.setMaquette(maquette1);
        classeService.updateClasse(classe);
        return "redirect:/ChefDepartement/Classe/" + id + "/Maquette";
    }
    @PostMapping("/ChefDepartement/Classe/{classeid}/Maquette/supprimerMaquette")
    public String supprimerMaquette(@PathVariable("classeid") String id, Maquette maquette) {
        Long Id = Long.parseLong(id);
        Classe classe=classeService.findById(Id);
       System.out.println("classe: "+classe.getCode());
       maquette.setClasse(null);
       maquetteService.addMaquette(maquette);
       classe.setMaquette(null);

       classeService.addClasse(classe);



        maquetteService.deleteMaquette(maquette.getId());






        return "redirect:/ChefDepartement/Classe/" + id + "/Maquette";
    }

    @GetMapping("/generer-maquette")
    public String afficherMaquette(@RequestParam("formationId") Long formationId,
                                   @RequestParam("niveau") String niveau,
                                   Model model, RedirectAttributes redirectAttributes,Principal principal) {

        Formation formation = formationService.findById(formationId);
        List<Classe> classes=formation.getClasses();
        Classe classe1 = null;
        for (Classe classe : classes) {
            if (classe.getNiveau().equals(niveau)) {
                classe1 = classe;
                break; // Arrêter dès qu'on trouve la classe
            }
        }

        if (classe1 == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le niveau sélectionné n'existe pas pour cette formation.");
//            redirectAttributes.getFlashAttributes("errorMessage", "Le niveau sélectionné n'existe pas pour cette formation.");
//            return "redirect:/genererMaquette";
        }


        Maquette maquette = classe1.getMaquette();

        List<UE> ues = maquette.getUes();

        // Calcul des totaux pour chaque UE
        ues.forEach(ue -> {
            int nbrEC = ue.getEcs().size();

            int totalCm = ue.getEcs().stream().mapToInt(EC::getCm).sum();
            int totalTd = ue.getEcs().stream().mapToInt(EC::getTd).sum();
            int totalTp = ue.getEcs().stream().mapToInt(EC::getTp).sum();
            int totalTpe = ue.getEcs().stream().mapToInt(EC::getTpe).sum();
            int totalVht = ue.getEcs().stream().mapToInt(EC::getVht).sum();
            int totalCoeff = ue.getEcs().stream().mapToInt(EC::getCoefficient).sum();

            // Assurez-vous que "credit" est défini, ou calculez-le selon la logique métier
            int totalCredit = ue.getCredit();

            ue.setTotalCm(totalCm);
            ue.setTotalTd(totalTd);
            ue.setTotalTp(totalTp);
            ue.setTotalTpe(totalTpe);
            ue.setTotalVht(totalVht);
            ue.setNbrEC(nbrEC);
            ue.setTotalCoeff(totalCoeff);
            ue.setTotalCredit(totalCredit);
        });

        // Création des maps pour stocker les totaux par semestre
        Map<String, Integer> semestreTotalCmMap = new HashMap<>();
        Map<String, Integer> semestreTotalTpMap = new HashMap<>();
        Map<String, Integer> semestreTotalTdMap = new HashMap<>();
        Map<String, Integer> semestreTotalTpeMap = new HashMap<>();
        Map<String, Integer> semestreTotalVhtMap = new HashMap<>();
        Map<String, Integer> semestreTotalCreditMap = new HashMap<>();
        Map<String, Integer> semestreTotalCoeffMap = new HashMap<>();

        // Calcul des sommes pour chaque semestre
        for (UE ue : ues) {
            String semestre = ue.getSemestre();

            semestreTotalCmMap.put(semestre, semestreTotalCmMap.getOrDefault(semestre, 0) + ue.getTotalCm());
            semestreTotalTpMap.put(semestre, semestreTotalTpMap.getOrDefault(semestre, 0) + ue.getTotalTp());
            semestreTotalTdMap.put(semestre, semestreTotalTdMap.getOrDefault(semestre, 0) + ue.getTotalTd());
            semestreTotalTpeMap.put(semestre, semestreTotalTpeMap.getOrDefault(semestre, 0) + ue.getTotalTpe());
            semestreTotalVhtMap.put(semestre, semestreTotalVhtMap.getOrDefault(semestre, 0) + ue.getTotalVht());
            semestreTotalCreditMap.put(semestre, semestreTotalCreditMap.getOrDefault(semestre, 0) + ue.getTotalCredit());
            semestreTotalCoeffMap.put(semestre, semestreTotalCoeffMap.getOrDefault(semestre, 0) + ue.getTotalCoeff());
        }

        // Mise à jour de chaque UE avec les totaux calculés pour son semestre
        for (UE ue : ues) {
            ue.setTotalCm(semestreTotalCmMap.get(ue.getSemestre()));
            ue.setTotalTp(semestreTotalTpMap.get(ue.getSemestre()));
            ue.setTotalTd(semestreTotalTdMap.get(ue.getSemestre()));
            ue.setTotalTpe(semestreTotalTpeMap.get(ue.getSemestre()));
            ue.setTotalVht(semestreTotalVhtMap.get(ue.getSemestre()));
            ue.setTotalCredit(semestreTotalCreditMap.get(ue.getSemestre()));
            ue.setTotalCoeff(semestreTotalCoeffMap.get(ue.getSemestre()));
        }

        List<EC> ecs=ecRepository.findByUeMaquetteId(classe1.getMaquette().getId());

        // Grouper les UE par semestre
        Map<String, List<UE>> uesParSemestre = ues.stream()
                .collect(Collectors.groupingBy(UE::getSemestre));
        List<Formation> formations = formationService.findAllFormation();
        List<Classe> classesList=classeService.findAllClasse();
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("classes",classesList);
        model.addAttribute("formations", formations);
        model.addAttribute("ecs", ecs);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));

        // Ajouter les attributs au modèle pour la vue
        model.addAttribute("formation", formation);
        model.addAttribute("classe", classe1);
        model.addAttribute("uesParSemestre", uesParSemestre);


        return "genererMaquette";
    }
    @GetMapping("/Permanent/generer-maquette")
    public String afficherMaquettePer(@RequestParam("formationId") Long formationId,
                                   @RequestParam("niveau") String niveau,
                                   Model model, RedirectAttributes redirectAttributes,Principal principal) {

        Formation formation = formationService.findById(formationId);
        List<Classe> classes=formation.getClasses();
        Classe classe1 = null;
        for (Classe classe : classes) {
            if (classe.getNiveau().equals(niveau)) {
                classe1 = classe;
                break; // Arrêter dès qu'on trouve la classe
            }
        }

        if (classe1 == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le niveau sélectionné n'existe pas pour cette formation.");
//            redirectAttributes.getFlashAttributes("errorMessage", "Le niveau sélectionné n'existe pas pour cette formation.");
//            return "redirect:/genererMaquette";
        }


        Maquette maquette = classe1.getMaquette();

        List<UE> ues = maquette.getUes();

        // Calcul des totaux pour chaque UE
        ues.forEach(ue -> {
            int nbrEC = ue.getEcs().size();

            int totalCm = ue.getEcs().stream().mapToInt(EC::getCm).sum();
            int totalTd = ue.getEcs().stream().mapToInt(EC::getTd).sum();
            int totalTp = ue.getEcs().stream().mapToInt(EC::getTp).sum();
            int totalTpe = ue.getEcs().stream().mapToInt(EC::getTpe).sum();
            int totalVht = ue.getEcs().stream().mapToInt(EC::getVht).sum();
            int totalCoeff = ue.getEcs().stream().mapToInt(EC::getCoefficient).sum();

            // Assurez-vous que "credit" est défini, ou calculez-le selon la logique métier
            int totalCredit = ue.getCredit();

            ue.setTotalCm(totalCm);
            ue.setTotalTd(totalTd);
            ue.setTotalTp(totalTp);
            ue.setTotalTpe(totalTpe);
            ue.setTotalVht(totalVht);
            ue.setNbrEC(nbrEC);
            ue.setTotalCoeff(totalCoeff);
            ue.setTotalCredit(totalCredit);
        });

        // Création des maps pour stocker les totaux par semestre
        Map<String, Integer> semestreTotalCmMap = new HashMap<>();
        Map<String, Integer> semestreTotalTpMap = new HashMap<>();
        Map<String, Integer> semestreTotalTdMap = new HashMap<>();
        Map<String, Integer> semestreTotalTpeMap = new HashMap<>();
        Map<String, Integer> semestreTotalVhtMap = new HashMap<>();
        Map<String, Integer> semestreTotalCreditMap = new HashMap<>();
        Map<String, Integer> semestreTotalCoeffMap = new HashMap<>();

        // Calcul des sommes pour chaque semestre
        for (UE ue : ues) {
            String semestre = ue.getSemestre();

            semestreTotalCmMap.put(semestre, semestreTotalCmMap.getOrDefault(semestre, 0) + ue.getTotalCm());
            semestreTotalTpMap.put(semestre, semestreTotalTpMap.getOrDefault(semestre, 0) + ue.getTotalTp());
            semestreTotalTdMap.put(semestre, semestreTotalTdMap.getOrDefault(semestre, 0) + ue.getTotalTd());
            semestreTotalTpeMap.put(semestre, semestreTotalTpeMap.getOrDefault(semestre, 0) + ue.getTotalTpe());
            semestreTotalVhtMap.put(semestre, semestreTotalVhtMap.getOrDefault(semestre, 0) + ue.getTotalVht());
            semestreTotalCreditMap.put(semestre, semestreTotalCreditMap.getOrDefault(semestre, 0) + ue.getTotalCredit());
            semestreTotalCoeffMap.put(semestre, semestreTotalCoeffMap.getOrDefault(semestre, 0) + ue.getTotalCoeff());
        }

        // Mise à jour de chaque UE avec les totaux calculés pour son semestre
        for (UE ue : ues) {
            ue.setTotalCm(semestreTotalCmMap.get(ue.getSemestre()));
            ue.setTotalTp(semestreTotalTpMap.get(ue.getSemestre()));
            ue.setTotalTd(semestreTotalTdMap.get(ue.getSemestre()));
            ue.setTotalTpe(semestreTotalTpeMap.get(ue.getSemestre()));
            ue.setTotalVht(semestreTotalVhtMap.get(ue.getSemestre()));
            ue.setTotalCredit(semestreTotalCreditMap.get(ue.getSemestre()));
            ue.setTotalCoeff(semestreTotalCoeffMap.get(ue.getSemestre()));
        }

        List<EC> ecs=ecRepository.findByUeMaquetteId(classe1.getMaquette().getId());

        // Grouper les UE par semestre
        Map<String, List<UE>> uesParSemestre = ues.stream()
                .collect(Collectors.groupingBy(UE::getSemestre));
        List<Formation> formations = formationService.findAllFormation();
        List<Classe> classesList=classeService.findAllClasse();
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("classes",classesList);
        model.addAttribute("formations", formations);
        model.addAttribute("ecs", ecs);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));

        // Ajouter les attributs au modèle pour la vue
        model.addAttribute("formation", formation);
        model.addAttribute("classe", classe1);
        model.addAttribute("uesParSemestre", uesParSemestre);


        return "genererMaquettePermanent";
    }
    @GetMapping("/Vacataire/generer-maquette")
    public String afficherMaquetteVac(@RequestParam("formationId") Long formationId,
                                   @RequestParam("niveau") String niveau,
                                   Model model, RedirectAttributes redirectAttributes,Principal principal) {

        Formation formation = formationService.findById(formationId);
        List<Classe> classes=formation.getClasses();
        Classe classe1 = null;
        for (Classe classe : classes) {
            if (classe.getNiveau().equals(niveau)) {
                classe1 = classe;
                break; // Arrêter dès qu'on trouve la classe
            }
        }

        if (classe1 == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le niveau sélectionné n'existe pas pour cette formation.");
//            redirectAttributes.getFlashAttributes("errorMessage", "Le niveau sélectionné n'existe pas pour cette formation.");
//            return "redirect:/genererMaquette";
        }


        Maquette maquette = classe1.getMaquette();

        List<UE> ues = maquette.getUes();

        // Calcul des totaux pour chaque UE
        ues.forEach(ue -> {
            int nbrEC = ue.getEcs().size();

            int totalCm = ue.getEcs().stream().mapToInt(EC::getCm).sum();
            int totalTd = ue.getEcs().stream().mapToInt(EC::getTd).sum();
            int totalTp = ue.getEcs().stream().mapToInt(EC::getTp).sum();
            int totalTpe = ue.getEcs().stream().mapToInt(EC::getTpe).sum();
            int totalVht = ue.getEcs().stream().mapToInt(EC::getVht).sum();
            int totalCoeff = ue.getEcs().stream().mapToInt(EC::getCoefficient).sum();

            // Assurez-vous que "credit" est défini, ou calculez-le selon la logique métier
            int totalCredit = ue.getCredit();

            ue.setTotalCm(totalCm);
            ue.setTotalTd(totalTd);
            ue.setTotalTp(totalTp);
            ue.setTotalTpe(totalTpe);
            ue.setTotalVht(totalVht);
            ue.setNbrEC(nbrEC);
            ue.setTotalCoeff(totalCoeff);
            ue.setTotalCredit(totalCredit);
        });

        // Création des maps pour stocker les totaux par semestre
        Map<String, Integer> semestreTotalCmMap = new HashMap<>();
        Map<String, Integer> semestreTotalTpMap = new HashMap<>();
        Map<String, Integer> semestreTotalTdMap = new HashMap<>();
        Map<String, Integer> semestreTotalTpeMap = new HashMap<>();
        Map<String, Integer> semestreTotalVhtMap = new HashMap<>();
        Map<String, Integer> semestreTotalCreditMap = new HashMap<>();
        Map<String, Integer> semestreTotalCoeffMap = new HashMap<>();

        // Calcul des sommes pour chaque semestre
        for (UE ue : ues) {
            String semestre = ue.getSemestre();

            semestreTotalCmMap.put(semestre, semestreTotalCmMap.getOrDefault(semestre, 0) + ue.getTotalCm());
            semestreTotalTpMap.put(semestre, semestreTotalTpMap.getOrDefault(semestre, 0) + ue.getTotalTp());
            semestreTotalTdMap.put(semestre, semestreTotalTdMap.getOrDefault(semestre, 0) + ue.getTotalTd());
            semestreTotalTpeMap.put(semestre, semestreTotalTpeMap.getOrDefault(semestre, 0) + ue.getTotalTpe());
            semestreTotalVhtMap.put(semestre, semestreTotalVhtMap.getOrDefault(semestre, 0) + ue.getTotalVht());
            semestreTotalCreditMap.put(semestre, semestreTotalCreditMap.getOrDefault(semestre, 0) + ue.getTotalCredit());
            semestreTotalCoeffMap.put(semestre, semestreTotalCoeffMap.getOrDefault(semestre, 0) + ue.getTotalCoeff());
        }

        // Mise à jour de chaque UE avec les totaux calculés pour son semestre
        for (UE ue : ues) {
            ue.setTotalCm(semestreTotalCmMap.get(ue.getSemestre()));
            ue.setTotalTp(semestreTotalTpMap.get(ue.getSemestre()));
            ue.setTotalTd(semestreTotalTdMap.get(ue.getSemestre()));
            ue.setTotalTpe(semestreTotalTpeMap.get(ue.getSemestre()));
            ue.setTotalVht(semestreTotalVhtMap.get(ue.getSemestre()));
            ue.setTotalCredit(semestreTotalCreditMap.get(ue.getSemestre()));
            ue.setTotalCoeff(semestreTotalCoeffMap.get(ue.getSemestre()));
        }

        List<EC> ecs=ecRepository.findByUeMaquetteId(classe1.getMaquette().getId());

        // Grouper les UE par semestre
        Map<String, List<UE>> uesParSemestre = ues.stream()
                .collect(Collectors.groupingBy(UE::getSemestre));
        List<Formation> formations = formationService.findAllFormation();
        List<Classe> classesList=classeService.findAllClasse();
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("classes",classesList);
        model.addAttribute("formations", formations);
        model.addAttribute("ecs", ecs);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));

        // Ajouter les attributs au modèle pour la vue
        model.addAttribute("formation", formation);
        model.addAttribute("classe", classe1);
        model.addAttribute("uesParSemestre", uesParSemestre);


        return "genererMaquetteVacataire";
    }





    @PostMapping("/ChefDepartement/Classe/{classeid}/Maquette/ajouterMaquette")
    public String ajouterMaquette(@PathVariable("classeid") String id,Maquette maquette) {
        Long Id = Long.parseLong(id);
        Classe classe=classeService.findById(Id);
        maquette.setClasse(classe);




        classe.setMaquette(maquette);
        classeService.addClasse(classe);
        return "redirect:/ChefDepartement/Classe/" + id +"/Maquette";
    }




}

