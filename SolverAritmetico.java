public class SolverAritmetico {

    private final Nodo nodo;

    public SolverAritmetico(Nodo nodo) {
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

        if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double){
            switch (n.getValue().tipo){
                case Plus:
                    return ((Double)resultadoIzquierdo + (Double) resultadoDerecho);
                case Hyphen:
                    return ((Double)resultadoIzquierdo - (Double) resultadoDerecho);
                case Star:
                    return ((Double)resultadoIzquierdo * (Double) resultadoDerecho);
                case Slash:
                    return ((Double)resultadoIzquierdo / (Double) resultadoDerecho);
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            if (n.getValue().tipo == TipoToken.Plus){
                // Ejecutar la concatenación
                return ((String)resultadoIzquierdo + (String) resultadoDerecho);
            }
        }
        else{
            System.out.println("Error en tipos de operandos");
        }

        return null;
    }
}
