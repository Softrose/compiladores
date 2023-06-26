public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;

    final int posicion;

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.posicion = 0;
        this.literal = null;
    }

    public Token(TipoToken tipo, String lexema, int posicion) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.posicion = posicion;
        this.literal = null;
    }

    public Token(TipoToken tipo, String lexema, int posicion, Object literal) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.posicion = posicion;
        this.literal = literal;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token)) {
            return false;
        }

        if(this.tipo == ((Token)o).tipo){
            return true;
        }

        return false;
    }

    public String toString(){
        return tipo + " " + lexema + " " + (literal == null ? " " : literal.toString());
    }


    public boolean esOperando(){
        switch (this.tipo){
            case IDENTIFICADOR:
            case STRING:
            case NUMBER:
                return true;
            default:
                return false;
        }
    }

  public boolean esOperador(){
        switch (this.tipo){
            case Plus:
            case Hyphen:
            case Star:
            case Slash:
            case Exclamation_equal:
            case Less_equal:
            case Equal_equal:
            case LessThan:
            case GreatherThan:
            case Greather_equal:  
            case AND:
            case OR:
            case Equal:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case VAR:
            case IF:
            case FOR:
            case WHILE:
            case RETURN:
            case SUPER:
            case FUN:
            case CLASS:
            case TRUE:
            case FALSE:
            case THIS:
            case NULL:
            case CALL:
            case PRINT:
            case ELSE:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case IF:
            case FOR:
            case WHILE:
            case ELSE:
                return true;
            default:
                return false;
        }
    }

    public boolean precedenciaMayorIgual(Token t){
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    private int obtenerPrecedencia(){
        switch (this.tipo){
            case Star:
            case Slash:
                return 7;
            case Plus:
            case Hyphen:
                return 6;
            case LessThan:
            case Less_equal:
            case GreatherThan:
            case Greather_equal:
                return 5;
            case Equal_equal:
            case Exclamation_equal:
                return 4;
            case AND:
                return 3; 
            case OR:
                return 2;
            case Equal:
                return 1;
        }

        return 0;
    }

    public int aridad(){
        switch (this.tipo) {
            case Star:
            case Slash:
            case Plus:
            case Hyphen:
            case LessThan:
            case Less_equal:
            case GreatherThan:
            case Greather_equal:
            case Equal_equal:
            case Exclamation_equal:
            case AND:
            case OR:
            case Equal:
                return 2;
        }
        return 0;
    }
}
