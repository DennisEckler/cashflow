package dev.eckler.myData.transaktion;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TransaktionRepository extends CrudRepository<Transaktion, Long> {

  List<Transaktion> findAllByCategoryIsNull();
}
