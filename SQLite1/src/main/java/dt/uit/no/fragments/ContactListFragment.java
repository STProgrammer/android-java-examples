package dt.uit.no.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import dt.hin.no.R;
import dt.uit.no.db.ContactDataSource;
import dt.uit.no.entities.Contact;

/*
    Eksemplet viser hvordan man kan lagre kontakter, slette kontakter samt lese alle kontakter fra
    databasen. Data fra databasen koples til ListView vha. en ArrayAdapter.

    Alternativt: Bruk av SimpleCursorAdapter.
    Evt. bruk av ListFragment.
 */
public class ContactListFragment extends Fragment implements OnClickListener, OnItemClickListener {
	private ArrayList<Contact> contactItems = new ArrayList<Contact>();
	private ArrayAdapter<Contact> adapter = null;
	
	private ListView myListView=null;
	private Contact valgtContact =null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mylistfragment, container, false);

        final InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        EditText etFornavn = (EditText)view.findViewById(R.id.etFornavn);
        etFornavn.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))) {

                    ContactListFragment.this.addNewContact();
                    try {
                        //Skjuler tastatur etter trykk på dones:
                        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception e) {
                        //overse...
                    }
                    handled = true;
                }
                return handled;
            }
        });

        Button btnClose = view.findViewById(R.id.btnAvslutt);
        btnClose.setOnClickListener(this);

        Button btnInsert = view.findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(this);

        Button btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        myListView = view.findViewById(R.id.lvKontakter);
        //For å fange opp at brukeren velger/trykker i lista:
        myListView.setOnItemClickListener(this);

        //Setter opp adapteren:
        adapter = new ArrayAdapter<Contact>(this.getActivity(), android.R.layout.simple_list_item_1, contactItems);
        myListView.setAdapter(adapter);

        return view;
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
	}

    //Henter alle kontakter, fyller contactItems:
    public void refreshContactList() {
        ContactDataSource contactDataSource = new ContactDataSource(getActivity().getApplicationContext());
        contactDataSource.getAllContacts(contactItems);
        adapter.notifyDataSetChanged();
    }

    //Legger til en ny kontakt, basert på input fra bruker:
	private void addNewContact() {
		EditText fNavn = (EditText)getActivity().findViewById(R.id.etFornavn);
		String firstName = fNavn.getText().toString();
		EditText etterNavn = (EditText)getActivity().findViewById(R.id.etEtternavn);
		String lastName = etterNavn.getText().toString();
		String fbLink = "www.facebook.com/...";

        ContactDataSource contactDataSource = new ContactDataSource(getActivity().getApplicationContext());
		if (contactDataSource.createContact(lastName, firstName, fbLink)) {
            Toast.makeText(this.getActivity(), firstName + " er lagt til som ny kontakt...", Toast.LENGTH_SHORT).show();
            //Tøm feltene etter at vi har lagret og oppdatert lista:
            fNavn.setText("");
            etterNavn.setText("");
            //Leser innholdet på nytt, fyller lista:
            this.refreshContactList();
        } else {
            Toast.makeText(this.getActivity(), "Fikk ikke lagt til ny kontakt...", Toast.LENGTH_SHORT).show();
        }
	}

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Vis alle kontakter:
        this.refreshContactList();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //Sletter valgt kontakt:
	private void deleteContact() {
		if (valgtContact !=null) {

            ContactDataSource contactDataSource = new ContactDataSource(getActivity().getApplicationContext());
			contactDataSource.deleteContact(valgtContact);
			//Tøm feltene:
			EditText fNavn = (EditText)getActivity().findViewById(R.id.etFornavn);
			EditText etterNavn = (EditText)getActivity().findViewById(R.id.etEtternavn);
			fNavn.setText("");
			etterNavn.setText("");
			Toast.makeText(this.getActivity(), valgtContact.getForNavn() + " er fjernet fra kontakter...", Toast.LENGTH_SHORT).show();

            //Leser innholdet på nytt, fyller lista:
            this.refreshContactList();
		} else {
			Toast.makeText(this.getActivity(), "Velg en kontakt som skal slettes...", Toast.LENGTH_SHORT).show();
		}
		valgtContact =null;
	}

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnInsert:
                this.addNewContact();
                break;

            case R.id.btnDelete:
                this.deleteContact();
                break;

            case R.id.btnAvslutt:
                this.getActivity().finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
        // TODO Auto-generated method stub
        valgtContact = contactItems.get(position);

        EditText fNavn = (EditText)getActivity().findViewById(R.id.etFornavn);
        fNavn.setText(valgtContact.getForNavn());

        EditText etterNavn = (EditText)getActivity().findViewById(R.id.etEtternavn);
        etterNavn.setText(valgtContact.getEtterNavn());
    }
}
