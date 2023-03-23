//package mx.ipn.escom.compiladores;

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)


    // Palabras clave:
    And, Class, Else, False, For, Fun, If, Null, 
    Or, Print, Return, Super, This, True, Var, While,  


    // Simbolos
    Open_parent, Close_parent, Open_curly, Close_curly,
    Comma, Dot, Dot_comma, Hyphen, Plus, Star, Slash,
    ExclamationMark, Exclamation_equal, Equal, Equal_equal,
    LessThan, Less_equal, GreatherThan, Greather_equal,

    // Extras
    Identifier, String, Number,

    // Final de cadena
    EOF
}
