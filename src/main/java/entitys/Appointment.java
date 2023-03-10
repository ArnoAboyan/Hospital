package entitys;

import DAO.DAOException;
import DAO.impl.DoctorDao;
import DAO.impl.PatientDao;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Appointment {

    private Integer appointmentId;
    private Integer doctorId;
    private Integer patientId;
    private LocalDateTime appointmentData;
    private String appointmentDoctorName;
    private String appointmentDoctorSurname;
    private Category appointmentDoctorCategory;
    private String appointmentPatientName;
    private String appointmentPatientSurname;

    public Appointment() {
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getAppointmentData() {
        return appointmentData;
    }

    public void setAppointmentData(LocalDateTime appointmentData) {
        this.appointmentData = appointmentData;
    }

    public String getAppointmentDoctorName() throws DAOException {
        Doctor doctor;
        DoctorDao doctorDao = new DoctorDao();

       doctor = doctorDao.getByID(doctorId);

    return appointmentDoctorName =  doctor.getDoctorName();
    }

    public String getAppointmentDoctorSurname() throws DAOException {
        Doctor doctor;
        DoctorDao doctorDao = new DoctorDao();

        doctor = doctorDao.getByID(doctorId);

        return appointmentDoctorSurname =  doctor.getDoctorSurname();
    }

    public Category getAppointmentDoctorCategory() throws DAOException {
        Doctor doctor;
        DoctorDao doctorDao = new DoctorDao();

        doctor = doctorDao.getByID(doctorId);

        return appointmentDoctorCategory =  doctor.getCategory();
    }

    public String getAppointmentPatientName() {
        Patient patient;
        PatientDao patientDao = new PatientDao();

        patient = patientDao.getByID(patientId);

        return appointmentDoctorSurname =  patient.getPatientName();
    }
    public String getAppointmentPatientSurname() {
        Patient patient;
        PatientDao patientDao = new PatientDao();

        patient = patientDao.getByID(patientId);

        return appointmentDoctorSurname =  patient.getPatientSurname();
    }


    public void setAppointmentDoctorName(String appointmentDoctorName) {
        this.appointmentDoctorName = appointmentDoctorName;
    }

    public void setAppointmentDoctorSurname(String appointmentDoctorSurname) {
        this.appointmentDoctorSurname = appointmentDoctorSurname;
    }

    public void setAppointmentDoctorCategory(Category appointmentDoctorCategory) {
        this.appointmentDoctorCategory = appointmentDoctorCategory;
    }

    public void setAppointmentPatientName(String appointmentPatientName) {
        this.appointmentPatientName = appointmentPatientName;
    }

    public void setAppointmentPatientSurname(String appointmentPatientSurname) {
        this.appointmentPatientSurname = appointmentPatientSurname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(appointmentId, that.appointmentId) && Objects.equals(doctorId, that.doctorId) && Objects.equals(patientId, that.patientId) && Objects.equals(appointmentData, that.appointmentData) && Objects.equals(appointmentDoctorName, that.appointmentDoctorName) && Objects.equals(appointmentDoctorSurname, that.appointmentDoctorSurname) && appointmentDoctorCategory == that.appointmentDoctorCategory && Objects.equals(appointmentPatientName, that.appointmentPatientName) && Objects.equals(appointmentPatientSurname, that.appointmentPatientSurname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentId, doctorId, patientId, appointmentData, appointmentDoctorName, appointmentDoctorSurname, appointmentDoctorCategory, appointmentPatientName, appointmentPatientSurname);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                ", appointmentData=" + appointmentData +
                ", appointmentDoctorName='" + appointmentDoctorName + '\'' +
                ", appointmentDoctorSurname='" + appointmentDoctorSurname + '\'' +
                ", appointmentDoctorCategory=" + appointmentDoctorCategory +
                ", appointmentPatientName='" + appointmentPatientName + '\'' +
                ", appointmentPatientSurname='" + appointmentPatientSurname + '\'' +
                '}';
    }
}
