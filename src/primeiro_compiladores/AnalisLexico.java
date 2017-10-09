package primeiro_compiladores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnalisLexico {

	
	public String separaStrings(List<String> str) {
		
		//Como fazer isso agora?
		
		String StringSaida = str;
		return StringSaida;
	}
	
	public List<String> leArquivo() throws IOException {
		
		File file = new File("entrada");
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String line = null;
		List<String> arrayDeLetras = new ArrayList<String>();
		while((line = bufferedReader.readLine()) != null) {
			line = line.trim();
			for (int i = 0; i < line.length(); i++) {
				arrayDeLetras.add(""+line.charAt(i));
			}
		}
		return arrayDeLetras;
	} 
	
	public Token checaExpressoes(String cadeiaDeChar) {
		Token token = null;
		
		 if(cadeiaDeChar.matches("[a-zA- Z] \\w*")) {
			 if(cadeiaDeChar.equals("class") || cadeiaDeChar.equals("final") || cadeiaDeChar.equals("if") || cadeiaDeChar.equals("else") || cadeiaDeChar.equals("for") || cadeiaDeChar.equals("scan") || cadeiaDeChar.equals("print") || cadeiaDeChar.equals("int") || cadeiaDeChar.equals("float") || cadeiaDeChar.equals("bool") || cadeiaDeChar.equals("true") || cadeiaDeChar.equals("false") || cadeiaDeChar.equals("string")) {
				 token.setLexema(cadeiaDeChar);
				 token.setTipo("PALAVRA RESERVADA");
			 }
			 else {
				 token.setLexema(cadeiaDeChar);
				 token.setTipo("IDENTIFICADOR");
			 }
		 }
		 else if(cadeiaDeChar.matches("(-\\\\s)?\\\\d+(\\\\.\\\\d+)?")) {
			 token.setLexema(cadeiaDeChar);
			 token.setTipo("N�MERO");
		 }
		 else if(cadeiaDeChar.matches("//(\\\\w|\\\\s)* | (/\\\\* ( . | \\\\s | \\\\n)* \\\\*/)")) {
			 token.setLexema(cadeiaDeChar);
			 token.setTipo("COMENT�RIO");
		 }
		 else if(cadeiaDeChar.matches("\\\\�( . | \\\\s | \\\\\\� )*\\\\�")) {
			 token.setLexema(cadeiaDeChar);
			 token.setTipo("CADEIA DE CARACTERES");
		 }
		 else if(cadeiaDeChar.matches("(&& | \\|\\| | !)")) {
			 token.setLexema(cadeiaDeChar);
			 token.setTipo("OPERADOR L�GICO");
		 }
		 /*Verificar se os pr�ximos est�o certos
		  *else if(cadeiaDeChar.matches("+ | - | * | \\/ | %")) {
			 token.setLexema(cadeiaDeChar);
			 token.setTipo("OPERADOR ARITM�TICO");
		 }
		 else if(cadeiaDeChar.matches("; | , | \\( | \\) | \\[ | \\] | \\{ | \\} | :")) {
			 token.setLexema(cadeiaDeChar);
			 token.setTipo("DELIMITADORES");
		 }
		 else if(cadeiaDeChar.matches("#N�o fa�o a m�nima#")) {
			 token.setLexema(cadeiaDeChar);
			 token.setTipo("OPERADOR RELACIONAL");
		 } 
		  */
		 else {
			 //Verificar o que colocar
		 }
		 return token;
	}
	
	public void escreveArquivo() throws IOException{
		String str = separaStrings(leArquivo());
		Token tok = checaExpressoes(str);
		String saidaArquivo = tok.toString();
		
		File file = new File("C:/Users/SSENN4/Documents/testeSaida.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(saidaArquivo);
		writer.newLine();
		
		//Criando o conte�do do arquivo
		writer.flush();
		//Fechando conex�o e escrita do arquivo.
		writer.close();
	}
}
