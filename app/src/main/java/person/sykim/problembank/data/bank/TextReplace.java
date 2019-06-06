package person.sykim.problembank.data.bank;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TextReplace {
    public String select;
    public String replace = ""; // regex
    public String attr;

    public String get(Element element) {
        Element selected = element.select(select).first();
        if (selected == null) {
            return null;
        }
        String code;
        if (attr != null) {
            code = selected.attr(attr);
        } else {
            code = selected.text();
        }
        return code.replaceAll(replace, "");
    }

    public static void main(String[] args) {
        String html = "<table width=\"209\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "\t<tbody><tr align=\"center\">\n" +
                "\t\t<td background=\"http://www.jungol.co.kr/theme/jungol/img/menu_01.png\" width=\"209\" height=\"73\">\n" +
                "\t\t\t<h2 style=\"font-size:15px;font-weight: bold;color:#ffffff\"><i class=\"fa fa-folder\"></i> \n" +
                "\t\t\t기초다지기\t\t\t</h2>\n" +
                "\t\t</td>\n" +
                "\t</tr>\n" +
                "\t<tr>\n" +
                "\t\t<td bgcolor=\"#35549a\" width=\"209\" valign=\"top\" align=\"center\">\n" +
                "\t\t\t<table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-left:5;\">\n" +
                "\t\t\t\t<tbody><tr>\n" +
                "\t\t\t<td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">01.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=1010\" style=\"color:#ffffff;\" target=\"_self\"><strong>출력</strong></a></td><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">02.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=1020\" style=\"color:#ffffff;\" target=\"_self\"><strong>입력</strong></a></td></tr><tr><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">03.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=1030\" style=\"color:#ffffff;\" target=\"_self\"><strong>연산자</strong></a></td><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">04.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=1040\" style=\"color:#ffffff;\" target=\"_self\"><strong>디버깅</strong></a></td></tr><tr><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">05.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=1050\" style=\"color:#ffffff;\" target=\"_self\"><strong>선택제어문</strong></a></td><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">06.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=1060\" style=\"color:#ffffff;\" target=\"_self\"><strong>반복제어문1</strong></a></td></tr><tr><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">07.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=1070\" style=\"color:#ffffff;\" target=\"_self\"><strong>반복제어문2</strong></a></td><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">08.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=1080\" style=\"color:#ffffff;\" target=\"_self\"><strong>반복제어문3</strong></a></td></tr><tr><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">09.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=1090\" style=\"color:#ffffff;\" target=\"_self\"><strong>배열1</strong></a></td><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">10.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=10a0\" style=\"color:#ffffff;\" target=\"_self\"><strong>배열2</strong></a></td></tr><tr><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">11.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=10b0\" style=\"color:#ffffff;\" target=\"_self\"><strong>함수1</strong></a></td><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">12.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=10c0\" style=\"color:#ffffff;\" target=\"_self\"><strong>함수2</strong></a></td></tr><tr><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">13.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=10d0\" style=\"color:#ffffff;\" target=\"_self\"><strong>함수3</strong></a></td><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">14.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=10e0\" style=\"color:#ffffff;\" target=\"_self\"><strong>문자열1</strong></a></td></tr><tr><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">15.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=10f0\" style=\"color:#ffffff;\" target=\"_self\"><strong>문자열2</strong></a></td><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">16.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=10g0\" style=\"color:#ffffff;\" target=\"_self\"><strong>구조체</strong></a></td></tr><tr><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">17.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=10h0\" style=\"color:#ffffff;\" target=\"_self\"><strong>포인터</strong></a></td><td style=\"font-size:12px;color:#ffffff;\" width=\"40%\" height=\"25\">18.<a href=\"/bbs/board.php?bo_table=pbank&amp;sca=10i0\" style=\"color:#ffffff;\" target=\"_self\"><strong>파일입출력</strong></a></td></tr><tr>\t\t\t\t</tr>\n" +
                "\t\t\t</tbody></table>\n" +
                "\t\t</td>\n" +
                "\t</tr>\n" +
                "\t<tr>\n" +
                "\t\t<td background=\"http://www.jungol.co.kr/theme/jungol/img/menu_02.png\" height=\"12\"></td>\n" +
                "\t</tr>\n" +
                "\t<tr>\n" +
                "\t\t<td width=\"209\" height=\"5\"></td>\n" +
                "\t</tr>\n" +
                "</tbody></table>";
        Document document = Jsoup.parse(html);
        Elements elements = document.select("table table tr:has(td):last-of-type");
        System.out.println(elements);
    }
}
