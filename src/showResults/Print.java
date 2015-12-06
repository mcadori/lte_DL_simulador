package showResults;

import java.util.ArrayList;

import scheduling.Constants;

import lteStructure.Packet;
import lteStructure.SB;
import lteStructure.TTI;

public class Print {

	/**
	 * Recebe o ArrayList da simulacao efetuada e imprime os resultados</br>
	 * Imprime arrayList TTI->SB->PKTS
	 */
	public static void printTTI(ArrayList<TTI> simulation) {

		ArrayList<TTI> itr = simulation;
		ArrayList<SB> itr1 = null;

		for (int i = 0; i < itr.size(); i++) {
			TTI element = itr.get(i);
			itr1 = element.getSchedulingBlocks();

			System.out.println("\nTTI ID = " + element.getId());

			for (int j = 0; j < itr1.size(); j++) {
				SB element1 = itr1.get(j);
				System.out.println("| SB ID = " + element1.getId() + 
						" \n| | PKT ID = ----------> "
						+ element1.getPacket().getId() +
						" \n| | PKT tos = ---------> "
						+ element1.getPacket().getTos() +
						" \n| | PKT Delay = -------> "
						+ element1.getPacket().getDelay() + 
						" \n| | PKT Simbolos = ----> "
						+ element1.getPacket().getSimbolos() + 
						" \n| | PKT mean CQI = ----> "
						+ element1.getPacket().getMean_cqi() + 
						" \n| | PKT User = --------> "
						+ element1.getPacket().getUser() +						
						" \n| | PKT Delta = -------> "
						+ element1.getPacket().getDelta() +
						" \n| SB MCS = -------------> "
						+ element1.getMcs() +
						"\n| Throughput deste SB = "
						+ element1.getThroughput() +
						"\n| "
						);
			}
			System.out.println("Throughput deste TTI = "+element.getThroughput());
			
		}
	}
	
	/**
	 * Recebe uma simulação e retorna as estatisticas de quais tipos de pacotes foram alocados em cada TTI em uma evolução temporal.<br>
	 * Na matriz, tipoServicos[i][j]. As linhas [i], representam nro_total_TTIs e as colunas [j] os tipos de serviços logo, <br> 
	 * tipoServicos[i][j]= nro de pkts do tipo [j] alocados no TTI [i]. <br>
	 *  EX:..<br>
	 *  0.0 0.0 0.0 0.0 0.0 <br> 
	 *  1.0 3.0 3.0 0.0 0.0 <br>
	 *  2.0 0.0 0.0 6.0 0.0 <br>
	 *
	 * A matriz acima representa que 3 pkts do tipo A e 3 do tipo B foram alocados no TTI 1 e 6 do tipo 3 no segundo TTI. <br>
	 * 
	 * OBS:.. Pkts que não foram alocados, não são mostrados na matriz. 
	 * 
	 * @param ttiTmp
	 */
	public static void printPktServicesDelay(ArrayList<TTI> ttiTmp) {
		
		ArrayList<TTI> itr = ttiTmp;
		ArrayList<SB> itr1 = null;
		double[][] tosStatistic = new double[Constants.getTTI_TOTAL()+1][5] ;
		double a=1,b=1,c=1,d=1;

		for (int i = 0; i < itr.size(); i++) {
			TTI element = itr.get(i);
			itr1 = element.getSchedulingBlocks();
			
			tosStatistic[i+1][0] = i+1;
			
			for (int j = 0; j < itr1.size(); j++) {
				SB element1 = itr1.get(j);
				
				if (element1.getPacket().getTos() == 1) {
					tosStatistic[i+1][1] = a;
					a = a + 1;
				}
				if (element1.getPacket().getTos() == 2) {
					tosStatistic[i+1][2] = b;
					b = b + 1;
				} 
				if (element1.getPacket().getTos() == 3) {
					tosStatistic[i+1][3] = c;
					c = c + 1;
				} 
				if (element1.getPacket().getTos() == 4) {
					tosStatistic[i+1][4] = d;
					d = d + 1;
				} 				
			}
			
			a=1;b=1;c=1;d=1;
		}
		
		printMatrix(tosStatistic);
	}
	
