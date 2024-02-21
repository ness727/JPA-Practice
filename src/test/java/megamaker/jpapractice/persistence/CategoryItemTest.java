package megamaker.jpapractice.persistence;

import jakarta.persistence.EntityManager;
import megamaker.jpapractice.domain.manytomany.Category;
import megamaker.jpapractice.domain.manytomany.CategoryItem;
import megamaker.jpapractice.domain.manytomany.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaTransactionManager;
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
    //@Transactional
    public void save() {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());

        Category category = Category.builder().name("가전").build();
        entityManager.persist(category);

        Item item = Item.builder().name("tv").build();
        entityManager.persist(item);

        CategoryItem categoryItem = CategoryItem.builder().category(category).item(item).build();
        entityManager.persist(categoryItem);

        txManager.commit(status);

        Assertions.assertThat(entityManager.find(CategoryItem.class, categoryItem)).isNull();
    }
}
