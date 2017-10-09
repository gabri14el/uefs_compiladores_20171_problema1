package primeiro_compiladores;

import java.util.LinkedList;
import java.util.Queue;

public class Tabela {
	
	public LinkedList<Token> tokens;
	
	public static final String IDENTIFICADOR = "identificador";
	public static final String PALAVRA_RESERVADA = "palavra reservada";
	public static final String NUMERO = "número";
	public static final String OPERADOR_ARITMETICO = "operador aritmetico";
	public static final String OPERADOR_RELACIONAL = "operador relacional";
	public static final String OPERADOR_LOGICO = "operador aritmetico";
	public static final String COMENTARIO_DE_LINHA = "comentario de linha";
	public static final String COMENTARIO_DE_BLOCO = "comentario de bloco";
	public static final String DELIMITADORES = "delimitadores";
	public static final String CADEIA_DE_CARACTERES = "cadeia de caracteres";
	public static final String DESCONHECIDO = "desconhecido";
	
	
	
	public Tabela() {
		tokens = new LinkedList<>();
	}
	
	public void addToken(String lexema, String tipo, boolean mal_formado) {
		Token aux = new Token(); 
		aux.lexema = lexema;
		aux.tipo = tipo; 
		aux.mal_formado = mal_formado;
		tokens.add(aux);
	}
}
