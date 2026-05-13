package com.github.gitworktree

import java.util.Properties
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class GitWorktreeBundleTest {

    @Test
    fun `localized bundles keep the same keys`() {
        val defaultBundle = loadProperties("messages/GitWorktreeBundle.properties")
        val chineseBundle = loadProperties("messages/GitWorktreeBundle_zh_CN.properties")

        assertEquals(defaultBundle.keys, chineseBundle.keys)
    }

    @Test
    fun `default bundle is English fallback`() {
        val defaultBundle = loadProperties("messages/GitWorktreeBundle.properties")

        defaultBundle.forEach { key, value ->
            assertFalse(
                HAN_CHARACTER_REGEX.containsMatchIn(value.toString()),
                "Default bundle value for $key should not contain Chinese text",
            )
        }
    }

    private fun loadProperties(path: String): Properties {
        val stream = javaClass.classLoader.getResourceAsStream(path)
        assertNotNull(stream, "Missing resource: $path")
        return stream.use {
            Properties().apply { load(it.reader(Charsets.UTF_8)) }
        }
    }

    companion object {
        private val HAN_CHARACTER_REGEX = Regex("\\p{IsHan}")
    }
}
