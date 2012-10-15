package simulation;

import java.util.List;

/**
 * Tier ist eine abstrakte Superklasse f�r Tiere. 
 * Sie verwaltet Eigenschaften, die alle Tiere gemein haben,
 * wie etwas das Alter oder eine Position.
 * 
 * @author David J. Barnes und Michael K�lling
 * @version 2006.03.30
 */
public abstract class Tier
{
    // Das Alter dieses Tieres.
    private int alter;
    // Ist dieses Tier noch lebendig?
    private boolean lebendig;
    // Die Position dieses Tieres.
    private Position position;

    /**
     * Erzeuge ein Tier mit Alter Null (ein Neugeborenes).
     */
    public Tier()
    {
        alter = 0;
        lebendig = true;
    }
    
    /**
     * Lasse dieses Tier agieren - es soll das tun, was
     * es tun muss oder m�chte.
     * @param aktuellesFeld Das aktuell belegte Feld.
     * @param naechstesFeld Das zu belegende Feld.
     * @param neueTiere Liste, in die neue Tiere einzuf�gen sind.
     */
    abstract public void agiere(Feld aktuellesFeld, 
                             Feld naechstesFeld, List<Tier> neueTiere);
    
    /**
     * Pr�fe, ob dieses Tier noch lebendig ist.
     * @return true wenn dieses Tier noch lebendig ist.
     */
    public boolean istLebendig()
    {
        return lebendig;
    }

    /**
     * Signalisiere diesem Tier, dass es gestorben ist.   :-(
     */
    public void setzeGestorben()
    {
        lebendig = false;
    }
    
    /**
     * Liefere das Alter dieses Tieres.
     * @return das Alter dieses Tieres.
     */
    public int gibAlter()
    {
        return alter;
    }

    /**
     * Setze das Alter dieses Tieres.
     * @param alter das Alter dieses Tieres.
     */
    public void setzeAlter(int alter)
    {
        this.alter = alter;
    }
    
    /**
     * Liefere die Position dieses Tieres.
     * @return die Position dieses Tieres.
     */
    public Position gibPosition()
    {
        return position;
    }

    /**
     * Setze die Position dieses Tieres.
     * @param zeile die vertikale Koordinate der Position.
     * @param spalte die horizontale Koordinate der Position.
     */
    public void setzePosition(int zeile, int spalte)
    {
        this.position = new Position(zeile, spalte);
    }

    /**
     * Setze die Position dieses Tieres.
     * @param position die Position dieses Tieres.
     */
    public void setzePosition(Position position)
    {
        this.position = position;
    }
}
