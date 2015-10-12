package com.gamingbeaststudio.developtoolkit.network;

import java.io.Serializable;

public class MessageDao implements Serializable {

	private static final long serialVersionUID = 7141628062701178851L;
	private final String sendUserIp;
	private final String msgType;
	private final Serializable body;

	public MessageDao(final String sendUserIp, final String msgType,
			final Serializable body) {
		super();
		this.sendUserIp = sendUserIp;
		this.msgType = msgType;
		this.body = body;
	}

	public String getSendUserIp() {
		return sendUserIp;
	}

	public String getMsgType() {
		return msgType;
	}

	public Serializable getBody() {
		return body;
	}
}