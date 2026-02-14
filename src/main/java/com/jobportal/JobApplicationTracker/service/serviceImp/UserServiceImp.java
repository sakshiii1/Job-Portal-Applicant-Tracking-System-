package com.jobportal.JobApplicationTracker.service.serviceImp;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobportal.JobApplicationTracker.dto.UserDTO;
import com.jobportal.JobApplicationTracker.entity.User;
import com.jobportal.JobApplicationTracker.repository.UserRepository;
import com.jobportal.JobApplicationTracker.service.UserService;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Entity → DTO
    private UserDTO mapToDTO(User user) {

        UserDTO dto = new UserDTO();
        dto.setId(user.getUser_id());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setStatus(user.isStatus());
        dto.setCreatedAt(user.getCreatedAt());

        return dto;
    }

    // DTO → Entity
    private User mapToEntity(UserDTO dto) {

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());
        user.setCreatedAt(dto.getCreatedAt());

        return user;
    }

    @Override
    public UserDTO createUser(UserDTO dto) {

        User user = mapToEntity(dto);
        return mapToDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserById(int id) {
		return null;
    	
    }

	@Override
	public List<UserDTO> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO updateUser(int id, UserDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserStatus(int userId, String status) {
		// TODO Auto-generated method stub
		
	}}
    

       /* User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return mapToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<>();

        for (User user : users) {
            dtos.add(mapToDTO(user));
        }

        return dtos;
    }

    @Override
    public UserDTO updateUser(int id, UserDTO dto) {

        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setRole(dto.getRole());
        existing.setStatus(dto.getStatus());
        existing.setCreatedAt(dto.getCreatedAt());

        return mapToDTO(userRepository.save(existing));
    }

    @Override
    public void deleteUser(int id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userRepository.delete(user);
    }

	@Override
	public void updateUserStatus(int userId, String status) {
		// TODO Auto-generated method stub   

	
	public UserDTO getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO updateUser(int id, UserDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserStatus(int userId, String status) {
		// TODO Auto-generated method stub
		
	}
		
	}
}
*/

