package uasz.sn.gestion_enseignement.Maquette.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import uasz.sn.gestion_enseignement.Maquette.dto.RepartitionDto;
import uasz.sn.gestion_enseignement.Maquette.model.*;
import uasz.sn.gestion_enseignement.Maquette.repository.ECRepository;
import uasz.sn.gestion_enseignement.Maquette.service.ChoixService;
import uasz.sn.gestion_enseignement.Maquette.service.ClasseService;
import uasz.sn.gestion_enseignement.Maquette.service.EnseignementService;
import uasz.sn.gestion_enseignement.Maquette.service.FormationService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class RepartitionController {

    private final FormationService formationService;
    private final ClasseService classeService;
    private final UtilisateurService utilisateurService;
    private final ECRepository ecRepository;
    private final ChoixService choixService;
    private final EnseignementService enseignementService;

    @GetMapping("/ChefDepartement/Repartition")
    public String genererMaquette(Model model, Principal principal) {
        List<Formation> formations = formationService.findAllFormation();
        List<Classe> classes=classeService.findAllClasse();
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        model.addAttribute("classes",classes);
        model.addAttribute("formations", formations);
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "repartition";
    }

    @GetMapping("/generer-repartition")
    public String afficherMaquette(@RequestParam("formationId") Long formationId,
                                   @RequestParam("niveau") String niveau,
                                   @RequestParam("semestre") String semestre,
                                   Model model, RedirectAttributes redirectAttributes, Principal principal) {

        System.out.println("donnee:"+formationId+" "+niveau+" "+semestre);
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

        List<RepartitionDto> repartitionDtos=new ArrayList<>();

        List<Enseignement> enseignements=enseignementService.findAll();
        List<EC> ecs=new ArrayList<>();


        for (UE ue : ues) {
            if (ue.getSemestre().equals(semestre)) {
                for (EC ec : ue.getEcs()) {
                    for (Enseignement enseignement : enseignements) {
                        if (ec.getLibelle().equals(enseignement.getChoix().getEc().getLibelle())) {
                            RepartitionDto repartitionDto = new RepartitionDto();

                            repartitionDto.setNomEc(ec.getLibelle());
                            repartitionDto.setCreditEc(ec.getCredit());
                            repartitionDto.setCm(ec.getCm());
                            repartitionDto.setTd(ec.getTd());
                            repartitionDto.setTp(ec.getTp());
                            repartitionDto.setClasse(niveau + " " + formation.getNom());
                            repartitionDto.setEffectif(classe1.getEffectif());
                            repartitionDto.setDureeCour(ec.getCm());
                            repartitionDto.setNbreGroupe(classe1.getNbrGroupe());
                            repartitionDto.setSemestre(ue.getSemestre());

                            if (enseignement.getChoix().getType().equals("td")) {
                                repartitionDto.setResponsableTD(enseignement.getChoix().getEnseignant().getPrenom() + " " + enseignement.getChoix().getEnseignant().getNom());
                            } else if (enseignement.getChoix().getType().equals("tp")) {
                                repartitionDto.setResponsableTP(enseignement.getChoix().getEnseignant().getPrenom() + " " + enseignement.getChoix().getEnseignant().getNom());
                            } else {
                                repartitionDto.setEnseignantCM(enseignement.getChoix().getEnseignant().getPrenom() + " " + enseignement.getChoix().getEnseignant().getNom());
                            }

                            repartitionDtos.add(repartitionDto);
                        }
                    }
                }
            }
        }

        model.addAttribute("repartitions",repartitionDtos);







        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());


        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));




        return "repartition";
    }
}
