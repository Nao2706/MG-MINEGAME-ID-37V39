package me.nao.database.mg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;





import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;







@SuppressWarnings("deprecation")
public class BukkitSerialization {
	
	 private BukkitSerialization() {
		    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
     }
	
	 

	    public static String serializarMultipleItems(ItemStack[] arreglo) throws IOException {
	        if (arreglo == null) {
	            throw new NullPointerException("El arreglo no puede ser nulo");
	        }

	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        BukkitObjectOutputStream oos = new BukkitObjectOutputStream(bos);
	        oos.writeInt(arreglo.length);
	        for (ItemStack item : arreglo) {
	            oos.writeObject(item);
	        }
	        oos.close();
	        return  Base64Coder.encodeLines(bos.toByteArray());
	    }

	    public static ItemStack[] deserializarMultipleItems(String cadena) throws IOException, ClassNotFoundException {
	        if (cadena == null || cadena.isEmpty()) {
	            throw new NullPointerException("La cadena no puede ser nula o vacia");
	        }

	        byte[] bytes = Base64Coder.decodeLines(cadena);
	        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
	        BukkitObjectInputStream ois = new BukkitObjectInputStream(bis);
	        int length = ois.readInt();
	        ItemStack[] arreglo = new ItemStack[length];
	        for (int i = 0; i < length; i++) {
	            arreglo[i] = (ItemStack) ois.readObject();
	        }
	        ois.close();
	        return arreglo;
	    }
	 
	    
	    public static String serializarOneItem(ItemStack item) throws IOException {
	        if (item == null) {
	            throw new NullPointerException("El item no puede ser nulo");
	            
	        }
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        try (BukkitObjectOutputStream oos = new BukkitObjectOutputStream(bos)) {
	            oos.writeObject(item);
	        }
	        return Base64Coder.encodeLines(bos.toByteArray());
	    }

	    public static ItemStack deserializarOneItem(String cadena) throws IOException, ClassNotFoundException {
	        if (cadena == null || cadena.isEmpty()) {
	            throw new NullPointerException("La cadena no puede ser nula o vacia");
	        }
	        byte[] bytes = Base64Coder.decodeLines(cadena);
	        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
	        try (BukkitObjectInputStream ois = new BukkitObjectInputStream(bis)) {
	            return (ItemStack) ois.readObject();
	        }
	    }
	    


    
}
