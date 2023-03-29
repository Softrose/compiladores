//package mx.ipn.escom.compiladores;
//Delcaracion de librerias
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    //Cadena de entrada
    private String source;
    //Declaracion de lista de tokens
    private final List<Token> tokens = new ArrayList<>();
    //Linea actual que se escanea
    private int linea = 1;
    private String lexema = "";
    //Caracter actual leido
    private char caracter;
    //Variable para buscar simbolos
    private String buscarSimbolo = "";
    //Variable para buscar simbolos compuestos
    private String simboloCompuesto = "";
    //Variable para leer cadena completa con espacios
    private boolean leyendoCadena = false;
    //Valor del lexema
    private String literal = "";
    //Variable para saber si el lexema es cadena
    private boolean esCadena = false;
    //Longitud de la entrada
    private int longitud;
    //Variables para desactivar lectura de lexema
    private boolean comentario = false;
    private boolean comentarioLargo = false;

    //Hash Map con Palabras reservadas
    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("And", TipoToken.And);
        palabrasReservadas.put("Class", TipoToken.Class);
        palabrasReservadas.put("Else", TipoToken.Else);
        palabrasReservadas.put("False", TipoToken.False);
        palabrasReservadas.put("For", TipoToken.For);
        palabrasReservadas.put("Fun", TipoToken.Fun); //definir funciones
        palabrasReservadas.put("If", TipoToken.If);
        palabrasReservadas.put("Null", TipoToken.Null);
        palabrasReservadas.put("Or", TipoToken.Or);
        palabrasReservadas.put("Print", TipoToken.Print);
        palabrasReservadas.put("Return", TipoToken.Return);
        palabrasReservadas.put("Super", TipoToken.Super);
        palabrasReservadas.put("This", TipoToken.This);
        palabrasReservadas.put("True", TipoToken.True);
        palabrasReservadas.put("Var", TipoToken.Var); //definir variables
        palabrasReservadas.put("While", TipoToken.While);
    }

    //Hash Map con Simbolos especiales
    private static final Map<String, TipoToken> simbolos;
    static {
        simbolos = new HashMap<>();
        simbolos.put("(", TipoToken.Open_parent);
        simbolos.put(")", TipoToken.Close_parent);
        simbolos.put("{", TipoToken.Open_curly);
        simbolos.put("}", TipoToken.Close_curly);
        simbolos.put(",", TipoToken.Comma);
        simbolos.put(";", TipoToken.Dot_comma);
        simbolos.put("-", TipoToken.Hyphen);
        simbolos.put("+", TipoToken.Plus);
        simbolos.put("!", TipoToken.ExclamationMark);
        simbolos.put("!=", TipoToken.Exclamation_equal);
        simbolos.put("=", TipoToken.Equal);
        simbolos.put("==", TipoToken.Equal_equal);
        simbolos.put("<", TipoToken.LessThan);
        simbolos.put("<=", TipoToken.Less_equal);
        simbolos.put(">", TipoToken.GreatherThan);
        simbolos.put(">=", TipoToken.Greather_equal);
    }

    //Metodo constructor recibe como argumento la entrada de texto
    Scanner(String source){
        this.source = source;
    }

    //Metodo principal que regresa una lista de tokens
    List<Token> scanTokens(){
        //Aquí va el corazón del scanner <3.
        //Agrego un salto de linea para leer el ultimo lexema
        source = source + "\n \n";
        //Asigno la cantidad de caracteres en la entrada a longitud
        longitud = source.length();
        
        //Realizo un for para leer todos los caracteres de la entrada
        for(int x = 0; x < longitud; x++){
            //Asigno el caracter actual a la variable caracter
            caracter = source.charAt(x);
            //Asigno el caracter a la variable buscarSimbolo y la concateno con una 
            //cadena vacia para convertirlo en una cadena
            buscarSimbolo = caracter + "";
            
            //Si espacio o salto de linea lexema termina
            //No crea tokens si es cadena o comentario
            if((caracter == ' ' || caracter == '\n') && !leyendoCadena && !comentario && !comentarioLargo){
                //Si el lexema esta vacio o es salto de linea no lo agrega
              if(caracter == '\n' && lexema.length() > 0){
                    lexema = lexema.substring(0, lexema.length() - 1);
                    literal = literal.substring(0, literal.length() - 1);
                }
              if(lexema == "" && lexema.length() <= 1){
              }else{
                //Busco el lexema en palabras reservadas
                TipoToken tipo = palabrasReservadas.get(lexema);
                //Verifica si es numero, cadena o por defecto identificador
                    if(isNumeric(literal)){
                        tipo = TipoToken.Number;
                    }else if (esCadena) {
                        tipo = TipoToken.String;
                        esCadena = false;
                    }else if (tipo == null){
                        tipo = TipoToken.Identifier;
                    }
                    //Crea el token
                    Token token = new Token(tipo, lexema, literal, linea);
                    //Agrega el token a la lista de tokens
                    tokens.add(token);
                    
                }
                //Limpio el lexema y la literal para no acumular basura
                lexema = "";
                literal = "";
            //Si encuentro un simbolo y no estoy leyendo cadena y no es comentario
            //Creo 1 o 2 tokens dependiendo el caso, si el lexema ya tenia previamente 
            //Alguna lectura, crea 1 token + 1 token por el simbolo leido
            }else if((simbolos.containsKey(buscarSimbolo)) && !leyendoCadena && !comentario && !comentarioLargo){
                //Si el lexema esta vacio o es salto de linea no lo agrega
                if(caracter == '\n' && lexema.length() > 0){
                    lexema = lexema.substring(0, lexema.length() - 1);
                    literal = literal.substring(0, literal.length() - 1);
                }
                if(lexema == "" && lexema.length() <= 1){
                  }else{
                //Busco el lexema en palabras reservadas
                TipoToken tipo = palabrasReservadas.get(lexema);
                //Verifica si es numero, cadena o por defecto identificador
                    if(isNumeric(literal)){
                        tipo = TipoToken.Number;
                    }else if (esCadena) {
                        tipo = TipoToken.String;
                        esCadena = false;
                    }else if (tipo == null){
                        tipo = TipoToken.Identifier;
                    }
                    //Crea el token
                    Token token = new Token(tipo, lexema, literal, linea);
                    //Agrega el token a la lista de tokens
                    tokens.add(token);
                }
                //Le agrego el caracter siguiente al simbolo para verificar si es compuesto
                simboloCompuesto = buscarSimbolo + source.charAt(x+1);
                //Busco si es un simbolo compuesto
                if(simbolos.containsKey(simboloCompuesto)){
                    //Creo el tipo del simbolo compuesto
                    TipoToken tipoC = simbolos.get(simboloCompuesto);
                    //Crea el token
                    Token tokenC = new Token(tipoC, simboloCompuesto, null, linea);
                    //Agrego el token a la lista de tokens
                    tokens.add(tokenC);
                    //Avanzo el caracter actual en 1 posicion para no volver a leer el 
                    //mismo simbolo
                    x = x + 1;
                
                }else{
                    //Si es un simbolo normal obteno el tipo
                    TipoToken tipoS = simbolos.get(buscarSimbolo);
                    //Crea el Token
                    Token tokenS = new Token(tipoS, buscarSimbolo, null, linea);
                    //Agrego el token a la lista de tokens
                    tokens.add(tokenS);
                }
                //Limpio el lexema y la literal para no acumular basura
                lexema = "";
                literal = "";
            //Reviso si el caracter es el comienzo o fin de cadena
            }else if(caracter == '"'){
                //Activo la variable para indicar que es cadena el tipo de lexema
                esCadena = true;
                //Si estoy leyendo cadena cambio a falso y si es falso cambio a verdadero
                if (leyendoCadena == true) {
                    leyendoCadena = false;
                    lexema =  lexema + '"';
                }else{
                    leyendoCadena = true;
                    lexema =  '"' + lexema ;
                }
            //Reviso si comienza un comentario o comentario largo
            }else if(caracter == '/'){
                if(source.charAt(x+1) == '/'){
                    comentario = true;
                }else if(source.charAt(x+1) == '*'){
                    comentarioLargo = true;
                }
            //Reviso si es fin de comentario largo
            }else if(caracter == '*'){
                if(source.charAt(x+1) == '/'){
                    comentarioLargo = false;
                }
            }else{
                //No leo lexemas en caso de ser comentario
                if(comentario || comentarioLargo){
                }else{
                    //Agrego el caracter actual al lexema y literal
                    lexema = lexema + "" + caracter;
                    literal = literal + "" + caracter;
                }
            }
            //Si es salto de linea sumo el contador y termino el comentario de una linea
            if(caracter == '\n'){
                linea = linea + 1;
                comentario = false;
            }
        }

        //Agrego el fin de cada a los tokens
        tokens.add(new Token(TipoToken.EOF, "", null, linea));

        return tokens;
    }

    //Funcion para comprobar si una cade es un numero
    public static boolean isNumeric(String cadena) {

        boolean resultado;

        //Si puedo convertir cadena en numero regreso verdadero y sino falso
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

}

/*
Signos o símbolos del lenguaje:
(
)
{
}
,
.
;
-
+
*
/
!
!=
=
==
<
<=
>
>=
// -> comentarios (no se genera token)
/* ... * / -> comentarios (no se genera token)
Identificador,
Cadena
Numero
Cada palabra reservada tiene su nombre de token

 */