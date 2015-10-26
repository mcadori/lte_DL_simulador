package lteStructure;

import java.util.ArrayList;

public class TTI {
    private int id;
    private ArrayList<SB> schedulingBlocks; // DEFINIR O TAMANHO DO TTI 
    private double throughput;
        
    public TTI() {
        this.setId(id);
        this.setSchedulingBlocks(schedulingBlocks);
        this.setThroughput(throughput);
    }

    public TTI(TTI item) {
        this.id=item.id;
        this.schedulingBlocks=item.schedulingBlocks;
        this.throughput=item.throughput;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public double getThroughput() {
		return throughput;
	}

	public void setThroughput(double throughput) {
		this.throughput = throughput;
	}
	
    public void printSB_TTI() {
        System.out.println("TTI ID = " + id + " |  = " );
    }

	public ArrayList<SB> getSchedulingBlocks() {
		return schedulingBlocks;
	}

	public void setSchedulingBlocks(ArrayList<SB> schedulingBlocks) {
		this.schedulingBlocks = schedulingBlocks;
	}

		
}
