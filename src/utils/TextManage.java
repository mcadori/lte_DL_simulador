package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextManage {

	public static File arquivo;
    public static String nomeArquivo;
    public static String path = "\\";
	
		/**
		 * Metodo para escrever no txt
		 * @param conteudo
		 */
		public static void escreve(String conteudo) {
			FileWriter escreve;
			try {
				escreve = new FileWriter(nomeArquivo, true);
				conteudo += System.getProperty("line.separator");
				escreve.write(conteudo);
				escreve.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	    /**
	     * Metodo para criar txt
	     */
	    public static void criarTXT(){
	        if(arquivo.exists() == false){
	            try {
	                arquivo.createNewFile();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
}

