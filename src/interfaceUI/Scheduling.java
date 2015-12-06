package interfaceUI;

import generateResource.Traffic;

import java.io.IOException;
import java.util.ArrayList;

import lteStructure.Packet;
import lteStructure.TTI;
import scheduling.Constants;
import scheduling.Scheduler;
import showResults.Plot;
import showResults.Print;

public class Scheduling {

	/**
	 * Método que chama as simulações no modo user friendly é utilizada pela
	 * interface grafica
	 * 
	 * @throws IOException
	 */
	public ArrayList<TTI> doUserFriendlySimulation(Boolean sb_delay,
												   Boolean fairness_user,
												   Boolean rate_user,
												   Boolean sb_mcs,
												   Boolean sb_user,
												   Boolean allocationStatus) throws IOException {

		ArrayList<TTI> simulation = new ArrayList<TTI>();
		ArrayList<TTI> ttiTmp = new ArrayList<TTI>(simulation);
		ArrayList<Packet> pktsTmp = new ArrayList<Packet>();

		pktsTmp = Traffic.trafficGenerate();
		ttiTmp = Scheduler.allocateResources(pktsTmp);

		if (sb_delay)
			Plot.plotSBxDelay(ttiTmp);
		if (fairness_user)
			Plot.plotFairness(ttiTmp);
		if (rate_user)
			Plot.plotSBxRate(ttiTmp);
		if (sb_mcs)
			Plot.plotSBxMCS(ttiTmp);
		if (sb_user)
			Plot.plotSBxUser(ttiTmp);
		if (allocationStatus)
			Plot.plotAllocationStatus(pktsTmp, ttiTmp);
				
		return ttiTmp;
	}
		

	/**
	 * Método que chama as simulações no modo avançado
	 * 
	 * @throws IOException
	 */
	public void doExpertSimulation() throws IOException {

		ArrayList<TTI> simulation = new ArrayList<TTI>();
		Long t_inicial, t_final, t_total;

		ArrayList<TTI> ttiTmp = new ArrayList<TTI>(simulation);
		ArrayList<Packet> pktsTmp = new ArrayList<Packet>();

		if (Constants.INPUT_MODE == 0) {
			pktsTmp = Traffic.trafficGenerate(); // Gera tráfego dinâmicamente
		} else if (Constants.INPUT_MODE == 1) {
			pktsTmp = Traffic.trafficGenerate(); // Gera tráfego dinâmicamente
			Traffic.trafficToFile(pktsTmp); // Armazena o tráfego em um arquivo
		} else if (Constants.INPUT_MODE == 2) {
			pktsTmp = Traffic.convertTxtToPkts(Constants.INPUT_FILE); // Lê o tráfego de um arquivo
		}

		t_inicial = System.currentTimeMillis();

		switch (Constants.EXECUTION_MODE) {
		case 0:
			ttiTmp = Scheduler.allocateResources(pktsTmp); 	// Executa apenas uma simulação
			break;
		case 1:
			Scheduler.genTTIrate(pktsTmp); 					// Gera rate dos ttis
			break;
		case 2:
			Scheduler.genMapDelta(pktsTmp); 				// Gera um mapa de pontos delta x rate
			break;
		case 3:
			Scheduler.genMapFairness(pktsTmp); 				// Gera um mapa de pontos delta x fairness
			break;
		case 4:
			Scheduler.genMapLosses(pktsTmp); 				// Gera um mapa das perdas
			break;
		}

		t_final = System.currentTimeMillis();
		t_total = t_final - t_inicial;

		/************ PRINTS ************/

		// Print do tráfego (puro) gerado pelo simulador
//		System.out.println("\n# Tráfego Gerado #\n");
//		Print.printTraffic(pktsTmp);

		// Print de quantos pacotes foram atendidos ou não atendidos no escalonamento
//		System.out.println("\n# Status de alocação #\n");
//		Print.printAllocationStatus(pktsTmp, ttiTmp);

		// Print de todos -> TTIs -> SBs -> Pkts e suas estruturas
//		System.out.println("\n# Visão detalhada do escalonamento #\n");
//		Print.printTTI(ttiTmp);

		// Print da matriz de pkts do tipo x alocados no TTI y
//		System.out.println("\n# Matriz TTI x TipoServico = qntd pkts #\n");
//		Print.printPktServicesDelay(ttiTmp);

		// Print das características de fairness do escalonamento
//		System.out.println("\n# Caracteristicas de Fairness #\n");
//		Print.printFairness(ttiTmp);

//		Plot.plotFairness(ttiTmp);
//		Plot.plotSBxUser(ttiTmp);
//		Plot.plotSBxDelay(ttiTmp);
//		Plot.plotSBxRate(ttiTmp);
//		Plot.plotSBxMCS(ttiTmp);
//		Plot.plotAllocationStatus(pktsTmp, ttiTmp);

		System.out.println("\nTempo TOTAL de execucao programa = " + t_total + "ms");
	}

}
