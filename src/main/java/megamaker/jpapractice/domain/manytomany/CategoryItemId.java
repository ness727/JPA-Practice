package megamaker.jpapractice.domain.manytomany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryItemId implements Serializable {
    private Long category;
    private Long item;
}
