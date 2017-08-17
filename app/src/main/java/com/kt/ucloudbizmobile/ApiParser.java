package com.kt.ucloudbizmobile;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

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
    public int getNumberOfResponse(Document doc) {
        NodeList descNodes = doc.getElementsByTagName("virtualmachine");
        return descNodes.getLength();
    }

}
