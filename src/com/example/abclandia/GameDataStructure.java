package com.example.abclandia;

import java.util.HashMap;
import java.util.Map;

public class GameDataStructure {
	
	static final char[] a = { 'a','b','c','d' };
	
	

	 private static final Map<Integer, char[]> e1L1Secuences;
	 private static final Map<Integer, char[]> e1L2Secuences;
	 private static final Map<Integer, char[]> e1L3Secuences;
	 private static final Map<Integer, char[]> e45L1Secuences;
	 private static final Map<Integer, char[]> e45L2Secuences;
	 private static final Map<Integer, char[]> e45L3Secuences;
	 private static final Map<Integer, char[]> e6L1Secuences;
	 
	 
	
	 private static final Map<Integer, Map<Integer, char[]>> e123Levels;
	 private static final Map<Integer, Map<Integer, char[]>> e45Levels;
	 private static final Map<Integer, Map<Integer, char[]>> e6Levels;
	 
	 private static final Map<Integer, Map<Integer,Map<Integer,char[]>>> excercises;
	 
	 
	 
	    static
	    {	e1L1Secuences = new HashMap<Integer, char[]>();
	    	e1L1Secuences.put(1, new char[] {'a','e','i','o','u'});
	    	e1L1Secuences.put(2, new char[] {'a','e','m', 's','p'});
	    	e1L1Secuences.put(3, new char[] {'i','o','u','m','s'});
	    	e1L1Secuences.put(4, new char[] {'s','m','p','a','0'}); 
	    	e1L1Secuences.put(5, new char[] {'i','u','l','s','p'});
	    	e1L1Secuences.put(6, new char[] {'l','p','s','m','t'});
	    	e1L1Secuences.put(7, new char[] {'t','s','l','p','f'}); 
	    	e1L1Secuences.put(8, new char[] {'t','f','l','p','n'});
	    	e1L1Secuences.put(9, new char[] {'n','p','l','t','f'});
	    	
	    	e1L2Secuences = new HashMap<Integer, char[]>();
	    	e1L2Secuences.put(1, new char[] {'n','f','t','g','r'});
	    	e1L2Secuences.put(2, new char[] {'r','f','g','n','d'});
	    	e1L2Secuences.put(3, new char[] {'d','n','r','g','f'});
	    	e1L2Secuences.put(4, new char[] {'g','d','n','b','r'}); 
	    	e1L2Secuences.put(5, new char[] {'r','g','b','d','q'});
	    	e1L2Secuences.put(6, new char[] {'q','d','b','j','c'}); 
	    	e1L2Secuences.put(7, new char[] {'d','b','j','c','q'});
	    	e1L2Secuences.put(8, new char[] {'b','q','j','c','h'}); 
	    	
	    	e1L3Secuences = new HashMap<Integer, char[]>();
	    	e1L3Secuences.put(1, new char[] {'q','j','c','h','v'}); 
	    	e1L3Secuences.put(2, new char[] {'v','j','c','h','z'}); 
	    	e1L3Secuences.put(3, new char[] {'v','h','c','z','x'}); 
	    	e1L3Secuences.put(4, new char[] {'h','z','v','x','y'}); 
	    	e1L3Secuences.put(5, new char[] {'v','h','y','x','z'}); 
	    	e1L3Secuences.put(6, new char[] {'z','y','x','w','ñ'}); 
	    	e1L3Secuences.put(7, new char[] {'x','y','w','ñ','k'});   
	    	e1L3Secuences.put(8, new char[] {'y','w','ñ','k'}); 
	    	e1L3Secuences.put(9, new char[] {'ñ','w','k','y'});
	    	
	    	
	    	e45L1Secuences = new HashMap<Integer, char[]>();
	    	e45L1Secuences.put(1, new char[] {'a','e','o'});
	    	e45L1Secuences.put(2, new char[] {'u','i','s'});
	    	e45L1Secuences.put(3, new char[] {'m','p','l'});
	    	e45L1Secuences.put(4, new char[] {'t','j','f'});
	    	
	    	e45L2Secuences = new HashMap<Integer, char[]>();
	    	e45L1Secuences.put(1, new char[] {'v','c','w'});
	    	e45L1Secuences.put(2, new char[] {'x','z','ñ'});
	    	
	    	e45L3Secuences = new HashMap<Integer, char[]>();
	    	e45L3Secuences.put(1, new char[] {'q','y','r'});
	    	e45L3Secuences.put(2, new char[] {'h','b','g'});
	    	e45L3Secuences.put(3, new char[] {'d','k','n'});
	    	
	    	e6L1Secuences = new HashMap<Integer, char[]>();
	    	e45L3Secuences.put(1, new char[] {'a','e','o'});
	    	e45L3Secuences.put(2, new char[] {'m','u','s'});
	    	e45L3Secuences.put(3, new char[] {'p','i','l'});
	    	e45L3Secuences.put(4, new char[] {'d','b','f'});
	    	e45L3Secuences.put(5, new char[] {'e','a','o'});
	    	e45L3Secuences.put(6, new char[] {'a','o','e'});
	    	e45L3Secuences.put(7, new char[] {'o','n','r'});
	    	e45L3Secuences.put(8, new char[] {'i','o','n'});
	    	
	    	
	    	
	    	
	    	
	    }
	    static {
	    	e123Levels = new HashMap<Integer, Map<Integer,char[]>>();
	    	e123Levels.put(1, e1L1Secuences);
	    	e123Levels.put(2, e1L2Secuences);
	    	e123Levels.put(3, e1L3Secuences);
	    	
	    	
	    	e45Levels = new HashMap<Integer, Map<Integer,char[]>>();
	    	e45Levels.put(1, e45L1Secuences);
	    	e45Levels.put(2, e45L2Secuences);
	    	e45Levels.put(3, e45L3Secuences);
	    	
	    	e6Levels = new HashMap<Integer, Map<Integer,char[]>>();
	    	e6Levels.put(1, e6L1Secuences);
	    }
	    static {
	    	excercises = new HashMap<Integer, Map<Integer,Map<Integer,char[]>>>();
	    	excercises.put(1,e123Levels);
	    	excercises.put(2,e123Levels);
	    	excercises.put(3, e123Levels);
	    	excercises.put(4, e45Levels);
	    	excercises.put(5, e45Levels);
	    	excercises.put(6, e6Levels);
	    }
	    
	    public static char[] getSecuence(int excerciseNumber, int levelNumber, int secuenceNumber){
	    	return excercises.get(excerciseNumber).get(levelNumber).get(secuenceNumber);
	    	 
		
	    	
	    }
	    public static boolean isFinalSecuence(int exerciseNumber, int levelNumber, int secuenceNumber){
	    	 return excercises.get(exerciseNumber).get(levelNumber).containsKey(secuenceNumber);
	    	
	    }
	    public static boolean isFinalLevel(int exerciseNumber, int levelNumber){
	    	return excercises.get(exerciseNumber).containsKey(levelNumber);
	    }
}
