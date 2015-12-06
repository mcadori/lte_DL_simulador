package scheduling;

public class Constants {
	/* Recursos */
	private static int TTI_TOTAL=200; 	  									// Total de TTI (Tempo de simulacao)
	private static int SB_EACH_TTI=15;	  				    				// Total de SB em um TTI
	public static int N_TOTAL_SB=(getTTI_TOTAL()*getSB_EACH_TTI());  		// Total SB disponiveis
		
	/* Geração de Pkts de Usuarios */
	private static int N_TOTAL_PKTS=1200; 						// Total de pacotes gerados usuarios (opcionalmente pode ser = N_TOTAL_SB)
	private static int N_USERS=25; 								// Num de users para geração
	
	public static int CQI_DISTRIBUTION=0; 						// Distribuição do CQI (0 = CQI_NORMAL | 1 = CQI_MELHORES)
	public static int MEAN_CQI_DISTRIBUTION=0;					// Distribuição do mean_cqi (uniforme=0 | constante=1 | injusto=2 )
	
	public static int PKT_SIZE_DISTRIBUTION=0;					// Distribuição do tamanho dos pkts (poisson=0 | constante=1)
	public static int SIMBOLS_DISTRIBUTION=145;					// Media da poisson ou valor da constante caso a opção acima ser igual a 1
	
	public static int USER_PKTS_DIST=0;							// Distribuição dos users para os pkts (uniforme=0 | constante=1)
	
	/* CQIs por user */
	public static int ARRAY_CQI=getSB_EACH_TTI()+1; 			// Num do array de cqis reportado pelo user, destes valores calcula-se o meanCqi
	
	/* Escalonamento */
	private static int SORT_MODE=1; 							// Ordenamento por (0 = nada | 1 = OMEGA (ajustar valores) | 2 = CQI | 3 = DELAY)
	
	/* Parâmetros de OMEGA */
	private static double OMEGA_ALPHA = .8;						// Peso do CQI
	private static double OMEGA_BETA = 1.0;						// Peso do Delay
	
	/* Execucao */
	public static int EXECUTION_MODE=4;							// Modo de execucao (0 = execucao de apenas uma simulacao | 1 = gera rates por tti | 2 = gera MAPA rate | 3 = gera MAPA fairness | 4 = gera MAPA perdas )
	public static int INPUT_MODE=0;								// Entrada dos dados (0 = Automatica, sem arquivos | 1 = Gerar arquivo e executar | 2 = ler arquivo)
	public static String INPUT_FILE="arquivo1.txt";				// Arquivo entrada dados simulador
	public static int N_POINTS_MAP=600;							// Num de pontos do mapa do omega
	/**
	 * @return the n_TOTAL_PKTS
	 */
	public static int getN_TOTAL_PKTS() {
		return N_TOTAL_PKTS;
	}
	/**
	 * @param n_TOTAL_PKTS the n_TOTAL_PKTS to set
	 */
	public static void setN_TOTAL_PKTS(int n_TOTAL_PKTS) {
		N_TOTAL_PKTS = n_TOTAL_PKTS;
	}
	/**
	 * @return the tTI_TOTAL
	 */
	public static int getTTI_TOTAL() {
		return TTI_TOTAL;
	}
	/**
	 * @param tTI_TOTAL the tTI_TOTAL to set
	 */
	public static void setTTI_TOTAL(int tTI_TOTAL) {
		TTI_TOTAL = tTI_TOTAL;
	}
	/**
	 * @return the sB_EACH_TTI
	 */
	public static int getSB_EACH_TTI() {
		return SB_EACH_TTI;
	}
	/**
	 * @param sB_EACH_TTI the sB_EACH_TTI to set
	 */
	public static void setSB_EACH_TTI(int sB_EACH_TTI) {
		SB_EACH_TTI = sB_EACH_TTI;
	}
	/**
	 * @return the n_USERS
	 */
	public static int getN_USERS() {
		return N_USERS;
	}
	/**
	 * @param n_USERS the n_USERS to set
	 */
	public static void setN_USERS(int n_USERS) {
		N_USERS = n_USERS;
	}
	/**
	 * @return the sORT_MODE
	 */
	public static int getSORT_MODE() {
		return SORT_MODE;
	}
	/**
	 * @param sORT_MODE the sORT_MODE to set
	 */
	public static void setSORT_MODE(int sORT_MODE) {
		SORT_MODE = sORT_MODE;
	}
	/**
	 * @return the oMEGA_ALPHA
	 */
	public static double getOMEGA_ALPHA() {
		return OMEGA_ALPHA;
	}
	/**
	 * @param oMEGA_ALPHA the oMEGA_ALPHA to set
	 */
	public static void setOMEGA_ALPHA(double oMEGA_ALPHA) {
		OMEGA_ALPHA = oMEGA_ALPHA;
	}
	/**
	 * @return the oMEGA_BETA
	 */
	public static double getOMEGA_BETA() {
		return OMEGA_BETA;
	}
	/**
	 * @param oMEGA_BETA the oMEGA_BETA to set
	 */
	public static void setOMEGA_BETA(double oMEGA_BETA) {
		OMEGA_BETA = oMEGA_BETA;
	}
	
	//Bug, quando le direto do arquivo, nao preenche o TOS por isso não é possível gerar o mapa de perdas..
}