package in.sh.main.repositories;

import in.sh.main.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface OrdersRepository extends JpaRepository<Orders ,Long> {
     String SELECT_QUERY="SELECT o.date_of_purchase, c.description, c.image_url, c.name, c.updated_on FROM orders o JOIN\n" +
             "course c ON o.course_name = c.name WHERE o.user_email=:email";
   @Query(value = SELECT_QUERY,nativeQuery = true)
   List<Object[]> findPurchasedCoursesByEmail(@Param("email") String email);
}
