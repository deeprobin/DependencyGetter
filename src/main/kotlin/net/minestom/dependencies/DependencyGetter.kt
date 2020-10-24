package net.minestom.dependencies

import net.minestom.dependencies.maven.MavenRepository
import net.minestom.dependencies.maven.MavenResolver
import java.io.File

class DependencyGetter {

    private val resolverList = mutableListOf<DependencyResolver>()
    val resolvers: List<DependencyResolver> get()= resolvers

    fun addResolver(resolver: DependencyResolver): DependencyGetter {
        resolverList += resolver
        return this
    }

    /**
     * Shorthand to add a MavenResolver with the given repositories
     */
    fun addMavenResolver(repositories: List<MavenRepository>) = addResolver(MavenResolver(repositories))

    fun get(id: String, targetFolder: File): ResolvedDependency {
        for(resolver in resolverList) {
            try {
                return resolver.resolve(id, targetFolder)
            } catch (e: UnresolvedDependencyException) {
                // silence and go to next resolver
            }
        }
        throw UnresolvedDependencyException("Could not find $id inside resolver list: ${resolverList.joinToString { it.toString() }}")
    }
}