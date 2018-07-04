package flowanalysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author leo
 */
public class AttrExtraction {
    
    //set of attributes 1
    public void extractSet1(String fp, String nfl) throws FileNotFoundException, IOException{
        
        File frp = new File (fp);  // file with raw packets
        File nFile = new File(nfl);
        
        Scanner scanner = new Scanner(frp);
        scanner.useDelimiter(",");
        BufferedWriter out = new BufferedWriter(new FileWriter(nFile));        
        
        ArrayList <Packet> pa = new ArrayList<>();  //raw packets read from the frp file
        ArrayList <Conversation> conv = new ArrayList<>();
        
        for(int i=0; scanner.hasNext(); i++){    
            String line = scanner.nextLine();
            String [] s = line.split("(?!\\w),(?!(\\s|\\w|%|$|@|#|\\?|<|>|(\\{)|(\\})|(\\!)|(\"\"\\w)))"); 
            for(int j=0; j<s.length; j++){
                s[j] = s[j].replaceAll("\"","");
            }
            if (i==0){  //the headers of csv
                continue;
            }
            else{     //the values
                if(s[7].equals("")){ //s[7] -> duration
                   s[7] = "0";
                }
                if(s[8].equals("")) // source port will be "" for udp, ssdp, dhcp,etc.
                    s[8] = "0";
                if(s[9].equals(""))  //destination port will be "" for udp, ssdp, dhcp,etc.
                    s[9] = "0";
                
                Packet p = new Packet(Integer.parseInt(s[0]),Double.parseDouble(s[1]),
                        s[2],s[3],s[4],Integer.parseInt(s[5]),s[6],Integer.parseInt(s[7]),
                        Integer.parseInt(s[8]),Integer.parseInt(s[9]),s[10],s[11]);
                
                pa.add(p);
            }        
            
        }
        
        conv = flowExtr(pa);
        
        String header = "Packets,Bytes,Packets A-B,Bytes A-B,Packets B-A,"
                + "Bytes B-A,Flow Duration,Packets/sec,Bytes/packet,Status";
        
        out.write(header);
        out.write("\n");
        
        for(int j=0; j<conv.size(); j++){
            out.write(conv.get(j).totalPackets()+",");
            out.write(conv.get(j).totalLength()+",");
            out.write(conv.get(j).forwardPackets()+",");
            out.write(conv.get(j).forwardBytes()+",");
            out.write(conv.get(j).backwardPackets()+",");
            out.write(conv.get(j).backwardBytes()+",");
            out.write(String.valueOf(conv.get(j).FlowDuration())+",");
            
            if(conv.get(j).FlowDuration()==0){              // for "infinity" values of packets/sec that come up as a result of 0 flow duration
                out.write(String.valueOf(meanPPS(conv))+",");
            }
            else
                out.write(String.valueOf(conv.get(j).pps())+",");
            
            out.write(String.valueOf(conv.get(j).meanBytes())+",");
            out.write(String.valueOf(conv.get(j).getStatus()));
           
            out.write("\n");
        }
        
        out.close();
        scanner.close();
                
    }
    
