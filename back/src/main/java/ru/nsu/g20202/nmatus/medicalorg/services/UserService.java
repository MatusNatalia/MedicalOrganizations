package ru.nsu.g20202.nmatus.medicalorg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.g20202.nmatus.medicalorg.dtos.RoleDto;
import ru.nsu.g20202.nmatus.medicalorg.dtos.UserDto;
import ru.nsu.g20202.nmatus.medicalorg.entities.users.Role;
import ru.nsu.g20202.nmatus.medicalorg.entities.users.User;
import ru.nsu.g20202.nmatus.medicalorg.repositories.RoleRepository;
import ru.nsu.g20202.nmatus.medicalorg.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }


    public Iterable<UserDto> getAllUsers(){
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : userRepository.findAll()){
            userDtos.add(this.toDto(user));
        }
        return userDtos;
    }

    public List<String> getUserRoles(String username){
        List<String> roleDtos = new ArrayList<>();
        User user = userRepository.findByLogin(username);
        for(Role role : user.getRoles()){
            roleDtos.add(role.getName());
        }
        return roleDtos;
    }

    public void createUser(UserDto userDto){
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(pwEncoder.encode(userDto.getPassword()));
        List<Role> roles = new ArrayList<>();
        for(String role : userDto.getRoles()){
            roles.add(roleRepository.findByName(role).get(0));
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void updateUser(UserDto userDto){
        User user = userRepository.findById(userDto.getId()).get();
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        List<Role> roles = new ArrayList<>();
        for(String role : userDto.getRoles()){
            roles.add(roleRepository.findByName(role).get(0));
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

    public UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        List<String> roles = new ArrayList<>();
        for(Role role : user.getRoles()){
            roles.add(role.getName());
        }
        userDto.setRoles(roles);
        return userDto;
    }

}
