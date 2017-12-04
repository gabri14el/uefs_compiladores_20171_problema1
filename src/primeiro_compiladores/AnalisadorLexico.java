package primeiro_compiladores;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
//testando commit
public class AnalisadorLexico {
	String buffer; 
	int k = 0; //ultimo caractere lido no texto
	int linha = 1;
	char[] texto;
	Tabela tabela;
	LinkedList<BufferedWriter> writeres;
	String regex_letras = "[a-zA-Z]";
	String regex_numeros = "[0-9]";	
	public static void main (String[] argv){
		AnalisadorLexico b= new AnalisadorLexico();
		b.start();
		for (Token t : b.getTokens()) {
			System.out.println(t);
		}
			
	}
	
	
	public AnalisadorLexico() {
		buffer="";
		tabela = new Tabela();
		writeres = new LinkedList<>();
	}
	
	public LinkedList<Token> getTokens(){
		return tabela.tokens;
	}
	public void start(){
		File diretorio = new File("entrada");
		for (File f : diretorio.listFiles()) { //percorre todos os arquivos do diretório
			System.out.println("arquivo:" + f.getName());
			try {
				
				linha = 1;
				tabela = new Tabela();
				byte[] encoded = Files.readAllBytes(f.toPath());
				String aux = new String(encoded);
				texto = aux.toCharArray(); //transforma string em array de char
				for(k = 0; k < texto.length; k++){ //percorre o texto caracter a caractere
					
					System.out.println(buffer);
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
						criarOperadorRelacional();
					
					else if(texto[k] == '<' || texto[k] == '>')
						criarOperadorRelacional();
					
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
						tabela.addToken(linha,buffer, Tabela.DESCONHECIDO, true); //quando erro desconhecido acontece
				
					
				}
				
				File saida = new File(f.getAbsolutePath()+"_saida.txt");
				BufferedWriter writer = new BufferedWriter(new FileWriter(saida));
				
				
				for (Token t : getTokens()) {
					writer.write(t.toString());
					writer.newLine();	
				}
				
			writeres.add(writer);	
			} catch (IOException e) {
				System.out.println("deu ruim aqui");
			}
		}
		for (BufferedWriter writer : writeres) {
			try {
				//imprime os arquivos
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * Método responsável por criar operador lógico. 
	 * Ele testa o primeiro simbolo do operador e a partir dele define 
	 * se o eperador é válido ou não.
	 */
        private void criarOperadorLogico(){
            char aux = texto[k];
            k++;
            if(aux == '&'){
                if(texto[k] == '&'){
                    buffer+=texto[k];
                    tabela.addToken(linha,buffer, Tabela.OPERADOR_LOGICO, false);
                }else{
                	tabela.addToken(linha,buffer, Tabela.OPERADOR_LOGICO, true); //caso não ache outro símblo, cria token mal formado
                    k--;
                }
            }else{
                if(texto[k] == '|'){
                	buffer+=texto[k];
                    tabela.addToken(linha,buffer, Tabela.OPERADOR_LOGICO, false);
                }else{
                	tabela.addToken(linha,buffer, Tabela.OPERADOR_LOGICO, true);
                    k--;
                }
            }
        }
        
        /**
         * Método responsável por criar um número. 
         * 
         */
	private void criarNumero(){
            boolean continua = true; 
            boolean achouErro = false;
            boolean achouPonto = false;

            while(k < texto.length && continua){
            	k++;
            	String aux = Character.toString(texto[k]);
            	
            	//verifica se é um dígito
                if(aux.matches(regex_numeros)){
                    buffer+=aux; //adiciona ao buffer
                }else if(texto[k] == '.'){
                	buffer+=aux;
                    if(achouPonto) achouErro = true; //verifica se achou um ponto
                    else {
                    	achouPonto = true; //guarda sempre quando se achar um ponto
                    }
                    
                }
                else if(eFinalizador()){ //verifica se é um dos finalizadores
                    continua = false;
                }
                else if(texto[k] == '\n' || texto[k] == '\r') 
                    linha++;
                else {
                	achouErro = true;
                	buffer+=aux;
                }
            }
            k--;
            tabela.addToken(linha,buffer, Tabela.NUMERO, achouErro); //cria token com base ba variável que define se um erro foi encontrado
        }
        
	
		
	
        
        private void seBarraAparecer(){
            k++; 
            if(texto[k] == '/'){
            	buffer+=texto[k];
            	k++;
                criarComentarioDeLinha(); //caso apareçam duas barras
            }
            else if(texto[k] == '*') {
            	buffer+=texto[k];
            	criarComentarioDeBloco(); //caso apareça /*
            }
            else{
                k--;
                tabela.addToken(linha,buffer, Tabela.OPERADOR_ARITMETICO, false); //caso em que outra coisa aparece e a barra é um operador aritmetico
            }
        }
     
    private void seExlamacaoAparecer() {
    	if(texto[k+1] == '=') {
    		buffer+=texto[++k];
    		tabela.addToken(linha,buffer, Tabela.OPERADOR_RELACIONAL, false);  //operador relacional
    	}else {
    		tabela.addToken(linha,buffer, Tabela.OPERADOR_LOGICO, false); //operador logico
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
		if(texto[k] == '\r' || texto[k] == '\n') {
			linha++;
		}
		
		
		while(eEspaco(texto[k]) && !acabaramEspacos) {
			k++;
			if(texto[k] == '\r' || texto[k] == '\n') linha++;
			if(!eEspaco(texto[k])) {
				acabaramEspacos = true;
			}
		}
		if((Character.toString(texto[this.k])).matches(regex_numeros)) {
			buffer+=texto[k];
			criarNumero();
		}
		else  {
			k--;
			criarOperadorAritmetico(); 
		}
	}
	
	
	private boolean eEspaco(char c) {
		return c == '\t' || c == ' ' || c == '\n' || c == '\r'; //verifica se é um dos espaços
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
				System.out.println("merda em");
				buffer+=texto[k];
				k++;
				achouErro = true;
			}
		}
		
		if(!ePalavraReservada())
			tabela.addToken(linha,buffer, Tabela.IDENTIFICADOR, achouErro);
		else
			tabela.addToken(linha,buffer, Tabela.PALAVRA_RESERVADA, false);
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
		String cadeiaDeChar = buffer;
		if(cadeiaDeChar.equals("class") || cadeiaDeChar.equals("final") || cadeiaDeChar.equals("if") || cadeiaDeChar.equals("else") || cadeiaDeChar.equals("for") || cadeiaDeChar.equals("scan") || cadeiaDeChar.equals("print") || cadeiaDeChar.equals("int") || cadeiaDeChar.equals("float") || cadeiaDeChar.equals("bool") || cadeiaDeChar.equals("true") || cadeiaDeChar.equals("false") || cadeiaDeChar.equals("string"))
			return true;
		else
			return false;
	}
	
	private void criarOperadorAritmetico() {
		tabela.addToken(linha,buffer, Tabela.OPERADOR_ARITMETICO, false);
	}
	
	
	private void criarOperadorRelacional() {
		if(texto[k] == '=') {
			tabela.addToken(linha,buffer, Tabela.OPERADOR_RELACIONAL, false);
		}else if(texto[k] == '<' || texto[k] == '>') {
			if(texto[k+1] == '=') {
				buffer+=texto[++k];
				tabela.addToken(linha,buffer, Tabela.OPERADOR_RELACIONAL, false);
			}else
			{
				tabela.addToken(linha,buffer, Tabela.OPERADOR_RELACIONAL, false);
			}
		}
	}
	
	private void criarComentarioDeLinha() {
		while(texto[k] != '\n' && texto[k] != '\r' && k<texto.length) {
                    buffer+=texto[k];
                    k++;
		}
		k--;
		tabela.addToken(linha,buffer, Tabela.COMENTARIO_DE_LINHA, false);
	}
	
	private void criarComentarioDeBloco() {
		boolean terminou = false;
		boolean achouAsterisco = false;
		k++;
		while(!terminou && k < texto.length) {
			
			buffer+=texto[k];
                        
			if(texto[k] == '*') {
				if(texto[k+1] == '/') {
					buffer+=texto[++k];
					terminou = true;
				}
			}
			k++;
		}
		k--;
		tabela.addToken(linha,buffer, Tabela.COMENTARIO_DE_BLOCO, !terminou);
	}
	
        
       private void criarDelimitador() {
		tabela.addToken(linha,buffer, Tabela.DELIMITADORES, false);
	}
	
	private void criarCadeiaDeCaracteres() {
		boolean terminou = false;
		boolean achouAspas = false;
		while(k<texto.length && !terminou) {
			k++;
			if(texto[k] == '\\'){
				buffer+=texto[k];
				System.out.println(buffer);
				if(texto[k+1] == '"'){
					k++;
					buffer+=texto[k];
				}
			}else if(texto[k] == '"'){
				terminou = true;
				buffer+=texto[k];
				achouAspas = true;
			}
			else if(texto[k] == '\n' || texto[k] == '\r'){
                            terminou = true;
                            k--;
                        }
			else{
				buffer+=texto[k];
			}
		}
		tabela.addToken(linha,buffer, Tabela.CADEIA_DE_CARACTERES, !achouAspas);
		
	}

}