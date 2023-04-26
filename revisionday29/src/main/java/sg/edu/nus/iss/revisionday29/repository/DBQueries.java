package sg.edu.nus.iss.revisionday29.repository;

public class DBQueries {
        public static final String INSERT_INTO_RVSP_TABLE = """
                        insert into rsvp(name,email,phone,confirmation_date,
                        comments,food_type) values (?,?,?,?,?,?)
                                """;
}
