    package URLValidation;

import org.apache.commons.validator.UrlValidator;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;
import serviceTesting.smokeCommon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

    /**
 * Created by vinaykumarp on 5/8/2017.
 */
public class compareHTML extends smokeCommon {

    private String baseURL1 = "http://dbsweb-uat-sg-www.uat.dbs.com";
//    private String baseURL2 = "http://dbsweb-s02.dbs.com.sg";
    private String baseURL2 = "http://dbsweb-s02-prv.dbs.com.sg";

    private String URL1 = "/i-bank/default.page";
    private static List<String> validHref = new ArrayList<String>();

    public void URLUpdate() {
        Properties prop = new Properties();
        InputStream input;
        try {
            input = new FileInputStream(".//resources//ServiceDetails.properties");
            prop.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } // load a properties file
        catch (IOException e) {
            e.printStackTrace();
        }
        baseURL1=prop.getProperty("baseURL1");
        baseURL2=prop.getProperty("baseURL2");
        URL1=prop.getProperty("URL1");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void aHrefTestValidation() throws Throwable {
        try {
            getReporter().initTestCase(this.getClass().getName().substring(0, this.getClass().getName().lastIndexOf(".")), "Link href updated Test Validation", "Link href Test Validation", false);
        } catch (Exception ex) {
            System.out.println("Test case got skipped.....");
        }
        URLUpdate();
        System.setProperty("http.proxyHost", "10.5.1.26");
        System.setProperty("http.proxyPort", "8080");
        System.setProperty("https.proxyHost", "10.5.1.26");
        System.setProperty("https.proxyPort", "8080");
        try {
//            captureLinks(baseURL1 + URL1);
            captureLinks(baseURL2 + URL1);
            UrlValidator urlValidator = new UrlValidator();
            int validCount =0;
            for(String href:validHref){
                if (urlValidator.isValid(href) || (href.contains("www.") && ((href.contains(".co")) ||  href.contains(".gov")||  href.contains(".org")) ) || href.contains("itunes.apple.com") || ( (href.contains("templatedata")) && (href.contains(".xml")))) {

                }else if (href.trim().isEmpty() || href.trim().equalsIgnoreCase("/#")||href.trim().startsWith("#") || href.startsWith("$") || href.endsWith("]") || href.endsWith(")") || href.endsWith(";")|| href.endsWith(".pdf")) {

                } else if(href.contains("mailto") || href.contains("video.php") || href.contains("tel:") || href.contains("url=")|| href.endsWith(".zip") ){

                } else {
                    if (! (href.trim().startsWith("/i-bank")  || href.trim().startsWith("/i-bank/"))) {
                        System.out.println("Hyper link is not start with /i-bank" + href);
                    } else {
                        htmlSourceValidator(href);
                        validCount++;
                        Document doc1 = getHTMLSourceCode(baseURL2 + href);
                        if (doc1 != null) {
                            Elements linksHref12 = doc1.select("img");
                            for (Element link1 : linksHref12) {
                                String href1 = link1.attr("src");
                                System.out.println("Link hyperlink :" + link1.attr("src"));

                                if (urlValidator.isValid(href1)) {
                                    try {
                                        imageValidatorURL(baseURL2 + href, href1);
                                    } catch (HttpStatusException hse) {
                                        System.out.println(href1 + " page has been opened, and got the status exception, status code" + hse.getStatusCode() + " , exception message is " + hse.getMessage());
                                        //   getReporter().failureReport(href +" page open", "status code" + hse.getStatusCode() + " , exception message is " + hse.getMessage());
                                    } catch (IOException ex) {
                                        //   getReporter().failureReport(href +" page open", " , exception message is " + ex.getMessage());
                                    }
                                } else if (href1.trim().isEmpty() || href1.trim().startsWith("#") || href1.startsWith("$") || href1.endsWith("]") || href1.endsWith(")") || href1.endsWith(";")) {
                                    //getReporter().SuccessReport("Href attribute", " is not valid" +href );
                                } else {

                                    try {
                                        imageValidatorURL(baseURL2 + href, baseURL2 + href1);
                                    } catch (HttpStatusException hse) {
                                        System.out.println(baseURL2 + href1 + " page has been opened, and got the status exception, status code" + hse.getStatusCode() + " , exception message is " + hse.getMessage());
    //                                getReporter().failureReport(baseURL1+href +" page open", "status code" + hse.getStatusCode() + " , exception message is " + hse.getMessage());
                                    } catch (IOException ex) {
    //                                getReporter().failureReport(baseURL1+href  +" page open", " , exception message is " + ex.getMessage());
                                    }

                                }
                            }
                        }
                    }
                }
            }
            getReporter().SuccessReport("Total Links" , " are :" + validHref.size() + "  , and compared links count is : " + validCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private boolean captureLinks(String URLs) throws Throwable{
        Document doc1=null;
        URL aURL = new URL(URLs);
        if(validHref.contains(aURL.getAuthority())){
            return true;
        }
        try {
            doc1= getHTMLSourceCode(URLs);
            //getReporter().SuccessReport(URLs, " page has been opened, and got the valid status ");
            if(doc1 != null ) {
                Elements elements = doc1.select("a");
//                getReporter().SuccessReport(URLs, " page has been opened, and got the valid status ");
                for (Element link1 : elements) {
                    UrlValidator urlValidator = new UrlValidator();
                    String href = link1.attr("href");
                    if (!validHref.contains(href)) {
                        System.out.println(URLs + " - Hyperlink URL is :" + href);
                        if(validHref.size() > 2500) {
                            return false;
                        }

                        if (urlValidator.isValid(href) || (href.contains("www.") && ((href.contains(".co")) ||  href.contains(".gov")||  href.contains(".org")) ) || href.contains("itunes.apple.com") || ( (href.contains("templatedata")) && (href.contains(".xml")))) {

                        }else if (href.trim().isEmpty() || href.trim().equalsIgnoreCase("/#")||href.trim().startsWith("#") || href.startsWith("$") || href.endsWith("]") || href.endsWith(")") || href.endsWith(";")|| href.endsWith(".pdf")) {

                        } else if(href.contains("mailto") || href.contains("video.php") || href.contains("tel:") || href.contains("url=")|| href.endsWith(".zip") ){

                        } else {
                            if (! (href.trim().startsWith("/i-bank")  || href.trim().startsWith("/i-bank/"))) {
                                System.out.println("Hyper link is not start with /i-bank" + href);
                            } else {
                                validHref.add(href);
                                captureLinks(aURL.getProtocol() + "://" + aURL.getAuthority() + href);
                            }
                        }
                    }
                }
            }
        } catch(Exception hse) {
            System.out.println(URLs+ " page has been opened, and got the exception, exception message is " + hse.getMessage());
            // getReporter().failureReport(URLs, " page has been opened, and got the exception, exception message is " + hse.getMessage());
            return false;
        }

        return true;
    }

    public void htmlSourceValidator(String url) throws  Throwable{
        Document doc1 = null, doc2 = null;

        try {
            doc1= getHTMLSourceCode1(baseURL1 + url);
        } catch (Exception hse) {
            System.out.println(baseURL1 + url + " page has been opened, and got the exception message is " + hse.getMessage());
            getReporter().failureReport(baseURL1 + url+ " HTML source", " page has been opened, and got the exception message is " + hse.getMessage());
        }
        try {
            doc2= getHTMLSourceCode1(baseURL2 + url);
//            getReporter().SuccessReport(baseURL2 + url, " page has been opened, and got the valid status ");
        } catch (Exception hse) {
            System.out.println(baseURL2 + URL1 + " page has been opened, and got the exception message is " + hse.getMessage());
            getReporter().failureReport(baseURL2 + url+ " HTML source", " page has been opened, and got the exception message is " + hse.getMessage());
        }
        if( doc1 != null && doc2 != null){
            if (doc1.equals(doc2)) {
                System.out.println("Both HTML Source codes are same");
                getReporter().SuccessReport(baseURL2 + url + " HTML source", "is matched with HTML Code of " + baseURL1 + url);
            } else {
                System.out.println("Both HTML Source codes are not same");
                getReporter().failureReport(baseURL2 + url + " HTML source", "is not matched with HTML Code of " + baseURL1 + url);
            }
        }

    }

    public Document getHTMLSourceCode1(String args) throws Exception, Throwable {
        System.setProperty("http.proxyHost", "10.5.1.26");
        System.setProperty("http.proxyPort", "8080");
        System.setProperty("https.proxyHost", "10.5.1.26");
        System.setProperty("https.proxyPort", "8080");
        Document doc1 = null;
        URL u;
        InputStream is = null;
        DataInputStream dis;
        String s;
        args =args.replace(" ", "%20");
        try {
            u = new URL(args);
            is = u.openStream();
            dis = new DataInputStream(new BufferedInputStream(is));
            String line = null;
            while ((s = dis.readLine()) != null) {
//                System.out.println(s);
                line = line + s;
            }
            doc1 = Jsoup.parse(line);
        } catch (MalformedURLException mue) {
            System.err.println("Ouch - a MalformedURLException happened.");
            getReporter().failureReport(args, " is unable : MalformedURLException to open " +mue.getMessage() );
            mue.printStackTrace();
        } catch (IOException ioe) {
            System.err.println("Oops- an IOException happened.");
            getReporter().failureReport(args, " is unable : IOException to open " +ioe.getMessage() );
            ioe.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ioe) {

            }catch (Exception ioe) {

            }
        }
        return doc1;
    }

    public Document getHTMLSourceCode(String args) throws Exception, Throwable {
        System.setProperty("http.proxyHost", "10.5.1.26");
        System.setProperty("http.proxyPort", "8080");
        System.setProperty("https.proxyHost", "10.5.1.26");
        System.setProperty("https.proxyPort", "8080");
        Document doc1 = null;
        URL u;
        InputStream is = null;
        DataInputStream dis;
        String s;
        args =args.replace(" ", "%20");
        try {
            u = new URL(args);
            is = u.openStream();
            dis = new DataInputStream(new BufferedInputStream(is));
            String line = null;
            while ((s = dis.readLine()) != null) {
//                System.out.println(s);
                line = line + s;
            }
            doc1 = Jsoup.parse(line);
        } catch (MalformedURLException mue) {
            System.err.println("Ouch - a MalformedURLException happened.");
//            getReporter().failureReport(args, " is unable : MalformedURLException to open " +mue.getMessage() );
            mue.printStackTrace();
        } catch (IOException ioe) {
            System.err.println("Oops- an IOException happened.");
//            getReporter().failureReport(args, " is unable : IOException to open " +ioe.getMessage() );
            ioe.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ioe) {

            }catch (Exception ioe) {

            }
        }
        return doc1;
    }
    private void imageValidatorURL(String basedURL, String href) throws Throwable {

        try {
            if(href.endsWith(".svg")){
//                getReporter().SuccessReport(basedURL, "Image :-" + href + " is a SVG image url." );
            } else {
                href =href.replace(" ", "%20");
                URL url11= new URL(href);
//            URL url11= new URL(href);
//            BufferedImage img = ImageIO.read(url11);
                InputStream is = url11.openConnection().getInputStream();
                BufferedImage bitmap = ImageIO.read(is);
                if(bitmap.getHeight() > 0){
//                    getReporter().SuccessReport(basedURL, "Image :-" + url11.toString() + " is a valid image." );
                } else {
                    getReporter().failureReport(basedURL,"Image :-" + url11.toString() + " is not a valid image." );
                }
            }
        } catch (Exception e) {
            getReporter().failureReport(basedURL, "Image :-" + href +  " is not a valid image, exception:" + e.getMessage() + " , exception:"+ e.toString() + ", and stacktrace is "+ e.getStackTrace());
        }
    }
}
