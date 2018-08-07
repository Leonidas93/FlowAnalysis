package flowanalysis;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author leo
 */
public class Conversation implements Comparable<Conversation>{
    String addressA;
    String addressB;
    int portA;
    int portB;
    int status; 
    ArrayList <Packet> packets = new ArrayList<>();
    
    public Conversation(){
        addressA = "";
        addressB = "";
        portA = 0;
        portB = 0;
        //status = 0;
    }
    
    public String getAddressA() {
        return addressA;
    }

    public String getAddressB() {
        return addressB;
    }

    public int getPortA() {
        return portA;
    }

    public int getPortB() {
        return portB;
    }

    public ArrayList<Packet> getPackets() {
        return packets;
    }

    public void setAddressA(String addressA) {
        this.addressA = addressA;
    }

    public void setAddressB(String addressB) {
        this.addressB = addressB;
    }

    public void setPortA(int port) {
        this.portA = port;
    }

    public void setPortB(int portB) {
        this.portB = portB;   //this.packets.get(0).destPort;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
    @Override
    public int compareTo(Conversation c) {
        return Integer.compare(this.portA, c.portA);
    }
       
    public String toString(){
        return addressA+","+addressB+","+portA+","+portB;
    }
    
    //Bytes
    public int totalLength(){
        int total = 0;
        for(int i=0; i<this.packets.size(); i++){
            total = total + this.packets.get(i).length;
        }
        return total;
    }
    
    //Packets
    public int totalPackets(){
        return this.packets.size();
    }
    
    //payload
    public int totalPayload(){
        int total = 0;
        for(int i=0; i<this.packets.size(); i++){
            total = total + this.packets.get(i).payload;
        }
        return total;
    }
    
    public double FlowDuration(){
        double first =0;
        double last = 0;
        double duration = 0;
        
        Packet f = Collections.min(this.packets);
        first = f.time;
        Packet l = Collections.max(this.packets);
        last = l.time;
        
        duration = last-first;
        return duration;
    }
    
    //packets per second
    public double pps(){
        return (double)this.totalPackets()/this.FlowDuration();
    }
    
    //bytes per second
    public double bps(){
        return (double)this.totalLength()/this.FlowDuration();
    }
    
    //mean bytes/packet
    public double meanBytes(){
        double i=0;
        for(Packet p : this.packets){
            i += p.length;
        }
        return i/this.packets.size();
    }
    
    //mean payload/packet
    private double meanPayload(){
        double i=0;
        for(Packet p : this.packets){
            i += p.payload;
        }
        return i/this.packets.size();
    }
    
    public double median(){
        double median=0;
        ArrayList<Double> d = new ArrayList<>();
        for(int i=0; i<this.packets.size(); i++){
            d.add((double)this.packets.get(i).payload);
        }
        if((d.size()%2) == 0){
            return (d.get(d.size()/2) + d.get(d.size()/2 - 1))/2;
        }
        else
            return d.get(d.size()/2); 

    }
    
    private double variancePayload(){
        double mean = this.meanPayload();
        double temp = 0;
        ArrayList<Double> d = new ArrayList<>();
        
        for(int i=0; i<this.packets.size(); i++){
           d.add((double)this.packets.get(i).payload);
        }
        
        for(Double a : d)
            temp += (a-mean)*(a-mean);    
        return temp/(d.size()-1);
    }
    
    private double varianceBytes(){
        double mean = this.meanBytes();
        double temp = 0;
        ArrayList<Double> d = new ArrayList<>();
        
        for(int i=0; i<this.packets.size(); i++){
           d.add((double)this.packets.get(i).length);
        }
        
        for(Double a : d)
            temp += (a-mean)*(a-mean);
            
        return temp/(d.size()-1);
    }
    
    //standard deviation payload
    public double stdPayload(){
        return Math.sqrt(this.variancePayload());
    }
    
    //standard deviation payload
    public double stdBytes(){
        return Math.sqrt(this.varianceBytes());
    }
    
    //% of packets <128 bytes
    public double per1(){
        double temp1 = 0;
        double temp2 = 0;
        
        for(int i=0; i<this.packets.size(); i++){
            temp1 += packets.get(i).length;
            if(packets.get(i).length<128){
               temp2 += packets.get(i).length;
           }
        }
        
        return (temp2/temp1)*100;
    }
    
    //% of packets [128, 1024] bytes
    public double per2(){
        double temp1 = 0;
        double temp2 = 0;
        
        for(int i=0; i<this.packets.size(); i++){
            temp1 += packets.get(i).length;
            if((packets.get(i).length >= 128) && (packets.get(i).length < 1024)){
               temp2 += packets.get(i).length;
            }
        }
        
        return (temp2/temp1)*100;
    }
    
    //% of packets [1024, 1518] bytes
    public double per3(){
        double temp1 = 0;
        double temp2 = 0;
        
        for(int i=0; i<this.packets.size(); i++){
            temp1 += packets.get(i).length;
            if((packets.get(i).length >= 1024) && (packets.get(i).length <= 1518)){
               temp2 += packets.get(i).length;
            }
        }
        
        return (temp2/temp1)*100;
    }
    
    //% of packets >1518 bytes
    public double per4(){
        double temp1 = 0;
        double temp2 = 0;
        
        for(int i=0; i<this.packets.size(); i++){
            temp1 += packets.get(i).length;
            if(packets.get(i).length > 1518){
               temp2 += packets.get(i).length;
           }
        }
        
        return (temp2/temp1)*100;
    }
    
    //% of SYN packets
    public double perSYN(){
        double sum = 0;
        for(int i=0; i<this.packets.size(); i++){
            if(this.packets.get(i).flag.equals("0x002")){
                sum++;
            }
        }
        return (double)(sum/this.totalPackets())*100;
    }
    
    //% of SYN/ACK packets
    public double perSYNACK(){
        double sum = 0;
        for(int i=0; i<this.packets.size(); i++){
            if(this.packets.get(i).flag.equals("0x012")){
                sum++;
            }
        }
        return (double)(sum/this.packets.size())*100;
    }
    
    //% of ACK packets
    public double perACK(){
        double sum = 0;
        for(int i=0; i<this.packets.size(); i++){
            if(this.packets.get(i).flag.equals("0x010")){
                sum++;
            }
        }
        return (double)(sum/this.packets.size())*100;
    }
    
    //% of PUSH/ACK packets
    public double perPUSHACK(){
        double sum = 0;
        for(int i=0; i<this.packets.size(); i++){
            if(this.packets.get(i).flag.equals("0x018")){
                sum++;
            }
        }
        return (double)(sum/this.packets.size())*100;
    }
   
    public int firstPacketLength(){
        Packet f = Collections.min(this.packets);
        return f.length;
    }
    
    public int totalGETRequest(){
        int sum = 0;
        for(int i=0; i<this.packets.size(); i++){
            if(this.packets.get(i).HTTPRequest.equals("GET")){
                sum++;
            }
        }
        return sum;
    }
    
    //bits/second
    public double bitsps(){
        return this.bps()*8;
    }
    
    public int forwardPackets(){
        int sum = 0;
        for(int i=0; i<this.packets.size(); i++){
            if((this.packets.get(i).sourcePort == this.portA)){ 
                sum++;
            }
        }
        return sum;
    }
    
    public int backwardPackets(){
        int sum = 0;
        for(int i=0; i<this.packets.size(); i++){
            if((this.packets.get(i).sourcePort == this.portB)){ 
                sum++;
            }
        }
        return sum;
    }
    
    public int forwardBytes(){
        int sum = 0;
        for(int i=0; i<this.packets.size(); i++){
            if((this.packets.get(i).sourcePort == this.portA)){
                sum += this.packets.get(i).length;
            }
        }
        return sum;
    }
    
    public int backwardBytes(){
        int sum = 0;
        for(int i=0; i<this.packets.size(); i++){
            if((this.packets.get(i).sourcePort == this.portB)){
                sum += this.packets.get(i).length;
            }
        }
        return sum;
    }

}
