package com.raynor.geek.llmservice.model

import com.raynor.geek.shared.enums.OllamaMyModel

data class OllamaLLMArgument(
    // See: https://github.com/ggerganov/llama.cpp/blob/master/examples/main/README.md
    val model: OllamaMyModel,

    /**
     * Indicates whether NUMA (Non-Uniform Memory Access) is enabled.
     */
    val useNUMA: Boolean? = null,

    /**
     * Sets the size of the context window used to generate the next token. (Default: 2048)
     */
    val numCtx: Int? = null,

    /**
     * Prompt processing maximum batch size. (Default: 512)
     */
    val numBatch: Int? = null,

    /**
     * The number of layers to send to the GPU(s). Defaults to -1 for dynamic configuration. (Default: -1)
     */
    val numGPU: Int? = null,

    /**
     * Specifies the primary GPU for small tensor computations. Defaults to GPU 0.
     */
    val mainGPU: Int? = null,

    /**
     * Enables low VRAM mode. (Default: false)
     */
    val lowVRAM: Boolean? = null,

    /**
     * Uses FP16 precision for key and value tensors. (Default: true)
     */
    val f16KV: Boolean? = null,

    /**
     * Returns logits for all tokens instead of just the last one. (Default: false)
     */
    val logitsAll: Boolean? = null,

    /**
     * Loads only the vocabulary without the model weights.
     */
    val vocabOnly: Boolean? = null,

    /**
     * Enables memory-mapped model loading. (Default: null)
     */
    val useMMap: Boolean? = null,

    /**
     * Locks the model in memory to prevent it from being swapped out. (Default: false)
     */
    val useMLock: Boolean? = null,

    /**
     * Number of threads to use during generation. Defaults to the number of physical CPU cores.
     */
    val numThread: Int? = null,

    /**
     * Number of tokens to keep in memory during generation. (Default: 4)
     */
    val numKeep: Int? = null,

    /**
     * Random number seed for reproducibility. (Default: -1)
     */
    val seed: Int? = null,

    /**
     * Maximum number of tokens to predict during generation. (Default: 128)
     */
    val numPredict: Int? = null,

    /**
     * Controls the diversity of generated tokens. (Default: 40)
     */
    val topK: Int? = null,

    /**
     * Controls diversity further using a cumulative probability threshold. (Default: 0.9)
     */
    val topP: Double? = null,

    /**
     * Reduces the impact of less probable tokens. (Default: 1)
     */
    val tfsZ: Float? = null,

    /**
     * Typical probability setting for sampling. (Default: 1.0)
     */
    val typicalP: Float? = null,

    /**
     * Prevents repetition by specifying how far back the model should look. (Default: 64)
     */
    val repeatLastN: Int? = null,

    /**
     * Controls creativity in model responses by adjusting temperature. (Default: 0.8)
     */
    val temperature: Double? = null,

    /**
     * Penalizes repeated tokens to reduce redundancy. (Default: 1.1)
     */
    val repeatPenalty: Double? = null,

    /**
     * Penalizes new tokens to manage generation focus. (Default: 0.0)
     */
    val presencePenalty: Double? = null,

    /**
     * Penalizes token frequency to reduce repetition. (Default: 0.0)
     */
    val frequencyPenalty: Double? = null,

    /**
     * Enables Mirostat sampling to control perplexity. (Default: 0)
     */
    val mirostat: Int? = null,

    /**
     * Controls balance between coherence and diversity. (Default: 5.0)
     */
    val mirostatTau: Float? = null,

    /**
     * Adjusts how quickly Mirostat responds to feedback. (Default: 0.1)
     */
    val mirostatEta: Float? = null,

    /**
     * Penalizes newline generation. (Default: true)
     */
    val penalizeNewline: Boolean? = null,

    /**
     * Specifies stop sequences to terminate generation.
     */
    // val stop: List<String>? = null,

    /**
     * Specifies the desired format of model output. Valid values are null or "json".
     */
    // val format: Any? = null,

    /**
     * Duration to keep the model loaded. Parsed using Go's ParseDuration.
     */
    val keepAlive: String? = null,

    /**
     * Truncates input to fit context length. Returns error if false and context length is exceeded. (Default: true)
     */
    val truncate: Boolean? = null
)
