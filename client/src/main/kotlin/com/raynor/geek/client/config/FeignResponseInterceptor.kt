package com.raynor.geek.client.config

//@Component
//class FeignResponseInterceptor(
//) : ResponseInterceptor {
//
//    private val logger = LoggerFactory.getLogger(this::class.java)
//
//    override fun intercept(invocationContext: InvocationContext, chain: ResponseInterceptor.Chain): Any {
//        val response = invocationContext.response()
//        val endpoint = response.request().url()
//        val method = response.request().httpMethod()
//        val status = response.status()
//        logger.info("[$method] [$status] $endpoint")
//
//        return chain.next(invocationContext)
//    }
//}