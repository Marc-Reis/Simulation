package simulation;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;

/**
 * Ein einfacher J�ger-Beute-Simulator, basierend auf einem
 * Feld mit F�chsen und Hasen.
 * 
 * @author David J. Barnes und Michael K�lling
 * @version 2006.03.30
 */
public class Simulator
{
    // Konstanten f�r Konfigurationsinformationen �ber die Simulation.
    // Die Standardbreite f�r ein Feld.
    private static final int STANDARD_BREITE = 50;
    // Die Standardtiefe f�r ein Feld.
    private static final int STANDARD_TIEFE = 50;
    // Die Wahrscheinlichkeit f�r die Geburt eines Fuchses an
    // einer beliebigen Position im Feld.
    private static final double FUCHSGEBURT_WAHRSCHEINLICH = 0.02;
    // Die Wahrscheinlichkeit f�r die Geburt eines Fuchses an
    // einer beliebigen Position im Feld.
    private static final double HASENGEBURT_WAHRSCHEINLICH = 0.08;    

    // Die Liste der Tiere im Feld
    private List<Tier> tiere;
    // Die Liste der gerade geborenen Tiere
    private List<Tier> neueTiere;
    // Der aktuelle Zustand des Feldes
    private Feld feld;
    // Zweites Feld, mit dem der n�chste Schritt erzeugt wird
    private Feld naechstesFeld;
    // Der aktuelle Schritt der Simulation
    private int schritt;
    // Eine grafische Ansicht der Simulation
    private Simulationsansicht ansicht;
    
    /**
     * Erzeuge ein Simulationsfeld mit einer Standardgr��e.
     */
    public Simulator()
    {
        this(STANDARD_TIEFE, STANDARD_BREITE);
    }
    
    /**
     * Erzeuge ein Simulationsfeld mit der gegebenen Gr��e.
     * @param tiefe die Tiefe des Feldes (muss gr��er als Null sein).
     * @param breite die Breite des Feldes (muss gr��er als Null sein).
     */
    public Simulator(int tiefe, int breite)
    {
        if(breite <= 0 || tiefe <= 0) {
            System.out.println("Abmessungen m�ssen gr��er als Null sein.");
            System.out.println("Benutze Standardwerte.");
            tiefe = STANDARD_TIEFE;
            breite = STANDARD_BREITE;
        }
        tiere = new ArrayList<Tier>();
        neueTiere = new ArrayList<Tier>();
        feld = new Feld(tiefe, breite);
        naechstesFeld = new Feld(tiefe, breite);

        // Eine Ansicht der Zust�nde aller Positionen im Feld erzeugen.
        ansicht = new Simulationsansicht(tiefe, breite);
        ansicht.setzeFarbe(Fuchs.class, Color.blue);
        ansicht.setzeFarbe(Hase.class, Color.orange);
        
        // Einen g�ltigen Startzustand einnehmen.
        zuruecksetzen();
    }
    
    /**
     * Starte die Simulation vom aktuellen Zustand aus f�r einen l�ngeren
     * Zeitraum, etwa 500 Schritte.
     */
    public void starteLangeSimulation()
    {
        simuliere(500);
    }
    
    /**
     * F�hre vom aktuellen Zustand aus die angegebene Anzahl an
     * Simulationsschritten durch.
     * Brich vorzeitig ab, wenn die Simulation nicht mehr aktiv ist.
     * @param schritte Anzahl der zu simulierenden Schritte.
     */
    public void simuliere(int schritte)
    {
        for(int schritt = 1; schritt <= schritte && ansicht.istAktiv(feld); schritt++) {
            simuliereEinenSchritt();
        }
    }
    
    /**
     * F�hre einen einzelnen Simulationsschritt aus:
     * Durchlaufe alle Feldpositionen und aktualisiere den 
     * Zustand jedes Fuchses und Hasen.
     */
    public void simuliereEinenSchritt()
    {
        schritt++;
        neueTiere.clear();
        
        // alle Tiere agieren lassen
        for(Iterator<Tier> iter = tiere.iterator(); iter.hasNext(); ) {
            Tier tier = iter.next();
            tier.agiere(feld, naechstesFeld, neueTiere);
            // Tote Tiere aus der Simulation entfernen.
            if(!tier.istLebendig()) {
                iter.remove();
            }
        }
        // Neu geborene Tiere in die Liste der Tiere einf�gen.
        tiere.addAll(neueTiere);
        
        // feld und n�chstesFeld am Ende des Schritts austauschen.
        Feld temp = feld;
        feld = naechstesFeld;
        naechstesFeld = temp;
        naechstesFeld.raeumen();

        // Das neue Feld in der Ansicht anzeigen.
        ansicht.zeigeStatus(schritt, feld);
    }
        
    /**
     * Setze die Simulation an den Anfang zur�ck.
     */
    public void zuruecksetzen()
    {
        schritt = 0;
        tiere.clear();
        neueTiere.clear();
        feld.raeumen();
        naechstesFeld.raeumen();
        bevoelkere(feld);
        
        // Zeige den Startzustand in der Ansicht.
        ansicht.zeigeStatus(schritt, feld);
    }
    
    /**
     * Bev�lkere das Feld mit F�chsen und Hasen.
     * @param feld Das zu bev�lkernde Feld.
     */
    private void bevoelkere(Feld feld)
    {
        Random rand = new Random();
        feld.raeumen();
        for(int zeile = 0; zeile < feld.gibTiefe(); zeile++) {
            for(int spalte = 0; spalte < feld.gibBreite(); spalte++) {
                if(rand.nextDouble() <= FUCHSGEBURT_WAHRSCHEINLICH) {
                    Fuchs fuchs = new Fuchs(true);
                    fuchs.setzePosition(zeile, spalte);
                    tiere.add(fuchs);
                    feld.platziere(fuchs);
                }
                else if(rand.nextDouble() <= HASENGEBURT_WAHRSCHEINLICH) {
                    Hase hase = new Hase(true);
                    hase.setzePosition(zeile, spalte);
                    tiere.add(hase);
                    feld.platziere(hase);
                }
                // ansonsten die Position leer lassen
            }
        }
        Collections.shuffle(tiere);
    }
}
