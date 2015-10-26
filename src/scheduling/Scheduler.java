package scheduling;

import generateResource.Traffic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import lteStructure.Packet;
import lteStructure.SB;
import lteStructure.TTI;
import showResults.Plot;
import showResults.Print;
import utils.ArrayUtil;
import utils.TextManage;

public class Scheduler {

	private static ArrayUtil util = new ArrayUtil();
	private static ArrayList<TTI> simulation = new ArrayList<TTI>();
	private static long t_inicial, t_final, t_total;
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		/************ Scheduler TDPS - FDPS ************/

		ArrayList<TTI> ttiTmp = new ArrayList<TTI>(simulation);
		ArrayList<Packet> pktsTmp = new ArrayList<Packet>();

		if (Constants.INPUT_MODE==0){
			pktsTmp = Traffic.trafficGenerate(); 								//Gera tráfego dinâmicamente
		} else if (Constants.INPUT_MODE==1){
			pktsTmp = Traffic.trafficGenerate(); 								//Gera tráfego dinâmicamente
			Traffic.trafficToFile(pktsTmp); 									//Armazena o tráfego em um arquivo
		} else if (Constants.INPUT_MODE==2){
			pktsTmp = Traffic.convertTxtToPkts(Constants.INPUT_FILE); 			// Lê o tráfego de um arquivo
		}

		t_inicial = System.currentTimeMillis();

		switch (Constants.EXECUTION_MODE) {
		case 0:
			ttiTmp = allocateResources(pktsTmp); 	//Executa apenas uma simulação
			break;
		case 1:
			 genMapDelta(pktsTmp); 					//Gera um mapa de pontos delta x rate
			break;
		case 2: 
			genTTIrate(pktsTmp);					//Gera rate dos ttis
			break;
		case 3: 
			genMapFairness(pktsTmp);				//Gera um mapa de pontos delta x fairness
			break;
		case 4: 
			genMapLosses(pktsTmp);					//Gera um mapa das perdas
			break;
		}
		
		t_final = System.currentTimeMillis();
		t_total = t_final - t_inicial;

		/************ PRINTS ************/
		
//		Print.printTraffic(pktsTmp);
//		Print.printFairness(ttiTmp);
//		Print.printTTI(ttiTmp);
//		Print.printAllocationStatus(pktsTmp,ttiTmp);
//		Print.printPktServicesDelay(ttiTmp);
		
//		Plot.plotFairness(ttiTmp);
//		Plot.plotSBxUser(ttiTmp);
//		Plot.plotSBxDelay(ttiTmp); 		
//		Plot.plotSBxRate(ttiTmp);
//		Plot.plotSBxMCS(ttiTmp);
//		Plot.plotAllocationStatus(pktsTmp,ttiTmp);
		
