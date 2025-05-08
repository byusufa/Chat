package uz.pdp.chat.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.chat.entity.User;

import java.io.IOException;
import java.util.List;

@Component
public class Filter extends OncePerRequestFilter {
    List<String> openPages = List.of(
            "/login",
            "/auth/login",
            "/auth/resetPassword",
            "/auth/reset-password"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if (openPages.contains(url)) {
            filterChain.doFilter(request, response);
            return;

        } else {
            User user = (User) request.getSession().getAttribute("currentUser");
            if (user != null) {
                filterChain.doFilter(request, response);

            } else {
                response.sendRedirect("/login");
            }
        }

//        careful or put  return above

    }
}
