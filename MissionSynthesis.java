import java.util.*;

public class MissionSynthesis {

    // Private fields
    private final List<MolecularStructure> humanStructures;
    private final List<MolecularStructure> diffStructures;
    private final List<Molecule> lowestBondMoleculesHumans = new ArrayList<>();
    private final List<Molecule> lowestBondMoleculesVitales = new ArrayList<>();


    public MissionSynthesis(List<MolecularStructure> humanStructures, List<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }


    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();
        List<Molecule> lowestBondMoleculesHumans = new ArrayList<>();
        List<Molecule> lowestBondMoleculesVitales = new ArrayList<>();
        for (MolecularStructure structure : humanStructures) {
            lowestBondMoleculesHumans.add(structure.getMoleculeWithWeakestBondStrength());
        }
        for (MolecularStructure structure : diffStructures) {
            lowestBondMoleculesVitales.add(structure.getMoleculeWithWeakestBondStrength());
        }

        Molecule minhumanmolecule = Collections.min(lowestBondMoleculesHumans, Comparator.comparingDouble(Molecule::getBondStrength));
        Molecule minvitalemolecule = Collections.min(lowestBondMoleculesVitales, Comparator.comparingDouble(Molecule::getBondStrength));
        Molecule realmolecule = minvitalemolecule;
        if(minhumanmolecule.getBondStrength()<minvitalemolecule.getBondStrength()){
            realmolecule = minhumanmolecule;
        }
        int minstrength = Math.min(minhumanmolecule.getBondStrength(),minvitalemolecule.getBondStrength());
       for(Molecule themolecule : lowestBondMoleculesHumans) {
           if(!themolecule.getId().equals(realmolecule.getId())){
               double STRENGTH = (minstrength + themolecule.getBondStrength()) / 2.0;
               serum.add(new Bond(realmolecule, themolecule, STRENGTH));
           }
       }
        for(Molecule themolecule : lowestBondMoleculesVitales) {
            if(!themolecule.getId().equals(realmolecule.getId())){
                double STRENGTH = (minstrength + themolecule.getBondStrength()) / 2.0;
                serum.add(new Bond(realmolecule, themolecule, STRENGTH));
            }
        }


        return serum;
    }
    public void printSynthesis(List<Bond> serum) {
        System.out.println("Typical human molecules selected for synthesis: " + lowestBondMoleculesHumans);
        System.out.println("Vitales molecules selected for synthesis: " + lowestBondMoleculesVitales);
        System.out.println("Synthesizing the serum...");

        double totalBondStrength = 0.0;
        for (Bond bond : serum) {
            System.out.printf("Forming a bond between %s - %s with strength %.2f\n",
                    bond.getFrom(), bond.getTo(), bond.getWeight());
            totalBondStrength += bond.getWeight();
        }
        System.out.printf("The total serum bond strength is %.2f\n", totalBondStrength);
    }
}