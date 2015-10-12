package com.gamingbeaststudio.developtoolkit.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.gamingbeaststudio.developtoolkit.tools.Tools;

public class UdpTools {

	public static void send(final int localPort, final byte[] data,
			final int length, final String targetHost, final int targetPort) {

		final Thread thread = new Thread() {
			public void run() {

				try {
					final DatagramSocket ds = new DatagramSocket(localPort);
					final DatagramPacket packet = new DatagramPacket(data,
							data.length, InetAddress.getByName(targetHost),
							targetPort);
					packet.setData(data);
					ds.send(packet);
					ds.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
	}

	public static MessageDao receive(final int listeningPort) throws Exception {

		final DatagramSocket ds = new DatagramSocket(listeningPort);
		final byte[] dataBuffer = new byte[Constants.UDP_BUFFERSIZE];
		final DatagramPacket dp = new DatagramPacket(dataBuffer,
				dataBuffer.length);
		dp.setData(dataBuffer);
		ds.receive(dp);
		final byte[] data = new byte[dp.getLength()];
		System.arraycopy(dataBuffer, 0, data, 0, data.length);
		final MessageDao msg = (MessageDao) Tools.toObject(data);
		ds.close();

		return msg;
	}
}