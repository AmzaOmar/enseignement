package uasz.sn.gestion_enseignement.Maquette.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.gestion_enseignement.Utilisateur.model.Enseignant;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class EmploiDuTemps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    @OneToOne
    private Salle salle;
    @OneToOne
    private Enseignement enseignement;


}
