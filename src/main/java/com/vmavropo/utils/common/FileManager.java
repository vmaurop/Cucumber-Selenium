package com.vmavropo.utils.common;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileManager {

    public String lineSeparator() {
        return System.lineSeparator();
    }

    boolean touch(String filepath) {
        try {
            File f = new File(filepath);
            if (f.isDirectory())
                throw new RuntimeException("Cannot create file because a directory exists: " + filepath);
            return f.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e + " filepath: " + filepath);
        }
    }

    public void createFile(String filepath) {
        if (!touch(filepath))
            throw new RuntimeException("Cannot create file: " + filepath);
    }

    public void deleteFile(String filepath) {
        File f = new File(filepath);
        if (!f.exists())
            return;
        if (!f.delete())
            throw new RuntimeException("Cannot not delete file: " + filepath);
    }

    public void deleteFile(List<String> filepaths) {
        for (String filepath : filepaths)
            deleteFile(filepath);
    }

    public void appendToFile(String filepath, String text) {
        try {
            touch(filepath);
            FileWriter myWriter = new FileWriter(filepath, true);
            myWriter.append(String.valueOf(text));
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeToFile(String filepath, String text) {
        try {
            touch(filepath);
            FileWriter myWriter = new FileWriter(filepath);
            myWriter.write(String.valueOf(text));
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFileText(String filepath) throws IOException {
        return FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);
    }

    public void download(String url, String filepath) {
        try {
            URL urlResource = new URL(url);
            ReadableByteChannel channel = Channels.newChannel(urlResource.openStream());
            touch(filepath);
            FileOutputStream fos = new FileOutputStream(filepath);
            fos.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
            channel.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Document fileToDoc(String filepath) throws IOException {
        FileManager fileManager = new FileManager();
        return stringToDoc(fileManager.getFileText(filepath));
    }

    public Document stringToDoc(String xmlString) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlString));
            return builder.parse(is);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String docToString(Document doc) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer();
            StringWriter sw = new StringWriter();
            trans.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

}
