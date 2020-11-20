package com.example.segundoparcial;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Dialog.NameDialogListener , SearchView.OnQueryTextListener  {
    public List<PersonaModel> personas;
    private List<PersonaModel> personasCopy = new ArrayList<>();
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.prefs= getSharedPreferences("personas", Context.MODE_PRIVATE);
        String personasStr = this.prefs.getString("personaNueva","[]");

        this.personas = new ArrayList<PersonaModel>();

        this.personas.addAll(PersonaModel.generarLista(personasStr));

        this.personasCopy.addAll(personas);

        this.prefs= getSharedPreferences("personas", Context.MODE_PRIVATE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Segundo Parcial");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem((R.id.opSearch));
        SearchView search = (SearchView) item.getActionView();
        search.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.op1) {
            openDialog();
        }else if(item.getItemId() == android.R.id.home){
            super.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialog() {
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(),"Nuevo contacto");
    }

    @Override
    public void actualizarLista(PersonaModel persona) {
        this.personas.add(persona);
        this.personasCopy.clear();
        this.personasCopy.addAll(personas);
        TextView tv = findViewById(R.id.tvJSONArray);
        tv.setText(getJsonPersonas(this.personas).toString());

        SharedPreferences.Editor edit = this.prefs.edit();
        edit.putString("personaNueva" , getJsonPersonas(this.personas).toString());
        edit.commit();

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        filter(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private JSONArray getJsonPersonas(List<PersonaModel> lista) {
        JSONArray jsonArray = new JSONArray();
        for (int i=0; i < lista.size(); i++) {
            jsonArray.put(lista.get(i).getJSONObject());
        }
        return jsonArray;
    }

    public void filter(String text) {
        boolean encontrada = false;
        if(text.isEmpty()){
            personas.clear();
            personas.addAll(personasCopy);
        } else{
            List<PersonaModel> result = new ArrayList<>();
            text = text.toLowerCase();
            for(PersonaModel item: personasCopy){
                if(item.getNombre().toLowerCase().contains(text)){
                    result.add(item);
                    showDialog("Persona encontrada","El teléfono es " + item.getTelefono());
                    encontrada = true;
                    break;
                }
            }

            if(!encontrada){
                showDialog("No encontrada","La persona que busco no está dentro de la lista" );
            }
            personas.clear();
            personas.addAll(result);
        }
    }

    private void showDialog(String title, String message){
        GenericDialog dialog = new GenericDialog(title,message);
        dialog.show(getSupportFragmentManager(), "test");
    }

}