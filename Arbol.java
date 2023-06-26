import java.util.ArrayList;
import java.util.List;

public class Arbol {

    private Object res;
    private SolverAritmetico solver;
    private Nodo val;
    private Nodo identif;

    private final Nodo raiz;
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
                    break;
                case PRINT:
                    Nodo print = n.getHijos().get(0);
                    solver = new SolverAritmetico(print);
                    res = solver.resolver();
                    System.out.println(res);
                    break;

            }
        }
    }

}

