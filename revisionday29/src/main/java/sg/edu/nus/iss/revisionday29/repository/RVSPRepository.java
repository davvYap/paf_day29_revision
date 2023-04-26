package sg.edu.nus.iss.revisionday29.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.revisionday29.model.AggregationRSVP;
import sg.edu.nus.iss.revisionday29.model.RVSP;
import static sg.edu.nus.iss.revisionday29.repository.DBQueries.*;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class RVSPRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    public RVSP insertRvsp(RVSP rvsp) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement statement = conn.prepareStatement(INSERT_INTO_RVSP_TABLE,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, rvsp.getName());
            statement.setString(2, rvsp.getEmail());
            statement.setString(3, rvsp.getPhone());
            Timestamp timestamp = Timestamp.valueOf(rvsp.getConfirmationDate());
            statement.setTimestamp(4, timestamp);
            statement.setString(5, rvsp.getComments());
            statement.setString(6, rvsp.getFoodType());
            return statement;
        }, keyHolder);

        BigInteger pk = (BigInteger) keyHolder.getKey();
        rvsp.setId(pk.intValue());

        if (rvsp.getId() != 0) {
            rvsp.setConfirmationDate(null);
            mongoTemplate.insert(rvsp, "rsvp");
        }
        return rvsp;
    }

    public List<AggregationRSVP> aggregateByFoodType() {
        // ProjectionOperation pop = Aggregation.project("name", "foodType");
        GroupOperation gop = Aggregation.group("foodType")
                .push("name").as("names")
                .count().as("count");

        SortOperation sop = Aggregation.sort(Sort.by(Direction.DESC, "count"));

        Aggregation pipeline = Aggregation.newAggregation(gop, sop);

        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "rsvp", Document.class);

        List<AggregationRSVP> aggregationList = new ArrayList<>();

        List<Document> documents = results.getMappedResults();

        for (Document document : documents) {
            aggregationList.add(AggregationRSVP.createFromDocument(document));
        }

        // Iterator<Document> docIt = results.iterator();

        // while (docIt.hasNext()) {
        // Document doc = docIt.next();
        // aggregationList.add(AggregationRSVP.createFromDocument(doc));
        // }

        return aggregationList;
    }
}
