package entitys;


import DAO.DAOException;
import DAO.impl.DoctorDao;

import java.util.Objects;

public class Doctor {

    private int doctorId;
    private String doctorName;
    private String doctorSurname;
    private String login;
    private String password;
    private Category category;
    private Role role;
    private int countOfPatients;

    public Doctor() {
    }


    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSurname() {
        return doctorSurname;
    }

    public void setDoctorSurname(String doctorSurname) {
        this.doctorSurname = doctorSurname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(int id) {
        if (id == 1){
            this.role = Role.ADMIN;
        }
        else if (id == 2){
            this.role = Role.DOCTOR;
        }
        else if (id == 3){
            this.role = Role.NURSE;
        }
        else {
            throw new IllegalArgumentException("Incorrect id, do not have role for this"+id);
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(int  id) {
        if (id == 1){
            this.category = Category.ONKOLOGIST;
        }
        else if (id == 2){
            this.category = Category.TRAUMATOLOGIST;
        }
        else if (id == 3){
            this.category = Category.OPHTHALMOLOGIST;
        }
        else if (id == 4){
            this.category = Category.DENTIST;
        }
        else if (id == 5){
            this.category = Category.PSYCHIATRIST;
        }
        else if (id == 6){
            this.category = Category.THERAPIST;
        }
        else if (id == 7){
            this.category = Category.PEDIATRICIAN;
        }
        else if (id == 8) {
            this.category = Category.NURSE;
        }
        else {
            throw new IllegalArgumentException("Incorrect id, do not have role for this"+id);
        }
    }

    public int getCountOfPatients() {
        return countOfPatients;
    }

    public void setCountOfPatients(int countOfPatients) {
        this.countOfPatients = countOfPatients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return doctorId == doctor.doctorId && Objects.equals(doctorName, doctor.doctorName) && Objects.equals(doctorSurname, doctor.doctorSurname) && category == doctor.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorId, doctorName, doctorSurname, category);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId=" + doctorId +
                ", doctorName='" + doctorName + '\'' +
                ", doctorSurname='" + doctorSurname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", category=" + category +
                ", role=" + role +
                '}';
    }
}
