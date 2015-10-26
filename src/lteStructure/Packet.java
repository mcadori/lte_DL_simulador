package lteStructure;

public class Packet {
	private int id;
	private int simbolos;
	private int delay;
	private int tos;
	private int user;
	private int[] cqi;
	private int mean_cqi;
	private double delta;

	public Packet() {
		this.setId(id);
		this.setSimbolos(simbolos);
		this.setDelay(delay);
		this.setTos(tos);
		this.setUser(user);
		this.setCqi(cqi);
		this.setMean_cqi(mean_cqi);
	}

	public Packet(Packet item) {
		this.id = item.id;
		this.simbolos = item.simbolos;
		this.delay = item.delay;
		this.tos = item.tos;
		this.user = item.user;
		this.cqi = item.cqi;
		this.mean_cqi = item.mean_cqi;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public void printPacketList() {
		System.out.println("Packet ID = " + id + " | simbolos = " + simbolos
				+ " | delay = " + delay + " | user = " + user
				+ " | mean_cqi = " + mean_cqi + " | delta = " + delta);
	}

	public int getSimbolos() {
		return simbolos;
	}

	public void setSimbolos(int simbolos) {
		this.simbolos = simbolos;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int[] getCqi() {
		return cqi;
	}

	public void setCqi(int[] cqi) {
		this.cqi = cqi;
	}

	public int getMean_cqi() {
		return mean_cqi;
	}

	public void setMean_cqi(int mean_cqi) {
		this.mean_cqi = mean_cqi;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}
	
	public int getTos() {
		return tos;
	}

	public void setTos(int tos) {
		this.tos = tos;
	}

	public Packet getNewCopy() {
		Packet p = new Packet();
		p.setUser(user);
		p.setCqi(cqi);
		p.setDelay(delay);
		p.setTos(tos);
		p.setDelta(delta);
		p.setId(id);
		p.setMean_cqi(mean_cqi);
		p.setSimbolos(simbolos);
		return p;
	}
}