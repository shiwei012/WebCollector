package com.david;

import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by david on 18/08/2017.
 */
public class Crawler {

    public static final int expire = 7 * 24 * 3600;
    public static final String work_dir = "/Users/david/myFile/data/crawler_data";

    public static void updateContent(List<String> urls) throws Exception {
        String now = Utils.date2Str(new Date());
        for (String url : urls) {
            if (StringUtils.isEmpty(url)) continue;
            List<String> lines = new ArrayList<String>(4);
            Document doc = Jsoup.connect(url).get();
            lines.add(doc.title());
            lines.add(url);
            lines.add(ContentExtractor.getContentByDoc(doc));
            lines.add(now);
            FileUtils.writeLines(new File(work_dir + "/data/" + Utils.getMD5(url) + ".crawlFile"), lines);
        }
    }

    public static void crawlerKeyword() throws Exception{
        List<String> lines = FileUtils.readLines(new File(work_dir + "/keyword.list"));
        int now = Utils.date2intSecond(new Date());
        for (int i = 0; i < lines.size(); i++) {
            String[] ss = lines.get(i).split(",");
            if (ss.length == 1 || (now - Utils.string2int(ss[2]) > expire)) {
                updateContent(WeixinSougou.getUrlsByKeyword(ss[0]));
                lines.set(i, ss[0] + "," + Utils.int2String(now));
            }
        }
        FileUtils.writeLines(new File(work_dir + "/keyword.list"), lines);
    }

    public static void main(String[] args) throws Exception {
        crawlerKeyword();
    }
}
