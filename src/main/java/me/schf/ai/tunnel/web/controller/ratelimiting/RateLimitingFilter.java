package me.schf.ai.tunnel.web.controller.ratelimiting;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

	private final Map<String, Bucket> bucketsByIp = new ConcurrentHashMap<>();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String clientIp = request.getRemoteAddr();
		Bucket bucket = bucketsByIp.computeIfAbsent(clientIp, this::newBucket);

		if (bucket.tryConsume(1)) {
			filterChain.doFilter(request, response);
		} else {
			response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			response.getWriter().write("You're doing that too much! Try again later.");
		}
	}

	private Bucket newBucket(String ip) {
		Bandwidth limit = Bandwidth.builder()
				.capacity(10)
				.refillIntervally(10, Duration.ofSeconds(10))
				.build();
		return Bucket.builder().addLimit(limit).build();
	}

}
