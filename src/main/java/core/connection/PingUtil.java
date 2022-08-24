/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.connection;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author asimkaymak
 */
public class PingUtil {

    public static ArrayList<Boolean> opconPingController() {
        ArrayList<Boolean> opconsPing = new ArrayList<Boolean>();
        try {
            File xmlfile = new File("XMLFile.xml");
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbbuild = dbfac.newDocumentBuilder();
            Document xmldoc = dbbuild.parse(xmlfile);
            xmldoc.getDocumentElement().normalize();
            NodeList nodeList = xmldoc.getElementsByTagName("module");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    InetAddress address = InetAddress.getByName(eElement.getElementsByTagName("ipAdress").item(0).getTextContent());

                    opconsPing.add(address.isReachable(250));

                }
            }
            return opconsPing;
        } catch (Exception f) {
            return opconsPing;
        }
    }
}
