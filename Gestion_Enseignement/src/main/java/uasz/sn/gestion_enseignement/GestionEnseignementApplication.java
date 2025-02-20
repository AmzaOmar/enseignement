package uasz.sn.gestion_enseignement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uasz.sn.gestion_enseignement.Authentification.model.Role;
import uasz.sn.gestion_enseignement.Authentification.service.UtilisateurService;
import uasz.sn.gestion_enseignement.Maquette.model.Salle;
import uasz.sn.gestion_enseignement.Maquette.repository.SalleRepository;
import uasz.sn.gestion_enseignement.Utilisateur.model.Permanent;
import uasz.sn.gestion_enseignement.Utilisateur.model.Vacataire;
import uasz.sn.gestion_enseignement.Utilisateur.service.EnseignantService;

import java.util.Date;

@SpringBootApplication
public class GestionEnseignementApplication implements CommandLineRunner {
    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SalleRepository salleRepository;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(GestionEnseignementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


//        Role chefDepartement = utilisateurService.addRole(new Role("ChefDepartement"));
//        Role permanent = utilisateurService.addRole(new Role("Permanent"));
//        Role vacataire = utilisateurService.addRole(new Role("Vacataire"));
//        String password = passwordEncoder().encode("Passer123");

//        String[] types = {"S", "A", "B", "C"};
//        int count = 0;
//        for (String type : types) {
//            for (int i = 1; i <= 5; i++) {
//                if (count >= 15) break;
//                Salle salle = new Salle();
//                salle.setNom(type + (200 + i)); // Génère des noms variés comme S201, A202, B203...
//                salleRepository.save(salle);
//                count++;
//            }
//            if (count >= 15) break;
//        }
//        System.out.println("15 salles ajoutées avec succès !");




//
//        Permanent user1 = new Permanent();
//        user1.setPassword(password);
//        user1.setNom("SOW");
//        user1.setPrenom("Muhamed");
//        user1.setUsername("msow@uasz.sn");
//        user1.setDateCreation(new Date());
//        user1.setActive(true);
//        user1.setSpecialite("Full Stack");
//        user1.setMatricule("MS22");
//        user1.setGrade("Professeur");
//        enseignantService.addEnseignant(user1);
//        utilisateurService.addUtilisateurRole(user1, permanent);
//
//        Vacataire user2 = new Vacataire();
//        user2.setPassword(password);
//        user2.setNom("SARR");
//        user2.setPrenom("Babacar");
//        user2.setUsername("bsarr@uasz.sn");
//        user2.setDateCreation(new Date());
//        user2.setActive(true);
//        user2.setSpecialite("Back End");
//        user2.setNiveau("Doctorant");
//        enseignantService.addEnseignant(user2);
//        utilisateurService.addUtilisateurRole(user2, vacataire);
//
//        Permanent user3 = new Permanent();
//        user3.setNom("NIANG");
//        user3.setPrenom("Maguette");
//        user3.setUsername("mniang@uasz.sn");
//        user3.setPassword(password);
//        user3.setDateCreation(new Date());
//        user3.setActive(true);
//        user3.setSpecialite("Front End");
//        user3.setMatricule("MN78");
//        user3.setGrade("Professeur");
//        enseignantService.addEnseignant(user3);
//        utilisateurService.addUtilisateurRole(user3, chefDepartement);
    }}
