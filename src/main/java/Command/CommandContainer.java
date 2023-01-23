package Command;

import java.util.HashMap;
import java.util.Map;


// commandFactory
public class CommandContainer {

    private static Map<String, Command> commands;

    private CommandContainer() {
    }

    static{
        commands = new HashMap<>();
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("patientlistcommand", new PatientListCommand());
        commands.put("adminpagecommand", new AdminPageCommand());
        commands.put("changeLanguage", new ChangeLanguageCommand());
        commands.put("adddoctorcommand", new AddDoctorCommand());
        commands.put("deletedoctorcommand", new DeleteDoctorCommand());
        commands.put("deletepatientcommand", new DeletePatientCommand());
        commands.put("addpatientcommand", new AddPatientCommand());
        commands.put("appointmentpagecommand", new AppointmentListCommand());
        commands.put("addappointmentcommand", new AddAppointmentCommand());
        commands.put("patientlistbydoctor", new PatientListByDoctor());
        commands.put("hospitalcardbypatientid", new HospitalCardByPatientId());
        commands.put("hospitalcardbyid", new HospitalCardById());
        commands.put("changehospitalcardcommand", new ChangeHospitalCardCommand());
        commands.put("deleteappointmentcommand", new DeleteAppointmentCommand());
        commands.put("hospitalcardlist", new HospitalCardList());
//        update command
        commands.put("updatedoctorcommand", new UpdateDoctorCommand());
        commands.put("updatepatientcommand", new UpdatePatientCommand());


    }

    public static Command getCommand(String commandName){
        return commands.get(commandName);
    }

}
