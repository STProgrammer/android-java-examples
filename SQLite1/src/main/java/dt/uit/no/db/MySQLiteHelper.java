package dt.uit.no.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// MySQLiteHelper sørger for å opprette databasen om den ikke allerede finnes.
public class MySQLiteHelper extends SQLiteOpenHelper {
	//Singelton pattern:
	private static MySQLiteHelper instance=null;

	//Databasespesifikt:
	private static final String DATABASE_NAME = "MinKontaktDB1.db";
	private static final int DATABASE_VERSION = 2;

	//Bruker singelton pattern slik at det alltid kun eksisterer EN instans av denne klassen:
	public static synchronized MySQLiteHelper getInstance(Context context) {
		if (instance == null) {
			instance = new MySQLiteHelper(context.getApplicationContext());
		}
		return instance;
	}

	//NB! Privte constructor: Kall på super(...) trigger enten onCreate() eller onUpdate()
	private MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// Kalles når databasen ikke eksisterer og må opprettes
	@Override
	public void onCreate(SQLiteDatabase database) {
		ContactTable.onCreate(database);
		//OtherTable1.onCreate(database);
		//OtherTable2.onCreate(database); osv...
	}

	// Kalles ved behov for oppgradering, dvs. mismatch mellom ny og gammel versjon
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        ContactTable.onUpgrade(database, oldVersion, newVersion);
        // OtherTable1.onUpgrade(database, oldVersion, newVersion);
        // OtherTable2.onUpgrade(database, oldVersion, newVersion); osv...
	}
}
