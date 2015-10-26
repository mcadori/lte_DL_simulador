package showResults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lteStructure.Packet;
import lteStructure.SB;
import lteStructure.TTI;
import scheduling.Constants;

import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import com.panayotis.gnuplot.utils.Debug;

public class Plot {

	/**
	 * Plota o bit rate de cada SB pelo n total de SB.
	 * 
	 * @param ttiTmp
	 */
	public static void plotSBxRate(ArrayList<TTI> ttiTmp) {

		ArrayList<TTI> dataTmp = ttiTmp;
		ArrayList<Double> aux = getSBThroughput(dataTmp);

		double meanSBsRate = calcMeanRate(aux);
		//double meanSBsRate = calcMeanRateSample(aux); // Retorna media de aux/10
		
		double[][] dataToPlot = new double[aux.size()][2];

		for (int row = 0; row < aux.size(); row++) {
			for (int col = 0; col < dataToPlot[row].length; col++) {
				dataToPlot[row][0] = row;
				dataToPlot[row][1] = aux.get(row);
			}
		}
		
		JavaPlot p = new JavaPlot();
		DataSetPlot s = new DataSetPlot(dataToPlot);
		s.setTitle("Mbps");
		p.setTitle("Análise de Rate x SBs");
		p.getAxis("x").setLabel("SB");
		p.getAxis("y").setLabel("Rate (Mbps)");
		p.getAxis("x").setBoundaries(0.0, aux.size());
		p.getAxis("y").setBoundaries(0.0, 1.1);

		PlotStyle myPlotStyle = new PlotStyle();
		// myPlotStyle.setStyle(Style.POINTS);
		myPlotStyle.setStyle(Style.LINESPOINTS);
		myPlotStyle.setPointSize(1);
		myPlotStyle.setPointType(6);
		s.setPlotStyle(myPlotStyle);

		p.addPlot(s);
		p.newGraph();
		
		/* media */
		double[][] r = { { 1, meanSBsRate } };
		p.getAxis("x").setLabel("");
		p.getAxis("y").setLabel("Rate médio");
		p.getAxis("x").setBoundaries(0,2);
		p.getAxis("y").setBoundaries(0, 1);
		p.addPlot(r);
		
		p.plot();
	}

	/**
	 * Recebe um array com rates de SBs e retorna uma media, i.e. <br>
	 * soma_rate_todos_SBs/numero_SB
	 * @param aux
	 * @return
	 */
	public static double calcMeanRate(ArrayList<Double> aux) {
		ArrayList<Double> totalSB = aux;
		double sumSB = 0;
		for (Double atualSB : totalSB)
			sumSB = atualSB + sumSB;
		return sumSB / totalSB.size();
	}

	/**
	 * Recebe os dados da simulacao obtem o throughput de cada SB da simulação como um todo <br>
	 * levando em conta todos os TTIs, então, retorna um array com os throughputs normalizados <br>
	 * i.e. rate/rateMax.
	 * 
	 * @param ttiTmp
	 * @return
	 */
	public static ArrayList<Double> getSBThroughput(ArrayList<TTI> ttiTmp) {
		ArrayList<TTI> itr = ttiTmp;
		ArrayList<SB> itr1 = null;
		ArrayList<Double> aux = new ArrayList<Double>();
		double maxThroughput=1.008;

		for (int i = 0; i < itr.size(); i++) {
			TTI element = itr.get(i);
			itr1 = element.getSchedulingBlocks();
			for (int j = 0; j < itr1.size(); j++) {
				SB element1 = itr1.get(j);
				aux.add(element1.getThroughput()/maxThroughput);
			}
		}
		return aux;
	}

