import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MissionGenesis {

    private MolecularData molecularDataHuman;
    private MolecularData molecularDataVitales;

    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    public void readXML(String filename) {
        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList humanMolecules = doc.getElementsByTagName("HumanMolecularData").item(0).getChildNodes();
            List<Molecule> humanMoleculeList = parseMolecules(humanMolecules,true);

            NodeList vitalesMolecules = doc.getElementsByTagName("VitalesMolecularData").item(0).getChildNodes();
            List<Molecule> vitalesMoleculeList = parseMolecules(vitalesMolecules,false);

            molecularDataHuman = new MolecularData(humanMoleculeList);
            molecularDataVitales = new MolecularData(vitalesMoleculeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Molecule> parseMolecules(NodeList nodeList,boolean human) {
        List<Molecule> molecules = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element moleculeElement = (Element) nodeList.item(i);
                String id = moleculeElement.getElementsByTagName("ID").item(0).getTextContent();
                int bondStrength = Integer.parseInt(moleculeElement.getElementsByTagName("BondStrength").item(0).getTextContent());
                NodeList bonds = moleculeElement.getElementsByTagName("Bonds").item(0).getChildNodes();
                List<String> bondedMolecules = new ArrayList<>();
                for (int j = 0; j < bonds.getLength(); j++) {
                    if (bonds.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        bondedMolecules.add(bonds.item(j).getTextContent());
                    }
                }
                Molecule molecule = new Molecule(id, bondStrength, bondedMolecules);
                if(human){
                    molecule.setType(true);
                }
                else{
                    molecule.setType(false);
                }
                molecules.add(molecule);
            }
        }
        return molecules;
    }
}