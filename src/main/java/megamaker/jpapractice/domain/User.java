package megamaker.jpapractice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Role role;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Timestamp createdAt;
}
