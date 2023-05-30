package sg.edu.nus.iss.day16workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day16workshop.model.Mastermind;
import sg.edu.nus.iss.day16workshop.repository.BoardGameRepository;

@Service
public class BoardGameService {

    @Autowired
    private BoardGameRepository repo;

    public int saveGame(final Mastermind m) {
        return this.repo.saveGame(m);
    }

    public Mastermind findByID(final String mID) throws Exception {
        return this.repo.findByID(mID);
    }

    public int updateBoardGame(final Mastermind m) {
        return this.repo.updateBoardGame(m);
    }

}
