package megamaker.jpapractice.persistence;

import jakarta.persistence.EntityManager;
import megamaker.jpapractice.domain.manytomany.Category;
import megamaker.jpapractice.domain.manytomany.CategoryItem;
import megamaker.jpapractice.domain.manytomany.CategoryItemId;
import megamaker.jpapractice.domain.manytomany.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;

@SpringBootTest
public class CategoryItemTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PlatformTransactionManager txManager;

    @Test
    @Transactional
    public void save() {
        //TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());

        // 카테고리
        Category category = Category.builder().name("가전").build();
        entityManager.persist(category);

        // 아이템
        Item item = Item.builder().name("tv").build();
        entityManager.persist(item);

        // 카테고리_아이템
        CategoryItem categoryItem = new CategoryItem(category, item);
        entityManager.persist(categoryItem);

        //txManager.commit(status);

        CategoryItemId categoryItemId = new CategoryItemId(category.getId(), item.getId());
        Assertions.assertThat(entityManager.find(CategoryItem.class, categoryItemId)).isNotNull();
    }
}
