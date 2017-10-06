package primeiro_compiladores;

public class Token {
	
	String tipo;
	String lexema;
	boolean mal_formado;
	
	
	@Override
	public String toString() {
		return "<"+ lexema+", "+tipo+">";
	}

}