	/**
	 * Plota o pkt no tempo (delay) que ele foi transmitido i.e.  <br>
	 * se tivermos um ponto (x=0,y=50) significa que qndo o pkt foi <br> 
	 * transmitido o tempo (delay) do pkt era 50 ms.
	 * 
	 * @param ttiTmp
	 */
	public static void plotSBxDelay(ArrayList<TTI> ttiTmp) {

		ArrayList<TTI> dataTmp = ttiTmp;
		ArrayList<Double> aux = getSBDelay(dataTmp);
		
		double[][] dataToPlot = new double[aux.size()][2];

		for (int row = 0; row < aux.size(); row++) {
			for (int col = 0; col < dataToPlot[row].length; col++) {
				dataToPlot[row][0] = aux.get(row);
				dataToPlot[row][1] = row;
			}
		}

		JavaPlot p = new JavaPlot();
		DataSetPlot s = new DataSetPlot(dataToPlot);

		p.setTitle("Resultados Parciais");
		p.getAxis("x").setLabel("Delay");
		p.getAxis("y").setLabel("SB ID");
		p.getAxis("x").setBoundaries(-2.0, 10.0);
		p.getAxis("y").setBoundaries(0.0, aux.size());

		PlotStyle myPlotStyle = new PlotStyle();
		myPlotStyle.setStyle(Style.POINTS);
		myPlotStyle.setLineType(NamedPlotColor.BLUE);
		myPlotStyle.setPointType(6);
		s.setPlotStyle(myPlotStyle);

		p.addPlot(s);
		p.newGraph();
		p.plot();
	}
	
	/**
	 *  Plota dados de fairness.
	 *  
	 * @param simulation
	 */
	public static void plotFairness(ArrayList<TTI> simulation) {
		
		Double[] aux1 = new Double[Constants.N_USERS+1];
		
		for(int p=0;p<=Constants.N_USERS;p++)
			aux1[p]=0.0;
		
		Double fairness = getFairnessIndex(simulation);
		
		double[][] dataToPlot = new double[11][Constants.N_USERS];
		
		int x = 0;
		for (Double temp : aux1) {
			dataToPlot[x][0] = x;
			dataToPlot[x][1] = temp;
			x++;
		}
		
		JavaPlot fairnessPlot = new JavaPlot();
		DataSetPlot s = new DataSetPlot(dataToPlot);

		double limit_y = Constants.N_TOTAL_SB*1.08;
		
		fairnessPlot.setTitle("Resultados Parciais");
		fairnessPlot.setTitle("Análise de justiça");
		fairnessPlot.getAxis("y").setLabel("Rate (Mbps)");
		fairnessPlot.getAxis("x").setLabel("Usuario");
		fairnessPlot.getAxis("y").setBoundaries(0, limit_y);
		fairnessPlot.getAxis("x").setBoundaries(-1, Constants.N_USERS+1);

		PlotStyle myPlotStyle = new PlotStyle();
		
		myPlotStyle.setStyle(Style.ERRORBARS);
		myPlotStyle.setLineType(NamedPlotColor.BLUE);
		myPlotStyle.setLineWidth(2);
		s.setPlotStyle(myPlotStyle);

		fairnessPlot.addPlot(s);		
		fairnessPlot.newGraph();
		
		fairnessPlot.getAxis("y").setLabel("Indice de fairness");
		fairnessPlot.getAxis("x").setLabel("");
		fairnessPlot.getAxis("y").setBoundaries(0, 1);
		fairnessPlot.getAxis("x").setBoundaries(0, 2);
		
		double[][] r = { { 1, fairness } };
		
		fairnessPlot.addPlot(r);
		fairnessPlot.newGraph();
		fairnessPlot.plot();
	}

	/**
	 * Recebe uma simulação e retorna um double que indica o fairness calculado <br>
	 * através da equação do jain. 
	 * 
	 * fairness=(Math.pow(Math.abs(sum_rate),2) / (Constants.N_USERS*sum_quadrada));
	 * 
	 * @param simulation
	 * @return
	 */
	public static Double getFairnessIndex(ArrayList<TTI> simulation) {
		ArrayList<TTI> itr = simulation;
		ArrayList<SB> itr1 = null;
		Double[] aux = new Double[Constants.N_USERS+1];
		
		for(int p=0;p<=Constants.N_USERS;p++)
			aux[p]=0.0;
		
		for (int i = 0; i < itr.size(); i++) {
			TTI element = itr.get(i);
			itr1 = element.getSchedulingBlocks();

			for (int j = 0; j < itr1.size(); j++) {
				SB element1 = itr1.get(j);
				
				for (int k=1;k<=Constants.N_USERS;k++){
					if (element1.getPacket().getUser() == k){
						aux[k]= aux[k] + element1.getThroughput();
					}  
				} 
			}
		}
		
		Double sum_rate = 0., sum_quadrada = 0., fairness=0.;

		for(int p=1;p<=Constants.N_USERS;p++){
			//System.out.println("Rate do user "+p+" = "+aux[p]);
			sum_rate=(aux[p]/Constants.N_USERS)+sum_rate;
			sum_quadrada=Math.pow((aux[p]/Constants.N_USERS),2)+sum_quadrada;
		}
				
		fairness=(Math.pow(Math.abs(sum_rate),2) / (Constants.N_USERS*sum_quadrada));
		return fairness;
	}

