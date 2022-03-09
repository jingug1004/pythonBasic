package org.sosfo.sdl.cra.server.monitor;

import org.sosfo.framework.log.Log;

public class JVMMonitor extends Thread {

	private static Runtime	rt	= Runtime.getRuntime();

	/**
	 * �ʱ� ȯ�漳�� �ε�
	 */
	public JVMMonitor() {}

	/**
	 * ���α׷��� �����ϴ� ��Ʈ�� ����Ʈ
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		JVMMonitor mon = new JVMMonitor();

		try {
			mon.start();

		} catch (Exception e) {
			Log.error("ERROR", "", e);
		}

	}

	public void run() {

		try {
			while (true) {
				rt.gc();
				long free = rt.freeMemory();
				long total = rt.totalMemory();

				Log.info("MON", "#############################################");
				Log.info("MON", "Free : " + (free / 1024 / 1024) + "MB  " + "Total : " + (total / 1024 / 1024) + "MB");
				Log.info("MON", "#############################################");
				sleep(1000*60*30);
			}
		} catch (Exception e) {
			Log.error("ERROR", "", e);
		}

	}
}
