package com.mintai.spider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * �ļ�������
 *
 * @author leiteng
 *         Date 2015/9/3.
 */
public class CommonHelper {
    public static String formatDate(Date date, String pattern) {
        if (null == date || pattern == null || pattern.isEmpty()) {
            return null;
        }

        try {
            SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance();

            formatter.applyPattern(pattern);
            return formatter.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toDate(String date, String format) {
        if (date == null || date.isEmpty() || format == null || format.isEmpty()) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * ����ַ����Ƿ��ǿհף�<code>null</code>�����ַ���<code>""</code>��ֻ�пհ��ַ���
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str Ҫ�����ַ���
     * @return ���Ϊ�հ�, �򷵻�<code>true</code>
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * ����ַ����Ƿ��ǿհף�<code>null</code>�����ַ���<code>""</code>��ֻ�пհ��ַ���
     * <pre>
     * StringUtil.isBlank(null)      = false
     * StringUtil.isBlank("")        = false
     * StringUtil.isBlank(" ")       = false
     * StringUtil.isBlank("bob")     = true
     * StringUtil.isBlank("  bob  ") = true
     * </pre>
     *
     * @param str Ҫ�����ַ���
     * @return ���Ϊ�հ�, �򷵻�<code>true</code>
     */
    public static boolean isNotBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }
}
