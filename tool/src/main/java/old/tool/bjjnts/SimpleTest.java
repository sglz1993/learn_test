package old.tool.bjjnts;

import com.ctrip.framework.apollo.core.utils.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: pengyue.du
 * @time: 2020/7/9 5:27 下午
 */
public class SimpleTest extends BaseHttp {

    @Test
    public void get() throws Exception {
        String url = "https://www.bjjnts.cn/lessonStudy/6/152";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Cookie", "GUIDE_MAP:=1593875312; Hm_lvt_83efb6da7f0d1882680d3ee8ad0d78f0=1593875313; PHPSESSID=d0h5tam5j8irk42l6p94brfmf2; acw_tc=2760827815943811567838836e066d94b81c32c064e7fc9607937208c754cf; Hm_lpvt_83efb6da7f0d1882680d3ee8ad0d78f0=1594381168");
        headerMap.put("Referer", "https://www.bjjnts.cn/userCourse");
        headerMap.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");
//        System.out.println(get(url, headerMap));
        Document document = Jsoup.parse(get(url, headerMap));
        Elements elements = document.getElementsByClass("course_study_sonmenu");
        for (Element element : elements) {
            System.out.println(element);
            List<Node> nodes = element.childNodes();
            for (Node node : nodes) {
                if (StringUtils.isBlank(node.toString())) {
                    continue;
                }
                System.out.println(node);
            }
        }
    }

}
