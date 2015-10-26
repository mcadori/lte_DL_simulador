package utils;

import java.util.ArrayList;

import lteStructure.Packet;

public class ArrayUtil {
	
	/**
	 * Copia o array recebido para um novo array (deep copy)
	 * @param oldList
	 * @return
	 */
	public ArrayList<Packet> copyArray(ArrayList<Packet> oldList){
		
		ArrayList<Packet> newList = new ArrayList<Packet>();
		for(Packet p : oldList) {
			newList.add(p.getNewCopy()); 
		}
		return newList;		
	}
	
}
