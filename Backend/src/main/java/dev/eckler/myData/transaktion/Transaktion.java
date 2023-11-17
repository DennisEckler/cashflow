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
  private String agent;
  @NotNull
  private String bookingText;
  @NotNull
  private String purpose;
  @NotNull
  private float amount;
  @Enumerated(EnumType.STRING)
  private Category category;

  public Transaktion(Date valutaDate, String agent, String bookingText, String purpose, float amount) {
    this.valutaDate = valutaDate;
    this.agent = agent;
    this.bookingText = bookingText;
    this.purpose = purpose;
    this.amount = amount;
  }

  public Transaktion() {
  };

  public Date getDate() {
    return this.valutaDate;
  }

  public void setDate(Date valutaDate) {
    this.valutaDate = valutaDate;
  }

  public String getAgent() {
    return this.agent;
  }

  public void setAgent(String agent) {
    this.agent = agent;
  }

  public String getBookingText() {
    return this.bookingText;
  }

  public void setBookingText(String bookingText) {
    this.bookingText = bookingText;
  }

  public String getPurpose() {
    return this.purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public float getAmount() {
    return this.amount;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {

    return this.valutaDate + " " + this.amount + " " + this.purpose + " " + this.bookingText;
  }

}
