import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class ValidityCheck {
	
    public static void test(String[] args) {
    	/**
    	 * auto detects VLC folder, failed for some reason..
    	 */
        boolean found = new NativeDiscovery().discover();
        System.out.println(found);
        System.out.println(LibVlc.INSTANCE.libvlc_get_version());
    }	
}