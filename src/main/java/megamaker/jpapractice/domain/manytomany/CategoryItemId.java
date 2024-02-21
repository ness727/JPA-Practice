package megamaker.jpapractice.domain.manytomany;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class CategoryItemId implements Serializable {
    private Category category;
    private Item item;
}
