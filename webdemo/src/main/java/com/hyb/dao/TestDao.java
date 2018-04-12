package com.hyb.dao;

import com.hyb.anno.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2018/4/2.
 */
@Mapper
public interface TestDao {
    @Select("select distinct m.menu_id, m.menu_desc, m.menu_url, m.menu_pid from\n" +
            "t_menu_privilege mp,\n" +
            "t_sys_menu m,\n" +
            "t_privilege p,\n" +
            "t_user_role r\n" +
            "where\n" +
            "mp.privilege_id=p.privilege_id\n" +
            "and mp.role_id=r.role_id\n" +
            "and mp.menu_id=m.menu_id\n" +
            "and r.user_id=#{_parameter}\n" +
            "and FIND_IN_SET(m.MENU_ID,queryMenuChild('0'))\n" +
            "order by M.MENU_ID")
    List<HashMap<String,String>> getMenus(String user);
}
