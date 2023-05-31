package com.example.medicinehalal;

public class Medicine {
    public String medicinename, serialnumber, malnumber, manufacturer, activeingredient, inactiveingredient, haramingredient, mushboohingredient, halalstatus, counterfeitstatus, medicinepicture;

    public Medicine(){}

    public Medicine(String medicinename, String serialnum, String malnumber, String manufacturer, String activeingredient, String inactiveingredient, String haramingredient, String mushboohingredient, String halalstatus, String counterfeitstatus, String medicinepicture){
        this.medicinename=medicinename;
        this.serialnumber=serialnum;
        this.malnumber=malnumber;
        this.manufacturer=manufacturer;
        this.activeingredient=activeingredient;
        this.inactiveingredient=inactiveingredient;
        this.haramingredient=haramingredient;
        this.mushboohingredient=mushboohingredient;
        this.halalstatus=halalstatus;
        this.counterfeitstatus=counterfeitstatus;
        this.medicinepicture=medicinepicture;
    }

    public String getmedicinename(){
        return medicinename;
    }
    public void setmedicinename(String medicinename){
        this.medicinename = medicinename;
    }

    public String getseialnumber(){
        return serialnumber;
    }
    public void setserialnumber(String serialnumber){
        this.serialnumber = serialnumber;
    }

    public String getmalnumber(){
        return malnumber;
    }
    public void setmalnumber(String malnumber){
        this.malnumber = malnumber;
    }

    public String getmanufacturer(){
        return manufacturer;
    }
    public void setmanufacturer(String manufacturer){
        this.manufacturer = manufacturer;
    }

    public String getactiveingredient(){
        return activeingredient;
    }
    public void setactiveingredient(String activeingredient){
        this.activeingredient = activeingredient;
    }

    public String getinactiveingredient(){
        return inactiveingredient;
    }
    public void setinactiveingredient(String inactiveingredient){
        this.inactiveingredient = inactiveingredient;
    }

    public String getharamingredient(){
        return haramingredient;
    }
    public void setharamingredient(String haramingredient){
        this.haramingredient = haramingredient;
    }

    public String getmushboohingredient(){
        return mushboohingredient;
    }
    public void setmushboohingredient(String mushboohingredient){
        this.mushboohingredient = mushboohingredient;
    }

    public String gethalalstatus(){
        return halalstatus;
    }
    public void sethalalstatus(String halalstatus){
        this.halalstatus = halalstatus;
    }

    public String getcounterfeitstatus(){
        return counterfeitstatus;
    }
    public void setcounterfeitstatus(String counterfeitstatus){
        this.counterfeitstatus = counterfeitstatus;
    }

    public String getmedicinepicture(){
        return medicinepicture;
    }
    public void setmedicinepicture(String medicinepicture){
        this.medicinepicture = medicinepicture;
    }
}
