package entitys;

import java.math.BigInteger;
import java.sql.Date;

public class Patient {
    private int patientId;
    private String patientName;
    private String patientSurname;
    private Date patientDateOfBirth;
    private String patientGender;

    private Long patientPhone;

    private String patientPhoneString;

    public Patient() {
    }


    public int getPatientId() {
        return patientId;
    }


    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSurname() {
        return patientSurname;
    }

    public void setPatientSurname(String patientSurname) {
        this.patientSurname = patientSurname;
    }

    public Date getPatientDateOfBirth() {
        return patientDateOfBirth;
    }

    public void setPatientDateOfBirth(Date patientDateOfBirth) {
        this.patientDateOfBirth = patientDateOfBirth;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public Long getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(Long patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientPhoneString() {

        patientPhoneString = String.valueOf(patientPhone);
        return patientPhoneString;
    }


    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", patientSurname='" + patientSurname + '\'' +
                ", patientDateOfBirth=" + patientDateOfBirth +
                ", gender='" + patientGender + '\'' +
                ", patientPhone=" + patientPhone +
                '}';
    }
}
