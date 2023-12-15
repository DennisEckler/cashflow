package dev.eckler.myData.shared;

public enum Category {
  DENNIS("Dennis", new String[] { "test" }),
  SVETI("Sveti", new String[] { "" }),
  GUTSCHRIFT("Gutschrift", new String[] { "" }),
  MIETE("Miete", new String[] { "" }),
  STROM("Miete", new String[] { "" }),
  INTERNET("Miete", new String[] { "" }),
  HANDY("Miete", new String[] { "" }),
  VERSICHERUNG("Miete", new String[] { "" }),
  GEZ("Miete", new String[] { "" }),
  ABONNEMENT("Miete", new String[] { "" }),
  LEBENSMITTEL("Miete", new String[] { "" }),
  HAUSHALTSMITTEL("Miete", new String[] { "" }),
  KLEIDUNG("Miete", new String[] { "" }),
  MOBILITAET("Miete", new String[] { "" }),
  GESCHENKE("Miete", new String[] { "" }),
  AUSGEHEN("Miete", new String[] { "" }),
  SONSTIGES("Miete", new String[] { "" }),
  LEER("Miete", new String[] { "" });

  String category;
  String[] identifier;

  private Category(String value, String[] identifier) {
    this.category = value;
    this.identifier = identifier;
  }

}
