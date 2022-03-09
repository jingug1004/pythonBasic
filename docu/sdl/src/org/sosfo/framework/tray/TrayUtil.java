package org.sosfo.framework.tray;

/**
 * Tray�� ����� ���� ���� �پ��� ������ �����ϴ� ��ƿ��Ƽ Ŭ����.
 * @author �����
 * @version 1.0, 2004-04-19
 */
public class TrayUtil {
    /**
     * ������ ���� ���� private ������.
     */
    private TrayUtil() {
    }

    /**
     * Ʈ���̿� ��� ���� �ٸ� Ʈ���̿� ��ģ��. target tray�� �̹� ������ key�� �־��� ��쿡�� �ڿ� append�ȴ�.
     * @param target ������ target�� �Ǵ� tray
     * @param src ������ source�� �Ǵ� tray
     */
    public static void mergeTray(Tray target, Tray src) {
	mergeTray(target, src, false);
    }

    /**
     * Ʈ���̿� ��� ���� �ٸ� Ʈ���̿� ��ģ��. target tray�� �̹� ������ key�� �־��� ��쿡�� �ڿ� append�ȴ�.
     * @param target ������ target�� �Ǵ� tray
     * @param src ������ source�� �Ǵ� tray
     * @param keyToLower key�� �ҹ��ڷ� ��ȯ
     */
    public static void mergeTray(Tray target, Tray src, boolean keyToLower) {
	String[] src_keys = src.getKeys();
	for (int i = 0; i < src_keys.length; i++) {
	    // System.out.println("mergeTray src_keys["+i+"] =============>" + src_keys[i]);
	    int size = src.size(src_keys[i]);
	    for (int j = 0; j < size; j++) {
		target.add(keyToLower ? src_keys[i].toLowerCase() : src_keys[i], src.get(src_keys[i], j));

		// System.out.println("mergeTray src.get(src_keys["+i+"], "+j+") =============>" + src.get(src_keys[i], j));
	    }
	}
	// System.out.println("mergeTray target =============>" + target.toString());
	// System.out.println("mergeTray src =============>" + src.toString());
    }
}
