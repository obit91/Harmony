package ohad;

import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

public class DetectVLC {
	
	protected static void detection() {
		String NATIVE_LIBRARY_SEARCH_PATH = "C:\\Users\\Ohad\\Desktop\\vlc-2.2.0";
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), NATIVE_LIBRARY_SEARCH_PATH);
	}

}
