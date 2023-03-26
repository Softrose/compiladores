//package mx.ipn.escom.compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;
    private String lexema = "";
    private char caracter;
    private String buscarSimbolo = "";
    private String simboloCompuesto = "";
    private boolean leyendoCadena = false;
    private String literal = "";
    private boolean esCadena = false;
    private int longitud;
    private boolean comentario = false;
    private boolean comentarioLargo = false;

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

    private static final Map<String, TipoToken> simbolos;
    static {
        simbolos = new HashMap<>();
        simbolos.put("(", TipoToken.Open_parent);
        simbolos.put(")", TipoToken.Close_parent);
        simbolos.put("{", TipoToken.Open_curly);
        simbolos.put("}", TipoToken.Close_curly);
        simbolos.put(",", TipoToken.Comma);
        //simbolos.put(".", TipoToken.Dot);
        simbolos.put(";", TipoToken.Dot_comma);
        simbolos.put("-", TipoToken.Hyphen);
        simbolos.put("+", TipoToken.Plus);
        simbolos.put("*", TipoToken.Star);
        //simbolos.put("/", TipoToken.Slash);
        simbolos.put("!", TipoToken.ExclamationMark);
        simbolos.put("!=", TipoToken.Exclamation_equal);
        simbolos.put("=", TipoToken.Equal);
        simbolos.put("==", TipoToken.Equal_equal);
        simbolos.put("<", TipoToken.LessThan);
        simbolos.put("<=", TipoToken.Less_equal);
        simbolos.put(">", TipoToken.GreatherThan);
        simbolos.put(">=", TipoToken.Greather_equal);
    }


    Scanner(String source){
        this.source = source;
    }

    List<Token> scanTokens(){
        //Aquí va el corazón del scanner <3.
        source = source + " \n";
        longitud = source.length();
        
        for(int x = 0; x < longitud; x++){
            caracter = source.charAt(x);
            buscarSimbolo = caracter + "";
            
            //Si espacio o salto de linea lexema termina
            System.out.println(lexema);
            if((caracter == ' ' || caracter == '\n') && !leyendoCadena && !comentario && !comentarioLargo && lexema != ""){
                if(caracter == '\n' && lexema.length() == 1){
                }else{
                TipoToken tipo = palabrasReservadas.get(lexema);
                    if(isNumeric(literal)){
                        tipo = TipoToken.Number;
                    }else if (esCadena) {
                        tipo = TipoToken.String;
                        esCadena = false;
                    }else if (tipo == null){
                        tipo = TipoToken.Identifier;
                    }
                    Token token = new Token(tipo, lexema, literal, linea);

                    tokens.add(token);
                }
                    
                
                lexema = "";
                literal = "";
            //Si encuentro un simbolo
            /*}else if((simbolos.containsKey(buscarSimbolo)) && !leyendoCadena && !comentario && !comentarioLargo && lexema != ""){

                if(caracter == '\n' && lexema.length() == 1){
                }else{
                TipoToken tipo = palabrasReservadas.get(lexema);
                    if(isNumeric(literal)){
                        tipo = TipoToken.Number;
                    }else if (esCadena) {
                        tipo = TipoToken.String;
                        esCadena = false;
                    }else if (tipo == null){
                        tipo = TipoToken.Identifier;
                    }
                    Token token = new Token(tipo, lexema, literal, linea);

                    tokens.add(token);
                    
                }
                
                simboloCompuesto = buscarSimbolo + source.charAt(x+1);
                
                if(simbolos.containsKey(simboloCompuesto)){
                    TipoToken tipoC = simbolos.get(simboloCompuesto);

                    Token tokenC = new Token(tipoC, simboloCompuesto, null, linea);

                    tokens.add(tokenC);
                    x = x + 1;
                
                }else{
                    TipoToken tipoS = simbolos.get(buscarSimbolo);

                    Token tokenS = new Token(tipoS, buscarSimbolo, null, linea);

                    tokens.add(tokenS);
                }
                lexema = "";
                literal = "";
            }else if(caracter == '"'){
                esCadena = true;
                if (leyendoCadena == true) {
                    leyendoCadena = false;
                    lexema =  lexema + '"';
                }else{
                    leyendoCadena = true;
                    lexema =  '"' + lexema ;
                }
            }else if(caracter == '/'){
                if(source.charAt(x+1) == '/'){
                    comentario = true;
                }else if(source.charAt(x+1) == '*'){
                    comentarioLargo = true;
                }
            }else if(caracter == '*'){
                if(source.charAt(x+1) == '/'){
                    comentarioLargo = false;
                }*/
            }else{
                lexema = lexema + "" + caracter;
                literal = literal + "" + caracter;
            }

            if(caracter == '\n'){
                comentario = false;
                linea = linea + 1;
            }
        }

        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
        tokens.add(new Token(TipoToken.EOF, "", null, linea));

        return tokens;
    }


    public static boolean isNumeric(String cadena) {

        boolean resultado;

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