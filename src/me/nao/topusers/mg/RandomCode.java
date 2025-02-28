package me.nao.topusers.mg;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;






public class RandomCode {

	
	
	public String generateRandomCode(int codesize) {
		
		int t = codesize;
		SecureRandom mys = new SecureRandom();
		
		// 78 Caracteres
		String[] letras = {
				"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
				"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
				"0","1","2","3","4","5","6","7","8","9","!","*","/","+","-","_","#","=","$","&","%","?",":",";","<",">"
		};
		
		//NO JNC uwu
		
		String[] myp = new String[t];
		StringBuilder sb = new StringBuilder();
	//	System.out.println("claves randoms son:\n");
		for(int i = 0; i < t;i++) {
			int k = mys.nextInt(letras.length);
			 myp[i] = letras[k];
			
			sb.append(myp[i]);
		}
		
		
	
		String p = sb.toString();
	
		return p;
	
		
	}
	

	   public static String mgencode(String texto, String codigo){
	    	
	    	int bites = 16;
	    	
	    	if(codigo.length() < bites) {
	    		byte[] codigoBytes = codigo.getBytes();
	            int longitud = bites; // longitud deseada del código
	            byte[] codigoAlargado = new byte[longitud];

	            for (int i = 0; i < longitud; i++) {
	                codigoAlargado[i] = codigoBytes[i % codigoBytes.length];
	            }

	           codigo = new String(codigoAlargado);
	    	}else if(codigo.length() > bites) {
	    		codigo = codigo.substring(0, bites);
	    	}
	    	try {
	    	       // Crear una clave secreta a partir del código
	            SecretKeySpec clave = new SecretKeySpec(codigo.getBytes(StandardCharsets.UTF_8), "AES");

	            // Crear un objeto Cipher para encriptar
	            Cipher cipher = Cipher.getInstance("AES");
	            cipher.init(Cipher.ENCRYPT_MODE, clave);

	            // Encriptar el texto
	            byte[] bytesEncriptados = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));

	            // Convertir los bytes encriptados a una cadena de texto
	            return Base64.getEncoder().encodeToString(bytesEncriptados);	
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    	}
			return "Error";
	 
	    }

	    public static String mgdeenconde(String textoEncriptado, String codigo) {
	    	
	    	int bites = 16;
	    	
	    	if(codigo.length() < bites) {
	    		byte[] codigoBytes = codigo.getBytes();
	            int longitud = bites; // longitud deseada del código
	            byte[] codigoAlargado = new byte[longitud];

	            for (int i = 0; i < longitud; i++) {
	                codigoAlargado[i] = codigoBytes[i % codigoBytes.length];
	            }

	           codigo = new String(codigoAlargado);
	    	}else if(codigo.length() > bites) {
	    		codigo = codigo.substring(0, bites);
	    	}
	        try {
	        	
	        	// Crear una clave secreta a partir del código
	            SecretKeySpec clave = new SecretKeySpec(codigo.getBytes(StandardCharsets.UTF_8), "AES");

	            // Crear un objeto Cipher para desencriptar
	            Cipher cipher = Cipher.getInstance("AES");
	            cipher.init(Cipher.DECRYPT_MODE, clave);

	            // Convertir la cadena de texto encriptada a bytes
	            byte[] bytesEncriptados = Base64.getDecoder().decode(textoEncriptado);
	            // Desencriptar el texto
	            byte[] bytesDesencriptados = cipher.doFinal(bytesEncriptados);

	            // Convertir los bytes desencriptados a una cadena de texto
	            return new String(bytesDesencriptados, StandardCharsets.UTF_8);
	        } catch (Exception e) {
	            // Si se produce una excepción durante la desencriptación, es probable que el código sea incorrecto
	            return "Código incorrecto";
	        }
	    }
	
	
}