    //set of attributes 2
    public void extractSet2(String fp, String nfl) throws FileNotFoundException, IOException{

        File frp = new File (fp);  // file with raw packets
        File nFile = new File(nfl);
        
        Scanner scanner = new Scanner(frp);
        scanner.useDelimiter(",");
        BufferedWriter out = new BufferedWriter(new FileWriter(nFile));        
        
        ArrayList <Packet> pa = new ArrayList<>();  //raw packets read from the frp file
        ArrayList <Conversation> conv = new ArrayList<>();
        
        for(int i=0; scanner.hasNext(); i++){    
            String line = scanner.nextLine();
            String [] s = line.split("(?!\\w),(?!(\\s|\\w|%|$|@|#|\\?|<|>|(\\{)|(\\})|(\\!)|(\"\"\\w)))"); 
            for(int j=0; j<s.length; j++){
                s[j] = s[j].replaceAll("\"","");
            }
            if (i==0){  //the headers of csv
                continue;
            }
            else{     //the values
                if(s[7].equals("")){ //s[7] -> duration
                   s[7] = "0";
                }
                if(s[8].equals("")) // source port will be "" for udp, ssdp, dhcp,etc.
                    s[8] = "0";
                if(s[9].equals(""))  //destination port will be "" for udp, ssdp, dhcp,etc.
                    s[9] = "0";
                
                Packet p = new Packet(Integer.parseInt(s[0]),Double.parseDouble(s[1]),
                        s[2],s[3],s[4],Integer.parseInt(s[5]),s[6],Integer.parseInt(s[7]),
                        Integer.parseInt(s[8]),Integer.parseInt(s[9]),s[10],s[11]);
                
                pa.add(p);
            }        
            
        }
        
        conv = flowExtr(pa);
        
        String header = "Packets,Bytes,Payload,Flow duration,Packets/second,Bytes/second,Mean bytes/packet,Median payload/packet,"
                + "Standard deviation payload/packets,% packets <128 B,% packets \\[128 1024\\] B,% packets >1024 B,Status";
        
        out.write(header);
        out.write("\n");
        
        for(int j=0; j<conv.size(); j++){
            out.write(conv.get(j).totalPackets()+",");
            out.write(conv.get(j).totalLength()+",");
            out.write(conv.get(j).totalPayload()+",");
            out.write(String.valueOf(conv.get(j).FlowDuration())+",");
            
            if(conv.get(j).FlowDuration()==0){              // for "infinity" values of packets/sec that come up as a result of 0 flow duration
                out.write(String.valueOf(meanPPS(conv))+",");
            }
            else
                out.write(String.valueOf(conv.get(j).pps())+",");
            
            if(conv.get(j).FlowDuration()==0){              // for "infinity" values of bytes/sec that come up as a result of 0 flow duration
                out.write(String.valueOf(meanBPS(conv))+",");
            }
            else
                out.write(String.valueOf(conv.get(j).bps())+",");
            
            out.write(String.valueOf(conv.get(j).meanBytes())+",");
            out.write(String.valueOf(conv.get(j).median())+",");
            
            if(conv.get(j).totalPackets()==1){
                out.write(String.valueOf(meanSTDPayload(conv))+",");
            }
            else
                out.write(String.valueOf(conv.get(j).stdPayload())+",");
            
            out.write(String.valueOf(conv.get(j).per1())+",");
            out.write(String.valueOf(conv.get(j).per2())+",");
            out.write(String.valueOf(conv.get(j).per3())+",");
            out.write(String.valueOf(conv.get(j).getStatus()));
            
            out.write("\n");
        }
        
        out.close();
        scanner.close();
        
    }
    
    //set of attributes 3
    public void extractSet3(String fp, String nfl) throws FileNotFoundException, IOException{
        File frp = new File (fp);  // file with raw packets
        File nFile = new File(nfl); // new file
        
        Scanner scanner = new Scanner(frp);
        scanner.useDelimiter(",");
        BufferedWriter out = new BufferedWriter(new FileWriter(nFile));
   
        ArrayList <Packet> pa = new ArrayList<>();  //raw packets read from the frp file
        ArrayList <Conversation> conv = new ArrayList<>();
        
        for(int i=0; scanner.hasNext(); i++){    
            String line = scanner.nextLine();
            String [] s = line.split("(?!\\w),(?!(\\s|\\w|%|$|@|#|\\?|<|>|(\\{)|(\\})|(\\!)|(\"\"\\w)))"); //    "(?!a-zA-Z)," 
            
            for(int j=0; j<s.length; j++){
                s[j] = s[j].replaceAll("\"","");
            }
            if (i==0){  //the headers of csv
                continue;
            }
            else{     //the values
                if(s[7].equals("")) //s[7] -> duration
                   s[7] = "0";
                if(s[8].equals("")) // source port will be "" for udp, ssdp, dhcp,etc.
                    s[8] = "0";
                if(s[9].equals(""))  //destination port will be "" for udp, ssdp, dhcp,etc.
                    s[9] = "0";    
                
                Packet p = new Packet(Integer.parseInt(s[0]),Double.parseDouble(s[1]),
                        s[2],s[3],s[4],Integer.parseInt(s[5]),s[6],Integer.parseInt(s[7]),Integer.parseInt(s[8]),
                        Integer.parseInt(s[9]),s[10],s[11]);
                
                pa.add(p);
            }        
            
        }
        
        conv = flowExtr(pa);
        
        String header = "Packets,Bytes,Flow duration,Packets/second,Bytes/second,% SYN packets,% SYN/ACK packets,% ACK packets,"
                + "% PSH/ACK packets,Mean bytes/packet,Standard deviation bytes/packets,Status";
        
        out.write(header);
        out.write("\n");
        
        for(int j=0; j<conv.size(); j++){
            out.write(conv.get(j).totalPackets()+",");
            out.write(conv.get(j).totalLength()+",");
            out.write(String.valueOf(conv.get(j).FlowDuration())+",");
            
            if(conv.get(j).FlowDuration()==0){              // for "infinity" values of packets/sec that come up as a result of 0 flow duration
                out.write(String.valueOf(meanPPS(conv))+",");
            }
            else
                out.write(String.valueOf(conv.get(j).pps())+",");
            
            if(conv.get(j).FlowDuration()==0){              // for "infinity" values of bytes/sec that come up as a result of 0 flow duration
                out.write(String.valueOf(meanBPS(conv))+",");
            }
            else
                out.write(String.valueOf(conv.get(j).bps())+",");
            
            out.write(String.valueOf(conv.get(j).perSYN())+",");
            out.write(String.valueOf(conv.get(j).perSYNACK())+",");
            out.write(String.valueOf(conv.get(j).perACK())+",");
            out.write(String.valueOf(conv.get(j).perPUSHACK())+",");
            out.write(String.valueOf(conv.get(j).meanBytes())+",");
            
            if(conv.get(j).totalPackets()==1){
                out.write(String.valueOf(meanSTDBytes(conv))+",");
            }
                    
            else
                out.write(String.valueOf(conv.get(j).stdBytes())+",");
            
            out.write(String.valueOf(conv.get(j).getStatus()));
            
            out.write("\n");
            
        }
        
        out.close();
        scanner.close();
    }
    
