package com.example.medicinehalal;

public class Report {

    public String reportfullname, reportemail, reportphone, reportmedicinename, reportmedicinelocation, reportmedicinepictureurl, reportmedicineissue, reportadditional;

    public Report(){}

    public Report(String reportfullname, String reportemail, String reportphone, String reportmedicinename, String reportmedicinelocation, String reportmedicinepictureurl, String reportmedicineissue, String reportadditional){
        this.reportfullname=reportfullname;
        this.reportemail=reportemail;
        this.reportphone=reportphone;
        this.reportmedicinename=reportmedicinename;
        this.reportmedicinelocation=reportmedicinelocation;
        this.reportmedicinepictureurl=reportmedicinepictureurl;
        this.reportmedicineissue=reportmedicineissue;
        this.reportadditional=reportadditional;
    }

    public String getreportfullname(){
        return reportfullname;
    }
    public void setreportfullname(String reportfullname){
        this.reportfullname = reportfullname;
    }

    public String getreportemail(){
        return reportemail;
    }
    public void setreportemail(String reportemail){
        this.reportemail = reportemail;
    }

    public String getreportphone(){
        return reportphone;
    }
    public void setreportphone(String reportphone){
        this.reportphone = reportphone;
    }

    public String getreportmedicinename(){
        return reportmedicinename;
    }
    public void setreportmedicinename(String reportmedicinename){
        this.reportmedicinename = reportmedicinename;
    }

    public String getreportmedicinelocation(){
        return reportmedicinelocation;
    }
    public void setreportmedicinelocation(String reportmedicinelocation){
        this.reportmedicinelocation = reportmedicinelocation;
    }

    public String getreportmedicinepictureurl(){
        return reportmedicinepictureurl;
    }
    public void setreportmedicinepictureurl(String reportmedicinepictureurl){
        this.reportmedicinepictureurl = reportmedicinepictureurl;
    }

    public String getreportmedicineissue(){
        return reportmedicineissue;
    }
    public void setreportmedicineissue(String reportmedicineissue){
        this.reportmedicineissue = reportmedicineissue;
    }

    public String getReportadditional(){
        return reportadditional;
    }
    public void setReportadditional(String reportadditional){
        this.reportadditional = reportadditional;
    }
}
