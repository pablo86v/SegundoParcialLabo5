package com.example.segundoparcial;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class PersonaModel {
    private String nombre;
    private int telefono;

    public PersonaModel(){}

    public PersonaModel(String nombre, int telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonaModel that = (PersonaModel) o;
        return telefono == that.telefono &&
                nombre.equals(that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, telefono);
    }

    @Override
    public String toString() {
        return "PersonaModel{" +
                "nombre='" + nombre + '\'' +
                ", telefono=" + telefono +
                '}';
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("nombre", nombre);
            obj.put("telefono", telefono);
        } catch (JSONException e) {
            Log.d("error:",e.toString());
        }
        return obj;
    }
}
