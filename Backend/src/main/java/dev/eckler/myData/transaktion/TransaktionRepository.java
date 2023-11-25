package dev.eckler.myData.transaktion;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import dev.eckler.myData.shared.Category;

public interface TransaktionRepository extends CrudRepository<Transaktion, Long> {

  List<Transaktion> findAllByCategory(Category category);

  List<Transaktion> findAllByCategoryNot(Category category);

}
