public class GameOfLifeModel {
    public Rutenett rutenett = new Rutenett(20, 5);

    public void lagRutenett() {
        rutenett = new Rutenett(20, 5);
        rutenett.fyllMedTilfeldigeCeller();
        rutenett.kobleAlleCeller();
    }

    public int hentAntallLevende() {
        return rutenett.antallLevende();
    }

    public char hentStatus(int rad, int kol) {
        return rutenett.rutene[rad][kol].hentStatusTegn();
    }

    public void oppdatering(int rad, int kol) {
        for (int raderTell = 0; raderTell < rad; raderTell++) {
            for (int kolonTell = 0; kolonTell < kol; kolonTell++) {
                rutenett.hentCelle(raderTell, kolonTell).tellLevendeNaboer();
            }
        }
        for (int raderTell = 0; raderTell < rad; raderTell++) {
            for (int kolonTell = 0; kolonTell < kol; kolonTell++) {
                rutenett.hentCelle(raderTell, kolonTell).oppdaterStatus();
            }
        }
    }

    class Celle {
        public boolean levende;
        public Celle[] naboer;
        public int antNaboer;
        public int antLevendeNaboer;

        public Celle() {
            levende = false;
            naboer = new Celle[8];
            antNaboer = 0;
            antLevendeNaboer = 0;
        }

        public void settDoed() {
            levende = false;
        }

        public void settLevende() {
            levende = true;
        }

        public boolean erLevende() {
            return levende;
        }

        // returnerer "O", dersom status er levende, ellers "."
        public char hentStatusTegn() {
            if (levende) {
                return 'O';
            } else {
                return '.';
            }
        }

        // legger til parameter 'nabo' i array 'naboer' ved bruk av indeks (antNaboer++)
        public void leggTilNabo(Celle nabo) {
            naboer[antNaboer++] = nabo;
        }

        // teller antall levende naboer. Det må være "0" hver gang når det starter
        public void tellLevendeNaboer() {
            antLevendeNaboer = 0;
            for (int teller = 0; teller < antNaboer; teller++) {
                if (naboer[teller].erLevende()) {
                    antLevendeNaboer++;
                }
            }
        }

        /*
         * Ifølge av spillets regler;
         * dersom status er doed, og hvis antall levende naboer er lik 3, blir status
         * levende.
         * Dersom status er levende, hvis antall levende naboer er færre enn 2 eller
         * større enn 3, blir status død
         */
        public void oppdaterStatus() {
            if (levende) {
                if (antLevendeNaboer < 2 || antLevendeNaboer > 3) {
                    settDoed();
                }
            } else {
                if (antLevendeNaboer == 3) {
                    settLevende();
                }
            }
        }
    }

    public class Rutenett {
        int antRader;
        int antKolonner;
        public Celle[][] rutene;

        public Rutenett(int antRader, int antKolonner) {
            this.antRader = antRader;
            this.antKolonner = antKolonner;
            rutene = new Celle[antRader][antKolonner];
        }

        // oppretter celle ved å bruke Celle-objekt
        // hver celle har 1/3 sjanse for å være levende
        public void lagCelle(int rader, int kolonner) {
            if (rutene[rader][kolonner] == null) {
                rutene[rader][kolonner] = new Celle();
                if (Math.random() <= 0.3333) {
                    rutene[rader][kolonner].settLevende();
                }
            }
        }

        // et tilfeldig antall celler får status levende i denne metoden
        public void fyllMedTilfeldigeCeller() {
            for (int raderTell = 0; raderTell < antRader; raderTell++) {
                for (int kolonTell = 0; kolonTell < antKolonner; kolonTell++) {
                    lagCelle(raderTell, kolonTell);
                }
            }
        }

        // sjekker om celle som har en bestemt posisjon, ligger i rutenettet
        // hvis det er ikke i rutenett, returnerer Null
        public Celle hentCelle(int rad, int kolonne) {
            if ((rad >= 0 && rad < antRader) && (kolonne >= 0 && kolonne < antKolonner)) {
                return rutene[rad][kolonne];
            } else {
                return null;
            }
        }

