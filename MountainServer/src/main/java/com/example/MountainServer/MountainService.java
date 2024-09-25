/**
 * Service layer for REST server
 * <p>
 * Handles the maintenance of the list of mountains, and how the list of mountains
 * is handled & added to.
 *
 * @Author Ewan Lewis
 */

package com.example.MountainServer;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class MountainService {

    /**
     * List of mountains
     */
    private final List<Mountain> MOUNTAIN_LIST = new ArrayList<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Constructor
     */
    public MountainService() {

    }

    /**
     * Adds mountains
     *
     * @param newMountains Mountains to be added
     * @return if successful or not
     */
    public boolean addMountains(List<Mountain> newMountains) {
        lock.writeLock().lock();
        try {
            for (Mountain newMountain : newMountains) {
                for (Mountain existingMountain : MOUNTAIN_LIST) {
                    if (existingMountain.equals(newMountain)) {
                        return false; // Found a duplicate
                    }
                }
            }
            MOUNTAIN_LIST.addAll(newMountains);
            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Gets all mountains
     *
     * @return list of all mountains
     */
    public List<Mountain> getAllMountains() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(MOUNTAIN_LIST);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Gets all mountains in a country
     *
     * @param country Country to search for
     * @return List of all mountains in country
     */
    public List<Mountain> getMountainsByCountry(String country) {
        lock.readLock().lock();
        try {
            List<Mountain> returningMountains = new ArrayList<>();
            for (Mountain existingMountain : MOUNTAIN_LIST) {
                if (existingMountain.getCountry().equals(country)) { //
                    returningMountains.add(existingMountain);
                }
            }
            return returningMountains;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Gets all mountains in country & range
     *
     * @param country Country to search for
     * @param range   Range to search for
     * @return List of all mountains in country & range
     */
    public List<Mountain> getByCountryAndRange(String country, String range) {
        lock.readLock().lock();
        try {
            List<Mountain> returningMountains = new ArrayList<>();
            for (Mountain existingMountain : MOUNTAIN_LIST) {
                if (existingMountain.getCountry().equals(country) && existingMountain.getRange().equals(range)) {
                    returningMountains.add(existingMountain);
                }
            }
            return returningMountains;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Looks for all mountains in a hemisphere
     *
     * @param isNorthern Is in northern hemisphere
     * @return All mountains in desired hemisphere
     */
    public List<Mountain> getByHemisphere(Boolean isNorthern) {
        lock.readLock().lock();
        try {
            List<Mountain> returningMountains = new ArrayList<>();
            for (Mountain existingMountain : MOUNTAIN_LIST) {
                if (existingMountain.getIsNorthern() == isNorthern) {
                    returningMountains.add(existingMountain);
                }
            }
            return returningMountains;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Looks for all mountains by their country & altitude
     *
     * @param country Country to search
     * @param alt     Minimum altitude
     * @return ALl mountains in country & above altitude
     */
    public List<Mountain> getByCountryAltitude(String country, int alt) {
        lock.readLock().lock();
        try {
            List<Mountain> returningMountains = new ArrayList<>();
            for (Mountain existingMountain : MOUNTAIN_LIST) {
                boolean sameCountry = existingMountain.getCountry().equals(country);
                boolean isTaller = existingMountain.getAltitude() >= alt;
                if (sameCountry && isTaller) {
                    returningMountains.add(existingMountain);
                }
            }
            return returningMountains;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Detailed search of all mountains
     *
     * @param country Country of mountain
     * @param range   Range of mountain
     * @param name    Name of mountain
     * @return List of all mountains in the criteria
     */
    public List<Mountain> getByName(String country, String range, String name) {
        lock.readLock().lock();
        try {
            List<Mountain> returningMountains = new ArrayList<>();
            for (Mountain existingMountain : MOUNTAIN_LIST) {
                if (existingMountain.getCountry().equals(country)
                        && existingMountain.getRange().equals(range)
                        && existingMountain.getName().equals(name)) {
                    returningMountains.add(existingMountain);
                }
            }
            return returningMountains;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Searches for mountains by ID
     *
     * @param id ID of mountain
     * @return Mountain of ID
     */
    public List<Mountain> getById(int id) {
        lock.readLock().lock();
        try {
            List<Mountain> returningMountains = new ArrayList<>();
            for (Mountain existingMountain : MOUNTAIN_LIST) {
                if (existingMountain.getId() == id) {
                    returningMountains.add(existingMountain);
                    return returningMountains;
                }
            }
            return returningMountains;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Updates mountain's information
     *
     * @param id       ID of mountain to update
     * @param mountain New data of mountain
     * @return Success/not
     */
    public boolean updateMountain(int id, Mountain mountain) {
        lock.writeLock().lock();
        try {
            int i = 0;
            for (Mountain currentMountain : MOUNTAIN_LIST) {
                if (currentMountain.getId() == id) {
                    MOUNTAIN_LIST.set(i, mountain);
                    return true;
                }
                i++;
            }
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Deletes mountain's information
     *
     * @param id ID of mountain to delete
     * @return Success/not
     */
    public boolean deleteMountain(int id) {
        lock.writeLock().lock();
        try {
            int i = 0;
            for (Mountain currentMountain : MOUNTAIN_LIST) {
                if (currentMountain.getId() == id) {
                    MOUNTAIN_LIST.remove(i);
                    return true;
                }
                i++;
            }
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
