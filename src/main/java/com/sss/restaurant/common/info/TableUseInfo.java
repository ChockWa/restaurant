package com.sss.restaurant.common.info;

import com.sss.restaurant.table.dao.TableUseMapper;
import com.sss.restaurant.table.model.TableUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TableUseInfo {

    @Autowired
    private TableUseMapper tableUseMapper;

    private static ThreadLocal<TableUse> tableUseInfo = new ThreadLocal<>();

    public void saveInfo(String accessToken){
        tableUseInfo.set(tableUseMapper.selectByUidAndToken(accessToken,null));
    }

    public static TableUse getInfo(){
        return tableUseInfo.get();
    }
}
