package person.sykim.problembank.data.bank;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemBank {
    private static final String TAG = "ProblemBank";
    public String key;
    public String name;
    public String description;
    public int minPage;
    public int maxPage;
    public ProblemTemplate normal;
    public ProblemTemplate tutorial;
    public Login login;
    public String url_host;
    public String url_login;
    public String url_logout;
    public String url_normal;
    public String url_detail;

    private String username;

    private Map<String, String> cookies = new HashMap<>();

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    private void save(Map<String, String> cookies) {
        this.cookies.putAll(cookies);
        Prefs.putString("cookie-"+key, new Gson().toJson(this.cookies));
    }

    private Map<String, String> load() {
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        return new Gson().fromJson(Prefs.getString("cookie-"+key, "{}"), type);
    }

    public List<Problem> loadProblemList() {
        boolean tutorial = Prefs.getBoolean(key+"-tutorial", false);
        if (tutorial) {
            return loadTutorialList();
        } else {
            return loadNormalList();
        }
    }

    public List<Problem> loadNormalList() {
        try {
            Connection connection = Jsoup.connect(normal.url)
                    .timeout(10000);

            switch (normal.method) {
                case "POST":
                    connection.method(Connection.Method.POST);
                    break;
                case "GET":
                default:
                    connection.method(Connection.Method.GET);
                    break;
            }
            Connection.Response response = connection
                    .cookies(load())
                    .execute();

            save(response.cookies());

            Log.d(TAG, "getProblems: done: " + cookies);

            // parse problem list html....
            Document document = response.parse();

            ArrayList<Problem> problems = new ArrayList<>();
            Log.d(TAG, "printList: url: "+ normal.url);
            if (document == null) {
                Log.e(TAG, "parseProblemList: document is null");
                return problems;
            }

            Log.d(TAG, "getProblems: problem");

            Elements trElements = document.select(normal.list);
            for (Element trElement : trElements) {
                Problem problem = new Problem();

                String code = normal.code.get(trElement);
                problem.code = Integer.parseInt(code);

                problem.title = trElement.select(normal.title).first().text();

                String status = "none";
                Elements labelElements = trElement.select(normal.success);
                if (labelElements.size() > 0) {
                    status = "success";
                }
                labelElements = trElement.select(normal.failure);
                if (labelElements.size() > 0) {
                    status = "failure";
                }
                problem.status = status;

//                String solveCount = trElement.select(normal.solveCount).first().text();
//                problem.solveCount = Integer.parseInt(solveCount);

//                String totalCount = trElement.select(normal.totalCount).first().text();
//                problem.totalCount = Integer.parseInt(totalCount);

//                @SuppressWarnings("RegExpRedundantEscape")
//                String ratio = trElement.select(normal.ratio).first().text().replaceAll("[^\\.|\\d]", "");
//                problem.ratio = Double.parseDouble(ratio);

                problems.add(problem);
            }

            Element usernameElement = document.select(normal.username).first();
            if (usernameElement != null) {
                username = usernameElement.text();
            } else {
                username = null;
            }

            minPage = 1;
            String page = normal.page.get(document);
            maxPage = Integer.parseInt(page);

            return problems;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Problem> loadTutorialList() {
        try {
            Connection connection = Jsoup.connect(tutorial.url)
                    .timeout(10000);

            switch (tutorial.method) {
                case "POST":
                    connection.method(Connection.Method.POST);
                    break;
                case "GET":
                default:
                    connection.method(Connection.Method.GET);
                    break;
            }
            Connection.Response response = connection
                    .cookies(load())
                    .execute();

            save(response.cookies());

            Log.d(TAG, "getProblems: done: " + cookies);

            // parse problem list html....
            Document document = response.parse();

            ArrayList<Problem> problems = new ArrayList<>();
            Log.d(TAG, "printList: url: "+ tutorial.url);
            if (document == null) {
                Log.e(TAG, "parseProblemList: document is null");
                return problems;
            }

            Log.d(TAG, "getProblems: problem");

            Elements trElements = document.select(tutorial.list);
            for (Element trElement : trElements) {
                Problem problem = new Problem();

                String code = tutorial.code.get(trElement);
                problem.code = Integer.parseInt(code);

                problem.title = trElement.select(tutorial.title).first().text();

                String status = "none";
                Elements labelElements = trElement.select(tutorial.success);
                if (labelElements.size() > 0) {
                    status = "success";
                }
                labelElements = trElement.select(tutorial.failure);
                if (labelElements.size() > 0) {
                    status = "failure";
                }
                problem.status = status;

//                String solveCount = trElement.select(normal.solveCount).first().text();
//                problem.solveCount = Integer.parseInt(solveCount);

//                String totalCount = trElement.select(normal.totalCount).first().text();
//                problem.totalCount = Integer.parseInt(totalCount);

//                @SuppressWarnings("RegExpRedundantEscape")
//                String ratio = trElement.select(normal.ratio).first().text().replaceAll("[^\\.|\\d]", "");
//                problem.ratio = Double.parseDouble(ratio);

                problems.add(problem);
            }

            Element usernameElement = document.select(tutorial.username).first();
            if (usernameElement != null) {
                username = usernameElement.text();
            } else {
                username = null;
            }

            minPage = 1;
            // todo: 페이지의 표시 숫자와 값이 달라 수정이 필요함
            // display: 10
            // value: a0
            String page = tutorial.page.get(document);
            maxPage = Integer.parseInt(page);

            return problems;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 웹페이지에 한번이라도 접속을 하게되면 유저이름에 대한 정보가 들어간다
     * @return
     */
    public String getUserName() {
        return username;
    }

    public boolean login(String username, String password) {
        try {
            Connection connection = Jsoup.connect(login.url)
                    .timeout(10000)
                    .method(Connection.Method.POST)
                    .data(login.username, username)
                    .data(login.password, password);

            for (String key : login.data.keySet()) {
                String value = login.data.get(key);
                Log.i(TAG, "login: data: "+key+": "+value);
                connection.data(key, value);
            }

            Connection.Response response = connection
                    .cookies(load())
                    .execute();

            save(response.cookies());

            Document document = response.parse();

            Element usernameElement = document.select(login.check).first();
            if (usernameElement == null || usernameElement.text().length() <= 0) {
                return true;
            }

        } catch (IOException e) {
            Log.e(TAG, "login: ", e);
        }
        return false;
    }
}
/*


    public ProblemDetail getProblemDetail(int code) {
        try {
            String url = String.format(Locale.KOREAN, "https://www.acmicpc.net/problem/%d", code);
            Connection.Response response = Jsoup.connect(url)
                    .timeout(5000)
                    .method(Connection.Method.GET)
                    .cookies( load() )
                    .execute();
            Document document = response.parse();
            ProblemDetail problemDetail = new ProblemDetail();

            Elements sectionElements = document.select("section");

            // description
            {
                Element descriptionElement = document.select("#description").first();
                Element headlineElement = descriptionElement.select("div.headline").first();
                Element problemDesciptionElement = descriptionElement.select("#problem_description").first();
                String content = problemDesciptionElement.html();
                content = content.replace(" ", "&nbsp;").replace("\n", "<br/>");
                content = content.replace("</p><br/>", "</p>").replace("</p>&nbsp;<br/>", "</p>");
                problemDetail.addText(headlineElement.text(), content);
                Log.d(TAG, "getProblemDetail: " + content);
            }

            // input
            {
                Element inputElement = document.select("#input").first();
                Element headlineElement = inputElement.select("div.headline").first();
                Element problemInputElement = inputElement.select("#problem_input").first();
                problemDetail.addText(headlineElement.text(), problemInputElement.html());
            }

            // output
            {
                Element outputElement = document.select("#output").first();
                Element headlineElement = outputElement.select("div.headline").first();
                Element problemOutputElement = outputElement.select("#problem_output").first();
                problemDetail.addText(headlineElement.text(), problemOutputElement.html());
            }

            // sampleinput
            {
                for (int i=1; i<=5; i++) {
                    Element sampleInputElement = document.select("#sampleinput"+i).first();
                    if (sampleInputElement == null) {
                        continue;
                    }
                    Element headlineElement = sampleInputElement.select("div.headline").first();
                    Elements sampleDataElements = sampleInputElement.select("pre.sampledata");
                    for (int j=0; j<sampleDataElements.size(); j++) {
                        Element sampleDataElement = sampleDataElements.get(j);
                        problemDetail.addCode(
                                headlineElement.text() + " " + (i+1),
                                sampleDataElement.text()
                        );
                    }
                }
            }

            // sampleoutput
            {
                for (int i=1; i<=5; i++) {
                    Element sampleOutputElement = document.select("#sampleoutput"+i).first();
                    if (sampleOutputElement == null) {
                        continue;
                    }
                    Element headlineElement = sampleOutputElement.select("div.headline").first();
                    Elements sampleDataElements = sampleOutputElement.select("pre.sampledata");
                    for (int j=0; j<sampleDataElements.size(); j++) {
                        Element sampleDataElement = sampleDataElements.get(j);
                        problemDetail.addCode(
                                headlineElement.text() + " " + (j+1),
                                sampleDataElement.text()
                        );
                    }
                }
            }

            // hint
            {
                Element hintElement = document.select("#hint").first();
                Element problemHintElement = hintElement.select("#problem_hint").first();
                String hint = problemHintElement.text();
                if (hint.length() > 0) {
                    problemDetail.setHelp(problemHintElement.text());
                }
            }

            return problemDetail;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProblemHistoryItem submit(int code, String source) {
        ProblemHistoryItem historyItem = new ProblemHistoryItem();
        try {
            String url = String.format(Locale.KOREAN, "https://www.acmicpc.net/submit/%d", code);
            Connection connection = Jsoup.connect(url);

            // get csrf
            Connection.Response responseGet = connection
                    .cookies( load() )
                    .method(Connection.Method.GET)
                    .execute();
            save( responseGet.cookies() );
            Document documentGet = responseGet.parse();
            Element elementCsrf = documentGet.select("input[name=csrf_key]").first();
            String csrfKey = elementCsrf != null ? elementCsrf.val() : "";
            System.out.println(csrfKey);

            // post submit
            Connection.Response responsePost = connection
                    .cookies( load() )
                    .method(Connection.Method.POST)
                    .data("problem_id", code+"")
                    .data("language", "3") // java
                    .data("code_open", "close") // 비공개
                    .data("source", source)
                    .data("csrf_key", csrfKey)
                    .execute();
            save( responsePost.cookies() );

            Document document = responsePost.parse();

            Elements historyElements = document.select("#status-table tbody>tr");
            Elements tdsElements = historyElements.first().select("td");

            historyItem = toHistoryItem(tdsElements);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return historyItem;
    }

    private ProblemHistoryItem toHistoryItem(Elements tdsElements) {
        ProblemHistoryItem historyItem = new ProblemHistoryItem();

        // id
        Element idElement = tdsElements.get(0);
        historyItem.setId(Integer.parseInt(idElement.text()));

        // userid
        Element useridElement = tdsElements.get(1);
        historyItem.setUserId(useridElement.text());

        // code
        Element codeElement = tdsElements.get(2);
        historyItem.setCode(Integer.parseInt(codeElement.text()));
        Element codeLinkElement = codeElement.children().first();
        historyItem.setName(codeLinkElement.attr("data-original-title"));

        // result
        Element resultElement = tdsElements.get(3);
        historyItem.setResult(resultElement.text());
//            resultElement.select("spen.result-text").first()
//                    .children().first().attr("class");

        // memory
        Element memoryElement = tdsElements.get(4);
        historyItem.setMemory(memoryElement.text());

        // runningTime
        Element runningTimeElement = tdsElements.get(5);
        historyItem.setRunningTime(runningTimeElement.text());

        // language
        Element languageElement = tdsElements.get(6);
        historyItem.setLanguage(languageElement.text());

        // length
        Element lengthElement = tdsElements.get(7);
        historyItem.setLength(lengthElement.text());

        // sendTime
        Element sendTimeElement = tdsElements.get(8);
        Log.i(TAG, "sendTimeElement:"+sendTimeElement);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 M월 d일 H시 mm분 ss초", Locale.KOREA);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        try {
            Date date = dateFormat.parse(sendTimeElement.children().first().attr("title"));

            historyItem.setSendTime(date);
            Log.i(TAG, "sendTime:"+historyItem.getSendTime());
        } catch (ParseException e) {
            e.printStackTrace();
            historyItem.setSendTime(new Date());
        }

        return historyItem;
    }

    @Override
    public List<ProblemHistoryItem> getHistory(int pageData, Problem problem) {

        Document document;
        try {
            HashMap<String,String> requestParam = new HashMap<>();
            requestParam.put("user_id", userName != null ? userName : "");
            if (problem != null) requestParam.put("problem_id", problem.getCode()+"");
            if (pageData > 0) requestParam.put("top", pageData+"");
            Connection.Response response = Jsoup.connect(URL_HISTORY)
                    .timeout(5000)
                    .method(Connection.Method.GET)
                    .data(requestParam)
                    .cookies( load() )
                    .execute();
            save( response.cookies() );
            document = response.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ArrayList<ProblemHistoryItem> histories = new ArrayList<>();
        Elements historyElements = document.select("#status-table tbody>tr");
        for (Element historyElement : historyElements) {
            Elements tdsElements = historyElement.select("td");

            ProblemHistoryItem historyItem = toHistoryItem(tdsElements);

            histories.add(historyItem);
        }
        return histories;
    }
*/
