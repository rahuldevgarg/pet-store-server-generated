package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.constant.HeaderConstant;
import io.swagger.model.LoginResponseDTO;
import io.swagger.model.ModelApiResponse;
import io.swagger.model.User;
import io.swagger.security.JwtUtils;
import io.swagger.services.Impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Generated;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-10-21T12:36:17.472Z[GMT]")
@RestController
public class UserApiController implements UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final UserServiceImpl userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserApiController(ObjectMapper objectMapper, HttpServletRequest request, UserServiceImpl userService, JwtUtils jwtUtils) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<User> createUser(@Parameter(in = ParameterIn.DEFAULT, description = "Created user object", schema = @Schema()) @Valid @RequestBody User body) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            User user = userService.createUser(body);
            return new ResponseEntity<User>(user, HttpStatus.CREATED);
        }

        return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<User>> createUsersWithListInput(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema()) @Valid @RequestBody List<User> body) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            List<User> users = userService.createUsersWithListInput(body);
            return new ResponseEntity<List<User>>(users, HttpStatus.CREATED);
        }

        return new ResponseEntity<List<User>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteUser(@Parameter(in = ParameterIn.PATH, description = "The name that needs to be deleted", required = true, schema = @Schema()) @PathVariable("username") String username) {
        String accept = request.getHeader("accept");
        ModelApiResponse apiResponse = userService.deleteUser(username);
        return new ResponseEntity<Void>(HttpStatus.resolve(apiResponse.getCode()));
    }

    public ResponseEntity<User> getUserByName(@Parameter(in = ParameterIn.PATH, description = "The name that needs to be fetched. Use user1 for testing. ", required = true, schema = @Schema()) @PathVariable("username") String username) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            User user = userService.findByUserName(username);
            if(user==null){
                return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<User>(user,HttpStatus.OK);
        }

        return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<LoginResponseDTO> loginUser(@Parameter(in = ParameterIn.QUERY, description = "The user name for login", schema = @Schema()) @Valid @RequestParam(value = "username", required = false) String username, @Parameter(in = ParameterIn.QUERY, description = "The password for login in clear text", schema = @Schema()) @Valid @RequestParam(value = "password", required = false) String password) {
        String accept = request.getHeader(HeaderConstant.ACCEPT);
        if (accept != null && accept.contains("application/json")) {
            try {
                User user = userService.findByUserName(username);
                if(user==null){
                    return new ResponseEntity<LoginResponseDTO>(HttpStatus.NOT_FOUND);
                }
                if(passwordEncoder.encode(password).compareTo(user.getPassword())!=0){
                    return new ResponseEntity<LoginResponseDTO>(HttpStatus.UNAUTHORIZED);
                }
                LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
                loginResponseDTO.setUsername(username);
                String token = jwtUtils.generateToken(username);
                loginResponseDTO.setToken(token);
                return new ResponseEntity<LoginResponseDTO>(loginResponseDTO,HttpStatus.OK);
            } catch (DataAccessException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<LoginResponseDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<LoginResponseDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> logoutUser() {
        String accept = request.getHeader("accept");
        try {
            request.logout();
            return new ResponseEntity<Void>(HttpStatus.OK);
        }catch (ServletException e){
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> updateUser(@Parameter(in = ParameterIn.PATH, description = "name that need to be deleted", required = true, schema = @Schema()) @PathVariable("username") String username, @Parameter(in = ParameterIn.DEFAULT, description = "Update an existent user in the store", schema = @Schema()) @Valid @RequestBody User body) {
        String accept = request.getHeader("accept");
        ModelApiResponse apiResponse = userService.updateUser(username,body);
        return new ResponseEntity<Void>(HttpStatus.resolve(apiResponse.getCode()));
    }

}
