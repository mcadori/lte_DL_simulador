package generateResource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import lteStructure.Packet;
import scheduling.Constants;
import scheduling.Scheduler;
import utils.TextManage;

public class Traffic {
	
	public static final String SIMBOLS="simbols";
	public static final String USER="user";
	public static final String CQI="cqi";
	
	/* Definicoes de Classes de Servicos */
	public static final String SERVICES="services";
	public static final String N_SERVICES="1";
	
	public static final String S1="1.0"; 	
	public static final String S2="0.5"; 	
	public static final String S3="0.25"; 	
	public static final String S4="0.16"; 	
	public static final String S1_INT="50"; 	
	public static final String S2_INT="100"; 	
	public static final String S3_INT="200"; 	
	public static final String S4_INT="300";
	/* fim  */
	
	/* Definicoes de MCSs */
	public static final String MCS="mcs";
	public static final String N_MCS="3";
	
	public static final String M1="16";
 	public static final String M2="32";
 	public static final String M3="64";
 	public static final String M1_INT="6";
 	public static final String M2_INT="9";
 	public static final String M3_INT="15";
 	/* fim */

	
	/**
	 * Alimenta o arrayList (pkts) que representa o trafego dos usuarios
	 * N_total_pkts = TOTAL de Pacotes <br>
	 * cqi=aleatorio uniforme entre 1 e 15 <br>
	 * user=aleatorio uniforme entre 1 e 10 
	 * simbolos=poisson com media 145 <br>
	 * delay=aleatorio uniforme entre 5 ou 10 <br>
	 * 
	 * @return
	 */
	public static ArrayList<Packet> trafficGenerate() {

		ArrayList<Packet> pkts = new ArrayList<Packet>();
		Packet tmp = null;
		int maxNumberStatic = Math.floorDiv(Constants.N_TOTAL_PKTS, Constants.N_USERS);
		int maxNumberVar=maxNumberStatic;
		int a=1;
		
		for (int i = 1; i <= Constants.N_TOTAL_PKTS; i++) {
			tmp = new Packet();

			Integer rand_simbolos = Integer.valueOf(Distribution.getPoissonRandom(Constants.SIMBOLS_DISTRIBUTION));
			Integer rand_delay = Integer.valueOf(Distribution.getRandomDelay());				
			Integer rand_user = 0;
			
			if (Constants.USER_PKTS_DIST == 0) { // uniforme
				rand_user = Integer.valueOf(Distribution.getRandomUser());
			} else if (Constants.USER_PKTS_DIST == 1) { // constante
					
				if (i<=maxNumberVar){
					rand_user = a;
				}
				
				if (i==maxNumberVar){
					maxNumberVar=maxNumberVar+maxNumberStatic;
					a++;
				}
				
			}

			tmp.setId(i);
			
			if (Constants.PKT_SIZE_DISTRIBUTION==0){
				tmp.setSimbolos(rand_simbolos);
			} else if (Constants.PKT_SIZE_DISTRIBUTION==1){
				tmp.setSimbolos(Constants.SIMBOLS_DISTRIBUTION); 
			}

			tmp.setDelay(rand_delay);
			tmp.setTos(typeOfService(rand_delay));
			
			tmp.setUser(rand_user);
			
			if (Constants.MEAN_CQI_DISTRIBUTION==0){
				tmp.setMean_cqi(Distribution.getRandomCQI());
			} else if (Constants.MEAN_CQI_DISTRIBUTION==1){
				tmp.setMean_cqi(7);
			} else if (Constants.MEAN_CQI_DISTRIBUTION==2){
				
				if (rand_user==5){
					tmp.setMean_cqi(14);
				} else{
					tmp.setMean_cqi(1);
				}
			} 
			pkts.add(tmp);
		}

		return pkts;
	}
	
	/**
	 * Recebe o valor do delay do pacote, e define o tipo de servico que este pacote possui.
	 * 
	 * @param rand_delay
	 * @return tipo do servico
	 */
	private static int typeOfService(Integer rand_delay) {
		int value=0;
		if (rand_delay==50){
			value=1;
		} else if (rand_delay==100) {
			value=2;
		} else if (rand_delay==200) {
			value=3;
		} else if (rand_delay==300){
			value=4;
		}
		return value;
	}

