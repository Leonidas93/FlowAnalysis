package flowanalysis;

/**
 *
 * @author leo
 */
public class Packet implements Comparable<Packet>{
    int number;
    double time;
    String source;
    String destination;
    String protocol;
    int length;
    String info;
    int payload;
    int sourcePort;
    int destPort;
    String flag;
    String HTTPRequest;
      
    public Packet(int number, double time, String source, String destination, String protocol, int length, String info,int payload, int sourcePort, int destPort, String flag, String HTTPRequest){
        this.number = number;
        this.time = time;
        this.source = source;
        this.destination = destination;
        this.protocol = protocol;
        this.length = length;
        this.info = info;
        this.payload = payload;
        this.sourcePort = sourcePort;
        this.destPort = destPort;
        this.flag = flag;
        this.HTTPRequest = HTTPRequest;
    }
    
    public String toString(){
        return number+","+time+","+source+","+destination+","+protocol+","+length+","+info+","+payload+","+sourcePort+","+destPort;
    }
    
    @Override
    public int compareTo(Packet p) {
        return Double.compare(this.time, p.time);
    }
    
}
