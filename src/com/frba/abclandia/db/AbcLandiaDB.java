package com.frba.abclandia.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.frba.abclandia.db.AbcLandiaContract.*;

public class AbcLandiaDB extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ABCLandia.db";

    private static final String INT_NULL_TYPE = " INT NULL";
    private static final String INT_NOT_NULL_TYPE = " INT NOT NULL";
    private static final String INT_NOT_NULL_DEFAULT_0_TYPE = " INT NOT NULL DEFAULT 0";
    private static final String INT_NULL_DEFAULT_0_TYPE = " INT NULL DEFAULT 0";
    private static final String INT_NULL_DEFAULT_10_TYPE = " INT NULL DEFAULT 10";
    private static final String VARCHAR_45_TYPE = " VARCHAR(45)";
    private static final String VARCHAR_45_NOT_NULL_TYPE = " VARCHAR(45) NOT NULL";
    private static final String TEXT_TYPE = " TEXT NULL";
    private static final String BLOB_NULL_TYPE = " BLOB NULL";
    private static final String BIT_NOT_NULL_TYPE = " BIT NOT NULL";
    private static final String COMMA_SEP = " ,";
    
    private static final String SQL_CREATE_COLEGIOS = "CREATE TABLE " + ColegioEntry.TABLE_NAME + 
    		" (" + ColegioEntry.COLUMN_NAME_COLEGIO_ID + INT_NOT_NULL_TYPE + " AUTO INCREMENT" + COMMA_SEP +
    		ColegioEntry.COLUMN_NAME_COLEGIO_NOMBRE + VARCHAR_45_NOT_NULL_TYPE + COMMA_SEP +
    		ColegioEntry.COLUMN_NAME_COLEGIO_TELEFONO + VARCHAR_45_NOT_NULL_TYPE + COMMA_SEP + 
    		" PRIMARY KEY (" + ColegioEntry.COLUMN_NAME_COLEGIO_ID + ") );";
    private static final String  SQL_CREATE_CATEGORIAS = "CREATE TABLE " + CategoriasEntry.TABLE_NAME + 
    		" ( " + CategoriasEntry.COLUMN_NAME_CATEGORIA_ID + INT_NOT_NULL_TYPE + " AUTO INCREMENT " + COMMA_SEP +
    		CategoriasEntry.COLUMN_NAME_CATEGORIA_NOMBRE + VARCHAR_45_TYPE + COMMA_SEP +
    		CategoriasEntry.COLUMN_NAME_CATEGORIA_DESCRIPCION + TEXT_TYPE + COMMA_SEP + 
    		" PRIMARY KEY ( " + CategoriasEntry.COLUMN_NAME_CATEGORIA_ID + "));";
    private static final String SQL_CREATE_IMAGENES = "CREATE TABLE " + ImagenesEntry.TABLE_NAME + 
    		" ( " + ImagenesEntry.COLUMN_NAME_IMAGEN_ID + INT_NOT_NULL_TYPE + " AUTO INCREMENT " + COMMA_SEP +
    		ImagenesEntry.COLUMN_NAME_IMAGEN_BLOB + BLOB_NULL_TYPE + COMMA_SEP + 
    		" PRIMARY KEY ( " + ImagenesEntry.COLUMN_NAME_IMAGEN_ID + " ));";
    private static final String SQL_CREATE_SONIDOS = " CREATE TABLE " + SonidosEntry.TABLE_NAME + 
    		" ( " + SonidosEntry.COLUMN_NAME_SONIDO_ID +  INT_NOT_NULL_TYPE + " AUTO INCREMENT " + COMMA_SEP +
    		SonidosEntry.COLUMN_NAME_SONIDO_BLOB + BLOB_NULL_TYPE + COMMA_SEP +
    		" PRIMARY KEY ( " + SonidosEntry.COLUMN_NAME_SONIDO_ID + " ));";
    

	// Creamos el constructor llamando a 
	public AbcLandiaDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}
	


	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
