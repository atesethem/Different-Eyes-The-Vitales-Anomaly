import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

public class MolecularData {

    private final List<Molecule> molecules;

    public List<List<Molecule>> uniquemolecules = new ArrayList<>();
    public static List<List<Molecule>> humansmolecules = new ArrayList<>();
    public static List<List<Molecule>> vitalesmolecules = new ArrayList<>();


    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }


    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }

    // Method to identify molecular structures
    // Return the list of different molecular structures identified from the input data
    public List<MolecularStructure> identifyMolecularStructures() {
        ArrayList<MolecularStructure> structures = new ArrayList<>();
        List<Molecule> allMolecules = molecules;
        for (Molecule molecule : allMolecules) {
            boolean breakLoop = false;
            for (MolecularStructure structure : structures) {
                if (structure.hasMolecule(molecule.getId())) {
                    breakLoop = true; // bulundu kır
                    break;
                }
                for (String name : molecule.getBonds())
                {
                    if (structure.hasMolecule(name))
                    {
                        breakLoop = true; // bulundu kır
                        structure.addMolecule(molecule);
                        break;
                    }
                }

            }
            if (!breakLoop) { // hiç yapı yoksa daha
                MolecularStructure newstructure = new MolecularStructure();
                findConnectedMolecules(newstructure, molecule, allMolecules);
                Collections.sort(newstructure.getMolecules());

                structures.add(newstructure);
       }
    }
        return structures;
    }


    private void findConnectedMolecules(MolecularStructure structure, Molecule molecule, List<Molecule> allMolecules) {
        structure.addMolecule(molecule);
        for (String bondId : molecule.getBonds()) {
            Molecule newMolecule = null;
            for (Molecule themolecule : molecules) {
                if (themolecule.getId().equals(bondId)) {
                    newMolecule = themolecule;
                }
            }
            if (newMolecule != null && !structure.hasMolecule(bondId)) {
                findConnectedMolecules(structure, newMolecule, allMolecules);
            }
        }
    }

    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {
        System.out.println(molecularStructures.size() + " molecular structures have been discovered in " + species + ".");
        for (int i = 0; i < molecularStructures.size(); i++) {
            System.out.println("Molecules in Molecular Structure " + (i + 1) + ": " + molecularStructures.get(i).getMolecules());
        }
    }

    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targetStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();

        for (MolecularStructure targetStructure : targetStructures) {
            boolean found = false;
            for (MolecularStructure sourceStructure : sourceStructures) {
                if (sourceStructure.equals(targetStructure)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                anomalyList.add(targetStructure);
            }
        }

        return anomalyList;
    }

    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {
        System.out.println("Molecular structures unique to Vitales individuals:");
        for (MolecularStructure structure : molecularStructures) {
            System.out.println(structure.getMolecules());
        }
    }
}