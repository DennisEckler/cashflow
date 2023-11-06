package dev.eckler.myData.transaktion;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Transaktion {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Date valutaDate;
  private String holder;

  public Transaktion() {

  }

}
