package service;

import CustomException.LocationsNotFoundException;
import dao.LocationDao;
import lombok.extern.slf4j.Slf4j;
import model.Location;
import model.User;

import java.util.List;

@Slf4j
public class LocationService {

    private final LocationDao locationDao = new LocationDao();

    public void insert(Location location) {
        locationDao.insert(location);
    }

    public List<Location> getLocationsByUser(User user) throws LocationsNotFoundException {
        return locationDao.getLocationsByUser(user);
    }

    public void deleteById(Long id) {
        locationDao.deleteById(id);
    }
}
