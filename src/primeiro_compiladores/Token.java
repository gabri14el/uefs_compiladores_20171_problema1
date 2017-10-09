package primeiro_compiladores;

public class Token {
	
	String tipo;
	String lexema;
	boolean mal_formado;
	
	
	@Override
	public String toString() {
		return "<"+ lexema+", "+tipo+">";
	}
	
	public String setTipo(String tip) {
		this.tipo = tip;
		return tipo;
	}
	
	public String setLexema(String lex) {
		this.lexema = lex;
		return lexema;
	}

}
