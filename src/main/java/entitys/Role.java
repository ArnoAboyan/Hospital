package entitys;

public enum Role {

    ADMIN (1,"admin"),
    DOCTOR (2, "doctor"),
    NURSE (3, "nurse");

    private final int id;
    private final String title;


    Role(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Integer getTitleId() {
        return id;
    }

    public static Integer getIDByName(String title) {
        if (title.equalsIgnoreCase("admin")) {
            return 1;
        } else if (title.equalsIgnoreCase("doctor")) {
            return 2;
        } else if (title.equalsIgnoreCase("nurse")) {
            return 3;
        } else {
            throw new IllegalArgumentException("Incorrect id, do not have role for this");
        }
    }
}

