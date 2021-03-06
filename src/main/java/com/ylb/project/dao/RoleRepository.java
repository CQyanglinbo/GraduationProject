package com.ylb.project.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ylb.project.model.Role;
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
	public Role findByRoleName(String RoleName);
}
