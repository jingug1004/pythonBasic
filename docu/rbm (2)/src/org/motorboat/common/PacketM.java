package org.motorboat.common;

/**
 * �����͸� ���ϰ� ��Ŷ������ �м��ϰ� ���� Class  
 * 
 * @author ����� ����������
 *
 */
public class PacketM {
	private byte[] bb;
	private int cPos = 0;
	private int packetLen = 0;
	
	/**
	 * String������ ���� �����͸� PacketM.bb�� ������
	 *  
	 * @param packet
	 */
	public PacketM(String packet) {
		bb = packet.getBytes();
		packetLen = this.bb.length;
		cPos = 0;
	}
	
	/**
	 * byte[]������ ���� �����͸�PacketM.bb�� ������
	 * @param bb
	 */
	public PacketM(byte[] bb) {
		this.bb = bb;
		packetLen = this.bb.length;
		cPos = 0;
	}
	
	/**
	 * ������������ �������Ľ� �������� ��ġ�� �ʱ�ȭ��
	 *
	 */
	public void resetPos() {
		this.cPos = 0;
	}
	
	/**
	 * ������������ �������Ľ� �����͸� pos�� ��ġ�� ������
	 * @param pos
	 */
	public void seekPos(int pos) {
		if (pos > packetLen)
			return;
		this.cPos = pos;
	}

	/**
	 * ������������ �������Ľ� �������� ��ġ�� ��ȯ��
	 * 
	 * @return ������������ �������Ľ� ������ ��ġ
	 */
	public int getCurrentPos() {
		return this.cPos;
	}
	
	/**
	 * ���� �����͸� len��ŭ�� ũ��� �պκ� ���� �����Ͽ� byte[]���·� ��ȯ�� 
	 * 
	 * @param len ������ �������� ũ��
	 * @return len�� ������ ũ�⸸ŭ ����� ������
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
	 * ���� �����͸� len��ŭ�� ũ��� �պκ� ���� �����Ͽ� String���·� ��ȯ�� 
	 * 
	 * @param len ������ �������� ũ��
	 * @return len�� ������ ũ�⸸ŭ ����� ������
	 */
	public String splitPacketToString(int len) {
		byte[] cc = splitPacket(len);
		return new String(cc);
	}
	
	/**
	 * ���� �����͸� len��ŭ�� ũ��� �պκ� ���� �����Ͽ� int���·� ��ȯ�� 
	 * 
	 * @param len ������ �������� ũ��
	 * @return len�� ������ ũ�⸸ŭ ����� ������
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
	 * ������������ ������ �Ľ� �����͸� len�� ũ�⸸ŭ �ڷ� �̵���
	 * 
	 * @param len �̵���ų ũ��
	 */
	public void rollPos(int len) {
		cPos += len;
	}
}
