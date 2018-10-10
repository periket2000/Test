package com.ef.services.log_parse.piped_file_log_parse;

import com.ef.entities.BannedEntity;
import com.ef.services.log_parse.LogParseInterface;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class PipedFileLogParseService implements LogParseInterface<ArrayList<BannedEntity>, InputStream> {

    public ArrayList<BannedEntity> parse(InputStream input) {
        ArrayList<BannedEntity> a = new ArrayList();
        BannedEntity b = new BannedEntity().banned_ip("192.168.250.100")
                .comment("A banned ip test")
                .run("java -cp ...")
                .requests(1000)
                .start_date(new Date())
                .end_date(new Date());
        a.add(b);
        return a;
    }
}
