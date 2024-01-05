package dev.eckler.cashflow.model.transaktion.overview;

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
      case ("HAUSHALTSMITTEL") -> setHaushaltsmittel(amount);
      case ("KLEIDUNG") -> setKleidung(amount);
      case ("MOBILITAET") -> setMobilitaet(amount);
      case ("GESCHENKE") -> setGeschenke(amount);
      case ("AUSGEHEN") -> setAusgehen(amount);
      case ("SONSTIGES") -> setSonstiges(amount);
      default -> System.out.println("category not available" + category);
    }
  }

  public String getYear() {
    return year;
  }

  public String getMonth() {
    return month;
  }

  public float getDennis() {
    return dennis;
  }

  public float getSveti() {
    return sveti;
  }

  public float getGutschrift() {
    return gutschrift;
  }

  public float getMiete() {
    return miete;
  }

  public float getStrom() {
    return strom;
  }

  public float getInternet() {
    return internet;
  }

  public float getHandy() {
    return handy;
  }

  public float getVersicherung() {
    return versicherung;
  }

  public float getGez() {
    return gez;
  }

  public float getAbonnement() {
    return abonnement;
  }

  public float getLebensmittel() {
    return lebensmittel;
  }

  public float getHaushaltsmittel() {
    return haushaltsmittel;
  }

  public float getKleidung() {
    return kleidung;
  }

  public float getMobilitaet() {
    return mobilitaet;
  }

  public float getGeschenke() {
    return geschenke;
  }

  public float getAusgehen() {
    return ausgehen;
  }

  public float getSonstiges() {
    return sonstiges;
  }

}
