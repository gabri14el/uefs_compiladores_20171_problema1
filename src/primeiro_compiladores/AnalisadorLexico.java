package primeiro_compiladores;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AnalisadorLexico {
	String buffer; 
	int k = 0; //ultimo caractere lido no texto
	int linha = 1;
	char[] texto;
	Tabela tabela;
	
	String regex_letras = "[a-zA-Z]";
	String regex_numeros = "[0-9]";	
	public static void main (String[] argv){
		AnalisadorLexico b= new AnalisadorLexico();
		b.start();
	}
	
	
	public AnalisadorLexico() {
		buffer="";
		tabela = new Tabela();
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
					buffer = ""; //limpa o buffer sempre que um lexema novo será analisado
					buffer+=texto[k]; //sempre adiciona o caractere ao buffer
					
					if(texto[k] == '\n' || texto[k] == '\r') linha++;//controle de linha, vai que use né?? 
					
					else if(texto[k] == '\t' || texto[k] == ' '); //ignorar os espaços
					
					else if(texto[k] == '-'){
										seHifenAparecer();
                                        }
					
					else if(texto[k] == '/'){
                                         seBarraAparecer();
                                        }
					
					else if(texto[k] == '&' || texto[k] == '|'){
                                            criarOperadorLogico();
                                        } //método operador relacional eu acho 
					
					else if(texto[k] == '=')
						criarOperadorLogico();
					
					else if(texto[k] == '<' || texto[k] == '>')
						criarOperadorLogico();
					
					else if(texto[k] == '"') {
						criarCadeiaDeCaracteres();
					}
					
					else if(texto[k] == '+' || texto[k] == '*' || texto[k] == '%')
						criarOperadorAritmetico();
					
					else if(texto[k] == '!') 
						seExlamacaoAparecer();
					
					else if(eDelimitador()) 
						criarDelimitador();
	
					else if(Character.isLetter(texto[k])) 
						criarIdentificador();
					
					else if(Character.isDigit(texto[k]))
						criarNumero();
					else
						tabela.addToken(buffer, "desconhecido", true);
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
        private void criarOperadorLogico(){
            char aux = texto[k];
            k++;
            if(aux == '&'){
                if(texto[k] == '&'){
                    buffer+=texto[k];
                    tabela.addToken(buffer, Tabela.OPERADOR_RELACIONAL, false);
                }else{
                	tabela.addToken(buffer, Tabela.OPERADOR_RELACIONAL, true);
                    k--;
                }
            }else{
                if(texto[k] == '|'){
                	buffer+=texto[k];
                    tabela.addToken(buffer, Tabela.OPERADOR_RELACIONAL, false);
                }else{
                	tabela.addToken(buffer, Tabela.OPERADOR_RELACIONAL, true);
                    k--;
                }
            }
        }
	private void criarNumero(){
            boolean continua = true; 
            boolean achouErro = false;
            boolean achouPonto = false;

            while(k < texto.length && continua){
            	k++;
            	String aux = Character.toString(texto[k]);
                if(aux.matches(regex_numeros)){
                    buffer+=aux;
                }else if(texto[k] == '.'){
                	buffer+=aux;
                    if(achouPonto) achouErro = true;
                    else {
                    	achouPonto = true;
                    }
                    
                }
                else if(eFinalizador()){
                    continua = false;
                }
                else {
                	achouErro = true;
                	buffer+=aux;
                }
                
            }
            k--;
            tabela.addToken(buffer, Tabela.NUMERO, achouErro);
        }
        
	
		
	
        
        private void seBarraAparecer(){
            k++; 
            if(texto[k] == '/'){
            	buffer+=texto[k];
            	k++;
                criarComentarioDeLinha();
            }
            else if(texto[k] == '*') {
            	buffer+=texto[k];
            	k++;
            	criarComentarioDeBloco();
            }
            else{
                k--;
                tabela.addToken(buffer, Tabela.OPERADOR_ARITMETICO, false);
            }
        }
     
    private void seExlamacaoAparecer() {
    	if(texto[k+1] == '=') {
    		buffer+=texto[++k];
    		tabela.addToken(buffer, Tabela.OPERADOR_RELACIONAL, false);
    	}else {
    		tabela.addToken(buffer, Tabela.OPERADOR_LOGICO, false);
    	}
    }
    /**
     * Método chamado quando um "-" aparece. 
     * Esse método define se o hifen faz parte de um número ou se 
     * ele é um operador aritmético. 
     */
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
			buffer+=texto[k];
			criarNumero();
		}
		else  {
			buffer+=texto[k];
			criarOperadorAritmetico(); 
		}
	}
	
	
	private boolean eEspaco(char c) {
		return c == '\t' || c == ' ' || c == '\n' || c == '\r';
	}
	private void criarIdentificador() {
		boolean continua = true;
		boolean achouErro = false;
                k++;
		while(k< texto.length && continua) {
			String aux = Character.toString(texto[k]);
			if(aux.matches(regex_letras) || aux.matches(regex_numeros) || aux.equals("_")){
				buffer+=texto[k];
				k++;
			}else if(eFinalizador()){
                continua = false;
				k--; //pra garantir que no k++ ele pegará o finalizador;
			}
			else {
				buffer+=texto[k];
				achouErro = true;
			}
			/*if(Character.isLetter(texto[k]) || Character.isDigit(texto[k]) || texto[k]=='_'){
				
			}*/
		}
		
		tabela.addToken(buffer, Tabela.IDENTIFICADOR, achouErro);
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
	
	private void criarOperadorAritmetico() {
		tabela.addToken(buffer, Tabela.OPERADOR_ARITMETICO, false);
	}
	
	
	private void criarOperadorRelacional() {
		if(texto[k] == '=') {
			tabela.addToken(buffer, Tabela.OPERADOR_RELACIONAL, false);
		}else if(texto[k] == '<' || texto[k] == '>') {
			if(texto[k+1] == '=') {
				buffer+=texto[++k];
				tabela.addToken(buffer, Tabela.OPERADOR_RELACIONAL, false);
			}else
			{
				tabela.addToken(buffer, Tabela.OPERADOR_RELACIONAL, false);
			}
		}
	}
	
	private void criarComentarioDeLinha() {
		while(texto[k] != '\n' && texto[k] != '\r' && k<texto.length) {
			buffer+=texto[k];
			k++;
		}
		k--;
		tabela.addToken(buffer, Tabela.COMENTARIO_DE_BLOCO, false);
		
	}
	
	private void criarComentarioDeBloco() {
		boolean terminou = false;
		boolean achouAsterisco = false;
		while(!terminou && k < texto.length) {
			buffer+=texto[k];
			if(texto[k] == '*') achouAsterisco = true;
			else if(texto[k] == '/')
				if(achouAsterisco) terminou = true;
				else {
					achouAsterisco = false;
			}
		}
		
		tabela.addToken(buffer, Tabela.COMENTARIO_DE_LINHA, !terminou);
	}
	
        
       private void criarDelimitador() {
		tabela.addToken(buffer, Tabela.DELIMITADORES, false);
	}
	
	private void criarCadeiaDeCaracteres() {
		boolean terminou = false;
		boolean achouBarra = false;
		while(k<texto.length && !terminou) {
			k++; 
			buffer+=texto[k];
			if(texto[k] == '\\'){
					achouBarra = true;
			} else if(texto[k] == '"') {
				if (!achouBarra)
					terminou = true;
				else
					achouBarra = false;
			}
		}
		tabela.addToken(buffer, Tabela.CADEIA_DE_CARACTERES, !terminou);
	}

}