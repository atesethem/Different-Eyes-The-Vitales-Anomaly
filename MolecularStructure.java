import java.util.*;
import java.util.stream.Collectors;

// Class representing a molecular structure
public class MolecularStructure {
    
    // Private field
    private final List<Molecule> molecules = new ArrayList<>();


    public boolean hasMolecule(String moleculeId) {
        return molecules.stream().map(Molecule::getId).collect(Collectors.toList()).contains(moleculeId);
    }


    public void addMolecule(Molecule molecule) {
        molecules.add(molecule);
    }


    public List<Molecule> getMolecules() {
        return molecules;
    }


    public Molecule getMoleculeWithWeakestBondStrength() {
        return molecules.stream().min(Comparator.comparing(Molecule::getBondStrength)).orElse(null);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MolecularStructure other = (MolecularStructure) obj;
        return this.molecules.equals(other.molecules);
    }


    @Override
    public String toString() {
        ArrayList<Molecule> sortedMolecules = new ArrayList<>(molecules);
        Collections.sort(sortedMolecules);
        return sortedMolecules.toString();
    }
}
