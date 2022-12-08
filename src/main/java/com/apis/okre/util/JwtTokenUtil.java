package com.apis.okre.util;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.apis.okre.entity.User;
import com.apis.okre.mapper.UserMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 4550185165626007488L;

	@Autowired
	UserMapper userMapper;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Value("${jwt.token.validity}")
	private long jwtVal;

	@Value("${jwt.secret}")
	private String secret;

	@Value("${user.default.password}")
	private String pwd;

	@Value("${jwt.dev.mode}")
	private String devMode;
	
	@Value("${sso.key}")
	private String ssoKey;
	
	@Value("${sso.iv}")
	private String ssoIv;
	
	public String encryptAES(String data) throws Exception {

        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");   //参数分别代表 算法名称/加密模式/数据填充方式
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(ssoKey.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(ssoIv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            String ret =Base64.getEncoder().encodeToString(encrypted); 
            return ret;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptAES(String data) throws Exception {
        try {
            byte[] encrypted1 = Base64.getDecoder().decode(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NOPadding");
            SecretKeySpec keyspec = new SecretKeySpec(ssoKey.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(ssoIv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            String xml11pattern = "[^"
                    + "\u0001-\uD7FF"
                    + "\uE000-\uFFFD"
                    + "\ud800\udc00-\udbff\udfff"
                    + "]+";
            return originalString.replaceAll(xml11pattern, "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtVal * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String getEncodedDefaultPassword() {
		return bcryptEncoder.encode(pwd);
	}

	public User getUserDetailFromTokenPhone() {
		if (devMode.contains("product")) {
			try {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
				String username = userDetails.getUsername();
				User param = new User();
				if(username.contains("@")) {
					param.email = username;
				}else {
					param.phone = username;
				}
				List<User> userlist = userMapper.selectByFields(param);
				return userlist.get(0);
			}catch(Exception e) {
				return new User();
			}
		} else if (devMode.contains("test")) {
			User test = new User();
			test.id = 1L;
			test.user_company_id = 1L;
			test.user_role = "creator";
			test.user_password = bcryptEncoder.encode(pwd);
			test.user_dp_serial = ".1";
			test.user_post_address = "admin:e48fed99-19e9-45a8-8e9c-a198576ab7c8@virtual.account";
			return test;
		} else {
			User test = new User();
			return test;
		}
	}
}
