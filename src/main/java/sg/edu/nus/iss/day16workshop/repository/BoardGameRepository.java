package sg.edu.nus.iss.day16workshop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day16workshop.model.Mastermind;

@Repository
public class BoardGameRepository {

    @Autowired
    private RedisTemplate<String, Object> template;

    public int saveGame(final Mastermind m) {
        template.opsForValue().set(m.getId(), m.toJSON().toString());
        String result = (String) template.opsForValue().get(m.getId());

        if (result != null) {
            return 1;
        }

        return 0;
    }

    public Mastermind findByID(final String mID) throws Exception {
        String jsonVal = (String) template.opsForValue().get(mID);
        Mastermind m = Mastermind.create(jsonVal);
        return m;
    }

    public int updateBoardGame(final Mastermind m) {
        String result = (String) template.opsForValue().get(m.getId());

        if (m.isUpSert()) {
            if (result != null) {
                template.opsForValue().set(m.getId(), m.toJSON().toString());
            } else {
                m.setId(m.generateID(8));
                template.opsForValue().setIfAbsent(m.getId(), m.toJSON().toString());
            }
        } else {
            if (result != null) {
                template.opsForValue().set(m.getId(), m.toJSON().toString());
            }
        }

        result = (String) template.opsForValue().get(m.getId());

        if (result != null) {
            return 1;
        }

        return 0;
    }

}
