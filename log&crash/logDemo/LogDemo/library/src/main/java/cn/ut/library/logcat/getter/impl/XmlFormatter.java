package cn.ut.library.logcat.getter.impl;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import cn.ut.library.logcat.getter.StringFormatter;
import cn.ut.library.utils.StringUtils;


/**
 * Created by chenhang on 2016/6/14.
 */
public class XmlFormatter implements StringFormatter {

    @Override
    public String format(String string) throws Exception {

        String result;
        if (StringUtils.isEmpty(string)) {
            result = "Empty/Null xml content";
        } else {
            Source xmlInput = new StreamSource(new StringReader(string));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            result = xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        }

        return result;
    }
}
