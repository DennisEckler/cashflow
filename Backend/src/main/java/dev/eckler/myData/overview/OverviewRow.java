package dev.eckler.myData.overview;

public class OverviewRow {
  String year = "";
  String month = "";
  float dennis = 0;
  float sveti = 0;
  float gutschrift = 0;
  float miete = 0;
  float strom = 0;
  float internet = 0;
  float handy = 0;
  float versicherung = 0;
  float gez = 0;
  float abonnement = 0;
  float lebensmittel = 0;
  float haushaltsmittel = 0;
  float kleidung = 0;
  float mobilitaet = 0;
  float geschenke = 0;
  float ausgehen = 0;
  float sonstiges = 0;

  public void setSonstiges(float sonstiges) {
    this.sonstiges = sonstiges;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public void setMonth(String month) {
    this.month = month;
  }

  public void setDennis(float dennis) {
    this.dennis = dennis;
  }

  public void setSveti(float sveti) {
    this.sveti = sveti;
  }

  public void setGutschrift(float gutschrift) {
    this.gutschrift = gutschrift;
  }

  public void setMiete(float miete) {
    this.miete = miete;
  }

  public void setStrom(float strom) {
    this.strom = strom;
  }

  public void setInternet(float internet) {
    this.internet = internet;
  }

  public void setHandy(float handy) {
    this.handy = handy;
  }

  public void setVersicherung(float versicherung) {
    this.versicherung = versicherung;
  }

  public void setGez(float gez) {
    this.gez = gez;
  }

  public void setAbonnement(float abonnement) {
    this.abonnement = abonnement;
  }

  public void setLebensmittel(float lebensmittel) {
    this.lebensmittel = lebensmittel;
  }

  public void setHaushaltsmittel(float haushaltsmittel) {
    this.haushaltsmittel = haushaltsmittel;
  }

  public void setKleidung(float kleidung) {
    this.kleidung = kleidung;
  }

  public void setMobilitaet(float mobilitaet) {
    this.mobilitaet = mobilitaet;
  }

  public void setGeschenke(float geschenke) {
    this.geschenke = geschenke;
  }

  public void setAusgehen(float ausgehen) {
    this.ausgehen = ausgehen;
  }

  public void mapCategoryAmount(String category, Float amount) {
    switch (category.toUpperCase()) {
      case ("DENNIS") -> setDennis(amount);
      case ("SVETI") -> setSveti(amount);
      case ("GUTSCHRIFT") -> setGutschrift(amount);
      case ("MIETE") -> setMiete(amount);
      case ("STROM") -> setStrom(amount);
      case ("INTERNET") -> setInternet(amount);
      case ("HANDY") -> setHandy(amount);
      case ("VERSICHERUNG") -> setVersicherung(amount);
      case ("GEZ") -> setGez(amount);
      case ("ABONNEMENT") -> setAbonnement(amount);
      case ("LEBENSMITTEL") -> setLebensmittel(amount);
      case ("HAUSHALSMITTEL") -> setHaushaltsmittel(amount);
      case ("KLEIDUNG") -> setKleidung(amount);
      case ("MOBILITAET") -> setMobilitaet(amount);
      case ("GESCHENKE") -> setGeschenke(amount);
      case ("AUSGEHEN") -> setAusgehen(amount);
      case ("SONSTIGES") -> setSonstiges(amount);
    }
  }

  @Override
  public String toString() {
    return year + "  " + month + "  " + dennis + "  " + sveti + "  " + gutschrift + "  " + miete + "  " + strom + "  "
        + internet + "  " + handy + "  " + versicherung + "  " + gez + "  " + abonnement + "  " + lebensmittel + "  "
        + haushaltsmittel + "  " + kleidung + "  " + mobilitaet + "  " + geschenke + "  " + ausgehen + "  " + sonstiges;
  }

}
