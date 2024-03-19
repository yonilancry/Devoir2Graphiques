package sio.devoir2graphiques.Entities;

public class DatasGraph
{
    private int nb;
    private String tranche;

    public DatasGraph(int unNb, String uneTranche)
    {
        nb = unNb;
        tranche = uneTranche;
    }

    public int getNb() {
        return nb;
    }

    public String getTranche() {
        return tranche;
    }
}
