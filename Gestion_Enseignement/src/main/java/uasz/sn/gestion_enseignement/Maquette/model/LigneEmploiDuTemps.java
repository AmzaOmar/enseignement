package uasz.sn.gestion_enseignement.Maquette.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class LigneEmploiDuTemps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String jour;
    @ManyToOne // Une salle peut être utilisée plusieurs fois
    private Salle salle;
    @ManyToOne
    private Enseignement enseignement;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idEmploi")
    private EmploiDuTemps emploiDuTemps;
}
