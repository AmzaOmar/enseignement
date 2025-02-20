package uasz.sn.gestion_enseignement.Maquette.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle, code, description;
    private int cm, tp, td, hTotal, tpe, vht, coefficient;
    @ToString.Exclude
    @ManyToOne
    private UE ue;
    @ManyToOne
    @JoinColumn(name = "enseignement_id") // Spécifiez la clé étrangère
    private Enseignement enseignement;

    private int credit;
}