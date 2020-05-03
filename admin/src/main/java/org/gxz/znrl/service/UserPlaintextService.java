package org.gxz.znrl.service;

import org.gxz.znrl.entity.UserPlaintext;

/**
 * Created by rubish on 2015/5/8.
 */
public interface UserPlaintextService {

    void update(UserPlaintext userPlaintext);

    void save(UserPlaintext userPlaintext);

    void delete(Long id);

    UserPlaintext get(Long id);
}
