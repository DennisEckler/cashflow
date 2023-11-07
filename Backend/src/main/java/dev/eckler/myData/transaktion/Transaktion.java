package dev.eckler.myData.transaktion;

import java.sql.Date;

import dev.eckler.myData.shared.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Transaktion {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @NotNull
  private Date valutaDate;
  @NotNull
  private String user;
  @NotNull
  private String bookingText;
  @NotNull
  private String purpose;
  @NotNull
  private float amount;
  @Enumerated(EnumType.STRING)
  private Category category;

  public Transaktion() {

  }

}