		System.out.println("Tempo TOTAL de execucao programa = " + t_total + "ms");
		
	}

	/**
	 * Efetua a simulação de escalonamento escrevendo os resultados em um arquivo. <br>
	 * TTI rate. i.e. a média agregada dos SBs de um TTI para todos TTIs
	 * 
	 * @param pktsTmp
	 */
	public static void genTTIrate(ArrayList<Packet> pktsTmp) {
		
		Double v,w;
		v=1.;
		w=1.;
		
		 TextManage.arquivo = new File(TextManage.path);
		 TextManage.nomeArquivo = "ttiRate"+
		 "_rb="+Constants.N_TOTAL_SB+
		 "_pkts="+Constants.N_TOTAL_PKTS+
		 "_v="+v+
		 "_w="+w+
		 ".dat";
		TextManage.criarTXT();

		ArrayList<Packet> pkts = new ArrayList<Packet>();
		pkts = util.copyArray(pktsTmp);

		ArrayList<TTI> tti = new ArrayList<TTI>();
		tti = allocateResources(pkts, v, w);
		
		for (TTI a : tti ){
			String point = (a.getId()+" "+a.getThroughput());
			TextManage.escreve(point);
		}
		
	}
	
	/**
	 * Gera o mapa do rate x delta
	 * @param pktsTmp
	 */
	public static void genMapDelta(ArrayList<Packet> pktsTmp) {

		double pontos = Constants.N_POINTS_MAP;
		
		double pi = 0.0, pf = 2.0, v = 0, w = 0;
		
		TextManage.arquivo = new File(TextManage.path);
		TextManage.nomeArquivo = "deltaMap"+
					  "_pontos="+pontos+
					  "_rb="+Constants.TTI_TOTAL*Constants.SB_EACH_TTI+
					  "_pkts="+Constants.N_TOTAL_PKTS+
					  "_sort_mode="+Constants.SORT_MODE+
					  ".dat";
		TextManage.criarTXT();	

		ArrayList<Packet> pkts = new ArrayList<Packet>();
		pkts = util.copyArray(pktsTmp);

		
		for (int i = 0; i <= pontos; i++) {
			v = pi + i / pontos * (pf - pi);

			for (int j = 0; j <= pontos; j++) {
				w = pi + j / pontos * (pf - pi);

				ArrayList<TTI> tti = new ArrayList<TTI>();				
				tti = allocateResources(pkts, v, w);
				ArrayList<Double> aux = Plot.getSBThroughput(tti);
				double meanSBsRate = Plot.calcMeanRate(aux);
				String point = (v + " " + w + " " + meanSBsRate);
				TextManage.escreve(point);
			}
			w = 0;
			TextManage.escreve("");
		}
	}
	
	/**
	 * Gera o mapa do delta x fairness
	 * @param pktsTmp
	 */
	public static void genMapFairness(ArrayList<Packet> pktsTmp) {

		double pontos = Constants.N_POINTS_MAP;		
		
		double pi = 0.0, pf = 2.0, v = 0, w = 0;
		
		TextManage.arquivo = new File(TextManage.path);
		TextManage.nomeArquivo = "deltaMap_Fairness"+
					  "_pontos="+pontos+
					  "_rb="+Constants.TTI_TOTAL*Constants.SB_EACH_TTI+
					  "_pkts="+Constants.N_TOTAL_PKTS+
					  "_sort_mode="+Constants.SORT_MODE+
					  ".dat";
		TextManage.criarTXT();	

		ArrayList<Packet> pkts = new ArrayList<Packet>();
		pkts = util.copyArray(pktsTmp);
		
		for (int i = 0; i <= pontos; i++) {
			v = pi + i / pontos * (pf - pi);

			for (int j = 0; j <= pontos; j++) {
				w = pi + j / pontos * (pf - pi);

				ArrayList<TTI> tti = new ArrayList<TTI>();				
				tti = allocateResources(pkts, v, w);
				double jainFairness = Plot.getFairnessIndex(tti);				
				String point = (v + " " + w + " " + jainFairness);

				TextManage.escreve(point);
			}
			w = 0;
			TextManage.escreve("");
		}
	}
    
	/**
	 * Gera o mapa de perdas nas alocações
	 * @param pktsTmp
	 */
	public static void genMapLosses(ArrayList<Packet> pktsTmp) {

		double pontos = Constants.N_POINTS_MAP;
		double pi = 0.0, pf = 2.0, v = 0, w = 0;
		double gama=0.,x=0.,y=0.;
		
		TextManage.arquivo = new File(TextManage.path);
		TextManage.nomeArquivo = "deltaMap_Losses"+
					  "_pontos="+pontos+
					  "_rb="+Constants.TTI_TOTAL*Constants.SB_EACH_TTI+
					  "_pkts="+Constants.N_TOTAL_PKTS+
					  "_sort_mode="+Constants.SORT_MODE+
					  ".dat";
		TextManage.criarTXT();	

		ArrayList<Packet> pkts = new ArrayList<Packet>();
		pkts = util.copyArray(pktsTmp);
		
		for (int i = 0; i <= pontos; i++) {
			v = pi + i / pontos * (pf - pi);

			for (int j = 0; j <= pontos; j++) {
				w = pi + j / pontos * (pf - pi);

				ArrayList<TTI> simulacao = new ArrayList<TTI>();
				ArrayList<Integer> vet = allocateResourcesWithLosses(pkts, v, w);
				
				x=vet.get(0).doubleValue()/(Constants.N_TOTAL_PKTS/4);
				y=vet.get(1).doubleValue()/(Constants.N_TOTAL_PKTS/4);
							
				gama=(x+y)/2;
						
				String point = (v + " " + w + " " + gama);
				
				TextManage.escreve(point);
				
			}
			w = 0;
			TextManage.escreve("");
		}
	}
	
	/**
	 * Aloca os Pacotes -> SchedulingBlocks -> TTIs
	 * 
	 * @param pktsTmp
	 * @param ttiTmp
	 * @return ArrayList<TTI>
	 * 
	 */
	public static ArrayList<TTI> allocateResources(ArrayList<Packet> tmp) {

		ArrayList<TTI> ttiTmp = new ArrayList<TTI>();
		ArrayList<Packet> pktsParaAlocar = new ArrayList<Packet>();
		pktsParaAlocar = util.copyArray(tmp);
		boolean delta=false;

		if (pktsParaAlocar != null) {
			
			switch (Constants.SORT_MODE) {
			case 1:
				calcDelta(pktsParaAlocar,1,0.1);
				sortByDelta(pktsParaAlocar);
				delta=true;
				break;
			case 2:
				sortByCQI(pktsParaAlocar);
				break;
			case 3:
				sortByDelay(pktsParaAlocar);
				break;
			}
			
			for (int j = 0; j < Constants.TTI_TOTAL; j++) { // inicio TTI
				TTI tti_tmp = null;
				tti_tmp = new TTI();
				tti_tmp.setId(j);
				ArrayList<SB> sbTmp = createSB(pktsParaAlocar);
				tti_tmp.setSchedulingBlocks(sbTmp);
				tti_tmp.setThroughput(calcMeanRateSBs(sbTmp));
				ttiTmp.add(tti_tmp);
				pktsParaAlocar = updateDelay(pktsParaAlocar);
				if (delta)
					calcDelta(pktsParaAlocar,1,0.5);
			} // FIM TTI
		}

		return ttiTmp;
	}
	
	/**
	 * Aloca recursos contabilizando perdas de pacotes por tipo
	 * 
	 * @param pktsParaAlocar
	 * @param v
	 * @param w
	 * @return simulação
	 */
	public static ArrayList<Integer> allocateResourcesWithLosses(ArrayList<Packet> tmp, double v, double w) {

		ArrayList<TTI> ttiTmp = new ArrayList<TTI>();
		ArrayList<Packet> pktsParaAlocar = new ArrayList<Packet>();
		
		
		ArrayList<Integer> aux = new ArrayList<Integer>();
		int totalTipoA=0,totalTipoB=0;
		
		pktsParaAlocar = util.copyArray(tmp);

		if (pktsParaAlocar != null) {
			calcDelta(pktsParaAlocar, v, w);
			sortByDelta(pktsParaAlocar);

			for (int j = 0; j < Constants.TTI_TOTAL; j++) {
				TTI tti_tmp = null;
				tti_tmp = new TTI();
				tti_tmp.setId(j);
				
				ArrayList<SB> sbTmp = createSB(pktsParaAlocar);
				
				tti_tmp.setSchedulingBlocks(sbTmp);
				tti_tmp.setThroughput(calcMeanRateSBs(sbTmp));
				ttiTmp.add(tti_tmp);
				
				pktsParaAlocar = updateDelay(pktsParaAlocar);
				calcDelta(pktsParaAlocar, v, w);
				
				//TESTES
				// aqui verificar caso eu estiver no TTI 50+1 ou no TTI 100+1 para retirarmos os pacotes espirados
				if (j==51){ 
					for (Packet p : pktsParaAlocar){
						//System.out.println("pkt_ID="+p.getId()+" | "+"pkt_DELAY="+p.getDelay()+" | "+"pkt_TIPO="+p.getTos());
						if(p.getTos()==1){
							totalTipoA++;
						}
					}
					aux.add(totalTipoA);
				}
				
				if (j==101){ 
					for (Packet p : pktsParaAlocar){
						if(p.getTos()==2){
							totalTipoB++;
						} 
					}
						aux.add(totalTipoB);
				}
			}
		}
		return aux;
	}
	
	public static ArrayList<TTI> allocateResources(ArrayList<Packet> tmp, double v, double w) {

		ArrayList<TTI> ttiTmp = new ArrayList<TTI>();
		ArrayList<Packet> pktsParaAlocar = new ArrayList<Packet>();

		pktsParaAlocar = util.copyArray(tmp);

		if (pktsParaAlocar != null) {
			calcDelta(pktsParaAlocar, v, w);
			sortByDelta(pktsParaAlocar);

			for (int j = 0; j < Constants.TTI_TOTAL; j++) {
				TTI tti_tmp = null;
				tti_tmp = new TTI();
				tti_tmp.setId(j);
				ArrayList<SB> sbTmp = createSB(pktsParaAlocar);
				tti_tmp.setSchedulingBlocks(sbTmp);
				tti_tmp.setThroughput(calcMeanRateSBs(sbTmp));
				ttiTmp.add(tti_tmp);
				pktsParaAlocar = updateDelay(pktsParaAlocar);
				calcDelta(pktsParaAlocar, v, w);
			}
		}
		return ttiTmp;
	}
	
	/**
	 * Recebe um conjunto de SBs e calcula a media de rate destes SBs<br>
	 * se o numero de SBs for os SBs que compoe um TTI, esta média <br>
	 * corresponde a média de rate de um TTI.
	 * 
	 * @param aux
	 * @return meanRateSBs
	 */
	public static double calcMeanRateSBs(ArrayList<SB> schedulingBlocks) {
		ArrayList<SB> totalSB = schedulingBlocks;
		double sumSB = 0;
		for (SB atualSB : totalSB)
			sumSB = atualSB.getThroughput() + sumSB;
		return sumSB / totalSB.size();
	}

	public static ArrayList<SB> createSB(ArrayList<Packet> pktsParaAlocar) {
		ArrayList<SB> sbTmp = new ArrayList<SB>();
		for (int x = 0; x < Constants.SB_EACH_TTI; x++) { // inicio SB

			Iterator<Packet> it_pkt = pktsParaAlocar.iterator();

			if (it_pkt.hasNext()) {
				Packet c = it_pkt.next();
				SB sb_tmp = null;
				sb_tmp = new SB();
				sb_tmp.setId(x);
				sb_tmp.setPacket(c);
				
				// Restricao MCS em mesmos TTIs
				for (SB a : sbTmp) {
					//se o user do pkt q estou alocando neste SB for == ao user do pkt de algum dos SB q eu ja criei neste TTI
					if (c.getUser() == a.getPacket().getUser()) { 
						int f = mean(c.getMean_cqi(), a.getPacket().getMean_cqi());
						a.getPacket().setMean_cqi(f);
						sb_tmp.getPacket().setMean_cqi(f);
					}
				}

				sb_tmp.setMcs(mcsCalc(c.getMean_cqi()));
				sb_tmp.setThroughput(throughputCalc(c.getSimbolos(),sb_tmp.getMcs()));
				sbTmp.add(sb_tmp);
				try {
					pktsParaAlocar.remove(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		} // FIM SB
		return sbTmp;
	}
	
	/**
	 * Diminuir o delay quando itera o TTI
	 * Atualizar todos os delays dos pacotes em -1ms 
	 * @param pktsOrdenados 
	 */
	public static ArrayList<Packet> updateDelay(ArrayList<Packet> pktsOrdenados){
		ArrayList<Packet> tmp = new ArrayList<Packet>(pktsOrdenados);
		Iterator<Packet> itr_pkt = tmp.iterator();
		
		while(itr_pkt.hasNext()){
			Packet p = itr_pkt.next();
			p.setDelay(p.getDelay()-1);
		}
		return tmp;
	}
	
	/**
	 * Throughput de cada SB = R*LOG(C;2)/(T*NB))*NC</br></br>
	 *  R = CodeRate</br>
	 *  C = CONSTELACAO</br>
	 *  T = TEMPO_CADA_SIMBOLO</br>
	 *  NB = NR_SIMBOLOS_SB </br>
	 *  NC = NR_SIMBOLOS_CARREGAM_DADOS_SB</br>
	 *  
	 * @param simb
	 * @param mcs_scheme
	 * @return
	 */
	public static double throughputCalc(int simb, int mcs_scheme){
		double c=0,r=1,b=0,t=71.42,nb=14;
		c=Math.log(mcs_scheme)/Math.log(2);
		b=(r*c)/(t*nb)*simb;
		return b;
	}
	
	/**
	 * Atribui o MCS dado um CQI
	 * @param cqi_p
	 * @return
	 */
	public static int mcsCalc(int cqi_p){
		int mcs=0;
		if(cqi_p>=1&&cqi_p<=6) 		//QPSK
			mcs=16;
		if(cqi_p>=7&&cqi_p<=9) 		//16QAM 
			mcs=32;
		if(cqi_p>=10&&cqi_p<=15)	//64QAM
			mcs=64;
		return mcs;
	}

//	/**
//	 * Calcula o mean CQI
//	 * @param pktTmp
//	 * @return pktTmpSorted
//	 */
//	public static ArrayList<Packet> calcMeanCQI(ArrayList<Packet> pktTmp) {
//		for (Packet x : pktTmp){
//			x.setMean_cqi((int) mean(x.getCqi()));
//		}
//		return pktTmp;
//	}
	
	/**
	 * Retorna media do array
	 */
	public static double mean(int[] m) {
	    double sum = 0;
	    for (int i = 0; i < m.length; i++) {
	        sum += m[i];
	    }
	    return sum / m.length;
	}
	
	public static int mean(int n1, int n2) {
	    double sum = (n1+n2)/2;
	    int sum1=0;
	        sum1 = (int) Math.floor(sum);
	    return sum1;
	}

	/**
	 *  Ordena o Array de pacotes por delay
	 *  - Crescente (menor delay primeiro)
	 */
	public static void sortByDelay(ArrayList<Packet> pktTmp) {
		Collections.sort(pktTmp, new Comparator<Packet>() {
	        @Override
	        public int compare(Packet  p1, Packet  p2)
	        {
	        	Integer c1 = p1.getDelay();
	        	Integer c2 = p2.getDelay();
	        	return c1.compareTo(c2);	            
	        }
	    });
	}

	/**
	 *  Ordena o Array de pacotes por simbolos
	 *  - Decrescente (mais simbolos primeiro)
	 */
	public static void sortBysimbolos(ArrayList<Packet> pktTmp) {
		Collections.sort(pktTmp, new Comparator<Packet>() {
	        @Override
	        public int compare(Packet  p1, Packet  p2)
	        {
	        	Integer c1 = p1.getSimbolos();
	        	Integer c2 = p2.getSimbolos();
	        	return c1.compareTo(c2);	            
	        }
	    });
	}
	
	/**
	 *  Ordena o Array de pacotes por Mean_CQI
	 *  - Decrescente (maior cqi primeiro)
	 */
	public static void sortByCQI(ArrayList<Packet> pktTmp) {
		Collections.sort(pktTmp, new Comparator<Packet>() {
	        @Override
	        public int compare(Packet  p1, Packet  p2)
	        {
	        	Integer c1 = p1.getMean_cqi();
	        	Integer c2 = p2.getMean_cqi();
	        	return c2.compareTo(c1);	            
	        }
	    });
	}
	
	/**
	 *  Ordena o Array de pacotes pelo Delta
	 *  - TEM QUE SER DECRESCENTE (maior delta primeiro)
	 */
	public static void sortByDelta(ArrayList<Packet> pktTmp) {
		Collections.sort(pktTmp, new Comparator<Packet>() {
	        @Override
	        public int compare(Packet  p1, Packet  p2)
	        {
	        	Double c1 = p1.getDelta();
	        	Double c2 = p2.getDelta();
	        	return c2.compareTo(c1);	            
	        }
	    });
	}
	
	/**
	 * Calcula o Delta dos pkts </br>
	 * - Depende do trafficGenerate </br>
	 * peso_maximo = (cqi_max/delay_min) </br>
	 * peso = (cqi^v/delay^w)  </br>
	 * 
	 * v=alpha
	 * w=beta
	 * 
	 * Onde v e w são parâmetros de importância de cada fator.
	 *
	 * @param pktTmp
	 * @return
	 */
	public static ArrayList<Packet> calcDelta(ArrayList<Packet> pktTmp, double argV, double argW) {

		ArrayList<Packet> pktsWithDelta = new ArrayList<Packet>(pktTmp);
		Iterator<Packet> itr_pkt = pktsWithDelta.iterator();

		double v = 0, w = 0, vv = 0, ww = 0;
		v=argV;
		w=argW;
		
		while (itr_pkt.hasNext()) {
			Packet p = itr_pkt.next();
			double a, b, c;
			a = p.getMean_cqi();
			b = p.getDelay();
			vv = Math.pow(a, v);
			ww = Math.pow(b, w);
			c = (vv / ww);
			p.setDelta(c);
		}
		return pktsWithDelta;
	}
	
	/* LEMBRETES
	  - Calcular o numero de simbolos para retirar
	  - Criar vetor de pacotes descartados quando delay for menor que 0
	*/
	
}