package com.david;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 18/08/2017.
 */
public class WeixinSougou {
    private static final String query_url = "http://weixin.sogou.com/weixin?type=2&s_from=input&query=";
    private static final int depth = 2;
    public static List<String> getUrlsByKeyword(String keyword) throws Exception {
        List<String> ret = new ArrayList<String>();
        if (!StringUtils.isNotEmpty(keyword)) return ret;
        keyword = URLEncoder.encode(keyword, "utf-8");
        String url = query_url + keyword + "&page=";
        for (int i = 1; i <= depth; i++) {
            Document doc = Jsoup.connect(url + i).get();
            Elements news = doc.getElementsByClass("news-list");
            Elements links = news.get(0).select("a[href][target=_blank][data-z!=art][class!=account][uigs*=article_title_]");
            for (Element link : links) {
                ret.add(link.attr("abs:href"));
            }
        }
        return ret;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getUrlsByKeyword("软件").size());
    }
}
