package com.gamingbeaststudio.developtoolkit.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.util.Log;

public class TcpTools {

	public static File openServer(final int listeningPort,
			final String filePath, final String fileName, final int bufferSize,
			final int timeout) throws Exception {

		final File file = new File(filePath + fileName);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();

		final ServerSocket ss = new ServerSocket();
		ss.setReuseAddress(true);
		ss.bind(new InetSocketAddress(listeningPort));
		@SuppressWarnings("resource")
		Socket socket = new Socket();
		socket.setSoTimeout(timeout * 1000);
		try {
			socket = ss.accept();
		} catch (Exception e) {
			Log.e("dsd", e.getMessage());
			e.printStackTrace();
			socket.close();
			ss.close();
			throw new SocketTimeoutException();
		}

		final BufferedInputStream is = new BufferedInputStream(
				socket.getInputStream());
		final BufferedOutputStream os = new BufferedOutputStream(
				new FileOutputStream(file));
		final byte[] data = new byte[bufferSize];
		int len = -1;
		while ((len = is.read(data)) != -1) {
			os.write(data, 0, len);
		}
		is.close();
		os.flush();
		os.close();
		socket.close();
		ss.close();

		return file;
	}

	public static File openServer(final int listeningPort,
			final String filePath, final String fileName) throws Exception {
		return openServer(listeningPort, filePath, fileName,
				Constants.TCP_BUFFERSIZE, 10);
	}

	public static void openClient(final String filePath, final String fileName,
			final String targetIp, final int targetPort, final int bufferSize) {

		Thread thread = new Thread() {
			public void run() {
				try {
					Socket s = new Socket(targetIp, targetPort);
					final File file = new File(filePath + fileName);
					final BufferedInputStream is = new BufferedInputStream(
							new FileInputStream(file));
					final BufferedOutputStream os = new BufferedOutputStream(
							s.getOutputStream());
					final byte[] data = new byte[bufferSize];
					int len = -1;
					while ((len = is.read(data)) != -1) {
						os.write(data, 0, len);
					}
					is.close();
					os.flush();
					os.close();
					s.close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
	}

	public static void openClient(final String filePath, final String fileName,
			final String targetIp, final int targetPort) {
		openClient(filePath, fileName, targetIp, targetPort,
				Constants.TCP_BUFFERSIZE);
	}
}