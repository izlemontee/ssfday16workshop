package sg.nus.vttp.day16work.model;

public class Game {

    Integer gid;
    String name;
    Integer year;
    Integer ranking;
    Integer usersrated;
    String url;
    String image;

    public Game(){
        
    }

    public Game(Integer gid, String name, Integer year, Integer ranking, Integer usersrated, String url, String image){
        this.gid = gid;
        this.name = name;
        this.year = year;
        this.ranking = ranking;
        this.usersrated = usersrated;
        this.url = url;
        this.image = image;
    }



    public Integer getGid() {
        return gid;
    }
    public void setGid(Integer gid) {
        this.gid = gid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getRanking() {
        return ranking;
    }
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    public Integer getUsersrated() {
        return usersrated;
    }
    public void setUsersrated(Integer usersrated) {
        this.usersrated = usersrated;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    
}
