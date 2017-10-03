package primeiro_compiladores;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AnalisadorLexico {
	
	Queue<Character> buffer = new LinkedList<>();
	int k = 0; //ultimo caractere lido no texto
	int linha = 1;
	char[] texto;
	
	
	String regex_letras = "[a-zA-Z]";
	String regex_numeros = "[0-9]";	
	public static void main (String[] argv){
		AnalisadorLexico b= new AnalisadorLexico();
		b.start();
	}
	
	public void start(){
		File diretorio = new File("entrada");
		for (File f : diretorio.listFiles()) { //percorre todos os arquivos do diretório
			System.out.println("arquivo:" + f.getName());
			try {
				byte[] encoded = Files.readAllBytes(f.toPath());
				String aux = new String(encoded);
				
				System.out.println(aux);
				texto = aux.toCharArray(); //transforma string em array de char
				for(k = 0; k < texto.length; k++){ //percorre o texto caracter a caractere
					if(texto[k] == '\n' || texto[k] == '\r') linha++;//controle de linha, vai que use né?? 
					
					else if(texto[k] == '\t' || texto[k] == ' '); //ignorar os espaços
					
					else if(texto[k] == '-') seHifenAparecer();
					
					else if(texto[k] == '/'); //método da barra
					
					else if(texto[k] == '&' || texto[k] == '|'); //método operador relacional eu acho 
					
					else if(texto[k] == '='); //método algum operador que eu tenho que ver
					
					else if(texto[k] == '<' || texto[k] == '>'); //método operador relacional 
					
					else if(texto[k] == '"'); //cadeia de caracteres
					
					else if(texto[k] == '+' || texto[k] == '*' || texto[k] == '%');
					
					else if(texto[k] == '!');
					
					else if(eDelimitador());
					
					else if(Character.isLetter(texto[k])) {
						buffer.add(texto[k]);
						k++;
						criarIdentificador();
					}
					
					else if(Character.isDigit(texto[k]));
					
					else;
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	private void seHifenAparecer() {
		boolean acabaramEspacos = false;
		k++;
		while(eEspaco(texto[k]) && !acabaramEspacos) {
			k++;
			if(!eEspaco(texto[k])) {
				acabaramEspacos = true;
			}
		}
		if((Character.toString(texto[this.k])).matches(regex_numeros)) {
			//criar o bagulho de digitos
		}
		else  {
			//criar operador aritmetico 
			k--;  //pra quando o for atualizar o valor de k p
		}
	}
	
	
	private boolean eEspaco(char c) {
		return c == '\t' || c == ' ' || c == '\n' || c == '\r';
	}
	private void criarIdentificador() {
		boolean continua = true;
		boolean achouErro = false;
		while(k< texto.length && continua) {
			String aux = Character.toString(texto[k]);
			if(aux.matches(regex_letras) || aux.matches(regex_numeros) || aux.matches("_")){
				buffer.add(texto[k]);
				k++;
			}else if(eFinalizador()){
				continua = false;
				k--; //pra garantir que no k++ ele pegará o finalizador;
			}
			
			else {
				achouErro = true;
			}
			/*if(Character.isLetter(texto[k]) || Character.isDigit(texto[k]) || texto[k]=='_'){
				
			}*/
		}
		
		if(achouErro = true); //fazer alguma coisa pra tratar o erro
		else {
			//cria o token
			//zera o buffer
			//vida que segue 
		}
	}
	
	//método que verifica possíveis finalizadores em geral: espaços, operadores e delimitadores
	private boolean eFinalizador() {
		return texto[k] == '+' || texto[k] == '-' || texto[k] == '*' || texto[k] == '/' || texto [k] == ' ' ||
				texto[k] == '\n' || texto[k] == '\r' || texto[k] == '"' || texto[k] == '=' || texto[k] == '>'|| 
				texto[k] == '>' || texto[k] == '<' || texto[k] == '!' || texto[k] == '|' || texto[k] == '&' ||
				texto[k] == '\t' || texto[k] == '%' || texto[k] == ';' || texto[k] == '(' || texto[k] == ')' || 
				texto[k] == '[' || texto[k] == ']' || texto[k] == '{' || texto[k] == '}' || texto[k] == ':';
	}
	
	private boolean eDelimitador() {
		return texto[k] == ';' || texto[k] == '(' || texto[k] == ')' || 
				texto[k] == '[' || texto[k] == ']' || texto[k] == '{' || texto[k] == '}' || texto[k] == ':';
	}
	
	
	private boolean ePalavraReservada() {
		return false;
	}
	
	private void criarNumero() {
		
	}
	
	private void criarOperadorAritmetico() {
		
	}
	
	private void criarOperadorRelacional() {
		
	}
	
	private void criarOperadorLogico() {
		
	}
	
	private void criarComentarioDeLinha() {
		
	}
	
	private void criarComentarioDeBloco() {
		
	}
	
	private void criarDelimitador() {
		
	}
	
	private void criarCadeiaDeCaracteres() {
		
	}

}
