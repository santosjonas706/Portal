package br.com.portalNoticia.Controller.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Url {
    public static String decodeParam(String text) {
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
