package sg.nus.vttp.day16work.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.JSONPObject;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.servlet.http.HttpSession;
import sg.nus.vttp.day16work.model.Game;
import sg.nus.vttp.day16work.repo.BoardGameRepo;

@Service
public class BoardGameService {

    @Autowired
    BoardGameRepo gameRepo; 

            
    String userid = "user73";

    public final String path = "C:/Users/izt_4/Documents/Visa/Server Side Foundation/codes/day16work/game.json";
    //public final String path = "Server Side Foundation/codes/day16work/game.json";
    public String setID(HttpSession session){
        Random random = new Random();
        Integer idNumber = random.nextInt(999);
        String id = "user" + idNumber;
        return id;
    }

    public JsonArray readJson()throws Exception{
        Resource resource = new ClassPathResource("static/game.json");
        
        try(InputStream is = resource.getInputStream()){
            System.out.println("trying to read");
            JsonReader jsonreader = Json.createReader(is);
            
            JsonArray jsonObj = jsonreader.readArray();
            System.out.println("json can be read");
            return jsonObj;
            //System.out.println(jsonObj);
        }

    }

    public void insertJson(String id)throws Exception{
        //String id = setID(session);
        JsonArray jsonObj = readJson();
        for(int i=0;i<jsonObj.size();i++){
            JsonObject jso = jsonObj.getJsonObject(i);
            Integer gid = Integer.parseInt(jso.get("gid").toString());
            String name = jso.get("name").toString();
            Integer year = Integer.parseInt(jso.get("year").toString());
            Integer ranking = Integer.parseInt(jso.get("ranking").toString());
            Integer usersrated = Integer.parseInt(jso.get("users_rated").toString());
            String url = jso.get("url").toString();
            String image = jso.get("image").toString();
            //System.out.println(jso.get("gid"));
            Game game = new Game(gid,name,year,ranking,usersrated,url,image);
            // add to repo
            gameRepo.addJson(id,gid,game);
        }

    }

    public ResponseEntity<Map<String,Object>> response(String id){
        
        JsonObject jsonResponse = Json.createObjectBuilder()
             .add("insert count",Json.createValue((Integer)1))
             .add("id",Json.createValue((String)id))
             .build();
        Map<String, Object> responseMap = new HashMap<String,Object>();
        responseMap.put("insert count",1);
        responseMap.put("id",id);

        return new ResponseEntity<>(responseMap,HttpStatus.CREATED);
        //ResponseEntity<Json> response = new ResponseEntity<>(Json json,HttpStatusCode);
    }

    public ResponseEntity<Object> findGame(Integer gameid){

        Object jsonObj = gameRepo.getJson(userid, gameid);
        if(jsonObj != null){
            System.out.println("json retrieved");
            System.out.println(jsonObj);
            return new ResponseEntity<>(jsonObj,HttpStatus.OK);
        }
        else{
            System.out.println("Object not found.");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> updateGame(Integer gameid, Game game){
        Object jsonObj = gameRepo.getJson(userid, gameid);
        if(jsonObj != null){
            gameRepo.updateGame(game, userid, gameid);
            Map<String, Object> responseMap = new HashMap<String,Object>();
            responseMap.put("update count",1);
            responseMap.put("id",userid);
            return new ResponseEntity<>(responseMap,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<Object> insertGame(Integer gameid, Game game){
        System.out.println("upsert");
        Object jsonObj = gameRepo.getJson(userid, gameid);
        System.out.println("jsonObj: "+jsonObj);
        if(jsonObj == null){
            gameRepo.updateGame(game, userid, gameid);
            Map<String, Object> responseMap = new HashMap<String,Object>();
            responseMap.put("add count",1);
            responseMap.put("id",userid);
            return new ResponseEntity<>(responseMap,HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(null,HttpStatus.OK);
        }

    }



    
}
