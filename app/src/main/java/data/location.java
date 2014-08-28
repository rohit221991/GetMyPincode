package data;

/**
 * Created by Rohit on 8/28/2014.
 */
public class location {

    private long id;
    private String place;
    private Double longitude;
    private Double latitude;
    private String pincode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPLACE() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setLongitude(String longitude ){
        this.longitude= Double.parseDouble(longitude);
    }

    public Double getLongitude(){
        return longitude;
    }

    public void setLatitude(String latitude){
        this.latitude= Double.parseDouble(latitude);
    }

    public Double getLatitude(){
        return latitude;
    }

    public void setPincode(String pincode){
        this.pincode = pincode;
    }
    public String getPincode(){
        return pincode;
    }



    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return place;
    }
}
