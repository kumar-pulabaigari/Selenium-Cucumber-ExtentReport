package automation.drivers;


public class secureCredentials {

    public static void secureData() {
        System.getProperties().put("http.proxyHost", "10.5.1.26");
        System.getProperties().put("http.proxyPort", "8080");
        System.getProperties().put("https.proxyHost", "10.5.1.26");
        System.getProperties().put("https.proxyPort", "8080");
        System.setProperty("http.proxyUser", "swarupmishra");
        System.setProperty("http.proxyPassword", "Shaswat123");
        System.setProperty("https.proxyUser", "swarupmishra");
        System.setProperty("https.proxyPassword", "Shaswat123");

    }


}