        public void tegnRutenett() {
            System.out.print("+");
            for (int raderlinjer = 0; raderlinjer < antKolonner; raderlinjer++) {
                System.out.print("---+");
            }
            System.out.println();
            for (int raderTell = 0; raderTell < antRader; raderTell++) {
                System.out.print("|");
                for (int kolonTell = 0; kolonTell < antKolonner; kolonTell++) {
                    System.out.print(" " + rutene[raderTell][kolonTell].hentStatusTegn() + " |");
                }
                System.out.println();
                System.out.print("+");
                for (int raderlinjer = 0; raderlinjer < antKolonner; raderlinjer++) {
                    System.out.print("---+");
                }
                System.out.println();
            }
        }

        // henter en celle som har gitt koordinater
        // legger til hver nabo av den gitte cellen fordi det trenges å vite statusen
        // til hver nabo
        public void settNaboer(int rad, int kolonne) {
            Celle celle = hentCelle(rad, kolonne);
            if (hentCelle(rad - 1, kolonne - 1) != null) {
                celle.leggTilNabo(hentCelle(rad - 1, kolonne - 1));
            }
            if (hentCelle(rad - 1, kolonne) != null) {
                celle.leggTilNabo(hentCelle(rad - 1, kolonne));
            }
            if (hentCelle(rad - 1, kolonne + 1) != null) {
                celle.leggTilNabo(hentCelle(rad - 1, kolonne + 1));
            }
            if (hentCelle(rad, kolonne - 1) != null) {
                celle.leggTilNabo(hentCelle(rad, kolonne - 1));
            }
            if (hentCelle(rad, kolonne + 1) != null) {
                celle.leggTilNabo(hentCelle(rad, kolonne + 1));
            }
            if (hentCelle(rad + 1, kolonne - 1) != null) {
                celle.leggTilNabo(hentCelle(rad + 1, kolonne - 1));
            }
            if (hentCelle(rad + 1, kolonne) != null) {
                celle.leggTilNabo(hentCelle(rad + 1, kolonne));
            }
            if (hentCelle(rad + 1, kolonne + 1) != null) {
                celle.leggTilNabo(hentCelle(rad + 1, kolonne + 1));
            }
        }

        public void kobleAlleCeller() {
            for (int raderTell = 0; raderTell < antRader; raderTell++) {
                for (int kolonTell = 0; kolonTell < antKolonner; kolonTell++) {
                    settNaboer(raderTell, kolonTell);
                }
            }
        }

        // returnerer antall levende celler ved å sjekke om celle er levende
        public int antallLevende() {
            int antallLevende = 0;
            for (int raderTell = 0; raderTell < antRader; raderTell++) {
                for (int kolonTell = 0; kolonTell < antKolonner; kolonTell++) {
                    Celle celle = hentCelle(raderTell, kolonTell);
                    if (celle != null && celle.erLevende()) {
                        antallLevende++;
                    }
                }
            }
            return antallLevende;

        }
    }

    /*
     * Klassen Verden tar inn rader og kolonner
     * Generasjonsnummer skal være 0 i begynnelsen
     * Programmet fyller rutenettet med tilfeldige celler og koble cellene sammen
     * ved hjelp av metoder
     */
    public class Verden {
        int genNr = 0;
        public Rutenett rutenett;

        public Verden(int antRader, int antKolonner) {
            rutenett = new Rutenett(antRader, antKolonner);
            rutenett.fyllMedTilfeldigeCeller();
            rutenett.kobleAlleCeller();
        }

        // tegner rutenett, og skriver ut generasjonsnummeret og antall levende celler
        public void tegn() {
            System.out.println("Generasjonsnummer er: " + genNr);
            rutenett.tegnRutenett();
            System.out.println("Det er " + rutenett.antallLevende() + " levende celler.");
        }

        // henter alle celler i rutenett, og teller levende naboer
        // oppdaterer status på hver celle når alle celler i rutenett kommer på nytt
        public void oppdatering() {
            for (int raderTell = 0; raderTell < rutenett.antRader; raderTell++) {
                for (int kolonTell = 0; kolonTell < rutenett.antKolonner; kolonTell++) {
                    rutenett.hentCelle(raderTell, kolonTell).tellLevendeNaboer();
                }
            }
            for (int raderTell = 0; raderTell < rutenett.antRader; raderTell++) {
                for (int kolonTell = 0; kolonTell < rutenett.antKolonner; kolonTell++) {
                    rutenett.hentCelle(raderTell, kolonTell).oppdaterStatus();
                }
            }
            genNr++;
        }
    }

}
