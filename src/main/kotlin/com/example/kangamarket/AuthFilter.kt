package com.example.kangamarket

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse


class AuthFilter : Filter {

    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse?,
        chain: FilterChain?
    ) {
        if (request is HttpServletRequest) {
            val header = request.getHeader("Authorization")
            if (request.method == "POST" && header != "Bearer ABC123") {
                (response as HttpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED)
                return
            }
        }
        chain?.doFilter(request, response)
    }
}