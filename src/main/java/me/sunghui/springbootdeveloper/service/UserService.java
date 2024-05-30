package me.sunghui.springbootdeveloper.service;

//import lombok.RequiredArgsConstructor;
//import me.sunghui.springbootdeveloper.domain.User;
//import me.sunghui.springbootdeveloper.dto.AddUserRequest;
//import me.sunghui.springbootdeveloper.repository.UserRepository;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
////    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    public Long save(AddUserRequest dto) {
//
//        // 수정
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        return userRepository.save(User.builder()
//                .email(dto.getEmail())
//                // 패스워드 암호화
//                // 패스워드를 저장할 때 시큐리티를 설정하며 패스워드 인코딩용으로 등록한 빈을 사용해서
//                // 암호화한 후에 저장함
//                .password(encoder.encode(dto.getPassword()))
//                .build()).getId();
//    }
//
//    // 전달받은 유저 ID로 유저를 검색해서 전달하는 메서드
//    public User findById(Long userId) {
//        return userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
//    }
//
//    // 전달받은 유저 이메일로 유저를 검색해서 전달하는 메서드
//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
//    }
//}
import lombok.RequiredArgsConstructor;
import me.sunghui.springbootdeveloper.domain.User;
import me.sunghui.springbootdeveloper.dto.AddUserRequest;
import me.sunghui.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}