	/**
	 * Recebe os dados da simulacao Obtem o delay de cada SB Retorna array com
	 * os delays
	 * 
	 * @param ttiTmp
	 * @return
	 */
	public static ArrayList<Double> getSBDelay(ArrayList<TTI> ttiTmp) {
		ArrayList<TTI> itr = ttiTmp;
		ArrayList<SB> itr1 = null;
		ArrayList<Double> aux = new ArrayList<Double>();

		for (int i = 0; i < itr.size(); i++) {
			TTI element = itr.get(i);
			itr1 = element.getSchedulingBlocks();
			for (int j = 0; j < itr1.size(); j++) {
				SB element1 = itr1.get(j);
				aux.add((double) element1.getPacket().getDelay());
			}
		}
		return aux;
	}

	/**
	 * Recebe uma simulação e retorna um array com o user que esta usando cada SB
	 * 
	 * @param ttiTmp
	 * @return
	 */
	public static List<Double> getSBUser(ArrayList<TTI> ttiTmp) {
		ArrayList<TTI> itr = ttiTmp;
		ArrayList<SB> itr1 = null;
		List<Double> aux = new ArrayList<Double>();

		for (int i = 0; i < itr.size(); i++) {
			TTI element = itr.get(i);
			itr1 = element.getSchedulingBlocks();
			for (int j = 0; j < itr1.size(); j++) {
				SB element1 = itr1.get(j);
				aux.add((double) element1.getPacket().getUser());
			}
		}
		return aux;
	}

	/**
	 * Plota SBs por Usuarios
	 * 
	 * @param ttiTmp
	 */
	public static void plotSBxUser(ArrayList<TTI> ttiTmp) {

		ArrayList<TTI> dataTmp = ttiTmp;
		List<Double> aux1 = getSBUser(dataTmp);
		Set<Double> uniqueSet = new HashSet<Double>(aux1);
		// double meanSBsRate = calcMeanRate(aux);
		double[][] dataToPlot = new double[Constants.N_USERS][2];

		int x = 0;
		for (Double temp : uniqueSet) {
			dataToPlot[x][0] = temp;
			dataToPlot[x][1] = Collections.frequency(aux1, temp);
			x++;
		}

		JavaPlot p = new JavaPlot();
		DataSetPlot s = new DataSetPlot(dataToPlot);
		s.setTitle("SBs");
		
		p.setTitle("Análise de SBs x Usuários");
		p.getAxis("y").setLabel("N de SBs");
		p.getAxis("x").setLabel("Usuário");
		p.getAxis("y").setBoundaries(0, aux1.size());
		p.getAxis("x").setBoundaries(-1, Constants.N_USERS+1);

		PlotStyle myPlotStyle = new PlotStyle();
		myPlotStyle.setStyle(Style.IMPULSES);
		
		myPlotStyle.setLineType(2);
		myPlotStyle.setLineWidth(10);
		
		s.setPlotStyle(myPlotStyle);

		p.addPlot(s);
		p.newGraph();
		p.plot();
	}

	/**
	 * Recebe uma simulação e retorna um array com meanCqi de cada SB
	 * 
	 * @param ttiTmp
	 * @return
	 */
	public static List<Double> getSBMCS(ArrayList<TTI> ttiTmp) {
		ArrayList<TTI> itr = ttiTmp;
		ArrayList<SB> itr1 = null;
		List<Double> aux = new ArrayList<Double>();

		for (int i = 0; i < itr.size(); i++) {
			TTI element = itr.get(i);
			itr1 = element.getSchedulingBlocks();
			for (int j = 0; j < itr1.size(); j++) {
				SB element1 = itr1.get(j);
				aux.add((double) element1.getPacket().getMean_cqi());
			}
		}
		return aux;
	}

