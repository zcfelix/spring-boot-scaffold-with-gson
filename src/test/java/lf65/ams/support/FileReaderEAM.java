package lf65.ams.support;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class FileReaderEAM {

    private final FileReader reader;

    private FileReaderEAM(final String fileName) throws FileNotFoundException {
        final URL url = FileReaderEAM.class.getClassLoader().getResource(fileName);
        assert url != null;
        final File file = new File(url.getFile());
        reader = new FileReader(file);
    }

    public static String use(final String fileName, final UseInstance<FileReaderEAM, IOException> block) throws IOException {
        final FileReaderEAM readerEAM = new FileReaderEAM(fileName);
        try {
            return block.accept(readerEAM);
        } finally {
            readerEAM.close();
        }
    }

    private void close() throws IOException {
        reader.close();
    }

    public String readJson() {
        return new Gson().fromJson(reader, JsonObject.class).toString();
    }

    public String readString() {
        return reader.toString();
    }
}
