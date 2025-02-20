package uasz.sn.gestion_enseignement.Maquette.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.gestion_enseignement.Authentification.model.Utilisateur;
import uasz.sn.gestion_enseignement.Utilisateur.model.Enseignant;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Choix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // CM, TD, TP

    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "ec_id", nullable = false)
    private EC ec; // L’EC choisi

    private LocalDate date;
    @ManyToOne
    private Formation formation;
    @ManyToOne
    private Classe classe;


    private boolean accepteParChef; // Pour indiquer si le chef a validé ce choix
}

