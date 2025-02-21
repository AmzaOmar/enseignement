package uasz.sn.gestion_enseignement.Maquette.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class EmploiPersoDto {

    private String type;
    private String ec;
    private String Salle;
    private String jour;
    private LocalTime debut;
    private LocalTime fin;

}
