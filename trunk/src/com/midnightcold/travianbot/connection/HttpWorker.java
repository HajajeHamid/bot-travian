package com.midnightcold.travianbot.connection;

import com.midnightcold.travianbot.exceptions.LoadHttpPageException;
import com.midnightcold.travianbot.system.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class HttpWorker {

    private String host;
    private int port;
    private HtmlRequestResult lastRequestResult;
    private Cookie[] cookies = new Cookie[0];

    private String getHeadsAsString(String messageBody) {

        String headers = "";

        headers += "Host: " + host + "\n";
        headers += "User-Agent: TravianBot (Win16) TravianBot/20111016 TravianBot/0.0.1\n";
        headers += "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n";
        headers += "Accept-Language: ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3\n";
        headers += "Accept-Encoding: deflate\n";
        headers += "Accept-Charset: windows-1251,utf-8;q=0.7,*;q=0.7\n";
        headers += "Connection: close\n";

        if (cookies.length != 0) {
            headers += "Cookie: " + getCookiesAsString(cookies) + "\n";
        }

        if (messageBody.length() != 0) {
            headers += "Content-length: " + messageBody.length() + "\nContent-Type: application/x-www-form-urlencoded;\n";
        }

        return headers;


    }

    private Cookie[] getCookieByHeaders(Header[] headers) {

        ArrayList<Cookie> cookiesList = new ArrayList<Cookie>();

        for (int i = 0; i < headers.length; i++) {

            if (headers[i].name.equals("Set-Cookie")) {

                String[] cookie = headers[i].value.split("=");
                cookiesList.add(new Cookie(cookie[0], cookie[1]));

            }

        }

        Cookie[] localCookies = cookiesList.toArray(new Cookie[cookiesList.size()]);

        return localCookies;

    }

    private void updateCookie(Cookie[] responseCookies) {

        ArrayList<Cookie> newCookiesList = new ArrayList<Cookie>();

        for (int i = 0; i < responseCookies.length; i++) {
            int j;

            for (j = 0; j < this.cookies.length; j++) {
                if (responseCookies[i].name.equals(this.cookies[j].name)) {
                    this.cookies[j].value = responseCookies[i].value;
                    break;
                }
            }

            if (j == this.cookies.length) {
                newCookiesList.add(responseCookies[i]);
            }
        }

        Cookie[] newCookies = newCookiesList.toArray(new Cookie[newCookiesList.size()]);

        Cookie[] localCookies = new Cookie[newCookies.length + this.cookies.length];

        System.arraycopy(newCookies, 0, localCookies, 0, newCookies.length);
        System.arraycopy(this.cookies, 0, localCookies, newCookies.length, this.cookies.length);

        this.cookies = localCookies;

    }

    private String getCookiesAsString(Cookie[] cookies) {

        String cookiesString = "";

        for (int i = 0; i < cookies.length; i++) {

            cookiesString += cookies[i].name + "=" + cookies[i].value + "; ";

        }

        cookiesString = cookiesString.substring(0, cookiesString.length() - 2);

        return cookiesString;

    }

    /**
     * @return the lastRequestResult
     */
    protected HtmlRequestResult getLastRequestResult() {
        return lastRequestResult;
    }

    protected class Header {

        String name;
        String value;

        private Header(String name, String value) {

            this.name = name;
            this.value = value;

        }
    }

    protected class Cookie {

        String name;
        String value;

        private Cookie(String name, String value) {

            this.name = name;
            this.value = value;

        }
    }

    protected class HtmlRequestResult {

        private Header[] headers;
        private Cookie[] cookies;
        private String html;
        private int code;

        /**
         * @return the headers
         */
        public Header[] getHeaders() {
            return headers;
        }

        /**
         * @return the cookies
         */
        public Cookie[] getCookies() {
            return cookies;
        }

        /**
         * @return the html
         */
        public String getHtml() {
            return html;
        }

        /**
         * @return the code
         */
        public int getCode() {
            return code;
        }
    }

    protected HttpWorker(String host) {

        this.host = host;
        this.port = 80;
        Logger.log.info("Создали подключени к " + this.host + ":" + this.port);

    }

    protected void makeConnect(String method, String url, String messageBody) throws LoadHttpPageException {
        try {

            Socket socket = new Socket(host, port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String headsString = getHeadsAsString(messageBody);

            String request = method + " " + url + " HTTP/1.1\n" + headsString + "\n" + messageBody;
            String response;
            String html = "";

            out.println(request);

            ArrayList<Header> gottenHeadsList = new ArrayList<Header>();

            boolean isNowHeaders = false;
            boolean firstString = true;

            int code = 0;

            while ((response = in.readLine()) != null) {

                if (firstString) {

                    String[] parts = response.split(" ");
                    code = Integer.parseInt(parts[1]);

                    firstString = false;
                    isNowHeaders = true;
                    continue;

                }

                if (response.equals("")) {
                    isNowHeaders = false;
                }

                if (isNowHeaders) {

                    String[] header = response.split(":");
                    gottenHeadsList.add(new Header(header[0], header[1].trim()));

                } else {

                    html += response + "\n";

                }

            }

            Header[] headers = gottenHeadsList.toArray(new Header[gottenHeadsList.size()]);

            Cookie[] localCookies = getCookieByHeaders(headers);

            HtmlRequestResult result = new HtmlRequestResult();

            result.html = html;
            result.headers = headers;
            result.cookies = localCookies;
            result.code = code;

            lastRequestResult = result;

            updateCookie(localCookies);

            for (int i = 0; i < cookies.length; i++) {
                System.out.println(cookies[i].name + " " + cookies[i].value);
            }

        } catch (UnknownHostException ex) {
            Logger.log.error("Неизвестный хост при загрузке страницы Побробнее " + ex.getMessage());
            throw new LoadHttpPageException();
        } catch (IOException ex) {
            Logger.log.error("Ошибка вводы\\вывода при загрузке страницы Побробнее " + ex.getMessage());
            throw new LoadHttpPageException();
        }

    }

    protected class RequestData {

        String name;
        String value;

        protected RequestData(String name, String value) {

            this.name = name;
            this.value = value;

        }

        RequestData() {
        }
    }
}