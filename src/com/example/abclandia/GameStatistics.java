package com.example.abclandia;

public class GameStatistics {
	private GameActivity mGame;
	private long mStartTime, mElapsedTime;
	private int failsCount, hitsCount;
	
	public GameStatistics(GameActivity gameActivity){
		mGame = gameActivity;
		mStartTime = System.currentTimeMillis();
		
	}
	 public void countHit(){
		 hitsCount++;
	}
	 
	public void countFail(){
		 failsCount++;
	}
	//Aca tenes que hacer el insert en la BD. Fijate que hice un par de metodos get en GameActivity
	// para obtener categoria, alumno, etc. Fijate que esta la variable mGame para pedirle los datos
	public void saveStatistics(){
		mElapsedTime = System.currentTimeMillis() - mStartTime;
		
	}

}
