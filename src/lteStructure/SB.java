package lteStructure;

public class SB {
    private int id;
    private int mcs; 
    private Packet p;
    private double throughput;
        
    public SB() {
        this.setId(id);
        this.setMcs(mcs);
        this.setPacket(p);
        this.setThroughput(throughput);
    }

    public SB(SB item) {
        this.id=item.id;
        this.mcs=item.mcs;
        this.p=item.p;
        this.throughput=item.throughput;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public int getMcs() {
		return mcs;
	}

	public void setMcs(int mcs) {
		this.mcs = mcs;
	}

	public double getThroughput() {
		return throughput;
	}

	public void setThroughput(double throughput) {
		this.throughput = throughput;
	}
	
    public void printSBlocks() {
        System.out.println("ID = " + id + " | area = " );
    }

	public Packet getPacket() {
		return p;
	}

	public void setPacket(Packet p) {
		this.p = p;
	}	
}
