package com.kt.ucloudbizmobile;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by aicel on 2017-08-10.
 */

public class ApiParser {
    public Server[] parseServerList(Document doc, int numIndex) {
        Server servers[] = null;
        try {
            NodeList descNodes = doc.getElementsByTagName("virtualmachine");
            servers = new Server[numIndex];
            if(descNodes != null) {
                for(int i=0; i<numIndex;i++){
                    Server temp = new Server();
                    for(Node node = descNodes.item(i).getFirstChild(); node!=null; node=node.getNextSibling()){
                        if(node.getNodeName().equals("displayname")){
                            Log.d("displayname", node.getTextContent());
                            temp.displayname = node.getTextContent();
                        }else if(node.getNodeName().equals("state")) {
                            Log.d("state", node.getTextContent());
                            temp.state = node.getTextContent();
                        }else if(node.getNodeName().equals("zoneid")) {
                            System.out.println(node.getTextContent());
                            temp.zoneid = node.getTextContent();
                        }else if(node.getNodeName().equals("templatedisplaytext")) {
                            Log.d("os", node.getTextContent());
                            temp.os = node.getTextContent();
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
    public int getNumberOfResponse(String objects, Document doc) {
        NodeList descNodes = null;
        switch(objects) {
            case "server":
                descNodes = doc.getElementsByTagName("virtualmachine");
                break;
            case "disk":
                descNodes = doc.getElementsByTagName("volume");
                break;
            case "network":
                descNodes = doc.getElementsByTagName("volume");
                break;
            case "alarm":
                descNodes = doc.getElementsByTagName("alarm");
                break;
            case "metric":
                descNodes = doc.getElementsByTagName("metric");
                break;
            default:
                descNodes = doc.getElementsByTagName("virtualmachine");
        }

        return descNodes.getLength();
    }

    public Document getDocument(String data) {
        Document tmp = null;
        DocumentBuilderFactory factory  =  DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder    =  factory.newDocumentBuilder();
            tmp = builder.parse(new InputSource(new StringReader(data)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }

}
