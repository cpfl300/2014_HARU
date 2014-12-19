package haru.library.imageUtil;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author James
 */
public enum ImageType {
    JPG,
    GIF,
    PNG,
    UNKNOWN;
    
    private static final Map<String, ImageType> extensionMap = new HashMap<String, ImageType>();
    
    static {
        extensionMap.put("jpg", ImageType.JPG);
        extensionMap.put("jpeg", ImageType.JPG);
        extensionMap.put("gif", ImageType.GIF);
        extensionMap.put("png", ImageType.PNG);
    }

    public static ImageType getType(String ext) {
        ext = ext.toLowerCase();
        if (extensionMap.containsKey(ext))
            return extensionMap.get(ext);
        else
            return UNKNOWN;
    }
}
