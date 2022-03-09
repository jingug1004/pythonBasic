package org.motorboat.common;

/**
 * 데이터를 편리하게 패킷단위로 분석하게 위한 Class  
 * 
 * @author 김원겸 대상정보기술
 *
 */
public class PacketM {
	private byte[] bb;
	private int cPos = 0;
	private int packetLen = 0;
	
	/**
	 * String형태의 원본 데이터를 PacketM.bb에 설정함
	 *  
	 * @param packet
	 */
	public PacketM(String packet) {
		bb = packet.getBytes();
		packetLen = this.bb.length;
		cPos = 0;
	}
	
	/**
	 * byte[]형태의 원본 데이터를PacketM.bb에 설정함
	 * @param bb
	 */
	public PacketM(byte[] bb) {
		this.bb = bb;
		packetLen = this.bb.length;
		cPos = 0;
	}
	
	/**
	 * 원본데이터의 데이터파싱 포인터의 위치를 초기화함
	 *
	 */
	public void resetPos() {
		this.cPos = 0;
	}
	
	/**
	 * 원본데이터의 데이터파싱 포인터를 pos의 위치로 지정함
	 * @param pos
	 */
	public void seekPos(int pos) {
		if (pos > packetLen)
			return;
		this.cPos = pos;
	}

	/**
	 * 원본데이터의 데이터파싱 포인터의 위치를 반환함
	 * 
	 * @return 원본데이터의 데이터파싱 포인터 위치
	 */
	public int getCurrentPos() {
		return this.cPos;
	}
	
	/**
	 * 원본 데이터를 len만큼의 크기로 앞부분 부터 추출하여 byte[]형태로 반환함 
	 * 
	 * @param len 추출할 데이터의 크기
	 * @return len에 지정된 크기만큼 추출된 데이터
	 */
	public byte[] splitPacket(int len) {
		
		if ((cPos + len) >= packetLen)
			return new byte[0];
		
		byte[] rr = new byte[len];
		System.arraycopy(bb, cPos, rr, 0, len);
		cPos += len;
		
		return rr;
	}
	
	/**
	 * 원본 데이터를 len만큼의 크기로 앞부분 부터 추출하여 String형태로 반환함 
	 * 
	 * @param len 추출할 데이터의 크기
	 * @return len에 지정된 크기만큼 추출된 데이터
	 */
	public String splitPacketToString(int len) {
		byte[] cc = splitPacket(len);
		return new String(cc);
	}
	
	/**
	 * 원본 데이터를 len만큼의 크기로 앞부분 부터 추출하여 int형태로 반환함 
	 * 
	 * @param len 추출할 데이터의 크기
	 * @return len에 지정된 크기만큼 추출된 데이터
	 */
	public int splitPacketToInt(int len) {
		int result = 0;
		byte[] cc = splitPacket(len);
		
		try {
			result = Integer.parseInt(new String(cc));
		} catch(NumberFormatException ex) {
			result = 0;
		}
		
		return result;
	}
	
	/**
	 * 원본데이터의 데이터 파싱 포인터를 len의 크기만큼 뒤로 이동함
	 * 
	 * @param len 이동시킬 크기
	 */
	public void rollPos(int len) {
		cPos += len;
	}
}
