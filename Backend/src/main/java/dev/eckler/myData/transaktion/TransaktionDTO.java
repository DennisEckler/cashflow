package dev.eckler.myData.transaktion;

import dev.eckler.myData.shared.Category;

public class TransaktionDTO {
  private Long id;
  private Category category;

  public TransaktionDTO(Long id, Category category) {
    this.id = id;
    this.category = category;
  }

  Long getId() {
    return this.id;
  }

  Category getCategory() {
    return this.category;
  }

}
