package com.raynor.geek.llmservice.config

import com.raynor.geek.client.naver.NaverOpenClient
import com.raynor.geek.client.naver.dto.NaverNewsResponseDto
import com.raynor.geek.client.naver.dto.NaverSearchResponseDto
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Description

@Configuration
class ToolConfig(
    private val naverOpenClient: NaverOpenClient,
) {
    companion object {
        const val TOOL_WEATHER = "currentWeather"
        const val TOOL_SEARCH_WEB = "searchWeb"
        const val TOOL_SEARCH_NEWS = "searchNews"
    }

    @Bean(TOOL_WEATHER)
    @Description("Get weather information in location")
    fun currentWeather(): (WeatherRequest) -> WeatherResponse = MockWeatherTool()

    @Bean(TOOL_SEARCH_WEB)
    @Description("If you need realtime information, use this tool. You can get a web documents.")
    fun searchWeb(): Function1<SearchRequest, NaverSearchResponseDto> {
        return { request: SearchRequest ->
            naverOpenClient.searchWeb(request.query).getOrThrow()
        }
    }

    @Bean(TOOL_SEARCH_NEWS)
    @Description(
        "If you need recent news, use this tool. You can get a news articles." +
                "검색 키워드(2~5개의 핵심 단어 조합). 예시: 'AI 기술 발전', '주식 시장 동향'"
    )
    fun searchNews(): Function1<SearchRequest, NaverNewsResponseDto> {
        return { request: SearchRequest ->
            naverOpenClient.searchNews(request.query).getOrThrow()
        }
    }
}

class MockWeatherTool : Function1<WeatherRequest, WeatherResponse> {
    override fun invoke(request: WeatherRequest) = WeatherResponse(28.0, WeatherUnit.C)
}

enum class WeatherUnit { C, F }

data class WeatherRequest(
    val location: String,
    val unit: WeatherUnit,
)

data class WeatherResponse(
    val temp: Double,
    val unit: WeatherUnit,
)

data class SearchRequest(
    val query: String,
)

