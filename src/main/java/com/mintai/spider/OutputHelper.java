package com.mintai.spider;

import com.google.common.base.Throwables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 文件描述：
 *
 * @author leiteng
 *         Date 2015/9/3.
 */
public class OutputHelper {

    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private static Gson gson = new Gson();
    private static long lastTime = 0;

    public static void outputSource(String path, String realTime, String url, String source) {
        String urlHex = "";
        if (CommonHelper.isNotBlank(url)) {
            urlHex = DigestUtils.md5Hex(url.getBytes());
        }

        Date date = new Date();
        long time = date.getTime();
        // 最多每10分钟保存一次快照
        if (lastTime == 0 || time - lastTime > TimeUnit.MINUTES.toMillis(10)) {
            lastTime = time;

            String fileName = getFullName(path, realTime, CommonHelper.formatDate(date, DATE_FORMAT), urlHex);
            File file = new File(fileName);

            try {
                if (!file.exists()) {
                    System.out.println(fileName);
                    file.createNewFile();
                }

                Files.write(source.getBytes(), file);
            } catch (IOException e) {
                System.out.println(Throwables.getRootCause(e));
            }
        }
    }

    public static void outputExtract(String path, String realTime, Multimap<String, RealTimeSourceDO> result) {

        Set<String> keys = result.keySet();

        Set<String> newFiles = Sets.newHashSet();

        for (String key : keys) {
            Collection<RealTimeSourceDO> sourceList = result.get(key);

            for (RealTimeSourceDO sourceDO : sourceList) {
                String fileName = getFullName(path, realTime, CommonHelper.formatDate(sourceDO.getTime(), DATE_FORMAT), key);
                File file = new File(fileName);

                try {
                    // 文件存在
                    if (file.exists()) {
                        // 之前就存在的文件
                        if (!newFiles.contains(fileName)) {
                            // 先删除
                            file.deleteOnExit();
                            // 创建新的文件，以供写入新的数据
                            file.createNewFile();
                        }
                    }
                    // 文件不存在，创建之
                    else {
                        file.createNewFile();
                        // 记录本次创建过的文件
                        newFiles.add(fileName);
                    }

                    Files.append(gson.toJson(sourceDO) + "\n", file, Charset.forName("UTF-8"));
                } catch (IOException e) {
                    System.out.println(Throwables.getRootCause(e).getMessage());
                }
            }
        }
    }

    private static String getFullName(String path, String dir, String time, String key) {
        return path + dir + "/" + key + "_" + time + ".txt";
    }
}
