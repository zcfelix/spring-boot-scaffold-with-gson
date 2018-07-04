package com.cmb.lf65.ams.support;

import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
@Profile("test")
public class TruncateDatabaseService {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void truncate() {
        Session session = entityManager.unwrap(Session.class);

        final Metamodel metamodel = session.getSessionFactory().getMetamodel();

        final List<String> tableNames = metamodel
                .getManagedTypes()
                .stream()
                .filter(Objects::nonNull)
                .map(x -> x.getJavaType().getAnnotation(Table.class).name())
                .collect(toList());
        entityManager.flush();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
        tableNames.forEach(tableName -> entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate());
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

}
