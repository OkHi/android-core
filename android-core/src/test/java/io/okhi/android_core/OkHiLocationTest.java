package io.okhi.android_core;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

import io.okhi.android_core.models.OkHiLocation;

public class OkHiLocationTest {
    private final String id = "id";
    private final double lat = 1.4567;
    private final double lon = 36.6789;
    private final String placeId = "place";
    private final String plusCode = "plusCode";
    private final String propertyName = "propertyName";
    private final String streetName = "streetName";
    private final String title = "title";
    private final String subtitle = "subtitle";
    private final String directions = "directions";
    private final String otherInformation = "otherInformation";
    private final String url = "url";
    private final String streetViewPanoId = "streetViewPanoId";
    private final String streetViewPanoUrl = "streetViewPanoUrl";
    private final String userId = "userId";
    private final String photo = "photo";
    private final String propertyNumber = "propertyNumber";
    private final String country = "country";
    private final String state = "state";
    private final String city = "city";
    private final String displayTitle = "displayTitle";
    private final String countryCode = "countryCode";
    private final String neighborhood = "neighborhood";
    OkHiLocation location;
    OkHiLocation base_location;
    OkHiLocation.Builder builder;

    @Before
    public void set_up_location(){
        builder = new OkHiLocation.Builder(id, lat, lon);
        builder.setCity(city);
        builder.setCountry(country);
        builder.setCountryCode(countryCode);
        builder.setDirections(directions);
        builder.setNeighborhood(neighborhood);
        builder.setDisplayTitle(displayTitle);
        builder.setPhoto(photo);
        builder.setOtherInformation(otherInformation);
        builder.setPlaceId(placeId);
        builder.setPlusCode(plusCode);
        builder.setPropertyName(propertyName);
        builder.setPropertyNumber(propertyNumber);
        builder.setState(state);
        builder.setStreetViewPanoId(streetViewPanoId);
        builder.setStreetViewPanoUrl(streetViewPanoUrl);
        builder.setUrl(url);
        builder.setUserId(userId);
        builder.setSubtitle(subtitle);
        builder.setTitle(title);
        builder.setStreetName(streetName);
        location = builder.build();
    }

    @Test
    public void test_base_constructor(){
        base_location = new OkHiLocation(id, lat, lon);
        assertThat(base_location.getId()).isEqualTo(id);
        assertThat(base_location.getLat()).isEqualTo(lat);
        assertThat(base_location.getLon()).isEqualTo(lon);

        assertThat(base_location).isInstanceOf(OkHiLocation.class);
        assertThat(builder).isInstanceOf(OkHiLocation.Builder.class);
    }

    @Test
    public void test_get_city(){
        assertThat(location.getCity()).isEqualTo(city);
    }

    @Test
    public void test_get_lat(){
        assertThat(location.getLat()).isEqualTo(lat);
    }

    @Test
    public void test_get_lon(){
        assertThat(location.getLon()).isEqualTo(lon);
    }

    @Test
    public void test_get_title(){
        assertThat(location.getTitle()).isEqualTo(title);
    }

    @Test
    public void test_get_display_title(){
        assertThat(location.getDisplayTitle()).isEqualTo(displayTitle);
    }

    @Test
    public void test_get_directions(){
        assertThat(location.getDirections()).isEqualTo(directions);
    }

    @Test
    public void test_get_other_information(){
        assertThat(location.getOtherInformation()).isEqualTo(otherInformation);
    }

    @Test
    public void test_get_place_id(){
        assertThat(location.getPlaceId()).isEqualTo(placeId);
    }

    @Test
    public void test_get_plus_code(){
        assertThat(location.getPlusCode()).isEqualTo(plusCode);
    }

    @Test
    public void test_get_street_view_panoid(){
        assertThat(location.getStreetViewPanoId()).isEqualTo(streetViewPanoId);
    }

    @Test
    public void test_get_street_view_url(){
        assertThat(location.getStreetViewPanoUrl()).isEqualTo(streetViewPanoUrl);
    }

    @Test
    public void test_get_street_name(){
        assertThat(location.getStreetName()).isEqualTo(streetName);
    }

    @Test
    public void test_get_sub_title(){
        assertThat(location.getSubtitle()).isEqualTo(subtitle);
    }

    @Test
    public void test_get_url(){
        assertThat(location.getUrl()).isEqualTo(url);
    }

    @Test
    public void test_get_user_id(){
        assertThat(location.getUserId()).isEqualTo(userId);
    }

    @Test
    public void test_get_photo(){
        assertThat(location.getPhoto()).isEqualTo(photo);
    }

    @Test
    public void test_get_country_code(){
        assertThat(location.getCountryCode()).isEqualTo(countryCode);
    }

    @Test
    public void test_get_property_number(){
        assertThat(location.getPropertyNumber()).isEqualTo(propertyNumber);
    }

    @Test
    public void test_get_country(){
        assertThat(location.getCountry()).isEqualTo(country);
    }

    @Test
    public void test_get_state(){
        assertThat(location.getState()).isEqualTo(state);
    }

    @Test
    public void test_get_neighbourhood(){
        assertThat(location.getNeighborhood()).isEqualTo(neighborhood);
    }

    @Test
    public void test_get_property_name(){
        assertThat(location.getPropertyName()).isEqualTo(propertyName);
    }

}
