package com.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.util.Map;
import java.util.Set;

public class Encoder {

    private static XStream xs = new XStream(new XppDriver(new XmlFriendlyNameCoder("-_", "_")));

    public static <T> String toXml(String alias, T t) {
        xs.alias(alias, t.getClass());
        xs.processAnnotations(t.getClass());
        String result = xs.toXML(t);
        result = result.replaceAll(">\\s+<", "><");
        return Constants.XML_METADATA + result;
    }
}
