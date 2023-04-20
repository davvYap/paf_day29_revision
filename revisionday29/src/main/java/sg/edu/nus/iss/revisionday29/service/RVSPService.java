package sg.edu.nus.iss.revisionday29.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.revisionday29.model.AggregationRSVP;
import sg.edu.nus.iss.revisionday29.model.RVSP;
import sg.edu.nus.iss.revisionday29.repository.RVSPRepository;

@Service
public class RVSPService {
    @Autowired
    private RVSPRepository rvspRepository;

    public RVSP insertRvsp(RVSP rvsp) {
        return rvspRepository.insertRvsp(rvsp);
    }

    public List<AggregationRSVP> aggregateByFoodType() {
        return rvspRepository.aggregateByFoodType();
    }

}
