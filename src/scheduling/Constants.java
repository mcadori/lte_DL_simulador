package scheduling;

public class Constants {
	/* Recursos */
	public static int TTI_TOTAL=120; 	  						// Total de TTI (Tempo de simulacao)
	public static int SB_EACH_TTI=100;	  				    	// Total de SB em um TTI
	public static int N_TOTAL_SB=(TTI_TOTAL*SB_EACH_TTI);  		// Total SB disponiveis
		
	/* Geração de Pkts de Usuarios */
	public static int N_TOTAL_PKTS=30000; 						// Total de pacotes gerados usuarios (opcionalmente pode ser = N_TOTAL_SB)
	public static int N_USERS=1000; 							// Num de users para geração
	
	public static int CQI_DISTRIBUTION=0; 						// Distribuição do CQI (0 = CQI_NORMAL | 1 = CQI_MELHORES)
	public static int MEAN_CQI_DISTRIBUTION=0;					// Distribuição do mean_cqi (uniforme=0 | constante=1 | injusto=2 )
	
	public static int PKT_SIZE_DISTRIBUTION=0;					// Distribuição do tamanho dos pkts (poisson=0 | constante=1)
	public static int SIMBOLS_DISTRIBUTION=145;					// Media da poisson ou valor da constante caso a opção acima ser igual a 1
	
	public static int USER_PKTS_DIST=0;							// Distribuição dos users para os pkts (uniforme=0 | constante=1)
	
	/* CQIs por user */
	public static int ARRAY_CQI=SB_EACH_TTI+1; 					// Num do array de cqis reportado pelo user, destes valores calcula-se o meanCqi
	
	/* Escalonamento */
	public static int SORT_MODE=1; 								// Ordenamento por (0 = nada | 1 = DELTA | 2 = CQI | 3 = DELAY)
	
	/* Execucao */
	public static int EXECUTION_MODE=4;							// Modo de execucao (0 = execucao de apenas uma simulacao | 1 = gera MAPA rate | 2 = gera rates por tti | 3 = gera MAPA fairness | 4 = gera MAPA perdas )
	public static int INPUT_MODE=0;								// Entrada dos dados (0 = Automatica, sem arquivos | 1 = Gerar arquivo e executar | 2 = ler arquivo)
	public static String INPUT_FILE="arquivo.txt";				// Arquivo entrada dados simulador
	public static int N_POINTS_MAP=600;							// Num de pontos do mapa do omega
	
	//Colocar nesta parte os valores do delta
}