import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Manager {

    private static final String TAG_SIZE = "size";
    private static final String TAG_PRICE = "price";
    private static final String TAG_TITLE = "title";
    private static final String TAG_TYPE = "type";
    private static final String TAG_PROGRAM = "program";


    private double totalSize = 0;
    private double totalCost = 0;
    private final ArrayList<Soft> soft = new ArrayList<>();

    public void importFromFile() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
        } catch (Exception e){
            System.err.println(e.getMessage());
            System.exit(1);
            return;
        }
        Document document;
        try {
            document = builder.parse(new File("soft.xml"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return;
        }
        NodeList softElements = document.getDocumentElement().getElementsByTagName(TAG_PROGRAM);
        for (int i = 0; i < softElements.getLength(); ++i) {
            if (softElements.item(i).getNodeType() != Node.ELEMENT_NODE){
                continue;
            }
            Node node = softElements.item(i);
            addSoft(node);
        }
    }

    void addSoft(Node node){
        NamedNodeMap attributes = node.getAttributes();
        soft.add(new Soft(Soft.Type.valueOf(attributes.getNamedItem(TAG_TYPE).getNodeValue()),
                attributes.getNamedItem(TAG_TITLE).getNodeValue(),
                Double.parseDouble(attributes.getNamedItem(TAG_PRICE).getNodeValue()),
                Double.parseDouble(attributes.getNamedItem(TAG_SIZE).getNodeValue())));
    }



    public final double getTotalCost () {
        for (Soft el : soft) {
            totalCost += el.getPrice();
        }
        return totalCost;
    }

    public final double getTotalSize() {
        for (Soft el : soft) {
            totalSize += el.getSize();
        }
        return totalSize;
    }

    public final void printInformation() {
        int cnt = 0;
        for (Soft el : soft) {
            System.out.println(++cnt +" "+ el.toString());
        }
        System.out.println("Total cost: " + getTotalCost());
        System.out.println("Total size: " + getTotalSize());
    }

    public void exportToFile() {
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       DocumentBuilder builder;
       try{
           builder = factory.newDocumentBuilder();
       } catch (Exception e){
           System.err.println(e.getMessage());
           System.exit(1);
           return;
       }
       Document document;
       try{
           document = builder.newDocument();
       } catch (Exception e){
           System.err.println(e.getMessage());
           System.exit(1);
           return;
       }
       Element root = document.createElement("root");
       Element info = document.createElement("info");

       document.appendChild(root);
       root.appendChild(info);

       info.setAttribute(TAG_SIZE, Double.toString(getTotalSize()));
       info.setAttribute(TAG_PRICE, Double.toString(getTotalCost()));

        Transformer t;
        try {
            t = TransformerFactory.newInstance().newTransformer();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return;
        }

        try {
            t.transform(new DOMSource(document), new StreamResult(new FileOutputStream("output.xml")));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);

        }

    }

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.importFromFile();
        manager.exportToFile();
    }

}
