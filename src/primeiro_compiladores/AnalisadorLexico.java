package primeiro_compiladores;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Queue;

public class AnalisadorLexico {
	
	Queue<Character> buffer;
	int k = 0; //ultimo caractere lido no texto
	int linha = 1;
	
	
	
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
				char[] texto = aux.toCharArray(); //transforma string em array de char
				for(k = 0; k < texto.length; k++){ //percorre o texto caracter a caractere
					if(texto[k] == '\n') linha++;//controle de linha, vai que use né?? 
					else if(texto[k] == '\t' || texto[k] == ' '); //ignorar os espaços
					else if(texto[k] == '+' || texto[k] == '*'); //criar token de operador artimetico
					else if(texto[k] == '-'); //criar método que verifica qual o próximo
					else if(texto[k] == '/'); //método da barra
					else if(texto[k] == '&'); //método operador relacional eu acho 
					else if(texto[k] == '|'); //método operador relacional
					else if(texto[k] == '='); //método algum operador que eu tenho que ver
					else if(texto[k] == '<' || texto[k] == '>'); //método operador relacional 
					else if(texto[k] == '"'); //cadeia de caracteres 
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
