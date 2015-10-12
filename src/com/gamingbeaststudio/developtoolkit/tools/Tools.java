package com.gamingbeaststudio.developtoolkit.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import android.app.Activity;

public class Tools {

	public static String intToIp(final int ip) {

		return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "."
				+ ((ip >> 16) & 0xFF) + "." + (ip >> 24 & 0xFF);
	}

	public static byte[] toByteArray(final Object obj) {
		byte[] bytes = null;
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			final ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
		return bytes;
	}

	public static Object toObject(final byte[] bytes) {
		Object obj = null;
		try {
			final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			final ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (final IOException ex) {
			ex.printStackTrace();
		} catch (final ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	public static void writeFileFromAssets(final String fileName, final String filePath,
			final Activity main) {
		
		final File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		final File file = new File(filePath + fileName);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			final InputStream is = main.getResources().getAssets()
					.open(fileName);
			final OutputStream os = new FileOutputStream(filePath + fileName);

			final byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			os.flush();
			os.close();
			is.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}