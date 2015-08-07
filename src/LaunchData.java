/**
 * Created by Julia on 31.07.2015.
 */

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LaunchData {

    //public static final String DELIMITER_PATTERN = " ";

    private String launchDateStr;
    private String country;
    private String spaceport;
    private String rocketName;
    private String nssdcID;
    private String loadName;
    private String loadType;
    private Boolean isSuccessful;
    private Boolean isPilot;

    public LaunchData(String launchDateStr, String country, String spaceport,
                      String rocketName, String nssdcID, String loadName, String loadType,
                      Boolean isSuccessful, Boolean isPilot) {
        this.launchDateStr = launchDateStr;
        this.country = country;
        this.spaceport = spaceport;
        this.rocketName = rocketName;
        this.nssdcID = nssdcID;
        this.loadName = loadName;
        this.loadType = loadType;
        this.isSuccessful = isSuccessful;
        this.isPilot = isPilot;
    }


    public String getAttributes() {
        return
                String.format("|%s  |%s |%s |%s |%s |%s |%s |%s |%s |"
                        , launchDateStr
                        , country
                        , spaceport
                        , rocketName
                        , nssdcID
                        , loadName
                        , loadType
                        , isSuccessful
                        , isPilot
                );
    }
}

