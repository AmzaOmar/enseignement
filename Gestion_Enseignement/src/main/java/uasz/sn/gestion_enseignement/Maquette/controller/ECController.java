package uasz.sn.gestion_enseignement.Maquette.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import uasz.sn.gestion_enseignement.Maquette.model.EC;
import uasz.sn.gestion_enseignement.Maquette.model.Maquette;
import uasz.sn.gestion_enseignement.Maquette.model.UE;
import uasz.sn.gestion_enseignement.Maquette.repository.ECRepository;
import uasz.sn.gestion_enseignement.Maquette.service.ECService;
import uasz.sn.gestion_enseignement.Maquette.service.MaquetteService;
import uasz.sn.gestion_enseignement.Maquette.service.UEService;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class ECController {
    private final ECService ecService;
    private final MaquetteService maquetteService;

    private UEService ueService;
    private  UtilisateurService utilisateurService;
    private ECRepository ecRepository;

    @GetMapping("/ChefDepartement/UE/{ueid}/EC")
    public String ec(@PathVariable("ueid") String id, Model model, Principal principal) {
        Utilisateur user = utilisateurService.findUtilisateur(principal.getName());
        Long Id = Long.parseLong(id);
        UE ue = ueService.findById(Id);
        List<EC> ecs = ue.getEcs();
        model.addAttribute("ue", ue);
        model.addAttribute("ecs", ecService.findECByUE(ue));
        model.addAttribute("nom", user.getNom());
        model.addAttribute("prenom", user.getPrenom().charAt(0));
        return "ec";
    }

    @PostMapping("/ChefDepartement/UE/{ueid}/EC/ajouterEC")
    public String ajouterEC(@PathVariable("ueid") String id, EC ec) {
        Long Id = Long.parseLong(id);
        UE ue = ueService.findById(Id);
        ue.setCoefficient(ue.getCoefficient() + ec.getCoefficient());


        ec.setUe(ue);
        ec.setHTotal(ec.getCm() + ec.getTp() + ec.getTd());
        ec.setVht(ec.getHTotal() + ec.getTpe());
        ecService.addEC(ec);
        List<EC> ecs = ue.getEcs();
        ecs.add(ec);
         ue.setEcs(ecs);
        ueService.updateUE(ue);
        return "redirect:/ChefDepartement/UE/" + id +"/EC";
    }

    @PostMapping("/ChefDepartement/UE/{ueid}/EC/modifierEC")
    public String modifierEC(@PathVariable("ueid") String id, EC ec) {
        Long Id = Long.parseLong(id);
        UE ue = ueService.findById(Id);

        EC ec1=ecRepository.findById(Id).get();
        ue.setCoefficient(ue.getCoefficient() - ec1.getCoefficient()+ec.getCoefficient());

        ec.setHTotal(ec.getCm() + ec.getTp() + ec.getTd());
        ec.setVht(ec.getHTotal() + ec.getTpe());


        ecService.updateEC(ec);
        List<EC> ecs = ue.getEcs();
        ecs.removeIf(e -> Objects.equals(e.getId(), ec.getId()));
        ecs.add(ec);
        ue.setEcs(ecs);
        ueService.updateUE(ue);
        return "redirect:/ChefDepartement/UE/" + id + "/EC";
    }

    @PostMapping("/ChefDepartement/UE/{ueid}/EC/supprimerEC")
    public String supprimerEC(@PathVariable("ueid") String id, EC ec) {
        Long Id = Long.parseLong(id);
        UE ue = ueService.findById(Id);



        ue.setCoefficient(ue.getCoefficient() - ec.getCoefficient());
        ecService.deleteEC(ec.getId());
        List<EC> ecs = ue.getEcs();
        ecs.removeIf(e -> Objects.equals(e.getId(), ec.getId()));
        ue.setEcs(ecs);
        ueService.updateUE(ue);
        return "redirect:/ChefDepartement/UE/" + id + "/EC";
    }
}