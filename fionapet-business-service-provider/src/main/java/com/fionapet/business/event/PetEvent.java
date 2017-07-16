package com.fionapet.business.event;

/**
 * Created by tom on 2017/6/4.
 */
public class PetEvent extends TokenEvent {
    private String petId;

    public PetEvent(Object source, String petId, String token) {
        super(source,token);
        this.petId = petId;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

}
