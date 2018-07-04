package lf65.ams.support;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public abstract class TestHelper {

    private static final String ENCODING = "UTF-8";

    public static String readJsonFrom(String fileName) throws IOException {
        String content = readStringFrom(fileName);
        return new Gson().fromJson(content, JsonObject.class).toString();
    }

    public static String readStringFrom(String fileName) throws IOException {
        File file = getFile(fileName);
        Long length = file.length();
        byte[] content = new byte[length.intValue()];
        try (FileInputStream in = new FileInputStream(file)) {
            IOUtils.readFully(in, content);
        }
        return new String(content, ENCODING);
    }

    private static File getFile(String fileName) {
        final URL url = TestHelper.class.getClassLoader().getResource(fileName);
        assert url != null;
        return new File(url.getFile());
    }
}
