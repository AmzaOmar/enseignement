package uasz.sn.gestion_enseignement.Maquette.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RepartitionDto {
    private String classe;
    private int effectif;
    private int nbreGroupe;
    private String semestre;
    private String nomEc;
    private int creditEc;
    private int dureeCour;
    private String enseignantCM;
    private int cm;
    private String ResponsableTD;
    private String ResponsableTP;
    private int td;
    private int tp;



}
