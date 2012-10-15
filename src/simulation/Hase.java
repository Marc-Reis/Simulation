package simulation;

import java.util.List;
import java.util.Random;

/**
 * Ein einfaches Modell eines Hasen.
 * Ein Hase altert, bewegt sich, geb�rt Nachwuchs und stirbt.
 * 
 * @author David J. Barnes und Michael K�lling
 * @version 2006.03.30
 */
public class Hase extends Tier
{
    // Eigenschaften aller Hasen (statische Datenfelder).

    // Das Alter, in dem ein Hase geb�rf�hig wird.
    private static final int GEBAER_ALTER = 5;
    // Das H�chstalter eines Hasen.
    private static final int MAX_ALTER = 50;
    // Die Wahrscheinlichkeit, mit der ein Hase Nachwuchs geb�rt.
    private static final double GEBAER_WAHRSCHEINLICHKEIT = 0.15;
    // Die maximale Gr��e eines Wurfes (Anzahl der Jungen)
    private static final int MAX_WURFGROESSE = 5;
    // Ein Zufallsgenerator, der die Geburten beeinflusst.
    private static final Random rand = new Random();
    
    // Individuelle Eigenschaften eines Hasen (Instanzfelder).
    
    /**
     * Erzeuge einen neuen Hasen. Ein Hase kann das Alter 0 
     *(neu geboren) oder ein zuf�lliges Alter haben.
     * @param zufaelligesAlter soll der Hase ein zuf�lliges Alter haben?
     */
    public Hase(boolean zufaelligesAlter)
    {
        super();
        if(zufaelligesAlter) {
            setzeAlter(rand.nextInt(MAX_ALTER));
        }
    }
    
    /**
     * Das was ein Hase die meiste Zeit tut - er l�uft herum.
     * Manchmal geb�rt er Nachwuchs und irgendwann stirbt er
     * an Altersschw�che.
     * @param aktuellesFeld Das aktuell belegte Feld.
     * @param naechstesFeld Das zu belegende Feld.
     * @param neueTiere Liste, in die neue Hasen einzuf�gen sind.
     */
    public void agiere(Feld feld, Feld naechstesFeld, List<Tier> neueTiere)
    {
        alterErhoehen();
        if(istLebendig()) {
            int geburten = gebaereNachwuchs();
            for(int b = 0; b < geburten; b++) {
                Hase neuerHase = new Hase(false);
                neueTiere.add(neuerHase);
                neuerHase.setzePosition(naechstesFeld.zufaelligeNachbarposition(gibPosition()));
                naechstesFeld.platziere(neuerHase);
            }
            Position neuePosition = naechstesFeld.freieNachbarposition(gibPosition());
            // nur in das n�chste Feld setzen, wenn eine Position frei ist
            if(neuePosition != null) {
                setzePosition(neuePosition);
                naechstesFeld.platziere(this);
            }
            else {
                // weder Bleiben noch Gehen m�glich - �berpopulation - kein Platz 
                setzeGestorben();
            }
        }
    }
    
    /**
     * Erh�he das Alter dieses Hasen.
     * Dies kann zu seinem Tod f�hren.
     */
    private void alterErhoehen()
    {
        setzeAlter(gibAlter()+1);
        if(gibAlter() > MAX_ALTER) {
            setzeGestorben();
        }
    }
    
    /**
     * Geb�re Nachwuchs, wenn dieser Hase geb�rf�hig ist.
     * @return die Anzahl der Neugeborenen (kann Null sein).
     */
    private int gebaereNachwuchs()
    {
        int geburten = 0;
        if(kannGebaeren() && rand.nextDouble() <= GEBAER_WAHRSCHEINLICHKEIT) {
            geburten = rand.nextInt(MAX_WURFGROESSE) + 1;
        }
        return geburten;
    }

    public String toString()
    {
        return "Hase, Alter " + gibAlter();
    }
    /**
     * Ein Hase kann geb�ren, wenn er das geb�rf�hige Alter
     * erreicht hat.
     */
    private boolean kannGebaeren()
    {
        return gibAlter() >= GEBAER_ALTER;
    }
}
