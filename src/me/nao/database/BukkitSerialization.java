package me.nao.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.annotation.Nullable;


import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;








public class BukkitSerialization {
	
	 private BukkitSerialization() {
		    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
     }
	
	  public static @Nullable String serializeItems(final @Nullable ItemStack[] items) throws IOException {
		    if (items == null) {
		      return null;
		    }
		    final var byteOutputStream = new ByteArrayOutputStream();
		    try (final var output = new BukkitObjectOutputStream(byteOutputStream)) {
		      output.writeInt(items.length);
		      for (final var item : items) {
		        output.writeObject(item);
		      }
		      return Base64Coder.encodeLines(byteOutputStream.toByteArray());
		    }
		  }	

	  public static @Nullable ItemStack[] deserializeItems(final @Nullable String serializedItems)
			    throws IOException, ClassNotFoundException {
			    if (serializedItems == null) {
			      return null;
			    }
			    try (
			      final var dataInput =
			        new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(serializedItems)))
			    ) {
			      final var items = new ItemStack[dataInput.readInt()];
			      for (int i = 0; i < items.length; i++) {
			        final var object = dataInput.readObject();
			        if (object == null) {
			          items[i] = null;
			          continue;
			        }
			        items[i] = (ItemStack) object;
			      }
			      return items;
			    }
	 }

    
}
