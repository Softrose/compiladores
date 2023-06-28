//Analizador Sintáctico 
//ESCOM 2023
//Grupo 3CV13
//Creado por:
//Soledad Hernandez Karen - Hezarelez@gmail.com / ksoledadh1000@alumno.ipn.mx
//Rosete Chávez Raúl - Raulrrc96@gmail.com / rrosetec1400@alumno.ipn.mx

import java.util.ArrayList;
import java.util.List;

public class Arbol {

    private Object res;
    private SolverAritmetico solver;
    private SolverLogico solverLogico;
    private Nodo val;
    private boolean ejecutarElse;
    private Nodo identif;

    private final Nodo raiz;
    private Arbol programa;
    //TablaSimbolos tabla = new TablaSimbolos();

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){

        
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case Plus:
                case Hyphen:
                case Star:
                case Slash:
                    solver = new SolverAritmetico(n);
                    res = solver.resolver();
                    System.out.println(res);
                break;
                case Equal:
                    identif = n.getHijos().get(0);
                    val = n.getHijos().get(1);                   
                    solver = new SolverAritmetico(val);
                    res = solver.resolver();
                    
                    if(TablaSimbolos.existeIdentificador((String)identif.getValue().lexema)){
                        TablaSimbolos.asignar((String)identif.getValue().lexema, res);
                    }else{
                        System.out.println("No existe la variable " + (String)identif.getValue().lexema);
                    }
                    break;
                case VAR:
                    identif = n.getHijos().get(0);
                    if(n.getHijos().size() > 1){
                        val = n.getHijos().get(1);
                        solver = new SolverAritmetico(val);
                        res = solver.resolver();
                    }else{
                        val = null;
                    }

                    //Asignar identificador y valor a tabla de simbolos

                    if(TablaSimbolos.existeIdentificador((String)identif.getValue().lexema)){
                        System.out.println("La variable ya ha sido declarada anteriormente: " 
                            + (String)identif.getValue().lexema);
                    }else{
                         TablaSimbolos.asignar((String)identif.getValue().lexema, res);
                    }
                    break;
                case IF:
                    solver = new SolverAritmetico(n.getHijos().get(0));
                    res = solver.resolver();

                    if((boolean)res){
                        
                        //Aquí meter lo de dentro del While
                        
                        programa = new Arbol(n);
                        programa.recorrer();
                    }else{
                        if(n.getHijos().size() > 2){
                            Nodo existElse = n.getHijos().get(2);
                            programa = new Arbol(existElse);
                            programa.recorrer();
                        }
                    }
                    break;
                /*case ELSE:
                        programa = new Arbol(n);
                        programa.recorrer();
                    break;*/
                case PRINT:
                    Nodo print = n.getHijos().get(0);
                    solver = new SolverAritmetico(print);
                    res = solver.resolver();
                    System.out.println(res);
                    break;
                case WHILE:
                    solver = new SolverAritmetico(n.getHijos().get(0));
                    res = solver.resolver();

                    while((boolean)res){
                        
                        //Aquí meter lo de dentro del While
                        
                        programa = new Arbol(n);
                        programa.recorrer();

                        solver = new SolverAritmetico(n.getHijos().get(0));
                        res = solver.resolver();
                    }
                    break;
                case FOR:
                    solver = new SolverAritmetico(n.getHijos().get(0));
                    res = solver.resolver();
                    int x = 0;
                    while((boolean)res){
                        
                        //Aquí meter lo de dentro del While
                        
                        programa = new Arbol(n);
                        programa.recorrer();

                        solver = new SolverAritmetico(n.getHijos().get(0));
                        res = solver.resolver();
                    }
                    break;
            }
        }
    }

}