	/**
	 * Recebe uma simulação e calcula o fairness entre os usuários usando equacao do R. Jain
	 * 
	 * fairness=|sum_ratesMedios|^2 / numero_usuarios*(sum_ratesMedios_quadrados)
	 * 
	 * @param simulation
	 */
	public static void printFairness(ArrayList<TTI> simulation) {
	
		ArrayList<TTI> itr = simulation;
		ArrayList<SB> itr1 = null;
		Double[] aux = new Double[Constants.getN_USERS()+1];
		
		for(int p=0;p<=Constants.getN_USERS();p++)
			aux[p]=0.0;
		
		for (int i = 0; i < itr.size(); i++) {
			TTI element = itr.get(i);
			itr1 = element.getSchedulingBlocks();

			for (int j = 0; j < itr1.size(); j++) {
				SB element1 = itr1.get(j);
				
				for (int k=1;k<=Constants.getN_USERS();k++){
					if (element1.getPacket().getUser() == k){
						aux[k]= aux[k] + element1.getThroughput();
					}  
				} 
			}
		}
		
		Double sum_rate = 0., sum_quadrada = 0., fairness=0.;
		
		System.out.println("Rate total de cada user:");
		
		//calcula o sum_rate i.e. a media de todos rates somadas
		for(int p=1;p<=Constants.getN_USERS();p++){
			System.out.println("Usuário "+p+" = "+aux[p]);
			sum_rate=(aux[p]/Constants.getN_USERS())+sum_rate;
			sum_quadrada=Math.pow((aux[p]/Constants.getN_USERS()),2)+sum_quadrada;
		}
				
		fairness=(Math.pow(Math.abs(sum_rate),2) / (Constants.getN_USERS()*sum_quadrada));
		
		System.out.println("Soma das medias dos rates = "+sum_rate);
		System.out.println("Soma quadratica das medias = "+sum_quadrada);
		System.out.println("Fairness = "+fairness);		
	}
	
	/**
	 * Imprime o array List do trafego dos usuarios
	 */
	public static void printTraffic(ArrayList<Packet> pktsToPrint){
		
		for (Packet x : pktsToPrint){
            x.printPacketList();
        }
	}
	
	/**
	 * Imprime uma matriz do tipo double
	 * 
	 * @param matrix
	 */
	public static void printMatrix(double[][] matrix) {
		// display output
		for (int i = 0; i < matrix.length; i++) {

			for (int j = 0; j < matrix[i].length; j++) {

				System.out.print(matrix[i][j] + " ");
				// System.out.println();
			}
			System.out.println();
		}
	}

	/**
	 * Imprime um array de inteiros
	 * 
	 * @param arrayToPrint
	 */
	public static void printArrayInt(ArrayList<Integer> arrayToPrint){
		for (Integer x : arrayToPrint){
            System.out.println("PKT ID = " + x );
        }
	}
	
	/**
	 * Conferir quais os pkts de allPkts estao em ttiTmp e criar um plot
	 * 
	 * @param allPkts
	 * @param ttiTmp
	 */
	public static void printAllocationStatus(ArrayList<Packet> allPkts, ArrayList<TTI> ttiTmp) {

		ArrayList<TTI> ttiIterator = ttiTmp;
		ArrayList<SB> sbIterator = null;
		ArrayList<Packet> naoAlocados = new ArrayList<Packet>(allPkts);
		ArrayList<Packet> alocados = new ArrayList<Packet>();

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

		System.out.println("\nAlocados:\n");
		printTraffic(alocados);
		System.out.println("\n Nao Alocados: \n");
		printTraffic(naoAlocados);
		
	}
}
