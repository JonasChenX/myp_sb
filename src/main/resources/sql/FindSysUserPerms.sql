select
    *
--    distinct  sm.perms
    from sys_user_role ur
    left join sys_role r ON ur.role_id = r.id
    left join sys_role_menu srm on ur.role_id = srm.role_id
    left join sys_menu sm on sm.id = srm.menu_id
where
    user_id = :userId
    and r.status = 0
    and sm.status = 0