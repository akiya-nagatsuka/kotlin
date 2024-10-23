/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

@file:Suppress("TYPEALIAS_EXPANSION_DEPRECATION")

package org.jetbrains.kotlin.gradle.plugin

import org.gradle.api.Action
import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.attributes.AttributeContainer
import org.gradle.api.attributes.HasAttributes
import org.gradle.api.component.SoftwareComponent
import org.gradle.api.publish.maven.MavenPublication
import org.jetbrains.kotlin.gradle.PRESETS_API_IS_DEPRECATED_MESSAGE
import org.jetbrains.kotlin.gradle.DeprecatedTargetPresetApi
import org.jetbrains.kotlin.gradle.InternalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptionsDeprecated
import org.jetbrains.kotlin.gradle.dsl.KotlinGradlePluginDsl
import org.jetbrains.kotlin.tooling.core.HasMutableExtras

/**
 * Represents a target platform for which Kotlin code is built.
 *
 * This abstraction allows configuring tasks, dependencies, and other settings specific to the platform the code is intended to run on.
 *
 * By default, Kotlin target contains two [KotlinCompilations][KotlinCompilation] for [production][KotlinCompilation.MAIN_COMPILATION_NAME]
 * and [test][KotlinCompilation.TEST_COMPILATION_NAME] source codes.
 *
 * Examples of accessing the Kotlin target:
 *
 * - In Kotlin/JVM or Kotlin/Android projects:
 * ```
 * kotlin {
 *     target {
 *         // Configure JVM or Android target specifics here
 *     }
 * }
 * ```
 *
 * - In Kotlin/KMP projects:
 * ```
 * kotlin {
 *     jvm { // Creates JVM target
 *         // Configure JVM target specifics here
 *     }
 *
 *     linuxX64 {
 *         // Configure Kotlin native target for Linux X86_64 here
 *     }
 * }
 * ```
 *
 * To know more about targets in Kotlin check [this documentation](https://kotlinlang.org/docs/multiplatform-discover-project.html#targets).
 */
@KotlinGradlePluginDsl
interface KotlinTarget : Named, HasAttributes, HasProject, HasMutableExtras {

    /**
     * The name of the target in the Kotlin build configuration.
     */
    val targetName: String

    /**
     * Retrieves the disambiguation classifier for the Kotlin target.
     *
     * The disambiguation classifier can be used to distinguish between multiple Kotlin targets within the same project.
     * It is often applied as a prefix or suffix to generated names to avoid naming conflicts.
     */
    val disambiguationClassifier: String? get() = targetName

    /**
     * @suppress
     * Long deprecation cycle, because IDE might be calling into this via reflection.
     */
    @Deprecated("Scheduled for removal with Kotlin 2.2", level = DeprecationLevel.ERROR)
    val useDisambiguationClassifierAsSourceSetNamePrefix: Boolean

    /**
     * @suppress
     * Long deprecation cycle, because IDE might be calling into this via reflection.
     */
    @Deprecated("Scheduled for removal with Kotlin 2.2", level = DeprecationLevel.ERROR)
    val overrideDisambiguationClassifierOnIdeImport: String?

    /**
     * Represents the type of Kotlin platform associated with the target.
     */
    val platformType: KotlinPlatformType

    /**
     * A container for [Kotlin compilations][KotlinCompilation] related to this target.
     *
     * Allow accessing default [main][KotlinCompilation.MAIN_COMPILATION_NAME] or [test][KotlinCompilation.TEST_COMPILATION_NAME]
     * compilations or create any additional compilations.
     */
    val compilations: NamedDomainObjectContainer<out KotlinCompilation<KotlinCommonOptionsDeprecated>>

    /**
     * Name of the task responsible for assembling the final artifact for this target.
     */
    val artifactsTaskName: String

    /**
     * The name of the configuration that should be used when compiling against the API of this Kotlin target.
     *
     * This configuration is meant to be consumed by other components when they need to compile against it.
     */
    val apiElementsConfigurationName: String

    /**
     * The name of the configuration containing elements that are strictly required at runtime by this Kotlin target.
     *
     * Consumers of this configuration will get all the mandatory elements for this component to execute at runtime.
     */
    val runtimeElementsConfigurationName: String

    /**
     * The name of the configuration that represents the variant that carries the original source code in packaged form.
     *
     * Usually only needed for publishing.
     */
    val sourcesElementsConfigurationName: String

    /**
     * Indicates whether the Kotlin target is publishable.
     *
     * For example, target could have `false` value if it is not possible to compile into the target platform on the current host.
     */
    val publishable: Boolean

    /**
     * Configures publication of sources.
     *
     * @param publish Indicates whether the sources JAR should be published. Defaults to `true`.
     */
    fun withSourcesJar(publish: Boolean = true)

    /**
     * Represents a collection of Gradle [software components][SoftwareComponent] associated with this Kotlin target.
     *
     * **Note**: Returned [SoftwareComponent] potentially could be in not fully configured state (for example without some usages).
     * A fully configured state should be on Gradle execution state.
     */
    val components: Set<SoftwareComponent>

    /**
     * Configures the [Maven publication][MavenPublication] for this Kotlin target.
     */
    fun mavenPublication(action: MavenPublication.() -> Unit) = mavenPublication(Action { action(it) })

    /**
     * Configures the [Maven publication][MavenPublication] for this Kotlin target.
     */
    fun mavenPublication(action: Action<MavenPublication>)

    /**
     * Configures the attributes associated with this target.
     */
    fun attributes(configure: AttributeContainer.() -> Unit) = attributes.configure()

    /**
     * Configures the attributes associated with this target.
     */
    fun attributes(configure: Action<AttributeContainer>) = attributes { configure.execute(this) }

    /**
     * @suppress
     */
    @OptIn(DeprecatedTargetPresetApi::class, InternalKotlinGradlePluginApi::class)
    @get:Deprecated(
        PRESETS_API_IS_DEPRECATED_MESSAGE,
        level = DeprecationLevel.ERROR,
    )
    val preset: KotlinTargetPreset<out KotlinTarget>?

    /**
     * @suppress
     */
    override fun getName(): String = targetName

    /**
     * @suppress
     */
    @Deprecated(
        "Accessing 'sourceSets' container on the Kotlin target level DSL is deprecated. " +
                "Consider configuring 'sourceSets' on the Kotlin extension level.",
        level = DeprecationLevel.WARNING
    )
    val sourceSets: NamedDomainObjectContainer<KotlinSourceSet>
}

/**
 * @suppress TODO: KT-58858 add documentation
 */
interface KotlinTargetWithTests<E : KotlinExecution.ExecutionSource, T : KotlinTargetTestRun<E>> : KotlinTarget {
    /** The container with the test run executions.
     * A target may automatically create and configure a test run by the name [DEFAULT_TEST_RUN_NAME]. */
    val testRuns: NamedDomainObjectContainer<T>

    companion object {
        const val DEFAULT_TEST_RUN_NAME = "test"
    }
}
