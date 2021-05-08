package com.gaf.feedbacksystem.repository;

import com.gaf.feedbacksystem.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository  extends JpaRepository<Module,Integer> {
    Module findModuleByModuleID(Integer moduleID);

}
