package security.spring;

import dao.impl.UserDaoImpl;
import entity.User;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import security.spring.CustomUserDetails;

import java.lang.invoke.MethodHandles;

public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass());
    private UserDaoImpl userDaoImpl = new UserDaoImpl();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDaoImpl.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        LOG.info("Loaded user by username : " + username);

        return new CustomUserDetails(user);
    }
}
