package generateResource;

import java.util.Random;
import java.lang.Math;

import scheduling.Constants;

public class Distribution extends Random {

	private static final long serialVersionUID = 1L;

	/**
	 * Gera distribuição Poisson numero de simbolos
	 *  
	 *  20 bytes = http
	 *	160 bits = http 
	 *	SB 168 simbolos
	 *	1 SB = 1008 bits
	 *	1SB = 6,3 http pkts
	 *
	 */
	public static int getPoissonRandom(double mean) {
		Random r = new Random();
		double L = Math.exp(-mean);
		int k = 0;
		double p = 1.0;
		do {
			p = p * r.nextDouble();
			k++;
		} while (p > L);
		if(k>=167)
			k=169;
		return k - 1;
	}
	
	/**
	 * Gera Serviços diferenciados 
	 * Distribuição aleatória entre 1 e 4
	 * Se:
	 *  1 - 50ms  - 1 
	 *  2 - 100ms - 0.5
	 *  3 - 200ms - 0.25 
	 *  4 - 300ms - 0.16
	 */
	public static int getRandomDelay() {
		int maxNumber = 5; 
		Random rand = new Random();

		int num = 0;

		while (num == 0) {
			num = rand.nextInt(maxNumber);
		}

		if (num == 1)
			return 50 ;
		else if (num == 2)
			return 100 ; 
		else if (num==3)
			return 200 ;
		else 
			return 300 ;
		
	}

	/**
	 * Gera usuarios aleatórios uniformes entre 1 e 10
	 */
	public static int getRandomUser() {
		int maxNumber = Constants.getN_USERS()+1; // Valor maximo q ele retorna
		Random rand = new Random();

		int num = 0;

		while (num == 0) {
			num = rand.nextInt(maxNumber);
		}
		return num;
	}
		
	/**
	 * Gera valores robados para os CQIS entre 1-15
	 */
	public static int getBetterCQI() {
		
		Random rand = new Random();
		Double t = rand.nextDouble();
		int	num = (int) (15 * Math.pow(t, 0.5) + 1);
			
		return num;
	}
	
	/**
	 * Gera valores uniformes para os CQIS entre 1-15
	 */
	public static int getRandomCQI() {
		int maxNumber = 16; // Valor maximo q ele retorna
		Random rand = new Random();

		int num = 0;

		while (num == 0) {
			num = rand.nextInt(maxNumber);
		}
		return num;
	}

}