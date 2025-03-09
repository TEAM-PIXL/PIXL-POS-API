package pos.api.teampixl.org.models.logs.network;

public class ServerLocation {
    private String country;
    private String region;
    private String city;

    protected String getCountry() {
        return country;
    }

    protected void setCountry(String country) {
        this.country = country;
    }

    protected String getRegion() {
        return region;
    }

    protected void setRegion(String region) {
        this.region = region;
    }

    protected String getCity() {
        return city;
    }

    protected void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ServerLocation{" +
                "country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