	public static void plotSBxMCS(ArrayList<TTI> ttiTmp) {

		ArrayList<TTI> dataTmp = ttiTmp;
		List<Double> aux = getSBMCS(dataTmp);
		Set<Double> uniqueSet = new HashSet<Double>(aux);
		double[][] dataToPlot = new double[15][2];

		int x = 0;
		for (Double temp : uniqueSet) {
			dataToPlot[x][0] = temp;
			dataToPlot[x][1] = Collections.frequency(aux, temp);
			x++;
		}

		JavaPlot p = new JavaPlot();
		DataSetPlot s = new DataSetPlot(dataToPlot);

		p.setTitle("Resultados Parciais");
		p.getAxis("y").setLabel("N de SBs");
		p.getAxis("x").setLabel("MCS");
		p.getAxis("y").setBoundaries(0.0, aux.size());
		p.getAxis("x").setBoundaries(0.0, 15);

		PlotStyle myPlotStyle = new PlotStyle();
		myPlotStyle.setStyle(Style.IMPULSES);
		myPlotStyle.setLineWidth(10);
		s.setPlotStyle(myPlotStyle);

		p.addPlot(s);
		p.newGraph();
		p.plot();
	}
	
	/**
	 * Plota os status de uma simulação, recebe a simulação por parâmetro <br>
	 * e plota a quantidade de usuários que : <br>
	 * 
	 * foram atendidos x não foram atendidos
	 * 
	 * @param allPkts
	 * @param ttiTmp
	 */
	public static void plotAllocationStatus(ArrayList<Packet> allPkts, ArrayList<TTI> ttiTmp) {

		ArrayList<TTI> ttiIterator = ttiTmp;
		ArrayList<SB> sbIterator = null;
		ArrayList<Packet> naoAlocados = new ArrayList<Packet>(allPkts);
		ArrayList<Packet> alocados = new ArrayList<Packet>();
		
		ArrayList<Packet> t1_Alocado = new ArrayList<Packet>();
		ArrayList<Packet> t2_Alocado = new ArrayList<Packet>();
		ArrayList<Packet> t3_Alocado = new ArrayList<Packet>();
		ArrayList<Packet> t4_Alocado = new ArrayList<Packet>();
		ArrayList<Packet> t1_NaoAlocado = new ArrayList<Packet>();
		ArrayList<Packet> t2_NaoAlocado = new ArrayList<Packet>();
		ArrayList<Packet> t3_NaoAlocado = new ArrayList<Packet>();
		ArrayList<Packet> t4_NaoAlocado = new ArrayList<Packet>();
		
		for (int i = 0; i < ttiIterator.size(); i++) {
			TTI ttiAtual = ttiIterator.get(i);
			sbIterator = ttiAtual.getSchedulingBlocks();

			for (int j = 0; j < sbIterator.size(); j++) {
				SB sbAtual = sbIterator.get(j);
				for (Packet u : allPkts) {
					if (u.getId() == sbAtual.getPacket().getId()) {
						alocados.add(u);
						naoAlocados.remove(u);
						break;
					}
				}
			}
		}
		
		for(Packet p : alocados){
			if(p.getTos()==1)
				t1_Alocado.add(p);
			if(p.getTos()==2)
				t2_Alocado.add(p);
			if(p.getTos()==3)
				t3_Alocado.add(p);
			if(p.getTos()==4)
				t4_Alocado.add(p);
		}
		
		for(Packet p : naoAlocados){
			if(p.getTos()==1)
				t1_NaoAlocado.add(p);
			if(p.getTos()==2)
				t2_NaoAlocado.add(p);
			if(p.getTos()==3)
				t3_NaoAlocado.add(p);
			if(p.getTos()==4)
				t4_NaoAlocado.add(p);
		}
		
		System.out.println("tipo 1 - Alocados "+t1_Alocado.size()+"--"+"Nao Alocados "+t1_NaoAlocado.size());
		System.out.println("tipo 2 - Alocados "+t2_Alocado.size()+"--"+"Nao Alocados "+t2_NaoAlocado.size());
		System.out.println("tipo 3 - Alocados "+t3_Alocado.size()+"--"+"Nao Alocados "+t3_NaoAlocado.size());
		System.out.println("tipo 4 - Alocados "+t4_Alocado.size()+"--"+"Nao Alocados "+t4_NaoAlocado.size());

		double[][] data_1 = new double[1][2];
		double[][] data_2 = new double[1][2];

		data_1[0][0] = 0;
		data_1[0][1] = alocados.size();

		data_2[0][0] = 0.009;
		data_2[0][1] = naoAlocados.size();

		JavaPlot p = new JavaPlot();

		PlotStyle myPlotStyle = new PlotStyle();

		myPlotStyle.setStyle(Style.IMPULSES);
		
		myPlotStyle.setLineWidth(7);

		DataSetPlot s = new DataSetPlot(data_1);
		DataSetPlot q = new DataSetPlot(data_2);
		s.setTitle("Alocados");
		q.setTitle("Nao Alocados");

		s.setPlotStyle(myPlotStyle);
		q.setPlotStyle(myPlotStyle);

		p.setTitle("Resultados Parciais");
		p.getAxis("y").setLabel("N de Pkts");

		p.getAxis("y").setBoundaries(0, Math.max(alocados.size(), naoAlocados.size()) + 10);
		p.getAxis("x").setBoundaries(-0.1, 0.1);

		p.addPlot(q);
		p.addPlot(s);
		p.newGraph();
		
		//segundo grafico
		
		double[][] d1 = new double[1][2];
		double[][] d2 = new double[1][2];

		d1[0][0] = 0;
		d2[0][1] = 3;

		d1[0][0] = 0.009;
		d2[0][1] = 3;
		
		DataSetPlot v1 = new DataSetPlot(d1);
		DataSetPlot v2 = new DataSetPlot(d2);
		v1.setTitle("T1 Alocados");
		v2.setTitle("T1 N Alocados");

//		v1.setPlotStyle(myPlotStyle);
//		v2.setPlotStyle(myPlotStyle);

		p.addPlot(v1);
		p.addPlot(v2);
		p.newGraph();
		
//		//terceiro grafico
//		
//		double[][] d3 = new double[1][2];
//		double[][] d4 = new double[1][2];
//
//		d3[0][0] = 0;
//		d4[0][1] = t2_Alocado.size();
//
//		d3[0][0] = 0.009;
//		d4[0][1] = t2_NaoAlocado.size();
//		
//		DataSetPlot v3 = new DataSetPlot(d3);
//		DataSetPlot v4 = new DataSetPlot(d4);
//		v3.setTitle("T2 Alocados");
//		v4.setTitle("T2 N Alocados");
//
//		v3.setPlotStyle(myPlotStyle);
//		v4.setPlotStyle(myPlotStyle);
//
//		p.addPlot(v3);
//		p.addPlot(v4);
//		p.newGraph();
//
//		//quarto grafico
//		
//		double[][] d5 = new double[1][2];
//		double[][] d6 = new double[1][2];
//
//		d5[0][0] = 0;
//		d6[0][1] = t3_Alocado.size();
//
//		d5[0][0] = 0.009;
//		d6[0][1] = t3_NaoAlocado.size();
//		
//		DataSetPlot v5 = new DataSetPlot(d5);
//		DataSetPlot v6 = new DataSetPlot(d6);
//		
//		v5.setTitle("T3 Alocados");
//		v6.setTitle("T3 N Alocados");
//
//		v5.setPlotStyle(myPlotStyle);
//		v6.setPlotStyle(myPlotStyle);
//
//		p.addPlot(v5);
//		p.addPlot(v6);
//		p.newGraph();
//		
//		//quarto grafico
//		
//		double[][] d7 = new double[1][2];
//		double[][] d8 = new double[1][2];
//
//		d7[0][0] = 0;
//		d8[0][1] = t4_Alocado.size();
//
//		d7[0][0] = 0.009;
//		d8[0][1] = t4_NaoAlocado.size();
//		
//		DataSetPlot v7 = new DataSetPlot(d7);
//		DataSetPlot v8 = new DataSetPlot(d8);
//		
//		v7.setTitle("T4 Alocados");
//		v8.setTitle("T4 N Alocados");
//
//		v7.setPlotStyle(myPlotStyle);
//		v8.setPlotStyle(myPlotStyle);
//
//		p.addPlot(v7);
//		p.addPlot(v8);
//		p.newGraph();

		p.plot();
		
	}
}