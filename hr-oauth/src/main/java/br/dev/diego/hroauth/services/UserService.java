package br.dev.diego.hroauth.services;

import br.dev.diego.hroauth.entities.User;
import br.dev.diego.hroauth.feignclients.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserFeignClient userFeignClient;

    public User findByEmail(String email) {
        User user = userFeignClient.findByEmail(email).getBody();
        if(user == null) {
            logger.error("Email not found " + email);
            throw new IllegalArgumentException("E-mail not found");
        }
        logger.info("Email encontrado: " + email);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userFeignClient.findByEmail(email).getBody();
        if(user == null) {
            logger.error("Email not found " + email);
            throw new UsernameNotFoundException("E-mail not found");
        }
        logger.info("Email encontrado: " + email);
        return user;
    }
}
