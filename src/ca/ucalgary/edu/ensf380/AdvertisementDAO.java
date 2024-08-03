package ca.ucalgary.edu.ensf380;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementDAO {
    private List<Advertisement> advertisements;

    public AdvertisementDAO() {
        advertisements = new ArrayList<>();
        // Example advertisements with actual paths
        advertisements.add(new Advertisement("Fashion Sale", "media\\Fashion_Sale.jpeg"));
        advertisements.add(new Advertisement("Fitness Center Membership", "media\\Fitness_Center_Membership.jpeg"));
        advertisements.add(new Advertisement("Restaurant Promotion", "media\\Restaurant_Promotion.jpeg"));
        advertisements.add(new Advertisement("Tech Product Launch", "media\\Tech_Product_Launch.jpeg"));
        advertisements.add(new Advertisement("Travel Agency Deal", "media\\Travel_Agency_Deal.jpeg"));
    }

    public List<Advertisement> getAllAdvertisements() {
        return advertisements;
    }

    public void addAdvertisement(Advertisement ad) {
        advertisements.add(ad);
    }
}
