package Util;

public class AttributFinal {

    public static final String PARAM_LOGIN = "login";
    public static final String PARAM_PASS = "password";
    public static final String PARAM_ROLE = "roleID";

    public static final String GET_DOCTOR_BY_LOGIN = "SELECT * FROM doctors WHERE login=?";
    public static final String GET_DOCTOR_BY_ROLE = "SELECT * FROM doctors WHERE role_id=?";
    public static final String GET_DOCTOR_BY_ID = "SELECT * FROM doctors WHERE doctor_id=?";

    public final static String ADDDOCTOR = "INSERT into doctors (doctor_name, doctor_surname, category_id, login, password, role_id) VALUES (?,?,?,?,?,?)";
    public final static String DELETEDOCTOR = "DELETE FROM doctors WHERE doctor_id=?";
    public final static String DELETEPATIENT = "DELETE FROM patients WHERE patient_id=?";

    public final static String DELETEAPPOINTMENT = "DELETE FROM appointments WHERE appointment_id=?";
    public final static String GET_ALL_DOCTOR = "SELECT * FROM doctors";
    public final static String GET_ALL_PATIENT = "SELECT * FROM patients";
    public static final String GET_PATIENT_BY_ID = "SELECT * FROM patients WHERE patient_id=?";
    public final static String ADDPATIENT = "INSERT into patients (patient_name, patient_surname, patient_date_of_birth, gender, phone) VALUES (?,?,?,?,?)";
    public static final String COUNT_OF_PATIENT = "SELECT COUNT(1) FROM patients";
    public static final String GET_ALL_PATIENT_LIMIT = "SELECT * FROM patients limit ?, ?";
    public static final String SORT_PATIENT = "SELECT * FROM patients ORDER BY ";
    public static final String LIMIT_PATIENT = " limit ?, ?";


    public final static String GET_ALL_APPOINTMENT = "SELECT * FROM appointments";
    public final static String ADDDAPPOINTMENT = "INSERT into appointments (doctor_id, patient_id, appointments_data) VALUES (?,?,?)";

    public final static String GET_APPOINTMENT_BY_DOCTOR_ID = "SELECT * from appointments where doctor_id = ?";
    public final static String GET_PATIENT_BY_DOCTOR_ID = "SELECT  * from patients inner join appointments on appointments.patient_id = patients.patient_id where appointments.doctor_id = ? group by patients.patient_id";

    public final static String GET_HOSPITALCARD_BY_PATIENT_ID = "SELECT * FROM hospitalcards where patientid = ?";

    public final static String GET_APPOINTMENT_BY_DOCTOR_AND_PATIENT_ID = "SELECT * from appointments where doctor_id = ? and patient_id=?";

    public final static String ADDHOSPITALCARD = "INSERT into hospitalcards (patientid, doctorid, diagnosis, procedures, medications, operations) VALUES (?,?,?,?,?,?)";

    public final static String CHECK_HOSPITALCARD_AVALABILE = "SELECT * from hospitalcards where patientid=? and  doctorid = ? ";

    public final static String UPDATE_HOSPITAL_CARD = "UPDATE hospitalcards SET diagnosis=?, procedures=?, medications=?, operations=? WHERE patientid=? and doctorid=?";
    public static final String SORT_DOCTOR = "SELECT * FROM doctors ORDER BY ";
    public static final String LIMIT_DOCTOR = " limit ?, ?";
    public static final String GET_ALL_DOCTOR_LIMIT = "SELECT * FROM doctors limit ?, ?";
    public static final String COUNT_OF_DOCTOR = "SELECT COUNT(1) FROM doctors";
    public static final String UPDATE_COUNT_PATIENT_BY_DOCTOR_ID = "UPDATE doctors set countofpatients = ? WHERE doctor_id = ?";
    public static final String GET_COUNT_PATIENT_BY_DOCTOR_ID = "SELECT COUNT(DISTINCT patients.patient_id) FROM patients inner join appointments on appointments.patient_id = patients.patient_id where appointments.doctor_id = ?";



    public static final String UPDATE_DOCTOR_BY_ID = "UPDATE doctors SET doctor_name=?, doctor_surname=?,category_id=?,login=?,password=?,role_id=? WHERE doctor_id=?";
    public static final String UPDATE_PATIENT_BY_ID = "UPDATE patients SET patient_name=?, patient_surname=?,patient_date_of_birth=?,gender=?,phone=? WHERE patient_id=?";


    public static final String GET_ALL_PATIENT_LIMIT_BY_ID = "SELECT * FROM patients inner join appointments on appointments.patient_id = patients.patient_id where appointments.doctor_id = ? group by patients.patient_id limit ?, ?";
    public static final String SORT_PATIENT_BY_ID = "SELECT  * from patients inner join appointments on appointments.patient_id = patients.patient_id where appointments.doctor_id = ? group by patients.patient_id ORDER BY ";
    public static final String LIMIT_PATIENT_BY_ID = " limit ?, ?";
    public static final String COUNT_OF_PATIENT_BY_ID = "SELECT COUNT(DISTINCT patients.patient_id) FROM patients inner join appointments on appointments.patient_id = patients.patient_id where appointments.doctor_id = ?";
    public static final String GET_ALL_APPOINTMENT_LIMIT = "SELECT * FROM appointments limit ?, ?";
    public static final String SORT_APPOINTMENT = "SELECT * FROM appointments ORDER BY ";
    public static final String LIMIT_APPOINTMENT = " limit ?, ?";
    public static final String COUNT_OF_APPOINTMENT = "SELECT COUNT(1) FROM appointments";




    public static final String SORT_HOSPITALCARD = "SELECT * FROM hospitalcards ORDER BY ";
    public static final String LIMIT_HOSPITALCARD = " limit ?, ?";
    public static final String COUNT_OF_HOSPITALCARD = "SELECT COUNT(1) FROM hospitalcards";
    public static final String GET_ALL_HOSPITALCARD_LIMIT = "SELECT * FROM hospitalcards limit ?, ?";
    public static final String GET_HOSPITALCARD_BY_ID = "SELECT * FROM hospitalcards WHERE hospitalcard_id=?";


   //valifdation
    public static final String  CHECK_DOCTOR_AVAILABILITY_BY_EMAIL = "SELECT * FROM doctors WHERE login=?";
    public static final String  CHECK_DOCTOR_AVAILABILITY_BY_ID = "SELECT * FROM doctors WHERE doctor_id=?";
    public static final String  CHECK_PATIENT_AVAILABILITY_BY_ID = "SELECT * FROM patients WHERE patient_id=?";
    public static final String  CHECK_APPOINTMENT_AVAILABILITY_BY_ID = "SELECT * FROM appointments WHERE appointment_id=?";

}

