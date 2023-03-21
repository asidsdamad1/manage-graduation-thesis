package utc.edu.thesis.service;

import utc.edu.thesis.domain.entity.Role;

public interface IRoleService extends IService<Role> {
    Role findByName(String name);
}
