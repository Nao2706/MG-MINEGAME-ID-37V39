package me.nao.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;





import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;








public class BukkitSerialization {
	
	 private BukkitSerialization() {
		    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
     }
	
	 

	    public static String serializar(ItemStack[] arreglo) throws IOException {
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

	    public static ItemStack[] deserializar(String cadena) throws IOException, ClassNotFoundException {
	        if (cadena == null || cadena.isEmpty()) {
	            throw new NullPointerException("La cadena no puede ser nula o vacía");
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
	 


    
}
