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
                            // 서버명
                            Log.d("displayname", node.getTextContent());
                            temp.displayname = node.getTextContent();
                        } else if (node.getNodeName().equals("nic")) {
                            // 내부주소
                            Log.d("ipaddress", node.getTextContent());
                            temp.ipaddress = node.getChildNodes().item(11).getTextContent();
                        } else if (node.getNodeName().equals("id")) {
                            // 서버 ID
                            System.out.println(node.getTextContent());
                            temp.serverid = node.getTextContent();
                        } else if (node.getNodeName().equals("templatedisplaytext")) {
                            // 운영체제
                            Log.d("os", node.getTextContent());
                            temp.os = node.getTextContent();
                        } else if (node.getNodeName().equals("cpunumber")) {
                            // CPU
                            Log.d("cpunumber", node.getTextContent());
                            temp.cpunumber = node.getTextContent();
                        } else if (node.getNodeName().equals("memory")) {
                            // 메모리
                            Log.d("memory", node.getTextContent());
                            temp.memory = node.getTextContent();
                        } else if (node.getNodeName().equals("name")) {
                            // 호스트명
                            Log.d("name", node.getTextContent());
                            temp.name = node.getTextContent();
                        } else if (node.getNodeName().equals("created")) {
                            // 생성일시
                            Log.d("created", node.getTextContent());
                            temp.created = node.getTextContent();
                        } else if (node.getNodeName().equals("zonename")) {
                            // Disk
                            Log.d("zonename", node.getTextContent());
                            temp.zonename = node.getTextContent();
                        } else if (node.getNodeName().equals("state")) {
                            // 상태
                            Log.d("state", node.getTextContent());
                            temp.state = node.getTextContent();
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
                            temp.displayname = node.getTextContent();
                        } else if (node.getNodeName().equals("size")) {
                            temp.size = node.getTextContent();
                        } else if (node.getNodeName().equals("id")) {
                            temp.volumeid = node.getTextContent();
                        } else if (node.getNodeName().equals("type")) {
                            temp.type = node.getTextContent();
                        } else if (node.getNodeName().equals("vmstate")) {
                            temp.state = node.getTextContent();
                        } else if (node.getNodeName().equals("virtualmachineid")) {
                            temp.vmid = node.getTextContent();
                        } else if (node.getNodeName().equals("vmdisplayname")) {
                            temp.vmname = node.getTextContent();
                        } else if (node.getNodeName().equals("zonename")) {
                            temp.zonename = node.getTextContent();
                        } else if (node.getNodeName().equals("created")) {
                            temp.created = node.getTextContent();
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

    public Network[] parseNetworkList(Document doc, int numIndex, boolean isN) {
        Network networks[] = null;
        if(isN){
            try {
                NodeList descNodes = doc.getElementsByTagName("network");
                networks = new Network[numIndex];
                if (descNodes != null) {
                    for (int i = 0; i < numIndex; i++) {
                        Network temp = new Network();
                        for (Node node = descNodes.item(i).getFirstChild(); node != null; node = node.getNextSibling()) {
                            if (node.getNodeName().equals("name")) {
                                temp.n_displayname = node.getTextContent();
                            } else if (node.getNodeName().equals("id")) {
                                temp.n_networkid = node.getTextContent();
                            } else if (node.getNodeName().equals("cidr")) {
                                Log.d("cidr", node.getTextContent());
                                temp.n_cidr = node.getTextContent();
                            } else if (node.getNodeName().equals("netmask")) {
                                temp.n_subnet = node.getTextContent();
                            } else if (node.getNodeName().equals("gateway")) {
                                temp.n_gateway = node.getTextContent();
                            } else if (node.getNodeName().equals("zonename")) {
                                Log.d("zonename", node.getTextContent());
                                temp.n_zonename = node.getTextContent();
                            } else if (node.getNodeName().equals("type")) {
                                temp.n_type = node.getTextContent();
                            } else if (node.getNodeName().equals("state")) {
                                temp.n_state = node.getTextContent();
                            }
                        }
                        networks[i] = temp;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                NodeList descNodes = doc.getElementsByTagName("publicipaddress");
                networks = new Network[numIndex];
                if (descNodes != null) {
                    for (int i = 0; i < numIndex; i++) {
                        Network temp = new Network();
                        for (Node node = descNodes.item(i).getFirstChild(); node != null; node = node.getNextSibling()) {
                            if (node.getNodeName().equals("ipaddress")) {
                                Log.d("ipaddress33", node.getTextContent());
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
                            } else if (node.getNodeName().equals("state")) {
                                temp.state = node.getTextContent();
                            }
                        }
                        networks[i] = temp;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return networks;
    }

    public Alarm[] parseAlarmList(Document doc, int numIndex) {
        Alarm alarms[] = null;
        try {
            NodeList descNodes = doc.getElementsByTagName("alarm");
            alarms = new Alarm[numIndex];
            if (descNodes != null) {
                for (int i = 0; i < numIndex; i++) {
                    Alarm temp = new Alarm();
                    for (Node node = descNodes.item(i).getFirstChild(); node != null; node = node.getNextSibling()) {
                        if (node.getNodeName().equals("alarmname")) {
                            temp.alarmname = node.getTextContent();
                        } else if (node.getNodeName().equals("statevalue")) {
                            temp.statevalue = node.getTextContent();
                        } else if (node.getNodeName().equals("metricname")) {
                            temp.metricname = node.getTextContent();
                        } else if (node.getNodeName().equals("comparisonoperator")) {
                            temp.compOperator = node.getTextContent();
                        } else if (node.getNodeName().equals("statistic")) {
                            temp.statistics = node.getTextContent();
                        } else if (node.getNodeName().equals("period")) {
                            temp.period = node.getTextContent();
                        } else if (node.getNodeName().equals("evaluationperiods")) {
                            temp.evalPeriod = node.getTextContent();
                        } else if (node.getNodeName().equals("threshold")) {
                            temp.threshold = node.getTextContent();
                        } else if (node.getNodeName().equals("unit")) {
                            temp.unit = node.getTextContent();
                        } else if (node.getNodeName().equals("actionsenabled")) {
                            temp.alarmSenabled = node.getTextContent();
                        }
                    }
                    alarms[i] = temp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alarms;
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
            if (descNodes != null) {
                for (int i = 0; i < count; i++) {
                    metricStat temp = new metricStat();
                    for (Node node = descNodes.item(i).getFirstChild(); node != null; node = node.getNextSibling()) {
                        if (node.getNodeName().equals("timestamp")) {
                            Log.d("timestamp", node.getTextContent());
                            temp.timestamp = node.getTextContent();
                        } else if (node.getNodeName().equals("unit")) {
                            Log.d("unit", node.getTextContent());
                            temp.unit = node.getTextContent();
                        } else if (node.getNodeName().equals("maximum")) {
                            Log.d("maximum", node.getTextContent());
                            temp.Maximum = Double.parseDouble(node.getTextContent());
                        } else if (node.getNodeName().equals("minimum")) {
                            Log.d("minimum", node.getTextContent());
                            temp.Minimum = Double.parseDouble(node.getTextContent());
                        } else if (node.getNodeName().equals("average")) {
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

    public Metric[] parseMetric(Document doc) {
        Metric Metric_Array[] = null;
        try {
            NodeList forwardNode = doc.getElementsByTagName("listmetricsresponse");
       //     String label = forwardNode.item(0).getFirstChild().getTextContent();
          //  Node node_temp = forwardNode.item(0).getFirstChild();
            int count = Integer.parseInt(forwardNode.item(0).getFirstChild().getTextContent());

            NodeList descNodes = doc.getElementsByTagName("metric");
            Metric_Array= new Metric[count];
            if(descNodes != null) {
                for(int i=0; i<count;i++){
                    Metric temp = new Metric();
                    for(Node node = descNodes.item(i).getFirstChild(); node!=null; node=node.getNextSibling()){
                        if(node.getNodeName().equals("metricname")){
                            Log.d("metricname", node.getTextContent());
                            temp.metricname = node.getTextContent();
                        }else if(node.getNodeName().equals("namespace")) {
                            Log.d("namespace", node.getTextContent());
                            temp.namespace = node.getTextContent();
                        }else if(node.getNodeName().equals("unit")) {
                            Log.d("unit", node.getTextContent());
                            temp.unit = node.getTextContent();
                        }else if(node.getNodeName().equals("dimensions")) {
                            Log.d("dimensions", node.getTextContent());
                            temp.dimensions = node.getTextContent();
                        }
                    }
                    Metric_Array[i] = temp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Metric_Array;
    }


    public int getNumberOfResponse(String objects, Document doc) {
        NodeList descNodes = null;
        if(doc.getElementsByTagName("errorcode").getLength() > 0) {
            Log.d("API 에러: ", "권한 없음");
            return 0;
        }
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
            case "n_network":
                descNodes = doc.getElementsByTagName("network");
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
            case "listmetricsresponse":
                descNodes = doc.getElementsByTagName("listmetricsresponse");
                break;
            case "jobid":
                descNodes = doc.getElementsByTagName("jobid");
                break;
            default:
                descNodes = doc.getElementsByTagName("count");
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
