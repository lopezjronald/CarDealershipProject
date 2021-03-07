package com.dealership.model;

public class Customer extends User {

    private String[] carsOwned;

    public Customer() {
        super();
        carsOwned = new String[5];
    }

    public String[] getCarsOwned() {
        return carsOwned;
    }

    public void setCarsOwned(String[] carsOwned) {
        this.carsOwned = carsOwned;
    }

    //TODO: need to add a functionality to add new cars and check status of the cars
    private void resizeCarInventory() {
        String[] currentCarsOwned = carsOwned;
        carsOwned = new String[carsOwned.length * 2];
        for (int i = 0; i < currentCarsOwned.length; i++) {
            carsOwned[i] = currentCarsOwned[i];
        }
    }
}
