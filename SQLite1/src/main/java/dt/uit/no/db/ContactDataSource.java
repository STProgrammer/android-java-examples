package dt.uit.no.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dt.uit.no.entities.Contact;

//Denne klassen gir tilgang til databasen og kontakttabellen via metoder som createContact(), deleteContact() osv.
//Disse bruker igjen SQLiteDatabase-metoder som insert(), delete() osv.
public class ContactDataSource {

	private MySQLiteHelper mySQLiteHelper=null;

	// MEDLEMMER:
	public String[] contactColumns = {
			ContactTable.CONTACT_COL_ID,
			ContactTable.CONTACT_COL_LASTNAME,
			ContactTable.CONTACT_COL_FIRSTNAME,
			ContactTable.CONTACT_COL_FBLINK
	};

	//Konstruktør: MySQLiteHelper sørger for å opprette databasen om den ikke allerede finnes.
	public ContactDataSource(Context context) {
		mySQLiteHelper = MySQLiteHelper.getInstance(context);
	}

	//Tom konstruktør (vil ikke at denne konstruktøren skal være tilgjengelig):
	private ContactDataSource() {}

	//Oppretter en ny kontakt i databasen:
	public boolean createContact(String lastName, String firstName, String fbLink) {

		ContentValues values = new ContentValues();
		values.put(ContactTable.CONTACT_COL_LASTNAME, lastName);
		values.put(ContactTable.CONTACT_COL_FIRSTNAME, firstName);
		values.put(ContactTable.CONTACT_COL_FBLINK, fbLink);

		//Databasen opprettes om den ikke finnes.
		SQLiteDatabase database = mySQLiteHelper.getWritableDatabase();
		long insertId = database.insert(ContactTable.CONTACT_TABLE, null, values);
		database.close();
		return insertId > -1;
	}

    //Sletter en gitt kontakt:
	public void deleteContact(Contact contact) {
		long id = contact.getId();
		SQLiteDatabase database = mySQLiteHelper.getWritableDatabase();
		database.delete(ContactTable.CONTACT_TABLE, ContactTable.CONTACT_COL_ID + " = " + id, null);
		database.close();
	}

    public void getAllContacts(ArrayList<Contact> allContacts) {

		SQLiteDatabase database = mySQLiteHelper.getWritableDatabase();
        Cursor cursor = database.query(ContactTable.CONTACT_TABLE, contactColumns, null, null, null, null, null);
        cursor.moveToFirst();
        allContacts.clear();
        while (!cursor.isAfterLast()) {
            allContacts.add(cursorToContact(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        database.close();

    }

	public Contact cursorToContact(Cursor cursor) {

		Contact contact = new Contact();
		int keyIndex = cursor.getColumnIndexOrThrow(ContactTable.CONTACT_COL_ID);
		int lastNameIndex = cursor.getColumnIndexOrThrow(ContactTable.CONTACT_COL_LASTNAME);
		int firstNameIndex = cursor.getColumnIndexOrThrow(ContactTable.CONTACT_COL_FIRSTNAME);
		int fbLinkIndex = cursor.getColumnIndexOrThrow(ContactTable.CONTACT_COL_FBLINK);

		contact.setId(cursor.getLong(keyIndex));
		contact.setEtterNavn(cursor.getString(lastNameIndex));
		contact.setForNavn(cursor.getString(firstNameIndex));
		contact.setFbLink(cursor.getString(fbLinkIndex));
		return contact;
	}


	//Henter kontakter basert på for/etter-navn vha. rawQuery():
	public void getContactsByName(ArrayList<Contact> contacts, String searchString) {

		SQLiteDatabase database = mySQLiteHelper.getWritableDatabase();
		searchString = "%" + searchString + "%";
		Cursor cursor = database.rawQuery("select * from " + ContactTable.CONTACT_TABLE + " where " + ContactTable.CONTACT_COL_FIRSTNAME + " like ? or " + ContactTable.CONTACT_COL_LASTNAME + " like ? ", new String[] { searchString, searchString });
		cursor.moveToFirst();
		contacts.clear();
		while (!cursor.isAfterLast()) {
			contacts.add(cursorToContact(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		database.close();
	}

}
