package lf65.ams.support;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

public class TestHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestHelper.class);

    public static String readJsonFrom(String fileName) {
        final URL url = TestHelper.class.getClassLoader().getResource(fileName);
        if (url == null) {
            LOGGER.info("can not find the file: {}", fileName);
        }
        final File file = new File(url.getFile());
        try {
            return new Gson().fromJson(new FileReader(file), JsonObject.class).toString();
        } catch (FileNotFoundException e) {
            LOGGER.info("can not find the file: {}", fileName);
        }
        return null;
    }

    public static String readFrom(String fileName) {
        final URL url = TestHelper.class.getClassLoader().getResource(fileName);
        if (url == null) {
            LOGGER.info("can not find the file: {}", fileName);
        }
        final File file = new File(url.getFile());
        try {
            return new FileReader(file).toString();
        } catch (FileNotFoundException e) {
            LOGGER.info("can not find the file: {}", fileName);
        }
        return null;
    }

}
