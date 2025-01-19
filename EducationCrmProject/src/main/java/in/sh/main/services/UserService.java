package in.sh.main.services;

import in.sh.main.entities.User;
import in.sh.main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void registerUserService(User user){

          userRepository.save(user);
    }

    public boolean loginUserService(String email ,String password){
       User user= userRepository.findByEmail(email);
       if (user !=null){  //user value not null
            return password.equals(user.getPassword()); //check the password..
       }
           return false;
    }
}
