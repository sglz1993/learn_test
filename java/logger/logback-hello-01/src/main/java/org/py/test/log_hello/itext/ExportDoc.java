package org.py.test.log_hello.itext;

import freemarker.template.Configuration;
import freemarker.template.Template;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ExportDoc {
    private Configuration configuration;
    private String encoding;

    public ExportDoc(String encoding) {
        this.encoding = encoding;
        configuration = new Configuration(Configuration.VERSION_2_3_22);
        configuration.setDefaultEncoding(encoding);
        configuration.setClassForTemplateLoading(this.getClass(), "/");
    }

    public Template getTemplate(String name) throws Exception {
        return configuration.getTemplate(name);
    }

    public String getImageStr(String image) throws IOException {
        InputStream is = new FileInputStream(image);
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] data = new byte[is.available()];
        is.read(data); is.close();
        return encoder.encode(data);
    }

    public Map<String, Object> getDataMap() {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("name", "lichmama");
        dataMap.put("address", "开发部");
      /*  dataMap.put("birthday", "19**年**月**日");
        try {
            dataMap.put("image", getImageStr("D:\\头像.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return dataMap;
    }

    public void exportDoc(String doc, String name) throws Exception {
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(doc), encoding));
        getTemplate(name).process(getDataMap(), writer);
    }

    public static void main(String[] args) throws Exception {
        ExportDoc maker = new ExportDoc("UTF-8");
        maker.exportDoc("test.doc", "22.ftl");
    }
}
