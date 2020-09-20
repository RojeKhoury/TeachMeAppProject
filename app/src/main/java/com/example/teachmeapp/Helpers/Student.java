package com.example.teachmeapp.Helpers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class Student {

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    private String name;
    private String surname;
    private String phone;
    private Map<String, UserLesson> classes;
    private String email;
    private String uid;
    private List<String> ratings;
    private List<String> favourites;
    private LatLng location;
    private String city;
    private String country;
    private String address;
    private Schedule schedule;
    private Schedule pendingLessonRequests;
    private ArrayList<String> languages;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setClasses(Map<String, UserLesson> classes) {
        this.classes = classes;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setRatings(List<String> ratings) {
        this.ratings = ratings;
    }

    public void setFavourites(List<String> favourites) {
        this.favourites = favourites;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setPendingLessonRequests(Schedule pendingLessonRequests) {
        this.pendingLessonRequests = pendingLessonRequests;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public Student() {
    }

    public Student(String name, String surname, String phone, Map<String, UserLesson> classes, String email, String uid, ArrayList<String> languages) {

        // [START_EXCLUDE]
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.classes = classes;
        this.email = email;
        this.uid = uid;
        this.languages = languages;
        this.ratings = new List<String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<String> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(String s) {
                return false;
            }

            @Override
            public boolean remove(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends String> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends String> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public String get(int i) {
                return null;
            }

            @Override
            public String set(int i, String s) {
                return null;
            }

            @Override
            public void add(int i, String s) {

            }

            @Override
            public String remove(int i) {
                return null;
            }

            @Override
            public int indexOf(@Nullable Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(@Nullable Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<String> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<String> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<String> subList(int i, int i1) {
                return null;
            }
        };
        this.favourites = new List<String>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<String> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(String s) {
                return false;
            }

            @Override
            public boolean remove(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends String> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends String> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public String get(int i) {
                return null;
            }

            @Override
            public String set(int i, String s) {
                return null;
            }

            @Override
            public void add(int i, String s) {

            }

            @Override
            public String remove(int i) {
                return null;
            }

            @Override
            public int indexOf(@Nullable Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(@Nullable Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<String> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<String> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<String> subList(int i, int i1) {
                return null;
            }
        };
        this.city = "";
        this.country = "";
        this.address = "";
        this.schedule = new Schedule();
        this.pendingLessonRequests = new Schedule();
        this.location = null;
        // [END_EXCLUDE]
    }

    public ArrayList<String> getLanguages(){return languages;}

    public Schedule getSchedule() {
        return schedule;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getUid() {
        return uid;
    }

    public Map<String, UserLesson> getLessons() {
        return classes;
    }

    public List<String> getRatings(){return  ratings;}

    public List<String> getFavourites(){return  favourites;}

    public LatLng getLocation(){return location;}

    public String getCity(){return city;}

    public String getCountry(){return country;}

    public String getAddress(){return address;}

}
