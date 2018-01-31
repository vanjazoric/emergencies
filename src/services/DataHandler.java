/**
 * 
 */
package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Vanja
 *
 */
public class DataHandler {

	public static void serialize(Serializable object, File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(object);
			out.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> deserialize(File file) {
		ArrayList<T> retVal = null;
		try {
			FileInputStream is = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(is);
			retVal = (ArrayList<T>) in.readObject();
			in.close();
			is.close();
			return retVal;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return retVal;
	}

	// save uploaded file to new location
	public static void savePicture(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}