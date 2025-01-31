package com.raynor.geek.client.config

//@Component
//class FeignRequestInterceptor : RequestInterceptor {
//
//    private val logger = LoggerFactory.getLogger(this::class.java)
//
//    override fun apply(template: RequestTemplate) {
//        val endpoint = template.feignTarget().url() + template.url()
//        val method = template.method()
//        val request = if (template.method() == HttpMethod.GET.name) {
//            template.queries()
//        } else {
//            template.body()?.toString() ?: ""
//        }
//        logger.info("[$method] $endpoint $request")
//    }
//}