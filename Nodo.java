//Analizador Sintáctico 
//ESCOM 2023
//Grupo 3CV13
//Creado por:
//Soledad Hernandez Karen - Hezarelez@gmail.com / ksoledadh1000@alumno.ipn.mx
//Rosete Chávez Raúl - Raulrrc96@gmail.com / rrosetec1400@alumno.ipn.mx

import java.util.ArrayList;
import java.util.List;

public class Nodo {
    private final Token value;
    private List<Nodo> hijos;

    public Nodo(Token value){
        this.value = value;
    }

    public void insertarHijo(Nodo n){
        if(hijos == null){
            hijos = new ArrayList<>();
            hijos.add(n);
        }
        else{
            hijos.add(0, n);
        }
    }

    public void insertarSiguienteHijo(Nodo n){
        if(hijos == null){
            hijos = new ArrayList<>();
            hijos.add(n);
        }
        else{
            hijos.add(n);
        }
    }

    public void insertarHijos(List<Nodo> nodosHijos){
        if(hijos == null){
            hijos = new ArrayList<>();
        }

        for(Nodo n : nodosHijos){
            hijos.add(n);
        }
    }

    public Token getValue(){
        return value;
    }

    public List<Nodo> getHijos(){
        return hijos;
    }
}
