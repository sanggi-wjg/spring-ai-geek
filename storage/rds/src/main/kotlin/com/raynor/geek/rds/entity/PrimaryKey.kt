package com.raynor.geek.rds.entity

import com.github.f4b6a3.ulid.UlidCreator
import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.util.*
import kotlin.jvm.Transient

@MappedSuperclass
abstract class PrimaryKey : Persistable<UUID>, Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private val id: UUID = UlidCreator.getMonotonicUlid().toUuid()

    @Transient
    private var isNew = true

    override fun getId() = id

    fun getIdString() = id.toString()

    override fun isNew() = isNew

    override fun hashCode() = Objects.hashCode(id)

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is HibernateProxy && other !is PrimaryKey) return false
        return id == getIdentifier(other)
    }

    @PostPersist
    @PostLoad
    protected fun load() {
        isNew = false
    }

    private fun getIdentifier(obj: Any): Any {
        return if (obj is HibernateProxy) {
            obj.hibernateLazyInitializer.identifier
        } else {
            (obj as PrimaryKey).id
        }
    }
}