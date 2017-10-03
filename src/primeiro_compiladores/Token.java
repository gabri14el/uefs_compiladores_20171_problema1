package primeiro_compiladores;

public class Token {
	
	String tipo;
	String lexema;
	
	
	@Override
	public String toString() {
		return "<"+ lexema+", "+tipo+">";
	}

}
