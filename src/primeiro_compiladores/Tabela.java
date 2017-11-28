package primeiro_compiladores;

import java.util.LinkedList;
import java.util.Queue;

public class Tabela {

	public LinkedList<Token> tokens;
	public static final String IDENTIFICADOR = "identificador";
	
	public static final String NUMERO = "número";

	// palavras reservadas
	public static final String PALAVRA_RESERVADA = "palavra reservada";
	public static final String PALAVRA_RESERVADA_for = "for";
	public static final String PALAVRA_RESERVADA_if = "if";
	public static final String PALAVRA_RESERVADA_else = "else";
	public static final String PALAVRA_RESERVADA_class = "class";
	public static final String PALAVRA_RESERVADA_int = "int";
	public static final String PALAVRA_RESERVADA_float = "float";
	public static final String PALAVRA_RESERVADA_bool = "bool";
	public static final String PALAVRA_RESERVADA_string = "string";
	public static final String PALAVRA_RESERVADA_true = "true";
	public static final String PALAVRA_RESERVADA_false = "false";

	
	//operadores aritmeticos
	public static final String OPERADOR_ARITMETICO_mais = "+";
	public static final String OPERADOR_ARITMETICO_menos = "-";
	public static final String OPERADOR_ARITMETICO_vezes = "*";
	public static final String OPERADOR_ARITMETICO_divido = "/";
	public static final String OPERADOR_ARITMETICO_resto = "%";
	public static final String OPERADOR_ARITMETICO = "operador aritmetico";
	
	//operadores relacionais
	public static final String OPERADOR_RELACIONAL = "operador relacional";
	public static final String OPERADOR_RELACIONAL_maior = ">";
	public static final String OPERADOR_RELACIONAL_menor = "<";
	public static final String OPERADOR_RELACIONAL_maiorOuIgual = ">=";
	public static final String OPERADOR_RELACIONAL_menorOuIgual = "<=";
	public static final String OPERADOR_RELACIONAL_igual = "=";
	private static final String OPERADOR_RELACIONAL_diferente = "!=";
	
	//operadores lógicos
	public static final String OPERADOR_LOGICO = "operador lógico";
	public static final String OPERADOR_LOGICO_e = "&&";
	public static final String OPERADOR_LOGICO_ou = "||";
	public static final String OPERADOR_LOGICO_negado = "!";
	
	public static final String COMENTARIO_DE_LINHA = "comentario de linha";
	public static final String COMENTARIO_DE_BLOCO = "comentario de bloco";
	
	//delimitadores
	public static final String DELIMITADORES = "delimitador";
	public static final String DELIMITADORES_pontoEVirgula = ";";
	public static final String DELIMITADORES_abrirParentese = "(";
	public static final String DELIMITADORES_fecharParentese = ")";
	public static final String DELIMITADORES_abrirChave = "{";
	public static final String DELIMITADORES_fecharChave = "}";
	public static final String DELIMITADORES_abrirColchete = "[";
	public static final String DELIMITADORES_fecharColchete = "]";
	public static final String DELIMITADORES_virgula = ",";
	
	
	public static final String CADEIA_DE_CARACTERES = "cadeia de caracteres";
	public static final String DESCONHECIDO = "desconhecido";
	

	public Tabela() {
		tokens = new LinkedList<>();
	}

	public void addToken(int linha, String lexema, String tipo, boolean mal_formado) {
		
		
		
		Token aux = new Token();
		aux.lexema = lexema;
		
		
		//verifica se é um delimitador
		if(tipo.equals(DELIMITADORES)) {
			if(lexema.equals(DELIMITADORES_abrirChave)) 
				aux.tipo = DELIMITADORES_abrirChave;
			else if(lexema.equals(DELIMITADORES_fecharChave)) 
				aux.tipo = DELIMITADORES_fecharChave;
			
			else if(lexema.equals(DELIMITADORES_abrirParentese)) 
				aux.tipo = DELIMITADORES_abrirParentese;
			else if(lexema.equals(DELIMITADORES_fecharParentese)) 
				aux.tipo = DELIMITADORES_fecharParentese;
			
			else if(lexema.equals(DELIMITADORES_abrirColchete)) 
				aux.tipo = DELIMITADORES_abrirColchete;
			else if(lexema.equals(DELIMITADORES_fecharColchete)) 
				aux.tipo = DELIMITADORES_fecharColchete;
			
			else if(lexema.equals(DELIMITADORES_pontoEVirgula)) 
				aux.tipo = DELIMITADORES_pontoEVirgula;
			else
				aux.tipo = DELIMITADORES_virgula;
		}
		else if(tipo.equals(PALAVRA_RESERVADA)) {
			if(lexema.equals("for"))
				aux.tipo = "for";
			else if(lexema.equals("int"))
				aux.tipo = "int";
			else if(lexema.equals("float"))
				aux.tipo = "float";
			else if(lexema.equals("bool"))
				aux.tipo = "bool";
			else if(lexema.equals("string"))
				aux.tipo = "string";
			else if(lexema.equals("class"))
				aux.tipo = "class";
			else if(lexema.equals("if"))
				aux.tipo = "if";
			else if(lexema.equals("else"))
				aux.tipo = "else";
			else if(lexema.equals("true"))
				aux.tipo = "true"; 
			else if(lexema.equals("false"))
				aux.tipo = "false";
		}
		else if(tipo.equals(OPERADOR_LOGICO)) {
			if(lexema.equals(OPERADOR_LOGICO_e))
				aux.tipo = OPERADOR_LOGICO_e;
			else if(lexema.equals(OPERADOR_LOGICO_ou))
				aux.tipo = OPERADOR_LOGICO_ou;
			else
				aux.tipo = OPERADOR_LOGICO_negado;
					
		}
		else if(tipo.equals(OPERADOR_ARITMETICO)) {
			if(lexema.equals(OPERADOR_ARITMETICO_divido))
				aux.tipo = OPERADOR_ARITMETICO_divido;
			else if(lexema.equals(OPERADOR_ARITMETICO_mais))
				aux.tipo = OPERADOR_ARITMETICO_mais;
			else if(lexema.equals(OPERADOR_ARITMETICO_menos))
				aux.tipo = OPERADOR_ARITMETICO_menos;
			else if(lexema.equals(OPERADOR_ARITMETICO_vezes))
				aux.tipo = OPERADOR_ARITMETICO_vezes;
			else 
				aux.tipo = OPERADOR_ARITMETICO_resto;
		}
		else if(tipo.equals(OPERADOR_RELACIONAL)) {
			if(lexema.equals(OPERADOR_RELACIONAL_igual))
				aux.tipo = OPERADOR_RELACIONAL_igual;
			else if(lexema.equals(OPERADOR_RELACIONAL_diferente))
				aux.tipo = OPERADOR_RELACIONAL_diferente;
			else if(lexema.equals(OPERADOR_RELACIONAL_maior))
				aux.tipo = OPERADOR_RELACIONAL_maior;
			else if(lexema.equals(OPERADOR_RELACIONAL_maiorOuIgual))
				aux.tipo = OPERADOR_RELACIONAL_maiorOuIgual;
			else if(lexema.equals(OPERADOR_RELACIONAL_menor))
				aux.tipo = OPERADOR_RELACIONAL_menor;
			else
				aux.tipo = OPERADOR_RELACIONAL_menorOuIgual;
			
		}else
			aux.tipo = tipo;
		
		
		aux.mal_formado = mal_formado;
		aux.linha = linha;
		tokens.add(aux);
	}
}
