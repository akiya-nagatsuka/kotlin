/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.java

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.caches.FirCache
import org.jetbrains.kotlin.fir.caches.firCachesFactory
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProviderWithoutCallables
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderInternals
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol
import org.jetbrains.kotlin.load.java.structure.JavaClass
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.utils.mapToSetOrEmpty
import java.util.concurrent.atomic.AtomicLong

// This symbol provider only loads JVM classes *not* annotated with Kotlin `@Metadata` annotation.
// Use it in application sessions for loading classes from Java files listed on the command line.
// For library and incremental compilation sessions use `KotlinDeserializedJvmSymbolsProvider`
// in order to load Kotlin classes as well.
//Also used in IDE for loading java classes separately from stub based kotlin classes
open class JavaSymbolProvider(
    session: FirSession,
    override val javaFacade: FirJavaFacade,
) : FirSymbolProvider(session), FirJavaAwareSymbolProvider {
    private class ClassCacheContext(
        val parentClassSymbol: FirRegularClassSymbol? = null,
        val foundJavaClass: JavaClass? = null,
    )

    private val classCache: FirCache<ClassId, FirRegularClassSymbol?, ClassCacheContext?> =
        session.firCachesFactory.createCache createValue@{ classId, context ->
            val javaClass = context?.foundJavaClass ?: javaFacade.findClass(classId) ?: return@createValue null
            val symbol = FirRegularClassSymbol(classId)
            javaFacade.convertJavaClassToFir(symbol, context?.parentClassSymbol, javaClass)
            symbol
        }

    override fun getClassLikeSymbolByClassId(classId: ClassId): FirRegularClassSymbol? {
//        if (getPackage(classId.packageFqName) == null) {
//            rejectedByPackageCounter.incrementAndGet()
//            return null
//        }
//
//        if (!javaFacade.hasTopLevelClassOf(classId)) {
//            rejectedByClassNameCounter.incrementAndGet()
//            return null
//        }
//
//        acceptedCounter.incrementAndGet()

        if (getPackage(classId.packageFqName) == null) {
            return null
        }
        return getClassLikeSymbolByClassId(classId, null)
    }

    fun getClassLikeSymbolByClassId(classId: ClassId, javaClass: JavaClass?): FirRegularClassSymbol? =
        classCache.getValue(
            classId,
            ClassCacheContext(
                parentClassSymbol = classId.outerClassId?.let { getClassLikeSymbolByClassId(it, null) },
                foundJavaClass = javaClass,
            )
        )

    @OptIn(FirSymbolProviderInternals::class)
    override fun getTopLevelCallableSymbolsTo(destination: MutableList<FirCallableSymbol<*>>, packageFqName: FqName, name: Name) {}

    @OptIn(FirSymbolProviderInternals::class)
    override fun getTopLevelFunctionSymbolsTo(destination: MutableList<FirNamedFunctionSymbol>, packageFqName: FqName, name: Name) {}

    @OptIn(FirSymbolProviderInternals::class)
    override fun getTopLevelPropertySymbolsTo(destination: MutableList<FirPropertySymbol>, packageFqName: FqName, name: Name) {}

    override fun getPackage(fqName: FqName): FqName? = javaFacade.getPackage(fqName)

    override val symbolNamesProvider: FirSymbolNamesProvider = object : FirSymbolNamesProviderWithoutCallables() {
        override val hasSpecificClassifierPackageNamesComputation: Boolean get() = false

        override fun getTopLevelClassifierNamesInPackage(packageFqName: FqName): Set<Name>? =
            javaFacade.knownClassNamesInPackage(packageFqName)?.mapToSetOrEmpty { Name.identifier(it) }
    }

    companion object {
//        private var rejectedByPackageCounter = AtomicLong()
//        private var rejectedByClassNameCounter = AtomicLong()
//        private var acceptedCounter = AtomicLong()
    }
}

val FirSession.javaSymbolProvider: JavaSymbolProvider? by FirSession.nullableSessionComponentAccessor()
