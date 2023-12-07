package sg.nus.vttp.day16work.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpSession;
import sg.nus.vttp.day16work.model.Game;
import sg.nus.vttp.day16work.service.BoardGameService;

@RestController
@RequestMapping(path = "/api")
public class BoardGameController {

    @Autowired
    BoardGameService gameSvc;

    @PostMapping(path = "/boardgame")
    public ResponseEntity insertDoc(HttpSession session) throws Exception{
        String id = gameSvc.setID(session);
        //gameSvc.readJson();
        gameSvc.insertJson(id);
        ResponseEntity<Map<String,Object>> response = gameSvc.response(id);
        System.out.println("yeah you got it");
        return response;
    }

    @GetMapping(path = "/boardgame/{gameid}")
    public ResponseEntity findGame(@PathVariable("gameid") String gameid){
        Integer gameidInt = Integer.parseInt(gameid);
        //System.out.println("getmapping path");
        System.out.println(gameid);
        ResponseEntity<Object> response = gameSvc.findGame(gameidInt);
        return response;
            
    }

    @PutMapping(path = "/boardgame/{gameid}")
    public ResponseEntity<Object> updateGame(@PathVariable("gameid") String gameid, 
    @RequestBody Game game, @RequestParam(name = "upsert", required = false, defaultValue = "false") Boolean upsert){
        Integer gameidInt = Integer.parseInt(gameid);
        System.out.println("param: "+upsert);
        if(!upsert){
            ResponseEntity<Object> response = gameSvc.updateGame(gameidInt, game);
            return response;
        }
        else{
            ResponseEntity<Object> response = gameSvc.insertGame(gameidInt, game);
            return response;
        }

  
    }



    
    
}
