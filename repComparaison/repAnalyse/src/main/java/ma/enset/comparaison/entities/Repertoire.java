package ma.enset.comparaison.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.util.List;

@Data
@Entity
@NoArgsConstructor @AllArgsConstructor
public class Repertoire {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String version;
    @JsonIgnore
    @OneToMany(mappedBy = "repertoire", fetch = FetchType.LAZY)
    private List<File> files ;
}
