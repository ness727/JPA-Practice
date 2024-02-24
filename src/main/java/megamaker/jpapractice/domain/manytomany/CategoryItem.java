package megamaker.jpapractice.domain.manytomany;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@IdClass(CategoryItemId.class)
@Entity
@Table(name = "category_item")
public class CategoryItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Id
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public CategoryItem(Category category, Item item) {
        this.category = category;
        this.item = item;
        category.getCategoryItemSet().add(this);
        item.getCategoryItemSet().add(this);
    }
}
