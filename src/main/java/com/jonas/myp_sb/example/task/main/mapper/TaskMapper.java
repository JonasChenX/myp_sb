package com.jonas.myp_sb.example.task.main.mapper;

import com.jonas.myp_sb.example.task.main.model.AcsTaskDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDetailsTask form(AcsTaskDetails detail);
}