    //set of attributes 4
    public void extractSet4(String fp, String nfl) throws FileNotFoundException, IOException{
        File frp = new File (fp);  // file with raw packets
        File nFile = new File(nfl); // new file
        
        Scanner scanner = new Scanner(frp);
        scanner.useDelimiter(",");
        BufferedWriter out = new BufferedWriter(new FileWriter(nFile));
   
        ArrayList <Packet> pa = new ArrayList<>();  //raw packets read from the frp file
        ArrayList <Conversation> conv = new ArrayList<>();
        
        for(int i=0; scanner.hasNext(); i++){    
            String line = scanner.nextLine();
            String [] s = line.split("(?!\\w),(?!(\\s|\\w|%|$|@|#|\\?|<|>|(\\{)|(\\})|(\\!))|(\"\"\\w))"); //    "(?!a-zA-Z)," 
            
            for(int j=0; j<s.length; j++){
                s[j] = s[j].replaceAll("\"","");
            }
            if (i==0){  //the headers of csv
                continue;
            }
            else{     //the values
                if(s[7].equals("")) //s[7] -> duration
                   s[7] = "0";
                if(s[8].equals("")) // source port will be "" for udp, ssdp, dhcp,etc.
                    s[8] = "0";
                if(s[9].equals(""))  //destination port will be "" for udp, ssdp, dhcp,etc.
                    s[9] = "0";    
                
                Packet p = new Packet(Integer.parseInt(s[0]),Double.parseDouble(s[1]),
                        s[2],s[3],s[4],Integer.parseInt(s[5]),s[6],Integer.parseInt(s[7]),Integer.parseInt(s[8]),
                        Integer.parseInt(s[9]),s[10],s[11]);
                
                pa.add(p);
            }        
            
        }
        
        conv = flowExtr(pa);
        
        String header = "Packets,Bytes,Flow duration,Length of first packet,Bytes/packet,Packets/sec,Bits/sec,Total HTTP GET,Status"; //"Status" -> 0 for benign traffic, 1 for malicious traffic
        
        out.write(header);
        out.write("\n");
        
        for(int j=0; j<conv.size(); j++){
            out.write(conv.get(j).totalPackets()+",");
            out.write(conv.get(j).totalLength()+",");
            out.write(String.valueOf(conv.get(j).FlowDuration())+",");
            out.write(String.valueOf(conv.get(j).firstPacketLength())+",");
            out.write(String.valueOf(conv.get(j).meanBytes())+",");
            
            if(conv.get(j).FlowDuration()==0){              // for "infinity" values of packets/sec that come up as a result of 0 flow duration
                out.write(String.valueOf(meanPPS(conv))+",");
            }
            else
                out.write(String.valueOf(conv.get(j).pps())+",");
            
            if(conv.get(j).FlowDuration()==0){              // for "infinity" values of bytes/sec that come up as a result of 0 flow duration
                out.write(String.valueOf(meanBPS(conv))+",");
            }
            else
                out.write(String.valueOf(conv.get(j).bps())+",");
            
            out.write(String.valueOf(conv.get(j).totalGETRequest())+",");
            out.write(String.valueOf(conv.get(j).getStatus()));
            
            out.write("\n");
        }
        
        out.close();
        scanner.close();
    }
    
