package uasz.sn.gestion_enseignement.Maquette.dto;

import jakarta.persistence.ManyToOne;
import lombok.Data;
import uasz.sn.gestion_enseignement.Maquette.model.Enseignement;
import uasz.sn.gestion_enseignement.Maquette.model.Salle;

import java.time.LocalTime;

@Data
public class LigneEmploiDto {

    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Long salleId;
    private Long enseignementId;
    private String jour;

}
