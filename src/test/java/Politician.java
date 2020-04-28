import java.util.Date;

public class Politician {
    private String id;
    private String country;
    private Date createdAt;
    private String name;
    private String position;
    private int risk;
    private int yob;

    public String getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public int getRisk() {
        return risk;
    }

    public int getYob() {
        return yob;
    }

    @Override
    public String toString() {
        return "Politician{" +
                "id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", risk=" + risk +
                ", yob=" + yob +
                '}';
    }
}