    //groups together packets that have the same source ip, dest ip, source port, dest port (flows)
    public ArrayList flowExtr(ArrayList <Packet> pa){      
        ArrayList <Conversation> ac = new ArrayList<Conversation>();  //an arraylist that contains conversations -- each conversation contains an arraylist of packets (flow)
        int temp = 0;        
        
        for(int i=0; i<pa.size(); i++){
            Conversation conv = new Conversation();
            
            if(pa.get(i).number == 0 || pa.get(i).protocol.equals("UDP") || pa.get(i).protocol.equals("SSDP") || pa.get(i).protocol.equals("DHCP") 
                    || pa.get(i).protocol.equals("ARP") || pa.get(i).protocol.equals("DNS") || pa.get(i).protocol.equals("GQUIC") 
                    || pa.get(i).protocol.equals("MDNS") || pa.get(i).protocol.equals("ICMPv6") || pa.get(i).protocol.equals("LLMNR") 
                    || pa.get(i).protocol.equals("NBNS") || pa.get(i).protocol.equals("TLSv1.2") || pa.get(i).protocol.equals("IGMPv3")
                    || pa.get(i).protocol.equals("TLSv1.3")){
                continue;                 
            }
            
            else{
                for(int j=0; j<pa.size(); j++){
                    if(pa.get(j).number==0 || pa.get(j).protocol.equals("UDP") || pa.get(j).protocol.equals("SSDP") || pa.get(j).protocol.equals("DHCP") 
                            || pa.get(j).protocol.equals("ARP") || pa.get(j).protocol.equals("DNS") || pa.get(j).protocol.equals("GQUIC") 
                            || pa.get(j).protocol.equals("MDNS") || pa.get(j).protocol.equals("ICMPv6") || pa.get(j).protocol.equals("LLMNR") 
                            || pa.get(j).protocol.equals("NBNS") || pa.get(j).protocol.equals("TLSv1.2")){
                        continue;                        
                    }
                    
                    else{
                        if((pa.get(j).source.equals(pa.get(i).source) || pa.get(j).source.equals(pa.get(i).destination))
                                && ((pa.get(j).destination.equals(pa.get(i).destination) || pa.get(j).destination.equals(pa.get(i).source))) 
                                && ((pa.get(j).sourcePort==pa.get(i).sourcePort) || (pa.get(j).sourcePort == pa.get(i).destPort)) 
                                && ((pa.get(j).destPort==pa.get(i).destPort) || (pa.get(j).destPort == pa.get(i).sourcePort))
                                ){
                            
                            conv.packets.add(pa.get(j));                            
                            pa.get(j).number = 0;  //for not including this packet in the next loop, avoids duplicate flows                           
                        }
                    }
                }
                
                conv.setAddressA(conv.packets.get(0).source);
                conv.setAddressB(conv.packets.get(0).destination);
                conv.setPortA(conv.packets.get(0).sourcePort);
                conv.setPortB(conv.packets.get(0).destPort);                
                
                if(conv.getPortA() == 3306 || conv.getPortB() == 3306){                    
                    conv.setStatus(1);
                }
                
                
                ac.add(conv);               
            }
        }
        
        Collections.sort(ac);        
        return ac;
    }
    
    public double meanPPS(ArrayList<Conversation> a){
        double mean = 0;
        double sum = 0;
        double temp = 0;
        for(Conversation c : a){
            if(c.FlowDuration()==0){
                temp++;
            }
            else
                sum += c.pps();
        }
        return sum/(a.size()-temp);
    }
    
    public double meanBPS(ArrayList<Conversation> a){
        double mean = 0;
        double sum = 0;
        double temp = 0;
        for(Conversation c : a){
            if(c.FlowDuration()==0){
                temp++;
            }
            else
                sum += c.bps();
        }
        return sum/(a.size()-temp);
    }
    
    public double meanSTDPayload(ArrayList<Conversation> a){
        double sum = 0;
        double temp = 0;
        for(Conversation c : a){
            if(c.totalPackets()==1){
                temp++;
            }
            else
                sum += c.stdPayload();
        }
        return sum/(a.size()-temp);
    }
    
    public double meanSTDBytes(ArrayList<Conversation> a){
        double sum = 0;
        double temp = 0;
        for(Conversation c : a){
            if(c.totalPackets()==1){
                temp++;
            }
            else
                sum += c.stdBytes();
        }
        return sum/(a.size()-temp);
    }
}