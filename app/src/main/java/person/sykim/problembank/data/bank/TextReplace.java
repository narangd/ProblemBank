package person.sykim.problembank.data.bank;

import org.jsoup.nodes.Element;

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
}