	/**
	 * Gera tráfego para um arquivo para depois ler este arquivo de qualquer fonte
	 *   Criar arquivo de entrada do exato, se eu defini que sao 1000 pkts:
	 *   usuarios 1-10
	 *   simbolos 1-168
	 *   cqi 1-15
	 *   servicos 1-2
	 **/
	public static void trafficToFile(ArrayList<Packet> dataPkts) {

		TextManage.arquivo = new File(TextManage.path);
//		TextManage.nomeArquivo = "userPkts"+
//					  "_totalPkts="+Constants.N_TOTAL_PKTS+
//					  "_nrUsers="+Constants.N_USERS+
//					  "_distribCQI="+Constants.CQI_DISTRIBUTION+
//					  "_distribSimbolos"+"Poisson."+Constants.SIMBOLOS_DISTRIBUTION+
//					  ".dat";
		TextManage.nomeArquivo = "arquivo.txt";
		
		TextManage.criarTXT();
		
		TextManage.escreve("data;");
		TextManage.escreve("param k :="+Constants.N_TOTAL_PKTS+";"); 	//numero de pacotes
		TextManage.escreve("param n :="+Constants.N_TOTAL_SB+";"); 		// numero de SBs 
		TextManage.escreve("param m :="+N_MCS+";"); 					// numero de mcss
		TextManage.escreve("param p :="+N_SERVICES+";"); 				//numero de servicos
		TextManage.escreve("");
		
		TextManage.escreve("param "+SIMBOLS+ " := " + " ");
		for (Packet p : dataPkts) {
			TextManage.escreve(p.getId()+" "+p.getSimbolos());
		}
		TextManage.escreve(";");

		TextManage.escreve("param "+SERVICES+ " := " + " ");
		for (Packet p : dataPkts) {
			if (p.getDelay() == 50){
				TextManage.escreve(p.getId()+" "+S1);	
			}
			if (p.getDelay() == 100){
				TextManage.escreve(p.getId()+" "+S2);	
			}
			if (p.getDelay() == 200){
				TextManage.escreve(p.getId()+" "+S3);	
			}
			if (p.getDelay() == 300){
				TextManage.escreve(p.getId()+" "+S4);	
			}
		}
		TextManage.escreve(";");

		TextManage.escreve("# "+"param "+USER+" := " + " ");
		for (Packet p : dataPkts) {
			TextManage.escreve("#"+p.getId()+" "+p.getUser());
		}
		TextManage.escreve("#;");

		TextManage.escreve("param "+MCS + " := " + " ");
		if (Constants.CQI_DISTRIBUTION == 0) {
			for (Packet p : dataPkts) {
				TextManage.escreve(p.getId()+" "+ Scheduler.mcsCalc(p.getMean_cqi()));
			}

		} else if (Constants.CQI_DISTRIBUTION == 1) {
			for (Packet p : dataPkts) {
				TextManage.escreve(p.getId()+" "+Scheduler.mcsCalc(p.getMean_cqi()));
			}
		}
		TextManage.escreve(";");
		
		TextManage.escreve("#param "+CQI + " := " + " ");
		if (Constants.CQI_DISTRIBUTION == 0) {
			for (Packet p : dataPkts) {
				TextManage.escreve("#"+p.getId()+" "+ p.getMean_cqi());
			}

		} else if (Constants.CQI_DISTRIBUTION == 1) {
			for (Packet p : dataPkts) {
				TextManage.escreve("#"+p.getId()+" "+p.getMean_cqi());
			}
		}
		TextManage.escreve("#;");
		
		TextManage.escreve("end;");
		
	}
	
	/**
	 * Recebe o nome do arquivo de entrada e lê todo este arquivo, originalmente no formato do Simplex <br>
	 *  e transforma estas informações na estrutura de dados chamada Packet, que é utilizada pelo <br>
	 *  escalonador. 
	 *  
	 * @param nomeTxt
	 * @return pkts
	 * @throws IOException
	 */
	public static ArrayList<Packet> convertTxtToPkts(String nomeTxt) throws IOException {
		ArrayList<Packet> pkts = new ArrayList<Packet>();
		Packet tmp = null;

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(new FileReader(nomeTxt)).useDelimiter(";");
		
		try {
			@SuppressWarnings("unused")
			String simbols = null, delay = null, users = null, mcs = null, cqi = null, resto=null;												
						
						resto = scanner.next();
						resto = scanner.next();
						resto = scanner.next();
						resto = scanner.next();
						resto = scanner.next();
						
						simbols = scanner.next().replace(":=", "").replace(SIMBOLS, "").
								replace("param","").
								replaceAll("\n", "");
						
												
						delay = scanner.next().replace(":=", "").replace(SERVICES, "").
								replace("param","").
								replaceAll("\n", "").
								replaceAll(" "+S1, " "+S1_INT).
								replaceAll(" "+S2, " "+S2_INT).
								replaceAll(" "+S3, " "+S3_INT).
								replaceAll(" "+S4, " "+S4_INT)
								;
						
						users = scanner.next().replace(":=", "").replace(USER, "").
								replace("param","").
								replace("#", "").
								replaceAll("\n", "");
						
						mcs = scanner.next().replace(":=", "").replace(MCS, "").
								replace("param","").
								replaceAll("\n", "")
								;
						
						cqi = scanner.next().replace(":=", "").replace(CQI, "").
								replace("param","").
								replace("#", "").
								replaceAll("\n", "");
 	
					ArrayList<Integer> arraySimbols = tokensToArray(simbols);
					ArrayList<Integer> arrayUsers = tokensToArray(users);
					ArrayList<Integer> arrayDelay = tokensToArray(delay);
					ArrayList<Integer> arrayMcs = tokensToArray(mcs);
					ArrayList<Integer> arrayCqi = tokensToArray(cqi);

					int a = 0;
					for (int j = 0; j < arraySimbols.size() / 2; j++) {
						
						tmp = new Packet();
						tmp.setId(arraySimbols.get(a));
						tmp.setSimbolos(arraySimbols.get(a + 1));
						tmp.setDelay(arrayDelay.get(a+1));					
						tmp.setMean_cqi(arrayCqi.get(a+1));
						tmp.setUser(arrayUsers.get(a+1)); 

						pkts.add(tmp);
						a = a + 2;
					}
					
					
		} finally {
			scanner.close();
		}
		return pkts;
	}

	 /**
	 * Recebe uma string com os dados referentes a algum parametro ex: delay, simbolos, users
	 * Retorna array de inteiros com os indices e os valores dos dados ex: [(0,146), (1,155)...]
	 * @param tokens
	 * @return arrayItens
	 */
	public static ArrayList<Integer> tokensToArray(String tokens) {

		ArrayList<Integer> arrayItens = new ArrayList<Integer>();
		int i = 0;
		StringTokenizer tokenizer = new StringTokenizer(tokens);

		while (tokenizer.hasMoreTokens()) {
			try {
				String token = tokenizer.nextToken();
				i = Integer.valueOf(token);
				arrayItens.add(i);
				i = i + 1;
			} catch (NumberFormatException e) {
				System.out.println("Conversão de dados inválida, confira o arquivo fonte");		
				e.printStackTrace();
			}
		}

		return arrayItens;
	}
}