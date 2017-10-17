package com.kt.ucloudbizmobile;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by aicel on 2017-08-10.
 */

public class ApiParser {
    public Server[] parseServerList(Document doc, int numIndex) {
        Server servers[] = null;
        try {
            NodeList descNodes = doc.getElementsByTagName("virtualmachine");
            servers = new Server[numIndex];
            if (descNodes != null) {
                for (int i = 0; i < numIndex; i++) {
                    Server temp = new Server();
                    for (Node node = descNodes.item(i).getFirstChild(); node != null; node = node.getNextSibling()) {
                        if (node.getNodeName().equals("displayname")) {
                            Log.d("displayname", node.getTextContent());
                            temp.displayname = node.getTextContent();
                        } else if (node.getNodeName().equals("state")) {
                            Log.d("state", node.getTextContent());
                            temp.state = node.getTextContent();
                        } else if (node.getNodeName().equals("zoneid")) {
                            System.out.println(node.getTextContent());
                            temp.zoneid = node.getTextContent();
                        } else if (node.getNodeName().equals("templatedisplaytext")) {
                            Log.d("os", node.getTextContent());
                            temp.os = node.getTextContent();
                        } else if (node.getNodeName().equals("zonename")) {
                            Log.d("zonename", node.getTextContent());
                            temp.zonename = node.getTextContent();
                        }
                    }
                    servers[i] = temp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return servers;
    }

    public Disk[] parseDiskList(Document doc, int numIndex) {
        Disk disks[] = null;
        try {
            NodeList descNodes = doc.getElementsByTagName("volume");
            disks = new Disk[numIndex];
            if (descNodes != null) {
                for (int i = 0; i < numIndex; i++) {
                    Disk temp = new Disk();
                    for (Node node = descNodes.item(i).getFirstChild(); node != null; node = node.getNextSibling()) {
                        if (node.getNodeName().equals("name")) {
                            Log.d("displayname", node.getTextContent());
                            temp.displayname = node.getTextContent();
                        } else if (node.getNodeName().equals("size")) {
                            Log.d("size", node.getTextContent());
                            temp.size = node.getTextContent();
                        } else if (node.getNodeName().equals("vmstate")) {
                            Log.d("state", node.getTextContent());
                            temp.state = node.getTextContent();
                        }else if (node.getNodeName().equals("id")) {
                            temp.volumeid = node.getTextContent();
                        } else if (node.getNodeName().equals("virtualmachineid")) {
                            temp.vmid = node.getTextContent();
                        }else if (node.getNodeName().equals("vmdisplayname")) {
                            Log.d("vmdisplayname", node.getTextContent());
                            temp.vmname = node.getTextContent();
                        } else if (node.getNodeName().equals("zonename")) {
                            Log.d("zonename", node.getTextContent());
                            temp.zonename = node.getTextContent();
                        }
                    }
                    disks[i] = temp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return disks;
    }

    public Network[] parseNetworkList(Document doc, int numIndex) {
        Network networks[] = null;
        try {
            NodeList descNodes = doc.getElementsByTagName("publicipaddress");
            networks = new Network[numIndex];
            if (descNodes != null) {
                for (int i = 0; i < numIndex; i++) {
                    Network temp = new Network();
                    for (Node node = descNodes.item(i).getFirstChild(); node != null; node = node.getNextSibling()) {
                        if (node.getNodeName().equals("ipaddress")) {
                            Log.d("ipaddress", node.getTextContent());
                            temp.ipaddress = node.getTextContent();
                        } else if (node.getNodeName().equals("id")) {
                            Log.d("id", node.getTextContent());
                            temp.addressid = node.getTextContent();
                        } else if (node.getNodeName().equals("zoneid")) {
                            Log.d("zoneid", node.getTextContent());
                            temp.zoneid = node.getTextContent();
                        }else if (node.getNodeName().equals("zonename")) {
                            temp.zonename = node.getTextContent();
                        } else if (node.getNodeName().equals("usageplantype")) {
                            temp.usageplan = node.getTextContent();
                        }
                    }
                    networks[i] = temp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return networks;
    }

    public metricStat[] parseMetricStatistics(Document doc) {
        metricStat metricStat[] = null;
        try {
            NodeList forwardNode = doc.getElementsByTagName("getmetricstatisticsresponse");
            String label = forwardNode.item(0).getFirstChild().getTextContent();
            Node node_temp = forwardNode.item(0).getFirstChild();
            int count = Integer.parseInt(node_temp.getNextSibling().getTextContent());

            NodeList descNodes = doc.getElementsByTagName("metricstatistics");
            metricStat = new metricStat[count];
            if(descNodes != null) {
                for(int i=0; i<count;i++){
                    metricStat temp = new metricStat();
                    for(Node node = descNodes.item(i).getFirstChild(); node!=null; node=node.getNextSibling()){
                        if(node.getNodeName().equals("timestamp")){
                            Log.d("timestamp", node.getTextContent());
                            temp.timestamp = node.getTextContent();
                        }else if(node.getNodeName().equals("unit")) {
                            Log.d("unit", node.getTextContent());
                            temp.unit = node.getTextContent();
                        }else if(node.getNodeName().equals("maximum")) {
                            Log.d("maximum", node.getTextContent());
                            temp.Maximum = Double.parseDouble(node.getTextContent());
                        }else if(node.getNodeName().equals("minimum")) {
                            Log.d("minimum", node.getTextContent());
                            temp.Minimum = Double.parseDouble(node.getTextContent());
                        }
                        else if(node.getNodeName().equals("average")) {
                            Log.d("average", node.getTextContent());
                            temp.Average = Double.parseDouble(node.getTextContent());
                        }
                    }
                    metricStat[i] = temp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metricStat;
    }


    public int getNumberOfResponse(String objects, Document doc) {
        NodeList descNodes = null;
        switch (objects) {
            case "server":
                descNodes = doc.getElementsByTagName("virtualmachine");
                break;
            case "disk":
                descNodes = doc.getElementsByTagName("volume");
                break;
            case "network":
                descNodes = doc.getElementsByTagName("publicipaddress");
                break;
            case "alarm":
                descNodes = doc.getElementsByTagName("alarm");
                break;
            case "metric":
                descNodes = doc.getElementsByTagName("metric");
                break;
            case "getmetricstatisticsresponse":
                descNodes = doc.getElementsByTagName("getmetricstatisticsresponse");
                break;
            default:
                descNodes = doc.getElementsByTagName("virtualmachine");
        }

        return descNodes.getLength();
    }

    public Document getDocument(String data) {
        Document tmp = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            tmp = builder.parse(new InputSource(new StringReader(data)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }

}
