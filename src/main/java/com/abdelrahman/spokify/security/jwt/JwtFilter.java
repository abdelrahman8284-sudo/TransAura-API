package com.abdelrahman.spokify.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String authHeader = request.getHeader("Authorization");
			String token = null ;
			String email =null ;
			
			if(authHeader!=null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
				email = jwtService.extractEmail(token);
			}
			if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				// دلوقت انا جبت ال userDetails جبت كل حاجة خاصة ب ال user موجودة في ال token 
				// الخطوة الجاية قبل ما اعمله authentication 
				// اني اتأكد من صحة البيانات اللي معايا زي اتأكد ان token valid , username هو صح وال password كمان صح
				if(jwtService.isTokenValid(token,userDetails)) {
					UsernamePasswordAuthenticationToken authToken =
							new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			filterChain.doFilter(request, response);

		}catch(Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		    response.setContentType("application/json"); // عشان يعرف إنه JSON
		    response.getWriter().write("{\"success\": false, \"message\": \"Invalid or expired token, you may login and try again!\"}");
		    return;
		}
	}

}