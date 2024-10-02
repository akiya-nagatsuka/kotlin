/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.scopes.impl

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.resolve.ScopeSession
import org.jetbrains.kotlin.fir.resolve.ScopeSessionKey
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
import org.jetbrains.kotlin.fir.resolve.providers.symbolProvider
import org.jetbrains.kotlin.fir.resolve.scopeSessionKey
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor
import org.jetbrains.kotlin.fir.scopes.DelicateScopeAPI
import org.jetbrains.kotlin.fir.scopes.FirScope
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.utils.SmartList
import java.util.concurrent.atomic.AtomicLong

class FirPackageMemberScope(
    val fqName: FqName,
    val session: FirSession,
    private val symbolProvider: FirSymbolProvider = session.symbolProvider,
    private val excludedNames: Set<Name> = emptySet(),
) : FirScope() {
    private val classifierCache: MutableMap<Name, FirClassifierSymbol<*>?> = mutableMapOf()
    private val functionCache: MutableMap<Name, List<FirNamedFunctionSymbol>> = mutableMapOf()
    private val propertyCache: MutableMap<Name, List<FirPropertySymbol>> = mutableMapOf()

    // TODO (marco): IDEA: Only compute "names in package" sets for known SPECULATIVE accesses.
    //               We probably have a LOT of simple imports where class IDs are known to exist. For those, we do not need to compute
    //               "names in package" sets.
    private val classifierNames: Set<Name>? by lazy(LazyThreadSafetyMode.PUBLICATION) {
        val result = symbolProvider.symbolNamesProvider.getTopLevelClassifierNamesInPackage(fqName)
//        if (result == null) {
//            nullClassifierNamesCounter.incrementAndGet()
//        } else {
//            existingClassifierNamesCounter.incrementAndGet()
//        }
        result
    }

    override fun processClassifiersByNameWithSubstitution(
        name: Name,
        processor: (FirClassifierSymbol<*>, ConeSubstitutor) -> Unit
    ) {
        if (name.asString().isEmpty()) return
        if (name in excludedNames) return

        val classifierNames = classifierNames
        if (classifierNames != null && name !in classifierNames) return

        val symbol = classifierCache.getOrPut(name) {
            val unambiguousFqName = ClassId(fqName, name)
            symbolProvider.getClassLikeSymbolByClassId(unambiguousFqName)
        }

        if (symbol != null) {
            processor(symbol, ConeSubstitutor.Empty)
        }
    }

    override fun processFunctionsByName(name: Name, processor: (FirNamedFunctionSymbol) -> Unit) {
        if (name in excludedNames) return

        val symbols = functionCache.getOrPut(name) {
            symbolProvider.getTopLevelFunctionSymbols(fqName, name)
        }
        for (symbol in symbols) {
            processor(symbol)
        }
    }

    override fun processPropertiesByName(name: Name, processor: (FirVariableSymbol<*>) -> Unit) {
        if (name in excludedNames) return

        val symbols = propertyCache.getOrPut(name) {
            symbolProvider.getTopLevelPropertySymbols(fqName, name)
        }
        for (symbol in symbols) {
            processor(symbol)
        }
    }

    override val scopeOwnerLookupNames: List<String> = SmartList(fqName.asString())

    @DelicateScopeAPI
    override fun withReplacedSessionOrNull(newSession: FirSession, newScopeSession: ScopeSession): FirPackageMemberScope {
        return FirPackageMemberScope(fqName, newSession, excludedNames = excludedNames)
    }

    companion object {
//        private val nullClassifierNamesCounter = AtomicLong()
//        private val existingClassifierNamesCounter = AtomicLong()
    }
}

val PACKAGE_MEMBER: ScopeSessionKey<Pair<FqName, FirSession>, FirPackageMemberScope> = scopeSessionKey()
