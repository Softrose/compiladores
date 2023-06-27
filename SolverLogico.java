public class SolverLogico {

    private final Nodo nodo;

    public SolverLogico(Nodo nodo) {
        this.nodo = nodo;
    }

    public Object resolver(){
        return resolver(nodo);
    }
    private Object resolver(Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            if(n.getValue().tipo == TipoToken.NUMBER || n.getValue().tipo == TipoToken.STRING){
                return n.getValue().literal;
            }
            else if(n.getValue().tipo == TipoToken.IDENTIFICADOR){
                if(TablaSimbolos.existeIdentificador((String)n.getValue().lexema)){
                    return TablaSimbolos.obtener((String)n.getValue().lexema);
                }else{
                    System.out.println("No existe la variable " + (String)n.getValue().lexema);
                    return null;
                }
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolver(izq);
        Object resultadoDerecho = resolver(der);

        if(resultadoIzquierdo instanceof Object && resultadoDerecho instanceof Object){
            switch (n.getValue().tipo){
                case LessThan:
                    return ((Double)resultadoIzquierdo < (Double) resultadoDerecho);
                case Less_equal:
                    return ((Double)resultadoIzquierdo <= (Double) resultadoDerecho);
                case GreatherThan:
                    return ((Double)resultadoIzquierdo > (Double) resultadoDerecho);
                case Greather_equal:
                    return ((Double)resultadoIzquierdo >= (Double) resultadoDerecho);
                case Equal_equal:
                    if( Double.compare((Double)resultadoIzquierdo, (Double)resultadoDerecho) == 0){
                        System.out.println("Iguales");
                    }
                    //System.out.println((Double)resultadoIzquierdo);
                    //System.out.println((Double)resultadoDerecho);
                    return ((Double)resultadoIzquierdo == (Double) resultadoDerecho);
                case Exclamation_equal:
                    return ((Double)resultadoIzquierdo != (Double) resultadoDerecho);
            }
        }
        /*else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            if (n.getValue().tipo == TipoToken.Plus){
                // Ejecutar la concatenación
                return ((String)resultadoIzquierdo + (String) resultadoDerecho);
            }
        }*/
        else{
            System.out.println("Error en tipos de operandos");
        }

        return null;
    }
}
