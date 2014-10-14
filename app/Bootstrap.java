import models.Notice;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * Created by qlp on 2014/10/14.
 */
@OnApplicationStart
public class Bootstrap extends Job{
    public void doJob(){
        if(Notice.count() == 0L){
            Fixtures.loadModels("initial-data.yml");
        }
    }
}
