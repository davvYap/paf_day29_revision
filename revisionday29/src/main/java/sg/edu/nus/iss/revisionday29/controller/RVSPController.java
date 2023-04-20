package sg.edu.nus.iss.revisionday29.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import sg.edu.nus.iss.revisionday29.model.AggregationRSVP;
import sg.edu.nus.iss.revisionday29.model.RVSP;
import sg.edu.nus.iss.revisionday29.service.RVSPService;

@RestController
@RequestMapping(path = "/api/rsvp", produces = MediaType.APPLICATION_JSON_VALUE)
public class RVSPController {

    @Autowired
    private RVSPService rvspService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveRsvp(@RequestBody String json) throws IOException {
        RVSP newRvsp = null;

        try {
            RVSP rvsp = RVSP.convertFromJSON(json);
            newRvsp = rvspService.insertRvsp(rvsp);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Json.createObjectBuilder()
                            .add("error", e.getMessage())
                            .build().toString());

        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Json.createObjectBuilder()
                        .add("Created", "RSVP with id of %d".formatted(newRvsp.getId()))
                        .build()
                        .toString());
    }

    @GetMapping(path = "/count")
    public ResponseEntity<String> getAggregateFoodType() {
        List<AggregationRSVP> aggList = rvspService.aggregateByFoodType();
        System.out.println("List number >>>>>>>>>" + aggList.size());
        JsonArrayBuilder jsArr = Json.createArrayBuilder();
        for (AggregationRSVP aggregationRSVP : aggList) {
            jsArr.add(aggregationRSVP.toJSON());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Json.createObjectBuilder()
                        .add("results", jsArr)
                        .build().toString());
    }

}
