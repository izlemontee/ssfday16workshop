package sg.nus.vttp.day16work.repo;

import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.util.JSONPObject;

import jakarta.json.JsonObject;
import sg.nus.vttp.day16work.Utils;
import sg.nus.vttp.day16work.model.Game;

@Repository
public class BoardGameRepo {

    @Autowired @Qualifier(Utils.REDIS)//ask spring to look for something, look for myredis bean
	private RedisTemplate<String,Object> template;
   

    public void addJson(String key,Integer hashkey,Game value){
        // System.out.println("can get hashoperations");
        // System.out.println(key);
        // System.out.println(hashkey);
        // System.out.println(value);
        //HO.put("testkey","isaac","hello");
        //template.opsForValue().set(hashkey,value);
        //System.out.println("able to put in opsforvalue");
        HashOperations<String, Integer, Game> HO = template.opsForHash();
        HO.put(key.toString(), hashkey, value);
    }
    public Object getJson(String userid, Integer gameid){
        HashOperations<String, Integer, Object> HO = template.opsForHash();
        System.out.println("boardgamerepo getjson");
        System.out.println(template.opsForHash().get(userid,gameid));
        Object obj = HO.get(userid, gameid);
        //Object obj = template.opsForHash().get(userid, gameid);
        //System.out.println("in the repo: "+obj);

        return obj;
    }

    public void updateGame(Game game, String userid, Integer gameid){
        HashOperations<String, Integer, Object> HO = template.opsForHash();
        HO.put(userid, gameid, game);
    }

    
}
