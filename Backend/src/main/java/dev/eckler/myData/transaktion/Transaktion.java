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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  private Date date;
  @NotNull
  private String agent;
  @NotNull
  private String bookingText;
  @NotNull
  private String purpose;
  @NotNull
  private float amount;
  @NotNull
  @Enumerated(EnumType.STRING)
  private Category category;

  public Transaktion(Date date, String agent, String bookingText, String purpose, float amount,
      Category category) {
    this.date = date;
    this.agent = agent;
    this.bookingText = bookingText;
    this.purpose = purpose;
    this.amount = amount;
    this.category = category;
  }

  public Transaktion() {
  };

  public Long getID() {
    return this.id;
  }

  public Date getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
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

  public Category getCategory() {
    return this.category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  @Override
  public String toString() {

    return this.date + " " + this.amount + " " + this.purpose + " " + this.bookingText + " " + this.category + " "
        + this.agent + "id: " + this.id;
  }

}
