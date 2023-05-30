package sg.edu.nus.iss.day16workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.day16workshop.model.Mastermind;
import sg.edu.nus.iss.day16workshop.service.BoardGameService;

@RestController
@RequestMapping(path = "api/boardgame", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardGameRestController {

    @Autowired
    private BoardGameService service;

    @PostMapping
    public ResponseEntity<String> createBoardGame(@RequestBody Mastermind m) {
        int insertCount = service.saveGame(m);
        Mastermind result = new Mastermind();
        result.setId(m.getId());
        result.setInsertCount(insertCount);

        if (insertCount == 0) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toJSONInsert().toString());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toJSONInsert().toString());
    }

    @GetMapping(path = "{mID}")
    public ResponseEntity<String> getBoardGame(@PathVariable String mID) throws Exception {
        System.out.println("get ...");
        Mastermind mResult = (Mastermind) service.findByID(mID);

        if (mResult == null || mResult.getName() == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("null");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mResult.toJSON().toString());
    }

    @PutMapping(path = "{msID}")
    public ResponseEntity<String> updateBoardGame(@RequestBody Mastermind ms, @PathVariable String msID,
            @RequestParam(defaultValue = "false") boolean isUpSert) throws Exception {
        Mastermind result = null;
        ms.setUpSert(isUpSert);

        if (!isUpSert) {
            result = service.findByID(msID);
            if (result == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("");
            }
        }

        ms.setId(msID);
        int updateCount = service.updateBoardGame(ms);
        ms.setUpdateCount(updateCount);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ms.toJSONUpdate().toString());
    }

}
