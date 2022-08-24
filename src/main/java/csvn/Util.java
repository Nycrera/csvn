package csvn;

import core.kafka.communication.types.Record;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
// Utility class

public class Util {

    /**
     * Creates and SDP file for use in the rtp stream receiving. SDP file is
     * created on the tmp directory and will be deleted on exit.
     *
     * @param ip String containing the IP address of the receiving client.
     * @param port String containing the Port number of the receiving client.
     * @throws IOException
     */
    public static File CreateSDPFile(final String ip, final String port) throws IOException {
        File TempSdpFile = File.createTempFile("streammod", ".sdp");
        TempSdpFile.deleteOnExit(); // Deletes the temporary file on standard program exit.
        FileWriter writer = new FileWriter(TempSdpFile);
        writer.write("v=0\n" + "o=- 0 0 IN IP4 " + ip + "\n" + "s=No Name\n" + "c=IN IP4 " + ip + "\n" + "t=0 0\n"
                + "a=tool:libavformat 55.2.100\n" + "m=video " + port + " RTP/AVP 96\n" + "a=rtpmap:96 H264/90000\n"
                + "a=fmtp:96 packetization-mode=1");
        // writer.write("c=IN IP4 " + ip + "\n" + "m=video " + port + " RTP/AVP 96 \n" +
        // "a=rtpmap:96 H264/90000");
        writer.close();
        return TempSdpFile;
    }

    // Validates IP and Port data.
    public static boolean ValidateData(final String ip, final String port) {
        final Pattern IPPATTERN = Pattern
                .compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        return IPPATTERN.matcher(ip).matches() && port.matches("-?(0|[1-9]\\d*)");
    }

    /**
     * Gets priority level from the metadata of a media file. If the priority is
     * not set returns null.
     *
     * @param filename Full or relative path and filename to the .mp4 file.
     * @throws Exception
     */
    public static String GetPriortityLevel(String filename) throws Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(filename);
        grabber.start();

        Map<String, String> metadataMap = grabber.getMetadata();
        String comment = metadataMap.get("comment");
        grabber.stop();
        grabber.close();

        if (comment != null) {
            String[] keyVals = comment.trim().split(",");
            for (String keyVal : keyVals) {
                String[] parts = keyVal.trim().split("=", 1);
                if (parts[0] == "priority") {
                    return parts[1];
                }
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * Gets and prints all of the metadata on a .mp4 file, for testing purposes.
     *
     * @param filename Full or relative path and filename to the .mp4 file.
     * @throws Exception
     */
    public static void PrintMetadata(String filename) throws Exception {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(filename);
        grabber.start();

        Map<String, String> metadataMap = grabber.getMetadata();
        for (Entry<String, String> entry : metadataMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }
        grabber.stop();
        grabber.close();
    }

    public static boolean DetectIfServer() {
        /* Detect if OPCON or server */
        try {
            File xmlfile = new File("Serverconfig.xml");
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbbuild = dbfac.newDocumentBuilder();
            Document xmldoc = dbbuild.parse(xmlfile);
            xmldoc.getDocumentElement().normalize();
            Element serverEl = (Element) xmldoc.getElementsByTagName("server").item(0);
            String amiserver = serverEl.getElementsByTagName("amiserver").item(0).getTextContent();
            if (amiserver.toLowerCase().equals("true")) { // Forced server detection.
                return true;
            } else { // Check if one of my ip adresses is equal to serverip address.
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface iface = interfaces.nextElement();
                    // filters out inactive interfaces
                    if (!iface.isUp()) {
                        continue;
                    }

                    Enumeration<InetAddress> addresses = iface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        if (addr.getHostAddress().equals(serverEl.getElementsByTagName("ipaddress").item(0).getTextContent())) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // If all checks fell, I am not the server.
    }

    public static String DetectOpconUsingIP() throws Exception {
        try {
            // Get my ip addresses
            List<String> ipaddresses = new ArrayList<String>();
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out inactive interfaces
                if (!iface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ipaddresses.add(addr.getHostAddress());
                }
            }

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
                    String nodeip = eElement.getElementsByTagName("ipAdress").item(0).getTextContent();
                    String nodename = eElement.getElementsByTagName("id").item(0).getTextContent();
                    if (ipaddresses.contains(nodeip)) {
                        return nodename;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("Can not find the opcon name");
    }

    public static String GetIPofOpcon(String opconName) throws Exception {
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
                    String nodeip = eElement.getElementsByTagName("ipAdress").item(0).getTextContent();
                    String nodename = eElement.getElementsByTagName("id").item(0).getTextContent();
                    if (nodename.toLowerCase().equals(opconName.toLowerCase())) {
                        return nodeip;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("Can not find the ip of given opcon name");
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
    public static ArrayList<Boolean> liveStatusCreator(){
        ArrayList<Boolean> list = new ArrayList<Boolean>();
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
                    list.add(Boolean.FALSE);

                }
            }
            return list;
        } catch (Exception f) {
            return list;
        }
    }
    public static ArrayList<Record> recordStatusCreator(){
        ArrayList<Record> list = new ArrayList<Record>();
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
                    Record record = new Record();
                    
                    String nodename = eElement.getElementsByTagName("id").item(0).getTextContent();
                    
                    record.setName("");
                    record.setSource(nodename);
                    record.setStatus(true);
                    
                    list.add(record);
                }
            }
            return list;
        } catch (Exception f) {
            return list;
        }
    }

}
