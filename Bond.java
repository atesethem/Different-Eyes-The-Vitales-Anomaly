
// Class representing a bond between two molecules
public class Bond {
    

    private Molecule to;
    private Molecule from;
    private Double weight;


    public Bond(Molecule to, Molecule from, Double weight) {
        this.to = to;
        this.from = from;
        this.weight = weight;
    }


    public Molecule getTo() {
        return to;
    }


    public Molecule getFrom() {
        return from;
    }


    public Double getWeight() {
        return weight;
    }
}
