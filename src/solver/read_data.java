package solver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Muleya
 */
public class read_data {
    public Map<location, List<routes>> getData() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File("xml.xml"));
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("location");
        Map<location, List<routes>> data = new LinkedHashMap<>();
        for (int itr = 0; itr < nodeList.getLength(); itr++) {
            Node node = nodeList.item(itr);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String name = eElement.getAttribute("name");
                int tonz = Integer.parseInt(eElement.getAttribute("tonnes"));
                NodeList lines = eElement.getElementsByTagName("route");
                List<routes> li = new LinkedList<>();

                for (int i = 0; i < lines.getLength(); i++) {
                    Node node1 = lines.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element line = (Element) node1;
                        int to = Integer.parseInt(line.getAttribute("to"));
                        int tonnes = Integer.parseInt(line.getAttribute("tonnes"));

                        int links = Integer.parseInt(line.getAttribute("links"));
                        int triaxle = Integer.parseInt(line.getAttribute("triaxle"));
                        int number = Integer.parseInt(line.getAttribute("number"));
                        int from = Integer.parseInt(line.getAttribute("from"));
                        li.add(new routes(number, triaxle, links, tonnes, to,from));
                       
                    }
                }
                data.put(new location(tonz, name), li);
            }
        }
       
        return data;

    }

}


