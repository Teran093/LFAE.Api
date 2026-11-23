package com.lfae.api.repositories;

import com.lfae.api.models.dto.LanguageDto;
import com.lfae.api.models.entities.LanguageTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LanguageTranslationRepository extends JpaRepository<LanguageTranslation, UUID> {

    @Query("""
        select new com.lfae.api.models.dto.LanguageDto(
            l.code, coalesce(t.name, l.code), l.active, l.isDefault)
        from Language l
        left join LanguageTranslation t
            on t.language = l and t.displayLanguage.code = :displayCode
        where (:onlyActives = false or l.active = true)
        order by coalesce(t.name, l.code)
        """)
    List<LanguageDto> findAllByDisplayLanguage(
            @Param("displayCode") String displayCode,
            @Param("onlyActives") boolean onlyActives);
}