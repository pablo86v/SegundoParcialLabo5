package com.example.segundoparcial;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.personas = new ArrayList<PersonaModel>();

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
        TextView tv = findViewById(R.id.tvJSONArray);
        tv.setText(getJsonPersonas(this.personas).toString());
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
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
}