package ma.enset.comparaison.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.comparaison.enums.Status;
import org.springframework.data.mongodb.core.mapping.Document;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor

public class File {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String content;
    @ManyToOne
    private Repertoire repertoire;
    @Enumerated(EnumType.STRING)
    private Status status;
}




