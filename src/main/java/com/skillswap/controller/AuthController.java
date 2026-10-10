package com.skillswap.controller;
import com.skillswap.model.User; import com.skillswap.service.AuthService; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/auth") public class AuthController {private final AuthService auth; public AuthController(AuthService a){auth=a;} @PostMapping("/register") public Map<String,Object> register(@RequestBody User u){return auth.register(u);} @PostMapping("/login") public Map<String,Object> login(@RequestBody Map<String,String> x){return auth.login(x.get("email"),x.get("password"),false);} }
