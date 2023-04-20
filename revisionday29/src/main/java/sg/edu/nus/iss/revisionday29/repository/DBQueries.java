package sg.edu.nus.iss.revisionday29.repository;

public class DBQueries {
    public static final String INSERT_INTO_RVSP_TABLE = """
            insert into rvsp(name,email,phone,confirmation_date,
            comments,food_type) values (?,?,?,?,?,?)
                    """;
}
