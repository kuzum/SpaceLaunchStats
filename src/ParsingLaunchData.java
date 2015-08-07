/**
 * Created by Julia on 31.07.2015.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import java.io.FileNotFoundException;


class ParsingLaunchData {
    public static final String OUTPUT_DATE_PATTERN = "dd.MM.yyyy";
    public static final String INPUT_DATE_PATTERN = "yyyy-MM-dd";

    public static void main(String[] args) {

        Document doc;
        try {
            doc = Jsoup.connect("https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BA%D0%BE%D1%81%D0%BC%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D1%85_%D0%B7%D0%B0%D0%BF%D1%83%D1%81%D0%BA%D0%BE%D0%B2_%D0%B2_1961_%D0%B3%D0%BE%D0%B4%D1%83").get();
            String pageTitle = doc.title();
            System.out.println("pageTitle : " + pageTitle);
            String[] splitPageTitle = pageTitle.split(" в | году ");
            String currentLaunchYear = splitPageTitle[1];
            System.out.println("Year : " + currentLaunchYear);
            Element table = doc.getElementsByTag("table").first();
            //System.out.println("Class : " + table.className());
            Elements rows = table.getElementsByTag("tr");

            //Elements row = table.getElementsByTag("tr").;

            int[] testRowNums = {3, 4, 15};

            for (int index : testRowNums) {
                Element tempRow = rows.get(index);
                //System.out.println("tempRow : " + tempRow.text());

                if (tempRow.getElementsByTag("td").size() == 6) {
                    LaunchData currentLaunchData = parseAttributes(currentLaunchYear, tempRow);
                    System.out.println(currentLaunchData.getAttributes());
                }
            }
            ;


            /*for (Element row : rows) {
                Elements headers = row.getElementsByTag("th");
                for (Element header : headers) {
                    String colName = header.text();
                    System.out.println("Value : " + colName);
                }
                Elements cols = row.getElementsByTag("td");
                for (Element col : cols) {
                    String colValue = col.text();
                    System.out.println("Value : " + colValue);
                }
            }*/


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static LaunchData parseAttributes(String currentLaunchYear, Element row) {
        String rowStyle = row.attr("style");
        Boolean isSuccessful = true;
        if (rowStyle.equalsIgnoreCase("background:#ffD0D0")) {
            isSuccessful = false;
        }
        Elements cols = row.getElementsByTag("td");


        SimpleDateFormat launchDateFormat = new SimpleDateFormat(INPUT_DATE_PATTERN);
        //Pattern delimitedPattern = Pattern.compile(DELIMITER_PATTERN);
        //String[] launchDateArr = delimitedPattern.split(cols.first().text());
        //String dateInString = "1957-" + launchDateArr[0];


        String dateInString = String.format("%s-%s", currentLaunchYear, cols.first().getElementsByTag("span").first().text());
        /*String[] dateInArr = dateInString.split("-");
        int lengthDateInArr = dateInArr.length;
        System.out.println(lengthDateInArr);
        for (int index=6; index > lengthDateInArr; index--){
            dateInString = String.format("%s-00", dateInString);
        }*/
        /*while (dateInString.endsWith("-")) {
            System.out.println("String contain -");
            dateInString = dateInString.substring(0,dateInString.length()-1);
            System.out.println(dateInString);
        }*/
        //String dateInString = "1957-" + cols.first().text().substring(0, 14);
        //String dateInString = "1957-09-04-12-12-14";
        //System.out.println(dateInString);
        //launchDate = new Date();

        try {
            Date launchDate = launchDateFormat.parse(dateInString);


            //Pattern DatePattern = Pattern.compile("-| ");
            //String LaunchDateStr = cols.first().text();
            //String [] DateArr = DatePattern.split(LaunchDateStr);
            //launchDate = new Date((Integer.parseInt("1957"),Integer.parseInt(DateArr[0]),Integer.parseInt(DateArr[1]),Integer.parseInt(DateArr[2]),Integer.parseInt(DateArr[3]),Integer.parseInt(DateArr[4]));
            launchDateFormat = new SimpleDateFormat(OUTPUT_DATE_PATTERN);
            String launchDateStr = launchDateFormat.format(launchDate);
            //System.out.println("Date : " + launchDateStr);
            //System.out.println(launchDate.getYear());
            Elements SpaceportAttrs = cols.get(1).getElementsByTag("a");
            String country = SpaceportAttrs.first().attr("title");
            String spaceport = cols.get(1).text();
            //String spaceport = SpaceportAttrs.get(1).text();
            //String spaceportAttr1 = SpaceportAttrs.get(1).attr("title");
            //String spaceportAttr2 = SpaceportAttrs.get(2).text();
            //String spaceportAttr3 = SpaceportAttrs.get(2).attr("title");
            String rocketName = cols.get(2).text();
            String nssdcID = cols.get(3).text();
            String loadName = cols.get(4).getElementsByTag("a").first().text();
            Boolean isPilot = false;
            if (!(cols.get(4).getElementsByTag("b").isEmpty())) {
                isPilot = true;
            }
            String loadType = cols.get(5).text();

            return new LaunchData(launchDateStr, country, spaceport, rocketName, nssdcID, loadName, loadType, isSuccessful, isPilot);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}

